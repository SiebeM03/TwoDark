package guis.topBar;

import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.graphics.Window;
import engine.ui.BaseComponent;
import engine.ui.RenderableComponent;
import engine.ui.Text;
import engine.ui.UIComponent;
import engine.ui.fonts.FontLoader;
import engine.util.AssetPool;
import org.joml.Vector2f;
import testGame.resources.Resource;
import testGame.resources.ResourceManager;


import static engine.util.Color.BLACK;
import static engine.util.Color.WHITE;

public class ResourceCounterUI extends RenderableComponent {
    private static final int ICON_SIZE = 30;
    private static final int BACKGROUND_HEIGHT = 20;
    private static final int BACKGROUND_WIDTH = 100;

    private Resource resource;
    private Text text;

    public ResourceCounterUI(Class<? extends Resource> clazz, float x, float y) {
        super(clazz.getSimpleName() + "Background", WHITE, new Sprite());

        resource = ResourceManager.getResource(clazz);

        // Background
        this.setTransform(new Transform(
                new Vector2f(x, y),
                new Vector2f(BACKGROUND_WIDTH, BACKGROUND_HEIGHT))
        );
        this.setNoInteraction();

        // Icon
        UIComponent icon = new RenderableComponent(
                clazz.getSimpleName() + "Icon",
                new Sprite().setTexture(AssetPool.getTexture(resource.getTexturePath()))
        );
        icon.setTransform(new Transform(
                new Vector2f(-ICON_SIZE, (int) ((BACKGROUND_HEIGHT - ICON_SIZE) / 2)),
                new Vector2f(ICON_SIZE, ICON_SIZE)
        ));
        icon.setNoInteraction();
        this.addChild(icon);


        // Text
        text = new Text("", FontLoader.getOpenSans(), BLACK, 5, 0);
        text.setParent(this);
    }

    @Override
    public void update() {
        super.update();
        text.change(resource.amount() + "");
    }
}
