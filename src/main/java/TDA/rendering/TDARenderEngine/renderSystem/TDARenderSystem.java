package TDA.rendering.TDARenderEngine.renderSystem;

import TDA.main.world.tiles.Tile;
import TDA.rendering.SceneRenderSystem;
import TDA.rendering.TDARenderEngine.renderer.RendererManager;
import TDA.scene.Scene;
import woareXengine.rendering.pickingRenderer.PickingRenderer;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Logger;

public class TDARenderSystem implements SceneRenderSystem {
    public final RendererManager renderer;
    // TODO handle entity data here

    public TDARenderSystem() {
        this.renderer = new RendererManager();
    }

    @Override
    public void render(Scene scene) {
        renderer.renderScene(scene, this);
    }

    @Override
    public void clear() {
        renderer.clear();
    }

    @Override
    public void cleanUp() {
        this.renderer.cleanUp();
    }

    @Override
    public void registerTile(Tile tile) {
        this.renderer.getRenderer(Quad.class).add(tile.renderObject);
    }

    @Override
    public void registerQuad(Quad quad) {
        this.renderer.getRenderer(Quad.class).add(quad);
    }

    @Override
    public void removeQuad(Quad quad) {
        this.renderer.getRenderer(Quad.class).remove(quad);
    }

    @Override
    public PickingRenderer getPickingRenderer() {
        return this.renderer.getPickingRenderer();
    }
}
