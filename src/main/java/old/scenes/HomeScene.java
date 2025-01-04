package old.scenes;

import old.engine.graphics.Camera;
import old.engine.ui.fonts.FontLoader;
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
