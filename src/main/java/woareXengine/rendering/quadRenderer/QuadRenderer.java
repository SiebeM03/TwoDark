package woareXengine.rendering.quadRenderer;

import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.openglWrapper.framebuffer.Framebuffer;
import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.rendering.RenderBatch;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.renderData.ShaderDataType;


public class QuadRenderer extends Renderer<Quad> {
    @Override
    public Shader createShader() {
        return new QuadShader();
    }

    @Override
    public Framebuffer createFramebuffer() {
        return Framebuffer.createDefault();
    }

    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Quad.primitive,
                ShaderDataType.FLOAT2, ShaderDataType.FLOAT4, ShaderDataType.FLOAT, ShaderDataType.FLOAT2
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
            }
        }
    }


    @Override
    protected void prepare() {

    }
}
