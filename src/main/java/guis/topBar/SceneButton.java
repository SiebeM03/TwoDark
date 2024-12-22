package guis.topBar;

import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.graphics.Window;
import engine.ui.*;
import engine.ui.fonts.FontLoader;
import engine.util.Color;
import org.joml.Vector2f;
import scenes.Scene;

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
