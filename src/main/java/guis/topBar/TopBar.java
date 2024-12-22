package guis.topBar;

import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.ui.RenderableComponent;
import engine.util.Color;
import engine.util.Settings;
import org.joml.Vector2f;
import scenes.HomeScene;
import testGame.resources.types.Metal;
import testGame.resources.types.Stone;
import testGame.resources.types.Wood;

public class TopBar extends RenderableComponent {

    public TopBar() {
        super("TopBar", Color.BACKGROUND, new Sprite());
        this.setTransform(new Transform(new Vector2f(0, Settings.PROJECTION_HEIGHT - 50), new Vector2f(Settings.PROJECTION_WIDTH, 50)));
        this.setNoInteraction();

        this.addChild(new ResourceCounterUI(Wood.class, 50, 15));
        this.addChild(new ResourceCounterUI(Metal.class, 200, 15));
        this.addChild(new ResourceCounterUI(Stone.class, 350, 15));

        this.addChild(new SceneButton("Main Menu", Settings.PROJECTION_WIDTH - 150, 0, 100, 40, HomeScene.class).getRootComponent());
    }
}
