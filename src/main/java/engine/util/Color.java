package engine.util;

import org.joml.Vector4f;

public class Color {
    public static Color WHITE = new Color(1, 1, 1, 1);
    public static Color BLACK = new Color(0, 0, 0, 1);
    public static Color RED = new Color(1, 0, 0, 1);
    public static Color GREEN = new Color(0, 1, 0, 1);
    public static Color BLUE = new Color(0, 0, 1, 1);
    public static Color YELLOW = new Color(1, 1, 0, 1);
    public static Color CYAN = new Color(0, 1, 1, 1);
    public static Color MAGENTA = new Color(1, 0, 1, 1);
    public static Color BACKGROUND = new Color("#919191");

    private Vector4f color;

    public Color(float r, float g, float b, float a) {
        this.color = new Vector4f(r, g, b, a);
    }

    public Color(Vector4f color) {
        this.color = color;
    }

    public Color(String hex) {
        this.color = new Vector4f(
                Integer.valueOf(hex.substring(1, 3), 16) / 255f,
                Integer.valueOf(hex.substring(3, 5), 16) / 255f,
                Integer.valueOf(hex.substring(5, 7), 16) / 255f,
                1
        );
    }

    /**
     * Converts the color to a Vector4f with values ranging from 0-1
     */
    public Vector4f toNormalizedVec4f() {
        return color;
    }

    /**
     * Converts the color to a Vector4f with values ranging from 0-255
     */
    public Vector4f toVec4f() {
        return new Vector4f(color.x * 255, color.y * 255, color.z * 255, color.w * 255);
    }

    public void set(Vector4f color) {
        this.color.set(color);
    }

    public void set(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public void set(Color color) {
        this.color.set(color.toNormalizedVec4f());
    }

    public float r() {
        return color.x;
    }

    public float g() {
        return color.y;
    }

    public float b() {
        return color.z;
    }

    public float a() {
        return color.w;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Color) {
            Color c = (Color) obj;
            return c.color.equals(this.color);
        }
        return false;
    }
}
