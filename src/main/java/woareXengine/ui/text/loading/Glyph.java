package woareXengine.ui.text.loading;

/**
 * Simple data structure class holding information about a certain glyph in the
 * font texture atlas. All sizes are for a font-size of 1.
 */
public class Glyph {
    public final int id;
    public final double xTextureCoord;
    public final double yTextureCoord;
    public final double xMaxTextureCoord;
    public final double yMaxTextureCoord;
    public final double xOffset;
    public final double yOffset;
    public final double sizeX;
    public final double sizeY;
    public final double xAdvance;


    /**
     * @param id            the ASCII value of the character
     * @param xTextureCoord the x texture coordinate for the top-left corner of the texture atlas
     * @param yTextureCoord the y texture coordinate for the top-left corner of the texture atlas
     * @param textureWidth  the width of the character in the texture atlas
     * @param textureHeight the height of the character in the texture atlas
     * @param xOffset       the x distance from the cursor to the left edge of the character's quad in pixels
     * @param yOffset       the y distance from the cursor to the top edge of the character's quad in pixels
     * @param sizeX         the width of the character's quad in pixels
     * @param sizeY         the height of the character's quad in pixels
     * @param xAdvance      the amount of pixels the cursor should advance after this character
     */
    protected Glyph(int id, double xTextureCoord, double yTextureCoord, double textureWidth, double textureHeight, double xOffset, double yOffset, double sizeX, double sizeY, double xAdvance) {
        this.id = id;
        this.xTextureCoord = xTextureCoord;
        this.yTextureCoord = yTextureCoord;
        this.xMaxTextureCoord = xTextureCoord + textureWidth;
        this.yMaxTextureCoord = yTextureCoord + textureHeight;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.xAdvance = xAdvance;
    }
}
