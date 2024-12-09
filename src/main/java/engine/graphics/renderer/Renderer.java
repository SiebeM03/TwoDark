package engine.graphics.renderer;

import engine.ecs.GameObject;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Primitive;
import engine.graphics.Shader;
import engine.graphics.ShaderDatatype;
import engine.util.AssetPool;
import engine.util.Layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Renderer class is responsible for rendering all GameObjects in the game. It separates GameObjects by their Z-Index into {@link RenderBatch batches}.
 * Each batch is then rendered in order of their Z-Index to ensure that GameObjects with higher Z-Index are rendered on top of GameObjects with lower Z-Index.
 * <p>This class also keeps track of the {@link #currentShader current shader}.</p>
 */
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
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, spriteRenderer.gameObject.zIndex(), Primitive.QUAD,
                    // Postion             color                  texCoords              texId                 entityId              cooldown
                    ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT4, ShaderDatatype.FLOAT2, ShaderDatatype.FLOAT, ShaderDatatype.FLOAT, ShaderDatatype.FLOAT);
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
            // Skip rendering picking texture for all batches with NO_INTERACTION (tooltip, etc)
            if (renderingPickingTexture && batch.zIndex() == Layer.NO_INTERACTION) continue;
            batch.render();
        }
    }
}
