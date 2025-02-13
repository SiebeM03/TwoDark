package TDA.ui.menus.settings;

import woareXengine.io.userInputs.MouseButton;
import woareXengine.io.window.DisplayMode;
import woareXengine.mainEngine.GameSettings;
import woareXengine.ui.common.dropdown.Dropdown;
import woareXengine.ui.common.dropdown.Option;
import woareXengine.ui.components.UiComponent;

public class SettingsUi extends UiComponent {


    @Override
    protected void init() {
        setTransform(50);

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

        add(displayModeDropdown);
        displayModeDropdown.transform.setX((transform.getWidth() - displayModeDropdown.transform.getWidth()) / 2);
        displayModeDropdown.transform.setY(transform.getHeight() - 100);
    }

    @Override
    protected void updateSelf() {

    }
}
