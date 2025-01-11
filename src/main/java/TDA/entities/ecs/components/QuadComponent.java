package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
import woareXengine.util.Layer;

public class QuadComponent extends Component {
    public Quad quad;
    private String filepath;

    public QuadComponent(String filepath, int zIndex) {
        this.filepath = filepath;
    }

    @Override
    public void init() {
        this.quad = new Quad(
                entity.transform.getWidth(), entity.transform.getHeight(),
                entity.transform.getPosition(),
                Layer.FOREGROUND
        );
        if (!filepath.isBlank()) {
            this.quad.texture = Assets.getTexture(filepath);
        }
        TDARenderSystem.get().renderer.getRenderer(Quad.class).add(this.quad);
    }

    @Override
    public void destroy() {
        TDARenderSystem.get().renderer.getRenderer(Quad.class).remove(quad);
    }
}
