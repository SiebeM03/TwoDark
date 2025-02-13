package TDA.ui.menus.pause;

import TDA.ui.TDAUi;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.common.Button;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.*;
import woareXengine.util.Assets;
import woareXengine.util.Color;

public class PauseButtons extends UiComponent {
    protected static final int BUTTON_WIDTH = 100;
    protected static final int BUTTON_HEIGHT = 40;
    protected static final int BUTTON_SPACING = 10;

    protected static final int BUTTON_COUNT = 3;
    Button continueButton = new Button(Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText("Continue", 0.8f));
    Button settingsButton = new Button(Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText("Settings", 0.8f));
    Button exitButton = new Button(Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText("Exit", 0.8f));

    private final PauseUi pauseUi;

    protected PauseButtons(PauseUi parent) {
        this.pauseUi = parent;
    }

    @Override
    protected void init() {
        continueButton.color = new Color(Color.WHITE);
        continueButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                TDAUi.get().gameUi.showPauseMenu(false);
            }
        });
        add(continueButton, new UiConstraints(
                new CenterConstraint(),
                new PositionConstraint(Position.TOP),
                new PixelConstraint(BUTTON_WIDTH),
                new PixelConstraint(BUTTON_HEIGHT)
        ));

        settingsButton.color = new Color(Color.WHITE);
        settingsButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                pauseUi.showSettingsMenu(true);
            }
        });
        add(settingsButton, new UiConstraints(
                new CenterConstraint(),
                new CenterConstraint(),
                new PixelConstraint(BUTTON_WIDTH),
                new PixelConstraint(BUTTON_HEIGHT)
        ));

        exitButton.color = new Color(Color.WHITE);
        exitButton.addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                Engine.instance().requestClose();
            }
        });
        add(exitButton, new UiConstraints(
                new CenterConstraint(),
                new PositionConstraint(Position.BOTTOM),
                new PixelConstraint(BUTTON_WIDTH),
                new PixelConstraint(BUTTON_HEIGHT)
        ));

    }

    @Override
    protected void updateSelf() {

    }
}
