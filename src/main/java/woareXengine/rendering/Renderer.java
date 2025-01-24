package woareXengine.rendering;

import woareXengine.openglWrapper.OpenGlUtils;
import woareXengine.openglWrapper.framebuffer.Framebuffer;
import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.rendering.renderData.RenderObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

public abstract class Renderer<T extends RenderObject> {
    protected final int MAX_BATCH_SIZE = 2;
    protected double start, end;

    protected List<RenderBatch> batches;
    public List<T> data;

    protected Shader currentShader;
    protected Framebuffer framebuffer;

    protected final int[] textureSlots = {0, 1, 2, 3, 4, 5, 6, 7};

    public Renderer() {
        this.data = new ArrayList<>();
        this.batches = new ArrayList<>();
        init();
    }

    public void add(T object) {
        data.add(object);
    }

    public void remove(T object) {
        data.remove(object);
    }

    protected abstract Shader createShader();

    protected abstract Framebuffer createFramebuffer();

    protected abstract RenderBatch createBatch(int zIndex);

    protected abstract void rebuffer();


    private void init() {
        framebuffer = createFramebuffer();
        currentShader = createShader();
    }

    protected void startBatches() {
        for (RenderBatch batch : batches) {
            batch.start();
        }
    }

    protected void finishBatches() {
        for (RenderBatch batch : batches) {
            batch.finish();
        }
    }

    protected void uploadUniforms() {
        currentShader.uploadUniforms();
    }

    protected RenderBatch getAvailableBatch(Texture texture, int zIndex) {
        for (RenderBatch batch : batches) {
            if (batch.hasVertexRoom() && batch.zIndex() == zIndex) {
                if (texture == null || batch.hasTextureRoom()) {
                    return batch;
                }
            }
        }

        RenderBatch batch = createBatch(zIndex);
        batch.init();
        batch.start();
        batches.add(batch);
        Collections.sort(batches);
        return batch;
    }

    public void render() {
        framebuffer.bind();
//        Logger.log("Bound to framebuffer (id " + framebuffer.getFboID() + ") in " + this.getClass().getSimpleName() + ".render()");
        prepare();
        currentShader.use();
        uploadUniforms();

        startBatches();
        rebuffer();
        finishBatches();

        for (RenderBatch batch : batches) {
            batch.bind();
            OpenGlUtils.showWireframe(false);
            glDrawElements(batch.primitive.openglPrimitive, batch.getVertexCount(), GL_UNSIGNED_INT, 0);
            batch.unbind();
        }
        currentShader.detach();
        framebuffer.unbind();
    }

    public void cleanUp() {
        framebuffer.cleanUp();
        currentShader.cleanUp();
    }

    protected abstract void prepare();
}
