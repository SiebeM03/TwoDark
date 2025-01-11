package old.engine.graphics.renderer;

import old.engine.graphics.Primitive;
import old.engine.graphics.ShaderDatatype;
import old.engine.util.Color;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * A RenderBatch is a collection of sprites that are rendered together. This is done to reduce the number of draw calls.
 * The RenderBatch is sorted by zIndex and then rendered.
 */
public class RenderBatch implements Comparable<RenderBatch> {
    /** Number of attributes (int or float, e.g., positions, colors, UVs) per vertex. */
    private int attributesPerVertex;
    /** Size of a single vertex in bytes. */
    private int bytesPerVertex;

    /** Array of shader data types that define the structure of a vertex. */
    private final ShaderDatatype[] attributes;
    /** Type of primitive this batch will render (e.g., TRIANGLES, QUADS). */
    private final Primitive primitive;

    /** Buffer for storing vertex data to be sent to the GPU. */
    public float[] vertexDataBuffer;

    /** List of textures currently bound to this batch. */
    private List<Texture> boundTextures;

    private int vaoID, vboID, eboID;
    private int maxBatchSize;
    private boolean hasVertexRoom;
    public int dataOffset;
    private int textureIndex;

    private int zIndex;

    public RenderBatch(int maxBatchSize, int zIndex, Primitive primitive, ShaderDatatype... attributes) {
        this.zIndex = zIndex;
        this.maxBatchSize = maxBatchSize;
        this.primitive = primitive;
        this.attributes = attributes;

        for (ShaderDatatype s : attributes) {
            attributesPerVertex += s.count;
            bytesPerVertex += s.sizeInBytes;
        }
        // 4 vertices quads
        vertexDataBuffer = new float[maxBatchSize * primitive.vertexCount * attributesPerVertex];

        textureIndex = 0;
        dataOffset = 0;
        this.hasVertexRoom = true;
        this.boundTextures = new ArrayList<>();
    }

    /**
     * Create the GPU resources.
     * Generates a vao, a dynamic vbo, and a static buffer of indices.
     */
    public void init() {
        // Tell GPU to give us enough space for doing all this
        // Generate and bind VAO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) maxBatchSize * primitive.vertexCount * bytesPerVertex, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, generateIndices(), GL_STATIC_DRAW);

        // Enable the buffer attribute pointers (telling how our vertex is built)
        int currentOffset = 0;
        for (int i = 0; i < attributes.length; i++) {
            ShaderDatatype attribute = attributes[i];
            glVertexAttribPointer(i, attribute.count, attribute.openglType, false, bytesPerVertex, currentOffset);
            glEnableVertexAttribArray(i);
            currentOffset += attribute.sizeInBytes;
        }
    }

    public void start() {
        dataOffset = 0;
        textureIndex = 0;
        boundTextures.clear();
        hasVertexRoom = true;
    }


    /**
     * Finish setting batch data. upload to gpu
     */
    public void finish() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexDataBuffer);
    }

    public int addTexture(Texture texture) {
        int texIndex;
        if (texture == null) return 0;
        if (boundTextures.contains(texture)) {
            texIndex = boundTextures.indexOf(texture) + 1;
        } else {
            boundTextures.add(texture);
            texIndex = ++textureIndex;
        }
        return texIndex;
    }

    public void bind() {
        glBindVertexArray(vaoID);
        for (int i = 0; i < boundTextures.size(); i++) {
            boundTextures.get(i).bindToSlot(i + 1);
        }
    }

    public void unbind() {
        for (Texture texture : boundTextures) {
            texture.unbind();
        }
        glBindVertexArray(0);
    }


    /**
     * Get the number of vertices to be drawn
     *
     * @return the number of vertices to be drawn
     */
    public int getVertexCount() {
        // Safety check (dataOffset should be a multiple of vertexCount)
        if (dataOffset % attributesPerVertex != 0) {
            assert false : "A renderer seems to not have the correct amount of vertices";
        }
        return (dataOffset * primitive.elementCount) / (attributesPerVertex * primitive.vertexCount);
    }

    /**
     * This method will generate the indices for the batch by calling {@link Primitive#elementCreation} for each sprite in the batch.
     */
    private int[] generateIndices() {
        int[] elements = new int[primitive.elementCount * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            primitive.elementCreation.accept(elements, i);
        }

        return elements;
    }

    public boolean hasRoom() {
        return this.hasVertexRoom;
    }

    public boolean hasTextureRoom() {
        return this.boundTextures.size() < 8;
    }

    public boolean hasTexture(Texture tex) {
        return this.boundTextures.contains(tex);
    }

    public int zIndex() {
        return this.zIndex;
    }

    public Primitive primitive() {
        return this.primitive;
    }

    /**
     * Compare this RenderBatch to another RenderBatch based on their {@link #zIndex}.
     */
    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex());
    }

    private void checkFullness() {
        if (dataOffset >= vertexDataBuffer.length) {
            hasVertexRoom = false;
        }
    }

    public void pushFloat(float f) {
        vertexDataBuffer[dataOffset++] = f;
        checkFullness();
    }

    public void pushInt(int i) {
        vertexDataBuffer[dataOffset++] = i;
        checkFullness();
    }

    public void pushVec2(float x, float y) {
        vertexDataBuffer[dataOffset++] = x;
        vertexDataBuffer[dataOffset++] = y;
        checkFullness();
    }

    public void pushVec2(Vector2f vec) {
        vertexDataBuffer[dataOffset++] = vec.x;
        vertexDataBuffer[dataOffset++] = vec.y;
        checkFullness();
    }

    public void pushVec3(float x, float y, float z) {
        vertexDataBuffer[dataOffset++] = x;
        vertexDataBuffer[dataOffset++] = y;
        vertexDataBuffer[dataOffset++] = z;
        checkFullness();
    }

    public void pushVec3(Vector3f vec) {
        vertexDataBuffer[dataOffset++] = vec.x;
        vertexDataBuffer[dataOffset++] = vec.y;
        vertexDataBuffer[dataOffset++] = vec.z;
        checkFullness();
    }

    public void pushVec4(float x, float y, float z, float w) {
        vertexDataBuffer[dataOffset++] = x;
        vertexDataBuffer[dataOffset++] = y;
        vertexDataBuffer[dataOffset++] = z;
        vertexDataBuffer[dataOffset++] = w;
        checkFullness();
    }

    public void pushVec4(Vector4f vec) {
        vertexDataBuffer[dataOffset++] = vec.x;
        vertexDataBuffer[dataOffset++] = vec.y;
        vertexDataBuffer[dataOffset++] = vec.z;
        vertexDataBuffer[dataOffset++] = vec.w;
        checkFullness();
    }

    public void pushColor(Color color) {
        pushVec4(color.r(), color.g(), color.b(), color.a());
    }
}
