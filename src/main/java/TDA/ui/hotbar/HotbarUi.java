package TDA.ui.hotbar;

import TDA.entities.ecs.components.Hotbar;
import TDA.entities.player.Player;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiComponent;
import woareXengine.util.Color;

public class HotbarUi extends UiComponent {
    private static final int WIDTH = 54;
    private static final int HEIGHT = 54;
    private static final int SPACING = 8;

    private Hotbar hotbar;

    @Override
    protected void init() {
        this.hotbar = Player.hotbar;

        setTransform(0);
        transform.setHeight(HEIGHT + SPACING);
        transform.setWidth(hotbar.getHotbarItems().length * (WIDTH + SPACING));
        transform.setX((Engine.window().getPixelWidth() - transform.getWidth()) / 2);

        color = new Color("#0061a6");
        color.setAlpha(0.8f);

        add(new HotbarSlotWrapper());
    }

    @Override
    protected void updateSelf() {

    }
}
