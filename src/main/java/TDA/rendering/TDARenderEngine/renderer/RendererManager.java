package TDA.rendering.TDARenderEngine.renderer;

import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.scene.Scene;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.rendering.quadRenderer.QuadRenderer;
import woareXengine.rendering.renderData.RenderObject;

import java.util.HashMap;
import java.util.Map;

public class RendererManager {


    public Map<Class<? extends RenderObject>, Renderer<? extends RenderObject>> rendererMap = new HashMap<>();

    private final Renderer<Quad> quadRenderer;
    // TODO add all renderers

    public RendererManager() {
        quadRenderer = new QuadRenderer();

        rendererMap.put(Quad.class, quadRenderer);
        // TODO init all renderers
    }

    private <T extends RenderObject> void putRenderer(Class<T> clazz, Renderer<T> renderer) {
        rendererMap.put(clazz, renderer);
    }

    @SuppressWarnings("unchecked")
    public <T extends RenderObject> Renderer<T> getRenderer(Class<T> clazz) {
        return (Renderer<T>) rendererMap.get(clazz);
    }

    public void renderScene(Scene scene, TDARenderSystem sceneData) {
        // TODO render all renderers
        quadRenderer.render();
    }

    public void cleanUp() {
        // TODO call cleanUp() on all renderers
        quadRenderer.cleanUp();
    }
}
