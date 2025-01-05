package TDA.main.configs;

import woareXengine.io.window.DisplayMode;

public class BuildConfigs extends GameConfigs {

    public BuildConfigs() {
        super.windowWidth = 1280;
        super.windowHeight = 720;
        super.windowTitle = "Idle Ark";
        super.cheats = true;

        super.displayMode = DisplayMode.WINDOWED;
    }
}
