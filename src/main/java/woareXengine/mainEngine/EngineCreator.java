package woareXengine.mainEngine;

import org.lwjgl.glfw.GLFWImage.Buffer;
import woareXengine.io.Timer;
import woareXengine.io.userInputs.Keyboard;
import woareXengine.io.userInputs.Mouse;
import woareXengine.io.window.Window;

import static org.lwjgl.opengl.GL.createCapabilities;

public class EngineCreator {

    private Window window;
    private Mouse mouse;
    private Keyboard keyboard;
    private Timer timer;

    protected Engine init(EngineConfigs configs) {
        initEssentialSystems(configs);
        initEngineSystems(configs);
        return new Engine(configs, window, mouse, keyboard, timer);
    }

    /**
     * Init the systems that are needed to show the splash screen.
     *
     * @param configs Engine configs.
     */
    private void initEssentialSystems(EngineConfigs configs) {
        this.window = setUpWindow(configs);
        createCapabilities();
        this.timer = new Timer();
        this.keyboard = new Keyboard(window.getId());
        this.mouse = new Mouse(window);
    }

    /**
     * Init the rest of the engine systems. The splash screen will be showing while this is happening.
     *
     * @param configs Engine configs.
     */
    private void initEngineSystems(EngineConfigs configs) {

    }

    private Window setUpWindow(EngineConfigs configs) {
//        Buffer buffer = loadWindowIcon();
        Window window = Window.newWindow(configs.windowWidth, configs.windowHeight, configs.windowTitle)
                                .setVsync(configs.vsync)
                                .displayMode(configs.displayMode)
                                // .withIcon(buffer)
                                .setMSAA(configs.msaa)
                                .create();
        return window;
    }

}
