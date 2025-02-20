package TDA.entities.components.rendering;

import TDA.entities.main.Component;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Layer;

public class QuadComp extends Component {
    public Quad quad;
    private Texture texture;

    public QuadComp(Texture texture, int zIndex) {
        this.texture = texture;
    }

    @Override
    public void init() {
        this.quad = new Quad(
                entity.transform.getWidth(), entity.transform.getHeight(),
                entity.transform.getPosition(),
                Layer.FOREGROUND
        );
        this.quad.setEntityID(entity.getId());
        this.quad.texture = texture;
        TDARenderSystem.get().renderer.getRenderer(Quad.class).add(this.quad);
    }

    @Override
    public void destroy() {
        TDARenderSystem.get().renderer.getRenderer(Quad.class).remove(quad);
    }
}
