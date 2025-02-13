package TDA.ui.menus.pause;

import TDA.ui.menus.pause.settings.SettingsUi;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.util.Color;

import static TDA.ui.menus.pause.PauseButtons.*;

public class PauseUi extends UiComponent {

    private SettingsUi settingsUi;
    private PauseButtons pauseButtons;

    @Override
    protected void init() {
        color = new Color(0, 0, 0, 0.8f);

        this.pauseButtons = new PauseButtons(this);
        add(pauseButtons, new UiConstraints(
                new CenterConstraint(),
                new CenterConstraint(),
                new PixelConstraint(BUTTON_WIDTH),
                new PixelConstraint((BUTTON_HEIGHT + BUTTON_SPACING) * BUTTON_COUNT - BUTTON_SPACING)
        ));

        this.settingsUi = new SettingsUi();
        add(settingsUi, ConstraintUtils.margin(50));
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

    protected void showSettingsMenu(boolean show) {
        pauseButtons.show(!show);
        settingsUi.show(show);
    }
}
