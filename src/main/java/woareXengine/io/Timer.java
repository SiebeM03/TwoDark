package woareXengine.io;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
    private float deltaTime;
    private double currentFrameBeginTime = 0;
    private double lastFrameEndTime = 0;

    public void update() {
        this.currentFrameBeginTime = glfwGetTime();
        this.deltaTime = (float) (currentFrameBeginTime - lastFrameEndTime);
        this.lastFrameEndTime = currentFrameBeginTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }
}
