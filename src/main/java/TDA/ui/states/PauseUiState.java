package TDA.ui.states;

import TDA.main.GameManager;
import TDA.ui.menus.pause.PauseUi;
import woareXengine.ui.constraints.ConstraintUtils;

public class PauseUiState extends UiState {
    private PauseUi pauseUi = new PauseUi();

    @Override
    protected void toggleMouseAndKeyboard(boolean isOpening) {
        GameManager.gameControls.playerControls.enableInput(!isOpening);
        GameManager.gameControls.inventoryControls.enableInput(!isOpening);
    }

    @Override
    protected void init() {
        add(pauseUi, ConstraintUtils.fill());
        enableState(false);
    }

    @Override
    protected void updateSelf() {

    }
}
