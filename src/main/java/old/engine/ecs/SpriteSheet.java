package old.engine.ecs;

import old.engine.graphics.renderer.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sprite sheet, which is a collection of {@link Sprite}s derived from a single {@link Texture}.
 * <p>
 * The sheet divides the texture into individual sprites based on dimensions and spacing.
 */
public class SpriteSheet {
    /**
     * The texture of the sprite sheet that contains all the sprites.
     */
    private Texture texture;

    /**
     * The list of sprites generated from the texture.
     */
    private List<Sprite> sprites;


    /**
     * Constructs a {@link SpriteSheet} by slicing a texture into individual sprites.
     *
     * @param texture      the texture containing the sprites
     * @param spriteWidth  the width of each sprite in pixels
     * @param spriteHeight the height of each sprite in pixels
     * @param numSprites   the total number of sprites to generate
     * @param spacing      the spacing between sprites in pixels
     */
    public SpriteSheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing) {
        this.sprites = new ArrayList<>();

        this.texture = texture;
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for (int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeight) / (float) texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
            float leftX = currentX / (float) texture.getWidth();
            float bottomY = currentY / (float) texture.getHeight();

            Vector2f[] texCoords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };
            Sprite sprite = new Sprite();
            sprite.setTexture(this.texture);
            sprite.setTexCoords(texCoords);
            sprite.setWidth(spriteWidth);
            sprite.setHeight(spriteHeight);
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }


    /**
     * Retrieves a sprite by its index in the sprite sheet.
     *
     * @param index the index of the sprite to retrieve
     * @return the {@link Sprite} at the specified index
     */
    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }

    public int size() {
        return sprites.size();
    }
}
