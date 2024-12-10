package engine.graphics.renderer;

import engine.ecs.GameObject;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Primitive;
import engine.graphics.Shader;
import engine.graphics.ShaderDatatype;
import engine.graphics.Window;
import engine.ui.MouseEventConsumer;
import engine.util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class PickingRenderer extends Renderer {
    protected final int MAX_BATCH_SIZE = 1000;

    protected final List<SpriteRenderer> sprites;

    public PickingRenderer() {
        sprites = new ArrayList<>();
    }

    public void init() {
        framebuffer = createFramebuffer();
        if (!init(Window.getWidth(), Window.getHeight())) {
            assert false : "Error initializing picking texture";
        }
        currentShader = createShader();
    }

    @Override
    public Shader createShader() {
        return AssetPool.getShader("src/assets/shaders/pickingShader.glsl");
    }

    private boolean init(int width, int height) {
        int depthTexture;
        int pickingTextureId;
        framebuffer.bind();

        // Create the texture to render the data to and attach it to our framebuffer
        pickingTextureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, pickingTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB32F, width, height, 0, GL_RGB, GL_FLOAT, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, pickingTextureId, 0);

        // Create texture object for the depth buffer
        glEnable(GL_TEXTURE_2D);
        depthTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, depthTexture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthTexture, 0);

        // Disable the reading
        glReadBuffer(GL_NONE);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            assert false : "Error: Framebuffer is not complete";
            return false;
        }
        glBindTexture(GL_TEXTURE_2D, 0);
        framebuffer.unbind();
        return true;
    }

    @Override
    protected Framebuffer createFramebuffer() {
        framebuffer = new Framebuffer(Window.getWidth(), Window.getHeight());
        return framebuffer;
    }


    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Primitive.QUAD,
                ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT4, ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT, ShaderDatatype.FLOAT, ShaderDatatype.FLOAT
        );
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

                // Load cooldown value
                MouseEventConsumer mouseEventConsumer = sprite.gameObject.getComponent(MouseEventConsumer.class);
                if (mouseEventConsumer != null && mouseEventConsumer.hasCooldownAnimation()) {
                    batch.pushFloat(Math.min(1.0f, mouseEventConsumer.clickDelayTimer() / mouseEventConsumer.clickDelay()));
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
    protected void uploadUniforms(Shader shader) {
        shader.uploadIntArray("uTextures", textureSlots);

        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
    }

    @Override
    public void render() {
        glDisable(GL_BLEND);
        framebuffer.bind();
        glViewport(0, 0, Window.getWidth(), Window.getHeight());
        currentShader.use();
        uploadUniforms(currentShader);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        start();
        rebuffer();
        finish();

        for (RenderBatch batch : batches) {
            batch.bind();
            glDrawElements(batch.primitive().openglPrimitive, batch.getVertexCount(), GL_UNSIGNED_INT, 0);
            batch.unbind();
        }

        currentShader.detach();
        framebuffer.unbind();
        glEnable(GL_BLEND);
    }

    public int readPixel(int x, int y) {
        if (framebuffer == null) {
            System.out.println("Framebuffer is null");
            return -1;
        }

        glBindFramebuffer(GL_READ_FRAMEBUFFER, framebuffer.getFboID());
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        float[] pixels = new float[3];
        glReadPixels(x, y, 1, 1, GL_RGB, GL_FLOAT, pixels);
        return (int) (pixels[0] - 1);
    }

    @Override
    public void prepare() {
    }
}
