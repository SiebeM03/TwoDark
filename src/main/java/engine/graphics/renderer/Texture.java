package engine.graphics.renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * A class representing a 2D texture in an OpenGL context.
 * Provides functionality to load textures from image files, create blank textures,
 * and manage texture parameters such as filtering and wrapping.
 */
public class Texture {
    private String filepath;
    private transient int texID;
    private int width, height;

    public Texture() {
        this.texID = -1;
        this.width = -1;
        this.height = -1;
    }

    public Texture(int width, int height) {
        this.filepath = "Generated";

        // Generate texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // The pixels value is NULL (0L), we don't have any data yet to send to the GPU
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);
    }

    /**
     * Initializes the texture by loading an image from the specified file path and generating a texture on the GPU.
     * This method sets texture parameters for wrapping and filtering and uploads the image data to the GPU.
     *
     * @param filepath the path to the image file to be used as the texture.
     *                 Supported formats include images that can be loaded by the STB library (e.g., PNG, JPG).
     * @throws AssertionError if the image cannot be loaded from the specified filepath or if the image contains an unsupported number of channels.
     *                        <p>
     *                        The following operations are performed in this method:
     *                        <ul>
     *                          <li>Generates a texture ID and binds it as a 2D texture.</li>
     *                          <li>Configures the texture wrapping mode to repeat in both the S and T directions.</li>
     *                          <li>Configures the filtering mode to use nearest-neighbor interpolation for both magnification and minification.</li>
     *                          <li>Loads the image using the STB library, flipping it vertically for correct texture orientation.</li>
     *                          <li>Determines the image format based on the number of color channels (RGB or RGBA).</li>
     *                          <li>Uploads the image data to the GPU.</li>
     *                          <li>Frees the memory allocated for the image once it is uploaded.</li>
     *                        </ul>
     *                        <p>
     *                        Note: If the image contains 3 color channels, it is treated as an RGB image. If it contains 4 channels, it is treated as an RGBA image.
     *                        Any other number of channels will cause an {@code AssertionError}.
     */
    public void init(String filepath) {
        this.filepath = filepath;

        // Generate texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1); IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if (image != null) {
            this.width = width.get(0);
            this.height = height.get(0);

            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                assert false : "Error: (Texture) Unknown number of channels '" + channels.get(0) + "'";
            }
        } else {
            assert false : "Error: (Texture) Could not load image '" + filepath + "'";
        }

        stbi_image_free(image);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getId() {
        return this.texID;
    }

    public String getFilepath() {
        return this.filepath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Texture)) return false;
        Texture oTex = (Texture) o;
        return oTex.getWidth() == this.width &&
                       oTex.getHeight() == this.height &&
                       oTex.getId() == this.texID &&
                       oTex.getFilepath().equals(this.filepath);
    }
}
