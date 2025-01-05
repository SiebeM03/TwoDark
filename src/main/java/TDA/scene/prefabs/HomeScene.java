package TDA.scene.prefabs;

import TDA.rendering.SceneRenderSystem;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.rendering.TDARenderEngine.renderer.RendererManager;
import TDA.scene.Scene;
import woareXengine.mainEngine.gameObjects.Camera;

public class HomeScene {

    public static Scene init() {
        Camera camera = new HomeCamera();
        SceneRenderSystem renderSystem = new TDARenderSystem(new RendererManager());
        Scene scene = new Scene(renderSystem, camera);

        return scene;
    }
}
