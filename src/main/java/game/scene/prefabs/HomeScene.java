package game.scene.prefabs;

import game.rendering.SceneRenderSystem;
import game.rendering.idaRenderEngine.renderSystem.IdaRenderSystem;
import game.rendering.idaRenderEngine.renderer.RendererManager;
import game.scene.Scene;
import woareXengine.mainEngine.gameObjects.Camera;

public class HomeScene {

    public static Scene init() {
        Camera camera = new HomeCamera();
        SceneRenderSystem renderSystem = new IdaRenderSystem(new RendererManager());
        Scene scene = new Scene(renderSystem, camera);

        return scene;
    }
}
