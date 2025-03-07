package TDA.ui.states;

import woareXengine.ui.components.UiComponent;

public abstract class UiState extends UiComponent {
    private boolean isOpen = false;

    public void enableState(boolean open) {
        isOpen = open;
        show(open);
        toggleMouseAndKeyboard(open);
    }

    protected abstract void toggleMouseAndKeyboard(boolean isOpening);

    public boolean isOpen() {
        return isOpen;
    }
}
