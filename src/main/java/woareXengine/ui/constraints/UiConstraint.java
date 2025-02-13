package woareXengine.ui.constraints;

import woareXengine.ui.components.UiComponent;

public abstract class UiConstraint {
    private UiComponent current;
    private UiComponent parent;

    /** Whether the axis is the x-axis or not. */
    private boolean xAxis;
    /** Whether the value is for position or not. */
    private boolean posValue;

    protected void setAxis(boolean xAxis, boolean posValue) {
        this.xAxis = xAxis;
        this.posValue = posValue;
    }

    protected boolean isXAxis() {
        return xAxis;
    }

    protected boolean isPosValue() {
        return posValue;
    }

    protected float getParentPixelSize() {
        if (xAxis) {
            return parent.transform.getWidth();
        } else {
            return parent.transform.getHeight();
        }
    }

    protected void notifyAdded(UiConstraints otherConstraints, UiComponent current, UiComponent parent) {
        this.current = current;
        this.parent = parent;
        completeSetUp(otherConstraints);
    }

    protected abstract void completeSetUp(UiConstraints otherConstraints);

    public abstract float getRelativeValue();

    public abstract void setPixelValue(int pixels);

    public abstract void setRelativeValue(float value);

    public void apply() {
        if (isPosValue()) {
            if (isXAxis()) {
                current.transform.setX(getRelativeValue() * parent.transform.getWidth());
            } else {
                current.transform.setY(getRelativeValue() * parent.transform.getHeight());
            }
        } else {
            if (isXAxis()) {
                current.transform.setWidth(getRelativeValue() * parent.transform.getWidth());
            } else {
                current.transform.setHeight(getRelativeValue() * parent.transform.getHeight());
            }
        }
    }
}
