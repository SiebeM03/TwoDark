package old.engine.ui.fonts;

public class FontLoader {
    private static Font openSans;

    public static void loadFonts() {
        openSans = new Font("src/assets/fonts/OpenSans-Regular.ttf", 15);
    }

    public static Font getOpenSans() {
        return openSans;
    }
}
