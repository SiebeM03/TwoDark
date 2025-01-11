package old.guis.topBar;

import old.engine.ecs.Sprite;
import old.engine.ecs.Transform;
import old.engine.ui.RenderableComponent;
import old.engine.util.Color;
import old.engine.util.Settings;
import old.scenes.HomeScene;
import old.testGame.resources.types.Metal;
import old.testGame.resources.types.Stone;
import old.testGame.resources.types.Wood;
import org.joml.Vector2f;

public class TopBar extends RenderableComponent {

    public TopBar() {
        super("TopBar", Color.BACKGROUND, new Sprite());
        this.setTransform(new Transform(new Vector2f(0, Settings.PROJECTION_HEIGHT - 50), new Vector2f(Settings.PROJECTION_WIDTH, 50)));
        this.setNoInteraction();

        this.addChild(new ResourceCounterUI(Wood.class, 50, 15));
        this.addChild(new ResourceCounterUI(Metal.class, 200, 15));
        this.addChild(new ResourceCounterUI(Stone.class, 350, 15));

        this.addChild(new SceneButton("old.Main Menu", Settings.PROJECTION_WIDTH - 150, 0, 100, 40, HomeScene.class).getRootComponent());
    }
}
