package woareXengine.ui.constraints;

public class MarginConstraint extends UiConstraint {
    private int value;

    public MarginConstraint(int value) {
        this.value = value;
    }

    @Override
    protected void completeSetUp(UiConstraints otherConstraints) {
        if (getParentPixelSize() < value * 2) {
            throw new IllegalArgumentException("Margin value is too large for parent size.");
        }
    }

    @Override
    public float getRelativeValue() {
        if (isPosValue()) {
            return value / getParentPixelSize();
        } else {
            return (getParentPixelSize() - 2 * value) / getParentPixelSize();
        }
    }

    @Override
    public void setPixelValue(int pixels) {
        if (value == pixels) return;
        this.value = pixels;
    }

    @Override
    public void setRelativeValue(float value) {
        this.value = Math.round(super.getParentPixelSize() * value);
    }
}
