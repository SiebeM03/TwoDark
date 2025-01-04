package old.engine.graphics.renderer;

import old.engine.graphics.Primitive;
import old.engine.graphics.Shader;
import old.engine.graphics.ShaderDatatype;
import old.engine.graphics.Window;
import old.engine.ui.Text;
import old.engine.ui.fonts.GlyphRenderer;
import old.engine.util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class TextRenderer extends Renderer {
    private static final int MAX_BATCH_SIZE = 1000;

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
        for (Text text : texts) {
            ArrayList<GlyphRenderer> glyphs = text.getGlyphRenderers();
            for (GlyphRenderer glyph : glyphs) {
                RenderBatch batch = getAvailableBatch(glyph.getTexture(), text.zIndex());
                pushGlyph(batch, glyph, text);
            }
        }
    }

    private static void pushGlyph(RenderBatch batch, GlyphRenderer glyph, Text text) {
        Vector2f pos;
        if (text.getParent() == null) {
            pos = glyph.getLocalTransform().position;
        } else {
            pos = new Vector2f(glyph.getLocalTransform().position).add(text.getParent().getAbsolutePosition());
        }
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
            batch.pushColor(glyph.getColor());

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
