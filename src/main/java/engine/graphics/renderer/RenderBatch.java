package engine.graphics.renderer;

import engine.graphics.Primitive;
import engine.graphics.ShaderDatatype;
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
    /** Amount of floats/ints in a single vertex */
    private int vertexCount;
    /** Amount of bytes for a single vertex */
    private int vertexSizeBytes;

    private final ShaderDatatype[] attributes;
    private final Primitive primitive;

    private float[] vertices;

    private List<Texture> textures;

    private int vaoID, vboID, eboID;
    private int maxBatchSize;
    private boolean hasRoom;
    public int dataOffset;
    private int textureIndex;

    private int zIndex;

    public RenderBatch(int maxBatchSize, int zIndex, Primitive primitive, ShaderDatatype... attributes) {
        this.zIndex = zIndex;
        this.maxBatchSize = maxBatchSize;
        this.primitive = primitive;
        this.attributes = attributes;

        for (ShaderDatatype s : attributes) {
            vertexCount += s.count;
            vertexSizeBytes += s.sizeInBytes;
        }
        // 4 vertices quads
        vertices = new float[maxBatchSize * primitive.vertexCount * vertexCount];

        textureIndex = 0;
        dataOffset = 0;
        this.hasRoom = true;
        this.textures = new ArrayList<>();
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
        glBufferData(GL_ARRAY_BUFFER, (long) maxBatchSize * primitive.vertexCount * vertexSizeBytes, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, generateIndices(), GL_STATIC_DRAW);

        // Enable the buffer attribute pointers (telling how our vertex is built)
        int currentOffset = 0;
        for (int i = 0; i < attributes.length; i++) {
            ShaderDatatype attribute = attributes[i];
            glVertexAttribPointer(i, attribute.count, attribute.openglType, false, vertexSizeBytes, currentOffset);
            glEnableVertexAttribArray(i);
            currentOffset += attribute.sizeInBytes;
        }
    }

    public void start() {
        dataOffset = 0;
        textureIndex = 0;
        textures.clear();
        hasRoom = true;
    }


    /**
     * Finish setting batch data. upload to gpu
     */
    public void finish() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
    }

    public int addTexture(Texture texture) {
        int texIndex;
        if (texture == null) return 0;
        if (textures.contains(texture)) {
            texIndex = textures.indexOf(texture) + 1;
        } else {
            textures.add(texture);
            texIndex = textures.size();
        }
        return texIndex;
    }

    public void bind() {
        glBindVertexArray(vaoID);
        for (int i = 0; i < textures.size(); i++) {
            textures.get(i).bindToSlot(i + 1);
        }
    }

    public void unbind() {
        for (Texture texture : textures) {
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
        if (dataOffset % vertexCount != 0) {
            assert false : "A renderer seems to not have the correct amount of vertices";
        }
        return (dataOffset * primitive.elementCount) / (vertexCount * primitive.vertexCount);
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
        return this.hasRoom;
    }

    public boolean hasTextureRoom() {
        return this.textures.size() < 8;
    }

    public boolean hasTexture(Texture tex) {
        return this.textures.contains(tex);
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
        if (dataOffset >= vertices.length) {
            hasRoom = false;
        }
    }

    public void pushFloat(float f) {
        vertices[dataOffset++] = f;
        checkFullness();
    }

    public void pushInt(int i) {
        vertices[dataOffset++] = i;
        checkFullness();
    }

    public void pushVec2(float x, float y) {
        vertices[dataOffset++] = x;
        vertices[dataOffset++] = y;
        checkFullness();
    }

    public void pushVec2(Vector2f vec) {
        vertices[dataOffset++] = vec.x;
        vertices[dataOffset++] = vec.y;
        checkFullness();
    }

    public void pushVec3(float x, float y, float z) {
        vertices[dataOffset++] = x;
        vertices[dataOffset++] = y;
        vertices[dataOffset++] = z;
        checkFullness();
    }

    public void pushVec3(Vector3f vec) {
        vertices[dataOffset++] = vec.x;
        vertices[dataOffset++] = vec.y;
        vertices[dataOffset++] = vec.z;
        checkFullness();
    }

    public void pushVec4(float x, float y, float z, float w) {
        vertices[dataOffset++] = x;
        vertices[dataOffset++] = y;
        vertices[dataOffset++] = z;
        vertices[dataOffset++] = w;
        checkFullness();
    }

    public void pushVec4(Vector4f vec) {
        vertices[dataOffset++] = vec.x;
        vertices[dataOffset++] = vec.y;
        vertices[dataOffset++] = vec.z;
        vertices[dataOffset++] = vec.w;
        checkFullness();
    }
}
