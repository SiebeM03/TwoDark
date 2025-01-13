package TDA.main.world.tiles.types;

import TDA.main.GameManager;
import TDA.main.world.tiles.Tile;
import org.joml.Vector2f;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

import static TDA.main.world.WorldConfigs.TILE_SIZE;

public class Path extends Tile {
    public Path(int x, int y) {
        this.normalizedCoordinates = new Vector2f(x, y);
        this.transform = new Transform(
                normalizedCoordinates.mul(TILE_SIZE, new Vector2f()),
                new Vector2f(TILE_SIZE, TILE_SIZE)
        );

        this.renderObject = new Quad(transform.getWidth(), transform.getHeight(), transform.getPosition(), Layer.BACKGROUND);
        renderObject.texture = Assets.getTexture("src/assets/images/tiles/path.png");

        GameManager.currentScene.renderer.registerTile(this);
    }
}
