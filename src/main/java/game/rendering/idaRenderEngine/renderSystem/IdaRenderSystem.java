package game.rendering.idaRenderEngine.renderSystem;

import game.rendering.SceneRenderSystem;
import game.rendering.idaRenderEngine.IdaRenderer;
import game.scene.Scene;

public class IdaRenderSystem implements SceneRenderSystem {

    private final IdaRenderer renderer;
    // TODO handle entity data here

    public IdaRenderSystem(IdaRenderer renderer) {
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
}
