package TDA.main.world;

import org.lwjgl.BufferUtils;
import woareXengine.mainEngine.Engine;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Assets;

import java.nio.ByteBuffer;

import static TDA.main.world.WorldConfigs.MAP_HEIGHT;
import static TDA.main.world.WorldConfigs.MAP_WIDTH;
import static org.lwjgl.opengl.GL30.*;

public class WorldGenerator {
    private Texture texture = Assets.getTexture("src/assets/images/map.png");

    private ByteBuffer pixels;

    protected void generatePixelBuffer() {
        int framebuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.textureID, 0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            assert false : "Error: (WorldGenerator) Framebuffer is not complete!";
        }

        pixels = BufferUtils.createByteBuffer(MAP_WIDTH * MAP_HEIGHT * 4);
        glReadPixels(0, 0, MAP_WIDTH, MAP_HEIGHT, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDeleteFramebuffers(framebuffer);
    }

    protected int readPixel(int x, int y) {
        int index = (x + y * MAP_WIDTH) * 4;
        return pixels.get(index) & 0xFF;
    }
}
