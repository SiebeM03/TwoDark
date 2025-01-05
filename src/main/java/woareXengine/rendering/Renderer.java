package woareXengine.rendering;

import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.rendering.renderData.RenderObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public abstract class Renderer<T extends RenderObject> {
    protected final int MAX_BATCH_SIZE = 2;
    protected double start, end;

    protected List<RenderBatch> batches;
    public List<T> data;
    protected Shader currentShader;
    protected final int[] textureSlots = {0, 1, 2, 3, 4, 5, 6, 7};

    public Renderer() {
        this.batches = new ArrayList<>();
        init();
    }

    public abstract void add(T object);

    public abstract void remove(T object);

    protected abstract Shader createShader();

    protected abstract RenderBatch createBatch(int zIndex);

    protected abstract void rebuffer();


    private void init() {
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
        prepare();
        currentShader.use();
        uploadUniforms();

        startBatches();
        rebuffer();
        finishBatches();

        for (RenderBatch batch : batches) {
            batch.bind();
            // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // render wireframe
            glDrawElements(batch.primitive.openglPrimitive, batch.getVertexCount(), GL_UNSIGNED_INT, 0);
            batch.unbind();
        }
        currentShader.detach();
    }

    public void cleanUp() {
        currentShader.cleanUp();
    }

    protected abstract void prepare();
}
