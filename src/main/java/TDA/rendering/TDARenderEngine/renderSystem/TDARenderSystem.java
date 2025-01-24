package TDA.rendering.TDARenderEngine.renderSystem;

import TDA.main.world.tiles.Tile;
import TDA.rendering.SceneRenderSystem;
import TDA.rendering.TDARenderEngine.renderer.RendererManager;
import TDA.scene.Scene;
import woareXengine.rendering.quadRenderer.Quad;

public class TDARenderSystem implements SceneRenderSystem {
    private static TDARenderSystem instance;


    public final RendererManager renderer;
    // TODO handle entity data here


    @Override
    public void render(Scene scene) {
        renderer.renderScene(scene, this);
    }

    @Override
    public void cleanUp() {
        this.renderer.cleanUp();
    }

    @Override
    public void registerTile(Tile tile) {
        this.renderer.getRenderer(Quad.class).add(tile.renderObject);
    }


    public static TDARenderSystem get() {
        if (instance == null) {
            instance = new TDARenderSystem(new RendererManager());
        }
        return instance;
    }

    private TDARenderSystem(RendererManager renderer) {
        this.renderer = renderer;
    }
}
