package testGame.resources;

import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.graphics.Window;
import engine.ui.RenderableComponent;
import engine.ui.Text;
import engine.ui.UIComponent;
import engine.ui.fonts.FontLoader;
import engine.util.AssetPool;
import org.joml.Vector2f;


import static engine.util.Color.BLACK;
import static engine.util.Color.WHITE;

public class ResourceCounterUI {
    private static final int ICON_SIZE = 30;
    private static final int BACKGROUND_HEIGHT = 20;
    private static final int BACKGROUND_WIDTH = 100;

    private Resource resource;
    private Text text;

    public ResourceCounterUI(Class<? extends Resource> clazz, float x, float y, UIComponent parent) {
        resource = ResourceManager.getResource(clazz);

        // Background
        UIComponent background = new RenderableComponent(clazz.getSimpleName() + "Background", WHITE, new Sprite());
        background.setTransform(new Transform(
                new Vector2f(x, y),
                new Vector2f(BACKGROUND_WIDTH, BACKGROUND_HEIGHT))
        );
        background.setNoInteraction();


        // Icon
        UIComponent icon = new RenderableComponent(
                clazz.getSimpleName() + "Icon",
                new Sprite().setTexture(AssetPool.getTexture(resource.texturePath))
        );
        icon.setTransform(new Transform(
                new Vector2f(-ICON_SIZE, (int) ((BACKGROUND_HEIGHT - ICON_SIZE) / 2)),
                new Vector2f(ICON_SIZE, ICON_SIZE)
        ));
        icon.setNoInteraction();
        background.addChild(icon);


        // Text
        text = new Text("", FontLoader.getOpenSans(), BLACK, 5, 0);
        text.setParent(background);


        // Initialize everything
        Window.getScene().addUIComponent(background);
        Window.getScene().addUIComponent(icon);

        parent.addChild(background);
    }

    public void update() {
        text.change(resource.amount() + "");
    }
}
