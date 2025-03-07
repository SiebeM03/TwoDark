package TDA.main;

import TDA.entities.dinos.StatsComp;
import TDA.entities.dinos.types.giga.Giga;
import TDA.entities.dinos.types.rex.Rex;
import TDA.main.configs.GameConfigs;
import TDA.main.controls.GameControls;
import TDA.main.world.World;
import TDA.scene.Scene;
import TDA.scene.prefabs.battleScene.BattleScene;
import TDA.scene.prefabs.homeScene.HomeScene;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.Team.*;
import TDA.ui.TDAUi;
import woareXengine.io.window.DisplayMode;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.GameSettings;
import woareXengine.util.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class GameManager {
    private static Engine engine;
    private static GameConfigs configs;

    public static GameControls gameControls;

    public static HomeScene homeScene;
    public static Scene currentScene;

    public static World world;

    public static void init(GameConfigs gameConfigs) {

        Logger.info("Initializing Game manager");
        configs = gameConfigs;
        engine = Engine.init(gameConfigs);

        gameControls = new GameControls();

        homeScene = new HomeScene();
        currentScene = homeScene;
        currentScene.fill();
    }

    public static void update() {
        if (Engine.keyboard().keyPressEvent(GLFW_KEY_O)) {
            startBattle(new BattleContext(
                    new AlliedTeam(new Rex(100, 0, new StatsComp(100, 20, 10)), new Giga(100, 200, new StatsComp(100, 60, 10)), null),
                    new EnemyTeam(new Rex(800, 0, new StatsComp(89, 24, 20)), null, new Giga(800, 200, new StatsComp(60, 40, 50))))
            );
        }
        if (Engine.keyboard().keyPressEvent(GLFW_KEY_U)) {
            switchScene(homeScene);
        }


        if (gameControls.windowControls.isDisplayModeSwitchPressed()) {
            switch (GameSettings.getDisplayMode()) {
                case FULLSCREEN -> GameSettings.setDisplayMode(DisplayMode.WINDOWED);
                case WINDOWED -> GameSettings.setDisplayMode(DisplayMode.FULLSCREEN);
            }
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

    public static void switchScene(Scene scene) {
        currentScene = scene;
    }

    public static void startBattle(BattleContext context) {
        switchScene(new BattleScene(context));
        currentScene.fill();
    }

    public static void cleanUp() {
        currentScene.cleanUp();
        engine.closeEngine();
    }
}
