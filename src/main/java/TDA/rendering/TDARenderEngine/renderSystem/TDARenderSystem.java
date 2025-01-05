package TDA.rendering.TDARenderEngine.renderSystem;

import TDA.entities.player.Player;
import TDA.entities.resources.Resource;
import TDA.main.world.tiles.Tile;
import TDA.rendering.SceneRenderSystem;
import TDA.rendering.TDARenderEngine.renderer.RendererManager;
import TDA.scene.Scene;
import woareXengine.rendering.quadRenderer.Quad;

public class TDARenderSystem implements SceneRenderSystem {

    private final RendererManager renderer;
    // TODO handle entity data here


    public TDARenderSystem(RendererManager renderer) {
        this.renderer = renderer;
    }

    @Override
    public void render(Scene scene) {
        renderer.renderScene(scene, this);
    }

    @Override
    public void cleanUp() {
        this.renderer.cleanUp();
    }

    @Override
    public void registerPlayer(Player player) {
        this.renderer.getRenderer(Quad.class).add(player.renderObject);
    }

    @Override
    public void registerResource(Resource resource) {
        this.renderer.getRenderer(Quad.class).add(resource.renderObject);
    }

    @Override
    public void registerTile(Tile tile) {
        this.renderer.getRenderer(Quad.class).add(tile.renderObject);
    }
}
