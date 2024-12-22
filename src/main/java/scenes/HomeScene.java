package scenes;

import engine.graphics.Camera;
import engine.ui.fonts.FontLoader;
import org.joml.Vector2f;

public class HomeScene extends Scene {
    public HomeScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        FontLoader.loadFonts();


    }

    @Override
    public void update() {

    }
}
