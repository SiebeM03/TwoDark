package old.guis.topBar;

import old.engine.ecs.Sprite;
import old.engine.ecs.Transform;
import old.engine.ui.RenderableComponent;
import old.engine.ui.Text;
import old.engine.ui.UIComponent;
import old.engine.ui.fonts.FontLoader;
import old.engine.util.AssetPool;
import old.testGame.resources.Resource;
import old.testGame.resources.ResourceManager;
import org.joml.Vector2f;

import static old.engine.util.Color.BLACK;
import static old.engine.util.Color.WHITE;

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
