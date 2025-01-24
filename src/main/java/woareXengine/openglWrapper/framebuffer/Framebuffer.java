package woareXengine.openglWrapper.framebuffer;

import woareXengine.openglWrapper.textures.Texture;
import woareXengine.util.Logger;

import static org.lwjgl.opengl.GL30.*;

public class Framebuffer {
    private int fboID = 0;
    private Texture texture = null;

    private int width;
    private int height;

    /**
     * A wrapper for the already existing framebuffer. This is used for the default framebuffer.
     */
    private Framebuffer(int id) {
        this.fboID = id;
    }

    public Framebuffer(int width, int height) {
        fboID = glGenFramebuffers();
        bind();

        this.texture = new Texture(width, height);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.textureID, 0);

        int rboID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboID);

        if (!isComplete()) {
            Logger.error("Framebuffer is not complete!");
        }
    }


    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public boolean isComplete() {
        bind();
        boolean complete = glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE;
        unbind();
        return complete;
    }

    public void cleanUp() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDeleteFramebuffers(fboID);
    }


    public static Framebuffer createDefault() {
        return new Framebuffer(0);
    }

    public int getFboID() {
        return fboID;
    }
}
