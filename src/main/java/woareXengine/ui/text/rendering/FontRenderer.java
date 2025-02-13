package woareXengine.ui.text.rendering;

import org.joml.Vector2f;
import woareXengine.openglWrapper.framebuffer.Framebuffer;
import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.rendering.RenderBatch;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.rendering.renderData.ShaderDataType;
import woareXengine.ui.text.basics.Font;
import woareXengine.ui.text.basics.Text;
import woareXengine.ui.text.loading.Glyph;
import woareXengine.util.Color;

public class FontRenderer extends Renderer<Text> {
    private Font activeFont;

    @Override
    public Shader createShader() {
        return new FontShader();
    }

    @Override
    public Framebuffer createFramebuffer() {
        return Framebuffer.createDefault();
    }

    @Override
    protected RenderBatch createBatch(int zIndex) {
        return new RenderBatch(MAX_BATCH_SIZE, zIndex, Quad.primitive,
                ShaderDataType.FLOAT2, ShaderDataType.FLOAT4, ShaderDataType.FLOAT2
        );
    }

    @Override
    protected void uploadUniforms() {
        currentShader.uploadUniforms();
    }

    @Override
    protected void rebuffer() {
        for (Text text : data) {
            if (!text.isVisible()) continue;
            RenderBatch batch = getAvailableBatch(text.texture, text.zIndex);

            double xCursor = text.getAbsoluteTransform().getX() + ((text.transform.getWidth() - text.calculateWidth()) / 2);
            double yCursor = text.getAbsoluteTransform().getY() + ((text.transform.getHeight() - Text.LINE_HEIGHT_PIXELS * text.getScale()) / 2);

            for (int charIndex = 0; charIndex < text.textString.length(); charIndex++) {
                char c = text.textString.charAt(charIndex);
                Glyph glyph = activeFont.getTextGenerator().charData.get((int) c);

                if (c == ' ') {
                    xCursor += 9 * text.getScale();
                    continue;
                }

                Vector2f[] texCoords = {
                        new Vector2f((float) glyph.xMaxTextureCoord, 1 - (float) glyph.yTextureCoord),
                        new Vector2f((float) glyph.xMaxTextureCoord, 1 - (float) glyph.yMaxTextureCoord),
                        new Vector2f((float) glyph.xTextureCoord, 1 - (float) glyph.yMaxTextureCoord),
                        new Vector2f((float) glyph.xTextureCoord, 1 - (float) glyph.yTextureCoord),
                };


                for (int i = 0; i < Quad.primitive.vertexCount; i++) {
                    float leftOffset = (float) (glyph.xOffset * text.getScale());
                    float bottomOffset = (float) ((Text.LINE_HEIGHT_PIXELS - glyph.sizeY - glyph.yOffset) * text.getScale());

                    batch.pushVec2(new Vector2f(
                            (float) (xCursor + (i == 0 || i == 1 ? glyph.sizeX * text.getScale() : 0) + leftOffset),
                            (float) (yCursor + (i == 0 || i == 3 ? glyph.sizeY * text.getScale() : 0) + bottomOffset)
                    ));

                    batch.pushColor(text.color);

                    batch.pushVec2(texCoords[i]);
                }
                xCursor += glyph.xAdvance * text.getScale();
            }
        }
    }

    public void setActiveFont(Font font) {
        activeFont = font;
    }

    @Override
    protected void prepare() {
        activeFont.getFontAtlas().bindToSlot(0);
    }
}
