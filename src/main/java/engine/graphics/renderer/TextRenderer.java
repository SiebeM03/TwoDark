package engine.graphics.renderer;

import engine.graphics.Primitive;
import engine.graphics.Shader;
import engine.graphics.ShaderDatatype;
import engine.graphics.Window;
import engine.ui.Text;
import engine.ui.fonts.GlyphRenderer;
import engine.util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextRenderer extends Renderer {
    private static final int MAX_BATCH_SIZE = 30;

    private final List<Text> texts;

    public TextRenderer() {
        texts = new ArrayList<>();
    }

    @Override
    public Shader createShader() {
        return AssetPool.getShader("src/assets/shaders/text.glsl");
    }

    @Override
    protected Framebuffer createFramebuffer() {
        return new Framebuffer(Window.getWidth(), Window.getHeight());
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
        for (Text text : texts) {
            ArrayList<GlyphRenderer> glyphs = text.getGlyphRenderers();
            for (GlyphRenderer glyph : glyphs) {
                RenderBatch batch = getAvailableBatch(glyph.getTexture(), text.zIndex());
                pushGlyph(batch, glyph);
            }
        }
    }

    private static void pushGlyph(RenderBatch batch, GlyphRenderer glyph) {
        Vector2f pos = glyph.getLocalTransform().position;
        Vector2f scale = glyph.getLocalTransform().scale;
        Vector2f[] texCoords = glyph.getTexCoords();

        int texID;
        if (glyph.getTexture() != null) {
            texID = batch.addTexture(glyph.getTexture());
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
            batch.pushVec4(glyph.getColor());

            // Load texture coordinates
            batch.pushVec2(texCoords[i]);

            // Load texture id
            batch.pushInt(texID);
        }
    }

    public void add(Text textObject) {
        if (textObject != null) {
            texts.add(textObject);
        }
    }

    public void remove(Text text) {
        if (text != null) {
            texts.remove(text);
        }
    }

    @Override
    public void prepare() {
    }

    public static int getMaxBatchSize() {
        return MAX_BATCH_SIZE;
    }
}
