package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.pickingRenderer.PickingRenderer;

public class ClickableEntity extends Component {

    private PickingRenderer pickingRenderer;

    private boolean isMouseOver = false;

    @Override
    public void init() {
        this.pickingRenderer = (PickingRenderer) TDARenderSystem.get().renderer.getPickingRenderer();

        this.pickingRenderer.add(entity.getComponent(QuadComponent.class).quad);
    }

    @Override
    public void update() {

    }

    /** @return whether the mouse is currently hovering the entity. Ignores alpha values below 0.5 (configured in picking.glsl) */
    public boolean isMouseOver() {
        return entity.getId() == pickingRenderer.readPixel(
                               (int) (Engine.mouse().getX() * Engine.window().getPixelWidth()),
                               (int) (Engine.mouse().getY() * Engine.window().getPixelHeight())
                       );
    }
}
