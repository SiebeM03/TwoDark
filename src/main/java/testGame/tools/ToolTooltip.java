package testGame.tools;

import engine.ecs.GameObject;
import engine.ecs.Transform;
import engine.ecs.components.SpriteRenderer;
import engine.ui.Text;
import engine.ui.fonts.Font;
import engine.ui.fonts.FontLoader;
import engine.util.Layer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class ToolTooltip {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 200;

    static Font font = FontLoader.getOpenSans();

    public static GameObject createTooltip(Tool tool) {
        Transform transform = tool.gameObject.transform;
        Vector2f toolTipPosition = new Vector2f(
                transform.position.x + (transform.scale.x - WIDTH) / 2,
                transform.position.y - HEIGHT - 10
        );
        GameObject tooltip = new GameObject("Tooltip_" + tool.name(), new Transform(toolTipPosition, new Vector2f(WIDTH, HEIGHT)), Layer.NO_INTERACTION);
        tooltip.addComponent(new SpriteRenderer().setColor(new Vector4f(1, 1, 1, 1)));
        Text level = new Text("Level: " + tool.level(), font, new Vector4f(0, 0, 0, 1), toolTipPosition.x + 10, toolTipPosition.y);
        return tooltip;
    }
}
