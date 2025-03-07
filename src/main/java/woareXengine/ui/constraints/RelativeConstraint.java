package woareXengine.ui.constraints;

public class RelativeConstraint extends UiConstraint {
    private float value;

    public RelativeConstraint(float value) {
        this.value = value;
    }

    @Override
    public void completeSetUp(UiConstraints otherConstraints) {

    }

    @Override
    public float getRelativeValue() {
        return value;
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
