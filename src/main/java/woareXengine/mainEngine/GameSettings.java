package woareXengine.mainEngine;

import woareXengine.io.window.DisplayMode;
import woareXengine.util.Logger;

public class GameSettings {
    protected static int viewportWidth;
    protected static int viewportHeight;
    protected static DisplayMode displayMode;

    public static void setViewportSize(int width, int height) {
        if (viewportWidth == width && viewportHeight == height) return;
        if (viewportWidth / viewportHeight != 16 / 9) {
            Logger.error("Invalid aspect ratio");
            return;
        }

        viewportWidth = width;
        viewportHeight = height;
        Engine.window().setScreenSize(viewportWidth, viewportHeight);
    }

    public static DisplayMode getDisplayMode() {
        return displayMode;
    }

    public static void setDisplayMode(DisplayMode displayMode) {
        GameSettings.displayMode = displayMode;
        Engine.window().changeDisplayMode(displayMode);
    }
}
