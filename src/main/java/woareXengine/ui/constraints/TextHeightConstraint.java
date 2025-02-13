package woareXengine.ui.constraints;

public class TextHeightConstraint extends UiConstraint {
    private int heightPixels;

    @Override
    protected void completeSetUp(UiConstraints otherConstraints) {

    }

    @Override
    public float getRelativeValue() {
        return (float) heightPixels / getParentPixelSize();
    }

    @Override
    public void setPixelValue(int pixels) {
        if (heightPixels == pixels) return;
        this.heightPixels = pixels;
    }

    @Override
    public void setRelativeValue(float value) {
        this.heightPixels = Math.round(super.getParentPixelSize() * value);
    }
}
