package old.guis.topBar;

import old.engine.ecs.Sprite;
import old.engine.ecs.Transform;
import old.engine.graphics.Window;
import old.engine.ui.EventConsumer;
import old.engine.ui.RenderableComponent;
import old.engine.ui.Text;
import old.engine.ui.UIComponent;
import old.engine.ui.fonts.FontLoader;
import old.engine.util.Color;
import org.joml.Vector2f;
import old.scenes.Scene;

public class SceneButton extends EventConsumer {

    private Class<? extends Scene> sceneToLoad;
    private UIComponent rootComponent;

    public SceneButton(String text, float x, float y, float width, float height, Class<? extends Scene> sceneToLoad) {
        UIComponent button = new RenderableComponent(text, Color.WHITE, new Sprite());
        button.setTransform(new Transform(
                new Vector2f(x, y),
                new Vector2f(width, height)
        ));

        Text buttonText = new Text(text, FontLoader.getOpenSans(), Color.BLACK, 8, 10);
        buttonText.setParent(button);

        rootComponent = button;
        this.sceneToLoad = sceneToLoad;

        button.setEventConsumer(this);
        this.uiComponent = button;
    }

    public UIComponent getRootComponent() {
        return rootComponent;
    }


    @Override
    protected void onClick() {
        Window.changeScene(sceneToLoad);
    }

    @Override
    protected void onHover() {
    }

    @Override
    protected void onEnter() {

    }

    @Override
    protected void onLeave() {

    }
}
