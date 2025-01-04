package game.rendering;

import game.scene.Scene;

public interface SceneRenderSystem {
    void render(Scene scene);

    void cleanUp();
}
