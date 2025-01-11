package TDA.main;

import TDA.main.configs.BuildConfigs;
import woareXengine.util.Logger;

public class TwoDarkGame {
    public static void main(String[] args) {
        GameManager.init(new BuildConfigs());
        Logger.success("Game Manager initialized");

        Logger.info("Starting game loop");
        while (!GameManager.readyToClose()) {
            GameManager.update();
        }
        Logger.info("Ending game loop");

        GameManager.cleanUp();
        Logger.success("Game Manager cleaned up");
    }
}
