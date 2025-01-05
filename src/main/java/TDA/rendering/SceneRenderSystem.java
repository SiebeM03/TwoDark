package TDA.rendering;

import TDA.entities.player.Player;
import TDA.entities.resources.Resource;
import TDA.main.world.tiles.Tile;
import TDA.scene.Scene;

public interface SceneRenderSystem {
    void render(Scene scene);

    void cleanUp();

    void registerPlayer(Player player);

    void registerResource(Resource resource);

    void registerTile(Tile tile);
}
