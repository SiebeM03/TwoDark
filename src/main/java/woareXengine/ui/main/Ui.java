package woareXengine.ui.main;

import org.joml.Vector2f;
import woareXengine.io.userInputs.Keyboard;
import woareXengine.io.userInputs.Mouse;
import woareXengine.io.window.Window;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.uiRenderer.UiRenderer;
import woareXengine.util.Transform;

public class Ui {
    private static final UiContainer CONTAINER = new UiContainer();

    private static UiRenderer uiRenderer;

    public static int displayWidthPixels;
    public static int displayHeightPixels;

    public static Mouse mouse;
    public static Keyboard keyboard;

    private static boolean mouseInteractionEnabled = true;

    public static void init(Window window, Mouse mouse, Keyboard keyboard) {
        Ui.uiRenderer = new UiRenderer();
        uiRenderer.add(CONTAINER);
        Ui.displayWidthPixels = window.getPixelWidth();
        Ui.displayHeightPixels = window.getPixelHeight();
        Ui.mouse = mouse;
        Ui.keyboard = keyboard;
        window.addSizeChangeListener(Ui::notifyScreenSizeChange);
        notifyScreenSizeChange(window.getPixelWidth(), window.getPixelHeight());
    }

    public static void update() {
        CONTAINER.update(Engine.getDelta(), true);
        uiRenderer.render();
    }

    public static void notifyScreenSizeChange(int width, int height) {
        displayWidthPixels = width;
        displayHeightPixels = height;
        Ui.CONTAINER.transform = new Transform(new Vector2f(0, 0), new Vector2f(width, height));
    }

    public static UiContainer getContainer() {
        return CONTAINER;
    }

    public static void enableMouseInteraction(boolean enable) {
        mouseInteractionEnabled = enable;
    }

    public static boolean isMouseEnabled() {
        return mouseInteractionEnabled;
    }
}
