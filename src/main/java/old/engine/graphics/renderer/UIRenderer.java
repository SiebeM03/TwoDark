package old.engine.graphics.renderer;

import old.engine.graphics.ShaderDatatype;
import old.engine.graphics.Window;
import old.engine.ui.RenderableComponent;
import old.engine.ui.UIComponent;
import old.engine.util.AssetPool;
import old.engine.util.Layer;
import old.engine.graphics.Primitive;
import old.engine.graphics.Shader;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class UIRenderer extends Renderer {
    private static final int MAX_BATCH_SIZE = 1000;
    private static final int Z_INDEX = Layer.TOP;

    private final List<RenderableComponent> components;

    public UIRenderer() {
        components = new ArrayList<>();
    }

    @Override
    public Shader createShader() {
        return AssetPool.getShader("src/assets/shaders/ui.glsl");
    }

    @Override
    protected Framebuffer createFramebuffer() {
        return Window.getFramebuffer();
    }

    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Primitive.QUAD,
                ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT4, ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT);
    }

    @Override
    protected void uploadUniforms(Shader shader) {
        shader.uploadIntArray("uTextures", textureSlots);

        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
    }

    @Override
    protected void rebuffer() {
        for (RenderableComponent component : components) {
            if (component.sprite == null) return;
            RenderBatch batch = getAvailableBatch(component.sprite.getTexture(), Z_INDEX);

            Vector2f pos = component.getAbsolutePosition();
            Vector2f scale = component.transform.scale;
            Vector2f[] texCoords = component.sprite.getTexCoords();

            int texID;
            if (component.sprite.getTexture() != null) {
                texID = batch.addTexture(component.sprite.getTexture());
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
                batch.pushColor(component.color);

                // Load texture coordinates
                batch.pushVec2(texCoords[i]);

                // Load texture id
                batch.pushInt(texID);
            }
        }
    }

    public void add(RenderableComponent component) {
        if (component != null) {
            components.add(component);
            for (UIComponent child : component.getChildren()) {
                if (child instanceof RenderableComponent) {
                    add((RenderableComponent) child);
                }
            }
        }
    }

    public void remove(RenderableComponent component) {
        if (component != null) {
            components.remove(component);
        }
    }

    @Override
    public void prepare() {
    }
}
