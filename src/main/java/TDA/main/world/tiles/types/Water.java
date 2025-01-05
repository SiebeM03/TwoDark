package TDA.main.world.tiles.types;

import TDA.entities.player.controller.collisions.GameCollisionBox;
import TDA.main.GameManager;
import TDA.main.world.World;
import TDA.main.world.tiles.Tile;
import old.engine.util.Layer;
import org.joml.Vector2f;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
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

    public void createCollisionBox(World world) {
        if (isSurroundedByWater(world)) return;
        new GameCollisionBox(transform);
    }

    private boolean isSurroundedByWater(World world) {
        return world.getTile((int) normalizedCoordinates.x, (int) normalizedCoordinates.y - 1) instanceof Water
                       && world.getTile((int) normalizedCoordinates.x, (int) normalizedCoordinates.y + 1) instanceof Water
                       && world.getTile((int) normalizedCoordinates.x - 1, (int) normalizedCoordinates.y) instanceof Water
                       && world.getTile((int) normalizedCoordinates.x + 1, (int) normalizedCoordinates.y) instanceof Water;
    }
}
