package TDA.ui.menus.pause;

import TDA.ui.TDAUi;
import org.joml.Vector2f;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.common.Button;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.main.Ui;
import woareXengine.util.Color;
import woareXengine.util.Transform;

public class PauseUi extends UiComponent {

    @Override
    protected void init() {
        setTransform(0);
        color = new Color(0, 0, 0, 0.8f);

        Button continueButton = new Button();
        continueButton.color = new Color(Color.WHITE);
        continueButton.transform = new Transform(new Vector2f((float) (Ui.displayWidthPixels - 100) / 2, (float) (Ui.displayHeightPixels - 40) / 2), new Vector2f(100, 40));
        continueButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                TDAUi.get().gameUi.showPauseMenu(false);
            }
        });
        add(continueButton);

        Button exitButton = new Button();
        exitButton.color = new Color(Color.WHITE);
        exitButton.transform = new Transform(new Vector2f((float) (Ui.displayWidthPixels - 100) / 2, (float) (Ui.displayHeightPixels - 40) / 2 - 50), new Vector2f(100, 40));
        exitButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                Engine.instance().requestClose();
            }
        });
        add(exitButton);
    }

    @Override
    protected void updateSelf() {

    }
}
