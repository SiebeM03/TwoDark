package woareXengine.ui.common;

import woareXengine.ui.components.ClickableUi;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Color;

public class Button extends ClickableUi {
    protected Color defaultColor;
    protected Color hoveredColor;
    protected Text text;

    public Button(Color defaultColor, Color hoveredColor, Text text) {
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
    }

    @Override
    protected void init() {
        constraints.apply();

        add(text, new UiConstraints(
                new CenterConstraint(),
                new CenterConstraint(),
                new PixelConstraint((int) text.calculateWidth()),
                new PixelConstraint((int) text.calculateHeight())
        ));
    }
}
