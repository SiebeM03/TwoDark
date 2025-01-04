package old.testGame.tools;

import old.engine.ecs.GameObject;
import old.engine.ecs.Transform;
import old.engine.ecs.components.SpriteRenderer;
import old.engine.ui.Text;
import old.engine.ui.fonts.Font;
import old.engine.ui.fonts.FontLoader;
import old.engine.util.Color;
import old.engine.util.Layer;
import org.joml.Vector2f;

public class ToolTooltip {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 200;

    static Font font = FontLoader.getOpenSans();

    public static GameObject createTooltip(Tool tool) {
        Transform transform = new Transform();
        Vector2f toolTipPosition = new Vector2f(
                transform.position.x + (transform.scale.x - WIDTH) / 2,
                transform.position.y - HEIGHT - 10
        );
        GameObject tooltip = new GameObject("Tooltip_" + tool.name(), new Transform(toolTipPosition, new Vector2f(WIDTH, HEIGHT)), Layer.NO_INTERACTION);
        tooltip.addComponent(new SpriteRenderer().setColor(Color.WHITE));

        Text level = new Text("Level: " + tool.level(), font, Color.BLACK, toolTipPosition.x + 10, toolTipPosition.y);
        return tooltip;
    }
}
