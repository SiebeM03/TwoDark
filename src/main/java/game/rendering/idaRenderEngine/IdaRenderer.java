package game.rendering.idaRenderEngine;

import game.rendering.idaRenderEngine.renderSystem.IdaRenderSystem;
import game.scene.Scene;

public interface IdaRenderer {
    void renderScene(Scene scene, IdaRenderSystem sceneData);

    void cleanUp();
}
