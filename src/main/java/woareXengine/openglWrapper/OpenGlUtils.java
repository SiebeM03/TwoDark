package woareXengine.openglWrapper;

import static org.lwjgl.opengl.GL11.*;

public class OpenGlUtils {
    private static boolean inWireframe = false;

    public static void showWireframe(boolean show) {
        if (show && !inWireframe) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            inWireframe = true;
        } else if (!show && inWireframe) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            inWireframe = false;
        }
    }
}
