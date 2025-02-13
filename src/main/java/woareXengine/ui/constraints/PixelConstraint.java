package woareXengine.ui.constraints;

public class PixelConstraint extends UiConstraint {
    private int value;
    private final boolean flipAxis;

    public PixelConstraint(int value) {
        this.value = value;
        this.flipAxis = false;
    }

    public PixelConstraint(int value, boolean flipAxis) {
        this.value = value;
        this.flipAxis = flipAxis;
    }

    @Override
    public void completeSetUp(UiConstraints otherConstraints) {

    }

    @Override
    public float getRelativeValue() {
        float parentSizePixels = super.getParentPixelSize();
        float relValue = value / parentSizePixels;
        if (flipAxis) {
            return 1 - relValue;
        }
        return relValue;
    }

    @Override
    public void setPixelValue(int pixels) {
        if (value == pixels) return;
        this.value = pixels;
    }

    @Override
    public void setRelativeValue(float value) {
        value = flipAxis ? 1 - value : value;
        this.value = Math.round(super.getParentPixelSize() * value);
    }
}
