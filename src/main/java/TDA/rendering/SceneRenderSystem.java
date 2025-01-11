package TDA.rendering;

import TDA.main.world.tiles.Tile;
import TDA.scene.Scene;

public interface SceneRenderSystem {
    void render(Scene scene);

    void cleanUp();

    void registerTile(Tile tile);
}
