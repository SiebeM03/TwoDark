package TDA.main;

import TDA.main.configs.GameConfigs;
import TDA.main.controls.GameControls;
import TDA.main.world.World;
import TDA.scene.Scene;
import TDA.scene.prefabs.HomeScene;
import TDA.ui.TDAUi;
import woareXengine.io.window.DisplayMode;
import woareXengine.io.window.Window;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.GameSettings;
import woareXengine.util.Logger;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;

public class GameManager {
    private static Engine engine;
    private static GameConfigs configs;

    public static GameControls gameControls;

    public static Scene currentScene;

    public static World world;

    public static void init(GameConfigs gameConfigs) {
        Logger.info("Initializing Game manager");
        configs = gameConfigs;
        engine = Engine.init(gameConfigs);

        gameControls = new GameControls();

        currentScene = HomeScene.init();

        world = new World();
        world.init();
    }

    public static void update() {
        if (gameControls.windowControls.isDisplayModeSwitchPressed()) {
            switch (GameSettings.getDisplayMode()) {
                case FULLSCREEN -> GameSettings.setDisplayMode(DisplayMode.WINDOWED);
                case WINDOWED -> GameSettings.setDisplayMode(DisplayMode.FULLSCREEN);
            }
        }

        if (Engine.keyboard().isKeyDown(GLFW_KEY_I)) {
            GameSettings.setViewportSize(1920, 1080);
        }

        if (gameControls.developerControls.isDevModeToggled()) {
            Engine.instance().debugging = !Engine.instance().debugging;
            Logger.debug("Toggled debugging: " + Engine.instance().debugging);
        }

        TDAUi.get().gameUi.update();
        currentScene.update();
        currentScene.render();

        engine.update();
    }

    public static boolean readyToClose() {
        return engine.isCloseRequested();
    }

    public static void cleanUp() {
        currentScene.cleanUp();
        engine.closeEngine();
    }
}
