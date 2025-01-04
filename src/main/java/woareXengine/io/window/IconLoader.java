package woareXengine.io.window;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;

import java.io.File;

public class IconLoader {
    public static Buffer loadIcon(File... files) {
        Buffer imageBuffer = GLFWImage.malloc(files.length);
        for (int i = 0; i < files.length; i++) {
            GLFWImage image;
            try {
                image = loadGlfwImage(files[i]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            imageBuffer.put(i, image);
            image.close();
        }
        return imageBuffer;
    }

    private static GLFWImage loadGlfwImage(File file) throws Exception {
//        RawByteImage rawImage = RawByteImage.load(file);
//        GLFWImage image = GLFWImage.malloc();
//        image.set(rawImage.getWidth(), rawImage.getHeight(), rawImage.getImage());
//        return image;
        return null;
    }
}
