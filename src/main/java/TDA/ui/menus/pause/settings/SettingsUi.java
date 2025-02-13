package TDA.ui.menus.pause.settings;

import woareXengine.io.userInputs.MouseButton;
import woareXengine.io.window.DisplayMode;
import woareXengine.mainEngine.GameSettings;
import woareXengine.ui.common.dropdown.Dropdown;
import woareXengine.ui.common.dropdown.Option;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;

public class SettingsUi extends UiComponent {

    @Override
    protected void init() {

        Dropdown displayModeDropdown = new Dropdown(
                new Option("Fullscreen", data -> {
                    if (data.isClick(MouseButton.LEFT)) {
                        GameSettings.setDisplayMode(DisplayMode.FULLSCREEN);
                    }
                }),
                new Option("Windowed", data -> {
                    if (data.isClick(MouseButton.LEFT)) {
                        GameSettings.setDisplayMode(DisplayMode.WINDOWED);
                    }
                })
        );
        displayModeDropdown.setSelected(GameSettings.getDisplayMode() == DisplayMode.FULLSCREEN ? 0 : 1);

        add(displayModeDropdown, new UiConstraints(
                new CenterConstraint(),
                new CenterConstraint(),
                new PixelConstraint(200),
                new PixelConstraint(40)
        ));
    }

    @Override
    protected void updateSelf() {

    }
}
