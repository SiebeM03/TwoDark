package old.engine.graphics.renderer;

import old.engine.ecs.GameObject;
import old.engine.graphics.Shader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

/**
 * The Renderer class is responsible for rendering all GameObjects in the game. It separates GameObjects by their Z-Index into {@link RenderBatch batches}.
 * Each batch is then rendered in order of their Z-Index to ensure that GameObjects with higher Z-Index are rendered on top of GameObjects with lower Z-Index.
 * <p>This class also keeps track of the {@link #currentShader current shader}.</p>
 */
public abstract class Renderer {
    /**
     * Texture slots to be uploaded to the shader. You don't have to upload them in your custom renderer.
     */
    protected final int[] textureSlots = {0, 1, 2, 3, 4, 5, 6, 7};

    protected List<RenderBatch> batches;
    protected Shader currentShader;
    protected Framebuffer framebuffer;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    /**
     * Add a gameObject to the renderer, and if it contains a component that affects rendering, like a sprite or light, those are added to a batch.
     */
    public void add(GameObject gameObject) {
    }

    /**
     * Remove a gameObject from the renderer if it contains the component that gets rendered.
     */
    public void remove(GameObject gameObject) {
    }

    // TODO make protected
    public abstract Shader createShader();

    protected abstract Framebuffer createFramebuffer();

    /**
     * Create a new Batch with appropriate parameters
     */
    protected abstract RenderBatch createBatch(int zIndex);


    /**
     * Upload the required uniforms
     */
    protected abstract void uploadUniforms(Shader shader);

    /**
     * Rebuffer all the data into batches
     */
    protected abstract void rebuffer();

    public void init() {
        framebuffer = createFramebuffer();
        currentShader = createShader();
    }

    /**
     * Start batches. Ready for buffering data
     */
    protected void start() {
        for (RenderBatch batch : batches) {
            batch.start();
        }
    }

    /**
     * Finish Setting data for all batches. Upload to gpu ready for rendering
     */
    protected void finish() {
        for (RenderBatch batch : batches) {
            batch.finish();
        }
    }

    /**
     * Get the batch in which the current data can be submitted
     * Has to be called PER PRIMITIVE SUBMISSION
     */
    public RenderBatch getAvailableBatch(Texture texture, int zIndex) {
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == zIndex)
                return batch;

            if (!batch.hasTextureRoom())
                if (batch.hasTexture(texture) && batch.zIndex() == zIndex)
                    return batch;
        }

        // All batches full
        RenderBatch batch = createBatch(zIndex);
        batch.init();
        batch.start();
        batches.add(batch);
        Collections.sort(batches);
        return batch;
    }

    public void render() {
        framebuffer.bind();
        prepare();
        currentShader.use();
        uploadUniforms(currentShader);

        start();
        rebuffer();
        finish();

        for (RenderBatch batch : batches) {
            batch.bind();
            // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // render wireframe
            glDrawElements(batch.primitive().openglPrimitive, batch.getVertexCount(), GL_UNSIGNED_INT, 0);
            batch.unbind();
        }
        currentShader.detach();
        framebuffer.unbind();
    }

    /**
     * Prepare for rendering. Do anything like setting background here.
     */
    protected abstract void prepare();

    public Framebuffer getFramebuffer() {
        return framebuffer;
    }
}
