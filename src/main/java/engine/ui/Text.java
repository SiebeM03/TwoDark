package engine.ui;

import engine.ecs.GameObject;
import engine.ecs.Transform;
import engine.graphics.Window;
import engine.graphics.renderer.TextRenderer;
import engine.ui.fonts.Font;
import engine.ui.fonts.Glyph;
import engine.ui.fonts.GlyphRenderer;
import engine.util.Color;
import engine.util.Layer;
import org.joml.Vector2f;

import java.util.ArrayList;

public class Text {
    private Transform lastTransform = new Transform();
    private Transform transform = new Transform();
    private int zIndex;

    private UIComponent parent;

    private ArrayList<GlyphRenderer> glyphRenderers;
    private Color color = Color.WHITE;

    private Font font;
    private CharSequence text;


    public Text(String string, Font font, Color color, float x, float y) {
        this.text = string;
        this.font = font;
        this.color = color;

        this.transform.setPosition(new Vector2f(x, y));
        this.lastTransform.setPosition(new Vector2f(x, y));
        this.zIndex = Layer.TOP;

        glyphRenderers = new ArrayList<>();

        generateGlyphs();
        Window.getScene().addTextToScene(this);
    }

    public void update() {
        if (!lastTransform.equals(this.transform)) {
            Vector2f movementDelta = new Vector2f(transform.getX() - lastTransform.getX(), transform.getY() - lastTransform.getY());

            for (GlyphRenderer gr : glyphRenderers) {
                gr.updatePosition(movementDelta);
            }
            for (GlyphRenderer gr : glyphRenderers) {
                gr.update();
            }
        }
        lastTransform.setX(transform.getX());
        lastTransform.setY(transform.getY());
    }

    public void change(String string) {
        glyphRenderers.clear();
        this.text = string + " ";
        generateGlyphs();
    }

    public String getText() {
        return (String) this.text;
    }

    private char ch;

    /**
     * Calculates the width of a single line of text based on the Glyph size for
     * each character contained in the CharSequence (a lower level representation of
     * String).
     *
     * @return the width in pixels of the line.
     */
    public float calculateLineWidth(CharSequence line) {
        float drawX = 0;
        float lineWidth = 0;

        for (int i = 0; i < line.length(); i++) {
            ch = line.charAt(i);

            if (ch == '\r')
                continue;

            drawX += font.getGlyphs().get(ch).width;
            lineWidth = drawX;
        }

        return lineWidth;
    }

    /**
     * Creates the glyphs (essentially sprites) for each character in the string at
     * the appropriate position relative to the anchor point and text alignment.
     */
    int textHeight;
    float maxTextWidth;

    private void generateGlyphs() {
        float[] lineLengths = new float[1];
        // TODO centered support

        textHeight = font.getHeight(text);
        int lineIncreases = 0;

        // Get the anchor point of the Text object
        Transform t = transform.copy();
        float drawX = t.getX();
        float drawY = t.getY();

        for (int i = 0; i < text.length(); i++) {
            // String sizes are automatically chopped off at a certain length due to
            // rendering speed and memory limitations.
            if (i >= TextRenderer.getMaxBatchSize() - 3) {
                // Replace the last three characters of the string with "..."
                if (i < TextRenderer.getMaxBatchSize()) {
                    ch = '.';
                } else
                    break;
            } else
                ch = text.charAt(i);

            if (ch == '\n') {
                // Line break, set x and y to draw at the next line and continue since there is
                // nothing to draw.
                lineIncreases++;

                drawX = t.getX();
                drawY = t.getY() + (font.getFontHeight() * lineIncreases);

                continue;
            }

            // Carriage return - cannot be drawn.
            if (ch == '\r')
                continue;

            // Add the Glyph that corresponds to the current character to the arrayList of
            // glyphRenders.
            Glyph glyph = font.getGlyphs().get(ch);

            glyphRenderers.add(new GlyphRenderer(new Transform(new Vector2f(drawX, drawY), new Vector2f(glyph.width, glyph.height)),
                    glyph, this, ch, this.color));

            drawX += glyph.width;
        }
        if (textHeight > font.getFontHeight()) {
            drawY += textHeight - font.getFontHeight();
        }
    }

    public ArrayList<GlyphRenderer> getGlyphRenderers() {
        return glyphRenderers;
    }

    public int zIndex() {
        return zIndex;
    }

    public void setPosition(Vector2f position) {
        this.transform.setPosition(position);
    }

    public void setParent(UIComponent component) {
        this.parent = component;
    }

    public UIComponent getParent() {
        return this.parent;
    }

}
