package woareXengine.rendering.uiRenderer;

import org.joml.Vector2f;
import woareXengine.openglWrapper.framebuffer.Framebuffer;
import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.rendering.RenderBatch;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.rendering.renderData.ShaderDataType;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.main.Ui;
import woareXengine.util.Transform;

import java.util.ArrayList;

public class UiRenderer extends Renderer<UiComponent> {

    @Override
    protected Shader createShader() {
        return new UiShader();
    }

    @Override
    public Framebuffer createFramebuffer() {
        return Framebuffer.createDefault();
    }

    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Quad.primitive,
                ShaderDataType.FLOAT2, ShaderDataType.FLOAT4, ShaderDataType.FLOAT, ShaderDataType.FLOAT2);
    }

    @Override
    protected void uploadUniforms() {
        currentShader.uploadUniforms();
        currentShader.uploadIntArray("uTextures", textureSlots);
    }

    @Override
    protected void rebuffer() {
        bufferUiComponent(Ui.getContainer());
    }

    private void bufferUiComponent(UiComponent component) {
        if (!component.isVisible()) return;
        RenderBatch batch = getAvailableBatch(component.texture, component.zIndex);

        int texID = batch.addTexture(component.texture);
        Transform absoluteTransform = component.getAbsoluteTransform();

        for (int i = 0; i < UiComponent.primitive.vertexCount; i++) {
            batch.pushVec2(new Vector2f(
                    absoluteTransform.getX() + (i == 0 || i == 1 ? absoluteTransform.getWidth() : 0),
                    absoluteTransform.getY() + (i == 0 || i == 3 ? absoluteTransform.getHeight() : 0)
            ));

            batch.pushColor(component.color);

            batch.pushInt(texID);

            batch.pushVec2(component.textureCoords[i]);
        }

        for (UiComponent child : component.children) {
            bufferUiComponent(child);
        }
    }

    @Override
    protected void prepare() {

    }
}
