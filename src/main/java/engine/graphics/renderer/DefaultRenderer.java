package engine.graphics.renderer;

import engine.ecs.GameObject;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Primitive;
import engine.graphics.Shader;
import engine.graphics.ShaderDatatype;
import engine.graphics.Window;
import engine.ui.EventConsumer;
import engine.util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class DefaultRenderer extends Renderer {
    protected final int MAX_BATCH_SIZE = 1000;

    protected final List<SpriteRenderer> sprites;

    public DefaultRenderer() {
        sprites = new ArrayList<>();
    }

    @Override
    public Shader createShader() {
        return AssetPool.getShader("src/assets/shaders/default.glsl");
    }

    @Override
    protected Framebuffer createFramebuffer() {
        return Window.getFramebuffer();
    }


    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Primitive.QUAD,
                ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT4, ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT, ShaderDatatype.FLOAT
        );
    }

    @Override
    protected void uploadUniforms(Shader shader) {
        shader.uploadIntArray("uTextures", textureSlots);

        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
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

            // Push vertices to the batch
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
                batch.pushColor(sprite.getColor());

                // Load texture coordinates
                batch.pushVec2(texCoords[i]);

                // Load texture id
                batch.pushInt(texID);

                // Load cooldown value
                EventConsumer eventConsumer = sprite.gameObject.eventConsumer;
                if (eventConsumer != null && eventConsumer.hasCooldownAnimation()) {
                    batch.pushFloat(Math.min(1.0f, eventConsumer.clickDelayTimer() / eventConsumer.clickDelay()));
                } else {
                    batch.pushFloat(0.0f);
                }
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
    protected void prepare() {
        glClearColor(12.0f / 255.0f, 122.0f / 255.0f, 138.0f / 255.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
