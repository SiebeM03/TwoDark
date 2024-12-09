package engine.graphics.renderer;

import engine.ecs.GameObject;
import engine.ecs.components.SpriteRenderer;
import engine.editor.PickingTexture;
import engine.graphics.Primitive;
import engine.graphics.Shader;
import engine.graphics.ShaderDatatype;
import engine.graphics.Window;
import engine.util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class PickingRenderer extends Renderer {
    protected final int MAX_BATCH_SIZE = 1000;
    public static PickingTexture pickingTexture;

    private List<SpriteRenderer> sprites;

    public PickingRenderer() {
        sprites = new ArrayList<>();
    }

    @Override
    public Shader createShader() {
        return AssetPool.getShader("assets/shaders/pickingShader.glsl");
    }

    @Override
    protected Framebuffer createFramebuffer() {
        framebuffer = new Framebuffer(Window.getWidth(), Window.getHeight());
        return framebuffer;
    }

    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Primitive.QUAD,
                ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT4, ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT, ShaderDatatype.INT);
    }

    @Override
    protected void rebuffer() {
        for (SpriteRenderer sprite : sprites) {
            RenderBatch batch = getAvailableBatch(sprite.getTexture(), sprite.gameObject.zIndex());

            Vector2f pos = sprite.gameObject.transform.position;
            Vector2f scale = sprite.gameObject.transform.scale;
            Vector2f[] texCoords = sprite.getTexCoords();

            int texID;
            if (sprite.getTexture() != null) {
                texID = batch.addTexture(sprite.getTexture());
            } else {
                texID = 0;
            }

            // Push vertices to the batchfloat xAdd = 1.0f;
            float xAdd = 1.0f;
            float yAdd = 1.0f;
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 1 -> yAdd = 0.0f;
                    case 2 -> xAdd = 0.0f;
                    case 3 -> yAdd = 1.0f;
                }

                float scaledX = (xAdd * scale.x);
                float scaledY = (yAdd * scale.y);

                // Load position
                batch.pushVec2(pos.x + scaledX, pos.y + scaledY);

                // Load color
                batch.pushVec4(sprite.getColor());

                // Load texture coordinates
                batch.pushVec2(texCoords[i]);

                // Load texture id
                batch.pushInt(texID);

                // Load entity id
                batch.pushInt(sprite.gameObject.getUid() + 1);
            }
        }
    }

    @Override
    public void add(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            sprites.add(spr);
        }
    }

    @Override
    public void remove(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            sprites.remove(spr);
        }
    }

    @Override
    protected void uploadUniforms(Shader shader) {
        shader.uploadIntArray("uTextures", textureSlots);

        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
    }

    @Override
    public void render() {
        System.out.println("rendering fbo " + framebuffer.getFboID());
        framebuffer.bind();
        currentShader.use();
        uploadUniforms(currentShader);

        glDisable(GL_BLEND);
        pickingTexture.enableWriting();
        glViewport(0, 0, Window.getWidth(), Window.getHeight());
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        start();
        rebuffer();
        finish();

        for (RenderBatch batch : batches) {
            System.out.println("Binding batch " + batch.zIndex());
            batch.bind();
            glDrawElements(batch.primitive().openglPrimitive, batch.getVertexCount(), GL_UNSIGNED_INT, 0);
            batch.unbind();
        }

        currentShader.detach();

        pickingTexture.disableWriting();
        glEnable(GL_BLEND);
    }

    public static PickingTexture getPickingTexture() {
        return pickingTexture;
    }
}
