package woareXengine.mainEngine;

import woareXengine.io.Timer;
import woareXengine.io.userInputs.Keyboard;
import woareXengine.io.userInputs.Mouse;
import woareXengine.io.window.Window;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

public class Engine {
    private static Engine currentInstance;

    private final EngineConfigs configs;

    private final Window window;
    private final Mouse mouse;
    private final Keyboard keyboard;

    private final Timer timer;
    private boolean closeFlag = false;

    public int defaultFboId;

    public boolean debugging = false;


    public Engine(EngineConfigs configs, Window window, Mouse mouse, Keyboard keyboard, Timer timer) {
        this.configs = configs;
        this.window = window;
        this.mouse = mouse;
        this.keyboard = keyboard;
        this.timer = timer;
        defaultFboId = glGenFramebuffers();
    }

    public static Engine instance() {
        if (currentInstance == null)
            throw new IllegalStateException("Engine not initialized yet.");
        return currentInstance;
    }

    public boolean isCloseRequested() {
        return closeFlag || window.closeButtonPressed();
    }

    public void update() {
        timer.update();
        keyboard.update();
        mouse.update();
        window.update();
        glClearColor(configs.backgroundColor.getR(), configs.backgroundColor.getG(), configs.backgroundColor.getB(), configs.backgroundColor.getA());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void requestClose() {
        this.closeFlag = true;
    }

    public void closeEngine() {
        window.destroy();
    }

    /**
     * Initializes an instance of the Engine. A splash screen will be shown while the engine is setting up. When this
     * method returns the engine and its resources are guaranteed to be fully loaded. The splash screen will still be
     * showing, allowing the user to continue loading the game while the splash screen shows.
     *
     * @param configs The engine settings.
     * @return The engine.
     */
    public static Engine init(EngineConfigs configs) {
        if (currentInstance != null) {
            System.err.println("Engine has already been initialized!");
        } else {
            currentInstance = new EngineCreator().init(configs);
        }
        return currentInstance;
    }


    public static float getDelta() {
        return instance().timer.getDeltaTime();
    }

    public static Window window() {
        return instance().window;
    }

    public static Mouse mouse() {
        return instance().mouse;
    }

    public static Keyboard keyboard() {
        return instance().keyboard;
    }
}
