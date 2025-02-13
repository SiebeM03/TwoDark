package woareXengine.ui.common;

import woareXengine.ui.components.ClickableUi;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Color;

public class Button extends ClickableUi {
    protected Color defaultColor;
    protected Color hoveredColor;
    protected Text text;

    public Button(float width, float height, Color defaultColor, Color hoveredColor, Text text) {
        this.transform.setDimensions(width, height);

        this.defaultColor = defaultColor;
        this.hoveredColor = hoveredColor;
        this.color = defaultColor;
        this.addMouseListener(data -> {
            if (data.isMouseOver()) {
                this.color = hoveredColor;
            }
            if (data.isMouseOff()) {
                this.color = defaultColor;
            }
        });

        this.text = text;
        add(text);
        text.setTransform(0);
        text.centerInParent();
    }

    @Override
    protected void init() {
    }
}
