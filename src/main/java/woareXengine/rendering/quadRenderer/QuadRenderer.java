package woareXengine.rendering.quadRenderer;

import game.main.GameManager;
import org.joml.Vector2f;
import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.rendering.RenderBatch;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.renderData.ShaderDataType;

import java.util.ArrayList;


public class QuadRenderer extends Renderer<Quad> {

    public QuadRenderer() {
        this.data = new ArrayList<>();
        this.currentShader = new QuadShader();
    }

    @Override
    public Shader createShader() {
        return new QuadShader();
    }

    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Quad.primitive,
                ShaderDataType.FLOAT2, ShaderDataType.FLOAT4, ShaderDataType.FLOAT, ShaderDataType.FLOAT2
        );
    }

    @Override
    public void add(Quad quad) {
        data.add(quad);
    }

    @Override
    public void remove(Quad quad) {
        data.remove(quad);
    }

    @Override
    protected void uploadUniforms() {
        currentShader.uploadUniforms();
        currentShader.uploadIntArray("uTextures", textureSlots);
    }

    @Override
    protected void rebuffer() {
        for (Quad quad : data) {
            RenderBatch batch = getAvailableBatch(quad.texture, quad.zIndex);

            int texID = batch.addTexture(quad.texture);

            for (int i = 0; i < Quad.primitive.vertexCount; i++) {
                batch.pushVec2(new Vector2f(
                        quad.position.x + (i == 0 || i == 1 ? quad.width : 0),
                        quad.position.y + (i == 0 || i == 3 ? quad.height : 0)
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
