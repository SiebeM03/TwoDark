package woareXengine.ui.common;

import woareXengine.ui.components.UiBlock;
import woareXengine.util.Color;
import woareXengine.util.Transform;

public class UiBorderedBlock extends UiBlock {
    protected final Color BORDER_COLOR = new Color("#002a47");
    protected final Color FILL_COLOR = new Color("#01487a");
    protected int BORDER_WIDTH = 2;

    private UiBlock contentBlock;

    private Transform lastTransform;

    @Override
    protected void init() {
        super.setTransform(0);
        color = BORDER_COLOR;

        contentBlock = new UiBlock();
        add(contentBlock);
        contentBlock.setTransform(BORDER_WIDTH);
        contentBlock.color = FILL_COLOR;
    }

    @Override
    protected void updateSelf() {
        if (!transform.equals(lastTransform)) {
            contentBlock.setTransform(BORDER_WIDTH);
        }

        lastTransform = transform.copy();
    }

    public void setBorderWidth(int width) {
        contentBlock.setTransform(width);
    }
}
