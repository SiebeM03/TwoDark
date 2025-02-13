package woareXengine.ui.constraints;

import woareXengine.util.Logger;

public class CenterConstraint extends UiConstraint {
    private UiConstraint sizeConstraint;

    @Override
    protected void completeSetUp(UiConstraints otherConstraints) {
        this.sizeConstraint = isXAxis() ? otherConstraints.getWidth() : otherConstraints.getHeight();
    }

    @Override
    public float getRelativeValue() {
        float relativeSize = sizeConstraint.getRelativeValue();
        return (1 - relativeSize) / 2;
    }

    @Override
    public void setPixelValue(int pixels) {
        Logger.error("CenterConstraint does not support setting pixel values.");
    }

    @Override
    public void setRelativeValue(float value) {
        Logger.error("CenterConstraint does not support setting relative values.");
    }
}
