package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.pickingRenderer.PickingRenderer;
import woareXengine.util.Logger;

public class ClickableEntity extends Component {

    private PickingRenderer pickingRenderer;

    @Override
    public void init() {
        this.pickingRenderer = (PickingRenderer) TDARenderSystem.get().renderer.getPickingRenderer();

        this.pickingRenderer.add(entity.getComponent(QuadComponent.class).quad);
    }

    @Override
    public void update() {
        int x = (int) (Engine.mouse().getX() * Engine.window().getPixelWidth());
        int y = (int) (Engine.mouse().getY() * Engine.window().getPixelHeight());
//        int x = (int) GameManager.currentScene.camera.getMouseWorldX(),
//        int y = (int) GameManager.currentScene.camera.getMouseWorldY()

        Logger.log("Reading pixel " + x + " " + y + ": " + pickingRenderer.readPixel(x, y) + "");
    }


}
