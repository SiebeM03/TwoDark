package woareXengine.ui.constraints;

import woareXengine.ui.components.UiComponent;
import woareXengine.util.Logger;

public class UiConstraints {
    private UiConstraint xConstraint;
    private UiConstraint yConstraint;
    private UiConstraint widthConstraint;
    private UiConstraint heightConstraint;


    public UiConstraints() {
        setX(new PixelConstraint(0));
        setY(new PixelConstraint(0));
        setWidth(new DefaultScaleConstraint());
        setHeight(new DefaultScaleConstraint());
    }

    public UiConstraints(UiConstraint x, UiConstraint y) {
        setX(x);
        setY(y);
        setWidth(new DefaultScaleConstraint());
        setHeight(new DefaultScaleConstraint());
    }

    public UiConstraints(UiConstraint x, UiConstraint y, UiConstraint width, UiConstraint height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    public void notifyAdded(UiComponent current, UiComponent parent) {
        try {
            xConstraint.notifyAdded(this, current, parent);
            yConstraint.notifyAdded(this, current, parent);
            widthConstraint.notifyAdded(this, current, parent);
            heightConstraint.notifyAdded(this, current, parent);
            apply();
        } catch (Exception e) {
            Logger.error("Failed to set up UI constraints.");
            e.printStackTrace();
        }
    }

    public UiConstraint getX() {
        return xConstraint;
    }

    public UiConstraint getY() {
        return yConstraint;
    }

    public UiConstraint getWidth() {
        return widthConstraint;
    }

    public UiConstraint getHeight() {
        return heightConstraint;
    }

    public UiConstraints setX(UiConstraint xConstraint) {
        this.xConstraint = xConstraint;
        xConstraint.setAxis(true, true);
        return this;
    }

    public UiConstraints setY(UiConstraint yConstraint) {
        this.yConstraint = yConstraint;
        yConstraint.setAxis(false, true);
        return this;
    }

    public UiConstraints setWidth(UiConstraint widthConstraint) {
        this.widthConstraint = widthConstraint;
        widthConstraint.setAxis(true, false);
        return this;
    }

    public UiConstraints setHeight(UiConstraint heightConstraint) {
        this.heightConstraint = heightConstraint;
        heightConstraint.setAxis(false, false);
        return this;
    }

    public void apply() {
        xConstraint.apply();
        yConstraint.apply();
        widthConstraint.apply();
        heightConstraint.apply();
    }
}
