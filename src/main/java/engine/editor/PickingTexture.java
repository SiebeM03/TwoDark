package engine.editor;

import engine.graphics.Shader;
import engine.graphics.renderer.Renderer;
import engine.util.AssetPool;
import scenes.Scene;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;

/**
 * This class is responsible for managing a picking texture used for object selection
 * in a rendered scene. It creates a framebuffer and associated textures to facilitate
 * reading object IDs based on pixel data.
 */
public class PickingTexture {
    /**
     * ID of the picking texture
     */
    private int pickingTextureId;
    /**
     * ID of the framebuffer object
     */
    private int fboID;
    /**
     * ID of the depth texture //TODO research what this is for
     */
    private int depthTexture;
    /**
     * The picking shader used to render the scene
     */
    private Shader pickingShader;

    /**
     * Initializes the picking texture with the specified dimensions and loads the picking shader.
     *
     * @param width  the width of the texture in pixels
     * @param height the height of the texture in pixels
     */
    public PickingTexture(int width, int height) {
        if (!init(width, height)) {
            assert false : "Error initializing picking texture";
        }
        pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");
    }


    /**
     * Sets up the framebuffer and its associated textures for picking operations.
     *
     * <p>Steps performed:
     * <ul>
     *     <li>Generates and binds a framebuffer.</li>
     *     <li>Creates and attaches a color texture to the framebuffer.</li>
     *     <li>Creates and attaches a depth texture to the framebuffer.</li>
     *     <li>Configures framebuffer read and draw settings.</li>
     *     <li>Validates framebuffer completeness.</li>
     * </ul>
     *
     * @param width  the width of the textures in pixels
     * @param height the height of the textures in pixels
     * @return true if the framebuffer was successfully initialized, false otherwise
     */
    public boolean init(int width, int height) {
        // Generate framebuffer
        fboID = glGenFramebuffers();
        this.bind();

        // Create the texture to render the data to and attach it to our framebuffer
        pickingTextureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, pickingTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB32F, width, height, 0, GL_RGB, GL_FLOAT, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, pickingTextureId, 0);

        // Create texture object for the depth buffer
        glEnable(GL_TEXTURE_2D);
        depthTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, depthTexture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthTexture, 0);

        // Disable the reading
        glReadBuffer(GL_NONE);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            assert false : "Error: Framebuffer is not complete";
            return false;
        }
        this.unbind();
        return true;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void enableWriting() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fboID);
    }

    public void disableWriting() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    /**
     * Reads a pixel from the picking texture at the specified screen coordinates
     * to determine the ID of the game object at that location.
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the ID of the game object at the specified pixel, or -1 if no object is present
     */
    public int readPixel(int x, int y) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, fboID);
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        float[] pixels = new float[3];
        glReadPixels(x, y, 1, 1, GL_RGB, GL_FLOAT, pixels);

        return (int) (pixels[0] - 1);
    }

    /**
     * Renders the specified scene to the picking texture, enabling object selection.
     *
     * <p>Steps performed:
     * <ul>
     *     <li>Disables blending for clarity in the picking operation.</li>
     *     <li>Binds the framebuffer and clears its color and depth buffers.</li>
     *     <li>Renders the scene using the picking shader.</li>
     *     <li>Re-enables blending after rendering is complete.</li>
     * </ul>
     *
     * @param width         the width of the viewport in pixels
     * @param height        the height of the viewport in pixels
     * @param sceneToRender the scene to render into the picking texture
     */
    public void render(int width, int height, Scene sceneToRender) {
        glDisable(GL_BLEND);
        enableWriting();
        glViewport(0, 0, width, height);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Renderer.bindShader(pickingShader);
        sceneToRender.render();

        disableWriting();
        glEnable(GL_BLEND);
    }
}
