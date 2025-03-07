package TDA.main.world.tiles.types;

import TDA.entities.main.Entity;
import TDA.entities.components.interactions.ColliderComp;
import TDA.main.GameManager;
import TDA.main.world.World;
import TDA.main.world.tiles.Tile;
import org.joml.Vector2f;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

import static TDA.main.world.WorldConfigs.TILE_SIZE;

public class Water extends Tile {
    public Water(int x, int y) {
        this.normalizedCoordinates = new Vector2f(x, y);
        this.transform = new Transform(
                normalizedCoordinates.mul(TILE_SIZE, new Vector2f()),
                new Vector2f(TILE_SIZE, TILE_SIZE)
        );

        this.renderObject = new Quad(transform.getWidth(), transform.getHeight(), transform.getPosition(), Layer.BACKGROUND);
        renderObject.texture = Assets.getTexture("src/assets/images/tiles/water.png");

        GameManager.currentScene.renderer.registerTile(this);
    }

    public void createCollisionBox() {
        if (isSurroundedByWater()) return;
        GameManager.currentScene.addEntity(new Entity(transform)
                                                   .addComponent(new ColliderComp()));
    }

    private boolean isSurroundedByWater() {
        World world = GameManager.homeScene.world;

        return world.getTile((int) normalizedCoordinates.x, (int) normalizedCoordinates.y - 1) instanceof Water
                       && world.getTile((int) normalizedCoordinates.x, (int) normalizedCoordinates.y + 1) instanceof Water
                       && world.getTile((int) normalizedCoordinates.x - 1, (int) normalizedCoordinates.y) instanceof Water
                       && world.getTile((int) normalizedCoordinates.x + 1, (int) normalizedCoordinates.y) instanceof Water;
    }
}
