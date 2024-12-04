package engine.graphics.renderer;

import engine.ecs.GameObject;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Shader;
import engine.util.AssetPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;

    private List<RenderBatch> batches;
    private static Shader currentShader;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRenderer spriteRenderer) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == spriteRenderer.gameObject.zIndex()) {
                Texture tex = spriteRenderer.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(spriteRenderer);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, spriteRenderer.gameObject.zIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(spriteRenderer);
            Collections.sort(batches);
        }
    }

    public void remove(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            remove(spr);
        }
    }

    private void remove(SpriteRenderer sprite) {
        for (RenderBatch batch : batches) {
            if (batch.zIndex() == sprite.gameObject.zIndex()) {
                batch.removeSprite(sprite);
            }
        }
    }

    public static void bindShader(Shader shader) {
        currentShader = shader;
    }

    public static Shader getBoundShader() {
        return currentShader;
    }

    public void render() {
        currentShader.use();
        boolean renderingPickingTexture = currentShader == AssetPool.getShader("assets/shaders/pickingShader.glsl");
        for (RenderBatch batch : batches) {
            if (renderingPickingTexture && batch.zIndex() == 999) continue;
            batch.render();
        }
    }
}
