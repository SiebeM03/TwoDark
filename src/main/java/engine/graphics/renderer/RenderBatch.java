package engine.graphics.renderer;

import engine.ecs.components.SpriteRenderer;
import engine.graphics.Shader;
import engine.graphics.Window;
import engine.ui.MouseEventConsumer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import testGame.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * A RenderBatch is a collection of sprites that are rendered together. This is done to reduce the number of draw calls.
 * The RenderBatch is sorted by zIndex and then rendered.
 */
public class RenderBatch implements Comparable<RenderBatch> {
    // Vertex
    // ======
    // Pos              Color                       Tex Coords      Tex Id
    // float, float,    float, float, float, float, float, float,   float
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int TEX_COORDS_SIZE = 2;
    private final int TEX_ID_SIZE = 1;
    private final int ENTITY_ID_SIZE = 1;
    private final int COOLDOWN_SIZE = 1;
    private final int VERTEX_SIZE = POS_SIZE + COLOR_SIZE + TEX_COORDS_SIZE + TEX_ID_SIZE + ENTITY_ID_SIZE + COOLDOWN_SIZE;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
    private final int ENTITY_ID_OFFSET = TEX_ID_OFFSET + TEX_ID_SIZE * Float.BYTES;
    private final int COOLDOWN_OFFSET = ENTITY_ID_OFFSET + ENTITY_ID_SIZE * Float.BYTES;

    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};

    private List<Texture> textures;
    private int vaoID, vboID;
    private int maxBatchSize;
    private int zIndex;

    public RenderBatch(int maxBatchSize, int zIndex) {
        this.zIndex = zIndex;
        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        // 4 vertices quads
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

        this.numSprites = 0;
        this.hasRoom = true;
        this.textures = new ArrayList<>();
    }

    /**
     * Start the render batch. This will create the VAO and VBO and upload the necessary data to the GPU.
     * This method should be called right after creation.
     */
    public void start() {
        // Tell GPU to give us enough space for doing all this
        // Generate and bind VAO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Enable the buffer attribute pointers (telling how our vertex is built)
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);

        glVertexAttribPointer(4, ENTITY_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, ENTITY_ID_OFFSET);
        glEnableVertexAttribArray(4);

        glVertexAttribPointer(5, COOLDOWN_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COOLDOWN_OFFSET);
        glEnableVertexAttribArray(5);
    }


    /**
     * Add a sprite to the render batch. This will add the sprite to the local array {@link #sprites} and update the {@link #vertices} array.
     * <p>This method will also add the sprite's texture (if it has one) to the {@link #textures} list.</p>
     * <p>If the batch is full, {@link #hasRoom} will be set to false.</p>
     */
    public void addSprite(SpriteRenderer spr) {
        // Get index and add renderObject
        int index = this.numSprites;
        this.sprites[index] = spr;
        this.numSprites++;

        if (spr.getTexture() != null) {
            if (!textures.contains(spr.getTexture())) {
                textures.add(spr.getTexture());
            }
        }

        // Add properties to local vertices array
        loadVertexProperties(index);

        if (numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }


    /**
     * Remove a sprite from the render batch. This will remove the sprite from the local array {@link #sprites}.
     * <p>If the sprite was found, all elements to the right of the sprite will be shifted to the left.</p>
     */
    public void removeSprite(SpriteRenderer sprite) {
        int index = -1;
        for (int i = 0; i < numSprites; i++) {
            if (sprites[i].equals(sprite)) {
                index = i;
                sprites[i] = null;
                break;
            }
        }

        // Return if sprite was not found
        if (index == -1) {
            return;
        }

        // Shift all elements to the left
        for (int i = index; i < numSprites - 1; i++) {
            sprites[i] = sprites[i + 1];
        }

        numSprites--;
    }


    /**
     * This method will render all sprites in the batch. It will also check if any sprite is dirty and update the vertex properties if necessary, if one or more sprites are dirty the data will be rebuffered.
     */
    public void render() {
        boolean rebufferData = false;
        for (int i = 0; i < numSprites; i++) {
            SpriteRenderer spr = sprites[i];
            if (spr.isDirty()) {
                loadVertexProperties(i);
                spr.setClean();
                rebufferData = true;
            }
        }
        if (rebufferData) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        // Use shader
        Shader shader = Renderer.getBoundShader();
        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }
        shader.uploadIntArray("uTextures", texSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        for (int i = 0; i < textures.size(); i++) {
            textures.get(i).unbind();
        }
        shader.detach();
    }

    /**
     * This method will load the vertex properties for a sprite at a given index.
     */
    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.sprites[index];

        // Find offset within array (4 vertices per sprite)
        int offset = index * 4 * VERTEX_SIZE;   // See loadElementIndices() for more explanation

        Vector4f color = sprite.getColor();
        Vector2f[] texCoords = sprite.getTexCoords();

        int texId = 0;
        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i).equals(sprite.getTexture())) {
                    texId = i + 1;  // +1 because 0 is reserved for the default texture (just a color: [0, tex, tex, tex])
                    break;
                }
            }
        }

        // Add vertices with the appropriate properties
        float xAdd = 1.0f;
        float yAdd = 1.0f;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 0.0f;
            } else if (i == 3) {
                yAdd = 1.0f;
            }

            // Load position
            vertices[offset] = sprite.gameObject.transform.position.x + (xAdd * sprite.gameObject.transform.scale.x);
            vertices[offset + 1] = sprite.gameObject.transform.position.y + (yAdd * sprite.gameObject.transform.scale.y);

            // Load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            // Load texture coordinates
            vertices[offset + 6] = texCoords[i].x;
            vertices[offset + 7] = texCoords[i].y;

            // Load texture id
            vertices[offset + 8] = texId;

            // Load entity id
            vertices[offset + 9] = sprite.gameObject.getUid() + 1;  // uid 0 will be used to represent an invalid object

            // Load cooldown value
            MouseEventConsumer mouseEventConsumer = sprite.gameObject.getComponent(MouseEventConsumer.class);
            if (mouseEventConsumer != null) {
                vertices[offset + 10] = Math.min(1.0f, mouseEventConsumer.clickDelayTimer() / mouseEventConsumer.clickDelay());
            }

            offset += VERTEX_SIZE;
        }
    }

    /**
     * This method will generate the indices for the batch by calling {@link #loadElementIndices(int[], int)} for each sprite in the batch.
     */
    private int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[6 * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }

        return elements;
    }

    /**
     * This method will load the element indices for a sprite at a given index. The indices will be loaded into the elements array at the correct offset.
     * <p>Each sprite will have 6 indices (2 triangles).</p>
     *
     * @param elements the array to load the indices into
     * @param index    the index of the sprite
     */
    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        // 4 comes from the fact that every quad uses 4 vertices
        // For triangle 1 it's 0, 1, 2, 3
        // For triangle 2 it's 4, 5, 6, 7 (vertices from triangle 1 + (4 * index) with index = 1)
        int offset = 4 * index;

        // 3, 2, 0, 0, 2, 1        7, 6, 4, 4, 6, 5
        // Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        // Triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
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

    /**
     * Compare this RenderBatch to another RenderBatch based on their {@link #zIndex}.
     */
    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex());
    }
}
