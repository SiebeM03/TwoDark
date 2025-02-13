package TDA.ui.menus.pause;

import TDA.ui.TDAUi;
import TDA.ui.menus.settings.SettingsUi;
import org.joml.Vector2f;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.common.Button;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.main.Ui;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;
import woareXengine.util.Transform;

public class PauseUi extends UiComponent {
    private SettingsUi settingsUi = new SettingsUi();
    Button continueButton = new Button(100, 40, Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText("Continue", 0.8f));
    Button settingsButton = new Button(100, 40, Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText("Settings", 0.8f));
    Button exitButton = new Button(100, 40, Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText("Exit", 0.8f));

    @Override
    protected void init() {
        setTransform(0);
        color = new Color(0, 0, 0, 0.8f);

        continueButton.color = new Color(Color.WHITE);
        continueButton.transform = new Transform(new Vector2f((float) (Ui.displayWidthPixels - 100) / 2, (float) (Ui.displayHeightPixels - 40) / 2 + 50), new Vector2f(100, 40));
        continueButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                TDAUi.get().gameUi.showPauseMenu(false);
            }
        });
        add(continueButton);

        settingsButton.color = new Color(Color.WHITE);
        settingsButton.transform = new Transform(new Vector2f((float) (Ui.displayWidthPixels - 100) / 2, (float) (Ui.displayHeightPixels - 40) / 2), new Vector2f(100, 40));
        settingsButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                showSettingsMenu(true);
            }
        });
        add(settingsButton);

        exitButton.color = new Color(Color.WHITE);
        exitButton.transform = new Transform(new Vector2f((float) (Ui.displayWidthPixels - 100) / 2, (float) (Ui.displayHeightPixels - 40) / 2 - 50), new Vector2f(100, 40));
        exitButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                Engine.instance().requestClose();
            }
        });
        add(exitButton);

        add(settingsUi);
        settingsUi.show(false);
    }

    @Override
    protected void updateSelf() {

    }

    @Override
    public void show(boolean show) {
        // If the settings menu is visible, close it when 'esc' pressed. Otherwise, toggle pauseUI
        if (!show && settingsUi.isVisible()) {
            showSettingsMenu(false);
        } else {
            super.show(show);
        }
    }

    private void showSettingsMenu(boolean show) {
        continueButton.show(!show);
        settingsButton.show(!show);
        exitButton.show(!show);
        settingsUi.show(show);
    }
}
