package woareXengine.io.window;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import woareXengine.rendering.debug.DebugDraw;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long id;

    private int pixelWidth, pixelHeight;
    private int desiredWidth, desiredHeight;
    private int widthScreenCoords, heightScreenCoords;

    private List<WindowSizeListener> windowSizeListeners = new ArrayList<>();

    private DisplayMode displayMode;
    private boolean vsync;


    public static WindowBuilder newWindow(int width, int height, String title) {
        return new WindowBuilder(width, height, title);
    }

    protected Window(long id, int desiredWidth, int desiredHeight, DisplayMode displayMode, boolean vsync) {
        this.id = id;
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
        this.displayMode = displayMode;
        this.vsync = vsync;
        getInitialWindowSizes();
        addScreenSizeListener();
        addPixelSizeListener();

        createCapabilities();
        // Alpha blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_MULTISAMPLE);
    }

    public void update() {
        DebugDraw.beginFrame();
        DebugDraw.draw();

        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    public long getId() {
        return id;
    }

    public int getScreenCoordWidth() {
        return widthScreenCoords;
    }

    public int getScreenCoordHeight() {
        return heightScreenCoords;
    }

    public float getAspectRatio() {
        return (float) pixelWidth / pixelHeight;
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        glfwSwapInterval(vsync ? 1 : 0);
    }

    public boolean closeButtonPressed() {
        return glfwWindowShouldClose(id);
    }

    public void destroy() {
        glfwFreeCallbacks(id);
        glfwDestroyWindow(id);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void changeDisplayMode(DisplayMode displayMode) {
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidMode = glfwGetVideoMode(monitor);
        if (displayMode == DisplayMode.FULLSCREEN) {
            switchToFullScreen(monitor, vidMode);
        } else if (displayMode == DisplayMode.WINDOWED) {
            switchToWindowed(vidMode);
        }
        // TODO implement BORDERLESS mode

        glViewport(0, 0, pixelWidth, pixelHeight);
        this.displayMode = displayMode;
    }

    private void switchToFullScreen(long monitor, GLFWVidMode vidMode) {
        this.desiredWidth = widthScreenCoords;
        this.desiredHeight = heightScreenCoords;
        glfwSetWindowMonitor(id, monitor, 0, 0, vidMode.width(), vidMode.height(), vidMode.refreshRate());
        glfwSwapInterval(vsync ? 1 : 0);
    }

    private void switchToWindowed(GLFWVidMode vidMode) {
        glfwSetWindowMonitor(id, NULL, 0, 0, desiredWidth, desiredHeight, vidMode.refreshRate());
        glfwSetWindowPos(id, (vidMode.width() - desiredWidth) / 2, (vidMode.height() - desiredHeight) / 2);
    }

    private void addScreenSizeListener() {
        glfwSetWindowSizeCallback(id, (window, width, height) -> {
            if (validSizeChange(width, height, widthScreenCoords, heightScreenCoords)) {
                this.widthScreenCoords = width;
                this.heightScreenCoords = height;
            }
        });
    }

    private void addPixelSizeListener() {
        glfwSetFramebufferSizeCallback(id, (window, width, height) -> {
            if (validSizeChange(width, height, pixelWidth, pixelHeight)) {
                this.pixelWidth = width;
                this.pixelHeight = height;
                // Update the viewport and notify listeners (e.g. cameras)
                glViewport(0, 0, pixelWidth, pixelHeight);
                windowSizeListeners.forEach(listener -> listener.sizeChanged(pixelWidth, pixelHeight));
            }
        });
    }

    private void getInitialWindowSizes() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer widthBuff = stack.mallocInt(1);
            IntBuffer heightBuff = stack.mallocInt(1);
            getInitialScreenSize(widthBuff, heightBuff);
            getInitialPixelSize(widthBuff, heightBuff);
        }
    }

    private void getInitialScreenSize(IntBuffer widthBuff, IntBuffer heightBuff) {
        glfwGetWindowSize(id, widthBuff, heightBuff);
        this.widthScreenCoords = widthBuff.get(0);
        this.heightScreenCoords = heightBuff.get(0);
        widthBuff.clear();
        heightBuff.clear();
    }

    private void getInitialPixelSize(IntBuffer widthBuff, IntBuffer heightBuff) {
        glfwGetFramebufferSize(id, widthBuff, heightBuff);
        this.pixelWidth = widthBuff.get(0);
        this.pixelHeight = heightBuff.get(0);
        widthBuff.clear();
        heightBuff.clear();
    }

    private boolean validSizeChange(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        if (newWidth == 0 || newHeight == 0) {
            return false;
        }
        return newWidth != oldWidth || newHeight != oldHeight;
    }

    public void addSizeChangeListener(WindowSizeListener listener) {
        windowSizeListeners.add(listener);
    }
}
