package TDA.ui.menus;

import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.DefaultScaleConstraint;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.util.Color;

public class TestUi extends UiComponent {
    @Override
    protected void init() {
        this.color = Color.WHITE;
        this.transform.setWidth(parent.transform.getWidth() - 20);
        this.transform.setHeight(parent.transform.getHeight() - 20);
        this.transform.setX(10);
        this.transform.setY(10);


        UiBlock uiBlock = new UiBlock();
        uiBlock.color.setAlpha(0.5f);
        add(uiBlock, new UiConstraints(
                new PixelConstraint(100, true),
                new PixelConstraint(0),
                new PixelConstraint(100),
                new DefaultScaleConstraint()
        ));
    }

    @Override
    protected void updateSelf() {

    }
}
