package woareXengine.rendering.pickingRenderer;

import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.openglWrapper.framebuffer.Framebuffer;
import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.rendering.RenderBatch;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.rendering.renderData.Primitive;
import woareXengine.rendering.renderData.ShaderDataType;
import woareXengine.util.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class PickingRenderer extends Renderer<Quad> {
    @Override
    protected Shader createShader() {
        return new PickingShader();
    }
    @Override
    protected Framebuffer createFramebuffer() {
        return new Framebuffer(Engine.window().getPixelWidth(), Engine.window().getPixelHeight());
//        return Framebuffer.createDefault();
    }

    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Primitive.QUAD,
                ShaderDataType.FLOAT2, ShaderDataType.FLOAT4, ShaderDataType.FLOAT, ShaderDataType.FLOAT2, ShaderDataType.FLOAT
        );
    }

    @Override
    protected void uploadUniforms() {
        currentShader.uploadUniforms();
        currentShader.uploadIntArray("uTextures", textureSlots);
    }

    @Override
    protected void rebuffer() {
        data.sort((quad1, quad2) -> Float.compare(quad2.transform.getY(), quad1.transform.getY()));

        for (Quad quad : data) {
            if (!GameManager.currentScene.camera.isOnScreen(quad.transform)) {
                continue;
            }

            RenderBatch batch = getAvailableBatch(quad.texture, quad.zIndex);

            int texID = batch.addTexture(quad.texture);

            for (int i = 0; i < Quad.primitive.vertexCount; i++) {
                batch.pushVec2(new Vector2f(
                        quad.transform.getX() + (i == 0 || i == 1 ? quad.transform.getWidth() : 0),
                        quad.transform.getY() + (i == 0 || i == 3 ? quad.transform.getHeight() : 0)
                ));

                batch.pushColor(quad.color);

                batch.pushInt(texID);

                batch.pushVec2(quad.textureCoords[i]);

                batch.pushInt(quad.getEntityID() + 1);
            }
        }
    }

    @Override
    public void render() {
        glDisable(GL_BLEND);
        framebuffer.bind();
        currentShader.use();
        uploadUniforms();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        startBatches();
        rebuffer();
        finishBatches();

        for (RenderBatch batch : batches) {
            batch.bind();
            glDrawElements(batch.primitive.openglPrimitive, batch.getVertexCount(), GL_UNSIGNED_INT, 0);
            batch.unbind();
        }

        currentShader.detach();
        framebuffer.unbind();
        glEnable(GL_BLEND);
    }

    @Override
    protected void prepare() {

    }

    public int readPixel(int x, int y) {
        if (framebuffer == null) {
            Logger.error("Trying to read framebuffer when null");
            return -1;
        }

        framebuffer.bind();
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        float[] pixels = new float[3];
        glReadPixels(x, y, 1, 1, GL_RGB, GL_FLOAT, pixels);
        return (int) (pixels[0]) - 1;
    }
}
