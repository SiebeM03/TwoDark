package old.engine.util;

public final class Engine {
    private static final Engine instance = new Engine();

    private float deltaTime;

    private Engine() {
        deltaTime = 0;
    }

    public static Engine getInstance() {
        return instance;
    }

    public static float deltaTime() {
        return getInstance().getDeltaTime();
    }

    public static void updateDeltaTime(float deltaTime) {
        getInstance().deltaTime = deltaTime;
    }

    private float getDeltaTime() {
        return deltaTime;
    }
}
