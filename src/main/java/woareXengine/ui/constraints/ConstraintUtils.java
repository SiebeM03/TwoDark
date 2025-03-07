package woareXengine.ui.constraints;

public class ConstraintUtils {
    public static UiConstraints margin(int val) {
        return new UiConstraints(
                new MarginConstraint(val),
                new MarginConstraint(val),
                new PixelConstraint(val * 2, true),
                new PixelConstraint(val * 2, true)
        );
    }

    public static UiConstraints margin(int x, int y) {
        return new UiConstraints(
                new MarginConstraint(x),
                new MarginConstraint(y),
                new PixelConstraint(x * 2, true),
                new PixelConstraint(y * 2, true)
        );
    }

    public static UiConstraints fill() {
        return new UiConstraints(
                new PixelConstraint(0),
                new PixelConstraint(0),
                new DefaultScaleConstraint(),
                new DefaultScaleConstraint()
        );
    }

    /**
     * Creates a UiConstraints object that fills the given size.
     *
     * @param width  The width of the object.
     * @param height The height of the object.
     */
    public static UiConstraints fill(int width, int height) {
        return new UiConstraints(
                new PixelConstraint(0),
                new PixelConstraint(0),
                new PixelConstraint(width),
                new PixelConstraint(height)
        );
    }
}
