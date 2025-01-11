package TDA.main;

import TDA.main.configs.BuildConfigs;

public class TwoDarkGame {
    public static void main(String[] args) {
        GameManager.init(new BuildConfigs());

        while (!GameManager.readyToClose()) {
            GameManager.update();
        }
        GameManager.cleanUp();
    }
}
