package TDA.entities.components.interactions;

import TDA.entities.main.Component;
import TDA.entities.main.Entity;
import TDA.entities.components.rendering.QuadComp;
import TDA.main.GameManager;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.rendering.pickingRenderer.PickingRenderer;

public class ClickableComp extends Component {

    private PickingRenderer pickingRenderer;

    public void init() {
        this.pickingRenderer = TDARenderSystem.get().renderer.getPickingRenderer();

        this.pickingRenderer.add(entity.getComponent(QuadComp.class).quad);
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
