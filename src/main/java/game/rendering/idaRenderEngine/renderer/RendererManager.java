package game.rendering.idaRenderEngine.renderer;

import game.rendering.idaRenderEngine.IdaRenderer;
import game.rendering.idaRenderEngine.renderSystem.IdaRenderSystem;
import game.scene.Scene;
import org.joml.Vector2f;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.rendering.Renderer;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.rendering.quadRenderer.QuadRenderer;
import woareXengine.rendering.renderData.RenderObject;
import woareXengine.util.Color;

import java.util.HashMap;
import java.util.Map;

public class RendererManager implements IdaRenderer {

    private final Renderer<Quad> quadRenderer;

    private Map<Class<? extends RenderObject>, Renderer<? extends RenderObject>> rendererMap = new HashMap<>();
    // TODO add all renderers

    public RendererManager() {
        quadRenderer = new QuadRenderer();
//        for (int i = 0; i < 128; i++) {
//            for (int j = 0; j < 72; j++) {
//                quadRenderer.add(new Quad(9, 9, new Color(i, j, 0), new Vector2f(i * 10, j * 10), 2));
//            }
//        }
        Quad quad = new Quad(300, 300, new Vector2f(100, 100), 3);
        quad.texture = Texture.getTexture("src/assets/images/seperateImages/stone2.png");
        quadRenderer.add(quad);

        Quad quad2 = new Quad(300, 300, new Vector2f(200, 100), 2);
        quad2.texture = Texture.getTexture("src/assets/images/seperateImages/metal2.png");
        quadRenderer.add(quad2);

        rendererMap.put(Quad.class, quadRenderer);
        // TODO init all renderers
    }


    @Override
    public void renderScene(Scene scene, IdaRenderSystem sceneData) {
        // TODO render all renderers
        quadRenderer.render();
    }

    @Override
    public void cleanUp() {
        // TODO call cleanUp() on all renderers
        quadRenderer.cleanUp();
    }
}
