package engine.ui.fonts;

import engine.graphics.renderer.Texture;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Font {
    private final Map<Character, Glyph> glyphs;

    private final Texture texture;

    private int fontHeight;

    public Font(String path, float size) {
        java.awt.Font f = null;
        try {
            java.awt.Font rawFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(path));
            f = rawFont.deriveFont(size);
        } catch (Exception e) {
            assert false : "Could not load font file: " + path + ", using default monospaced font";
            e.printStackTrace();
        }

        glyphs = new HashMap<>();
        texture = createFontTexture(f);
    }


    private Texture createFontTexture(java.awt.Font font) {
        // Loop through the characters to get charWidth and charHeight
        int imageWidth = 0;
        int imageHeight = 0;

        // Start at char #32, because ASCII 0 to 31 are just control codes
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                // ASCII 127 is the DEL control code, so we can skip it
                continue;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c);
            if (ch == null) {
                // If char image is null that font does not contain the char
                continue;
            }

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        fontHeight = imageHeight;

        // Image for the texture
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        int x = 0;

        // Create image for the standard chars, omitting ASCII 0 to 31 because they are just control codes.
        for (int i = 32; i < 256; i++) {

            // ASCII 127 is the DEL control code, so we can skip it
            if (i == 127) continue;

            char c = (char) i;

            BufferedImage charImage = createCharImage(font, c);

            // If char image is null that font does not contain the char
            if (charImage == null) continue;

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            // Create glyph and draw char on image
            Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight);
            g.drawImage(charImage, x, 0, null);
            x += ch.width;
            glyphs.put(c, ch);
        }

        Texture finalTexture = bufferedImageToTexture(image);

        // Finally, calculate the UV coordinates on the generated texture and store it in each Glyph
        for (int i = 32; i < 256; i++) {
            if (glyphs.get((char) i) != null) {
                glyphs.get((char) i).calculateUVs(finalTexture);
            }
        }

        return finalTexture;
    }

    private Texture bufferedImageToTexture(BufferedImage image) {
        // OpenGL has its origin (0,0) in the bottom left corner, while BufferedImage has it in the top left corner
        // So we need to flip the image vertically
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

        // Get charWidth and charHeight of image
        int width = image.getWidth();
        int height = image.getHeight();

        // Get pixel data of image
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        // Load the pixel data into a ByteBuffer
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // & 0xFF is used to separate the final 2 bytes of the int
                // Pixel as RGBA: 0xAARRGGBB
                int pixel = pixels[i * width + j];
                // Red component 0xAARRGGBB >> 16 = 0x0000AARR
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                // Green component 0xAARRGGBB >> 8 = 0x00AARRGG
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                // Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB
                buffer.put((byte) (pixel & 0xFF));
                // Alpha component 0xAARRGGBB >> 24 = 0x000000AA
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        // The buffer limit is set to the buffer position and the position is set to 0
        // This way the buffer is ready to be read from by OpenGL (from position 0 to limit)
        buffer.flip();

        return new Texture().createTexture(width, height, buffer);
    }

    private BufferedImage createCharImage(java.awt.Font font, char c) {
        // Creating temporary image to extract character size
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        // Get char charWidth and charHeight
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        // Check if charWidth is 0
        if (charWidth == 0) {
            return null;
        }

        // Create image for holding the char
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setPaint(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    /**
     * Gets the height of the specified text.
     *
     * @return Height in pixels of the text.
     */
    public int getHeight(CharSequence text) {
        int height = 0;
        int lineHeight = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                // Line end, add line height to stored height
                height += lineHeight;
                lineHeight = 0;
                continue;
            }

            if (c == '\r') continue;

            Glyph g = glyphs.get(c);
            lineHeight = Math.max(lineHeight, g.height);
        }
        height += lineHeight;
        return height;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public Map<Character, Glyph> getGlyphs() {
        return glyphs;
    }

    public Texture getTexture() {
        return texture;
    }
}
