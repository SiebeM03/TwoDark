package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.entities.ecs.Entity;
import TDA.main.GameManager;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.rendering.pickingRenderer.PickingRenderer;

public class ClickableEntity extends Component {

    private PickingRenderer pickingRenderer;

    public void init() {
        this.pickingRenderer = TDARenderSystem.get().renderer.getPickingRenderer();

        this.pickingRenderer.add(entity.getComponent(QuadComponent.class).quad);
    }

    @Override
    public void update() {

    }

    /** @return whether the mouse is currently hovering the entity. Ignores alpha values below 0.5 (configured in <i>picking.glsl</i>) */
    public boolean isMouseOver() {
        Entity hoveredEntity = GameManager.currentScene.getClickableEntityAtMouse();
        if (hoveredEntity == null) return false;
        return entity.getId() == GameManager.currentScene.getClickableEntityAtMouse().getId();
    }
}
