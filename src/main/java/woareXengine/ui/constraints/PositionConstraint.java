package woareXengine.ui.constraints;

public class PositionConstraint extends UiConstraint {
    private UiConstraint sizeConstraint;
    private Position type;
    private int offset;

    public PositionConstraint(Position type) {
        this.type = type;
        this.offset = 0;
    }

    public PositionConstraint(Position type, int offset) {
        this.type = type;
        this.offset = offset;
    }

    @Override
    protected void completeSetUp(UiConstraints otherConstraints) {
        if (!isPosValue()) {
            throw new IllegalArgumentException("Cannot use a size value for this constraint");
        }
        if (isXAxis() && (type == Position.TOP || type == Position.BOTTOM)) {
            throw new IllegalArgumentException("Position must be either LEFT or RIGHT for the x axis");
        }
        if (!isXAxis() && (type == Position.LEFT || type == Position.RIGHT)) {
            throw new IllegalArgumentException("Position must be either TOP or BOTTOM for the y axis");
        }

        sizeConstraint = isXAxis() ? otherConstraints.getWidth() : otherConstraints.getHeight();
    }

    @Override
    public float getRelativeValue() {
        return type == Position.BOTTOM || type == Position.LEFT ? 0 + (offset / getParentPixelSize()) : 1 - sizeConstraint.getRelativeValue() - (offset / getParentPixelSize());
    }

    @Override
    public void setPixelValue(int pixels) {

    }

    @Override
    public void setRelativeValue(float value) {

    }
}
