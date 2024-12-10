package engine.ui.fonts;

import engine.ecs.Sprite;
import engine.graphics.renderer.Texture;
import org.joml.Vector2f;

public class Glyph extends Sprite {
    public final int width;
    public final int height;
    private final int x;
    private final int y;
    private Vector2f[] uvCoordinates;

    public Glyph(int width, int height, int x, int y) {
        this.setTexture(new Texture());
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void calculateUVs(Texture texture) {
        this.texture = texture;

        System.out.println("X: " + this.x);
        System.out.println("Y: " + this.y);
        System.out.println("Width: " + this.width);
        System.out.println("Height: " + this.height);

        float topY = (y + height) / (float) texture.getHeight();
        float rightX = (x + width) / (float) texture.getWidth();
        float leftX = x / (float) texture.getWidth();
        float bottomY = y / (float) texture.getHeight();

        uvCoordinates = new Vector2f[]{
                new Vector2f(rightX, bottomY),
                new Vector2f(rightX, topY),
                new Vector2f(leftX, topY),
                new Vector2f(leftX, bottomY)
        };
    }

    public Vector2f[] getUV() {
        return uvCoordinates;
    }


}
