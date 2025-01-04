package game.main;

import game.main.configs.BuildConfigs;

public class IdleArkGame {
    public static void main(String[] args) {
        GameManager.init(new BuildConfigs());

        while (!GameManager.readyToClose()) {
            GameManager.update();
        }
        GameManager.cleanUp();
    }
}
