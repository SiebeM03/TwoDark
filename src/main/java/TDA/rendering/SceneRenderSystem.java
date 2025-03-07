package TDA.rendering;

import TDA.main.world.tiles.Tile;
import TDA.scene.Scene;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.pickingRenderer.PickingRenderer;
import woareXengine.rendering.quadRenderer.Quad;

public interface SceneRenderSystem {
    void render(Scene scene);

    void clear();

    void cleanUp();

    void registerTile(Tile tile);

    void registerQuad(Quad quad);

    void removeQuad(Quad quad);

    PickingRenderer getPickingRenderer();
}
