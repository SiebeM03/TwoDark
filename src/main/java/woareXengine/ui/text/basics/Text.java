package woareXengine.ui.text.basics;

import woareXengine.ui.components.UiComponent;
import woareXengine.ui.text.loading.Glyph;
import woareXengine.util.Color;

public class Text extends UiComponent {
    /** Height of a line of text at font size 1 (UI scale 1) */
    public static final int LINE_HEIGHT_PIXELS = 32;
    public String textString;
    private final float scale;
    public final Font font;

    protected Text(String text, Font font, float scale) {
        this.textString = text;
        this.font = font;
        this.scale = scale;
        this.color = Color.BLACK;
    }

    @Override
    protected void init() {
        this.transform.setDimensions(calculateWidth(), calculateHeight());
    }

    @Override
    protected void updateSelf() {
    }

    public float calculateWidth() {
        float width = 0;
        for (int i = 0; i < textString.length(); i++) {
            char c = textString.charAt(i);
            if (c == (int) ' ') {
                width += 9;
                continue;
            }
            Glyph g = font.getTextGenerator().charData.get((int) c);
            if (i == 0) {   // First char only relies on offset
                width += g.xOffset;
            }
            if (i == textString.length() - 1) { // Last char should only account for the char's width
                width += g.sizeX;
                continue;
            }
            width += g.xAdvance;    // Otherwise add xAdvance value
        }
        return width * scale;
    }

    public float calculateHeight() {
        float height = 0;
        for (int i = 0; i < textString.length(); i++) {
            char c = textString.charAt(i);
            if (c == (int) ' ') continue;
            Glyph g = font.getTextGenerator().charData.get((int) c);
            height = Math.max(height, (float) g.sizeY);
        }
        return height * scale;
    }

    public float getScale() {
        return scale;
    }
}
