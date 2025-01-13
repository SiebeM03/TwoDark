package woareXengine.ui.components;


import woareXengine.io.userInputs.MouseButton;

public class EventData {

    private final MouseButton button;
    private final boolean eventState;
    private final boolean isDoubleClick;

    protected EventData(MouseButton button, boolean eventState) {
        this.button = button;
        this.eventState = eventState;
        this.isDoubleClick = false;
    }

    protected EventData(MouseButton button, boolean eventState, boolean isDoubleClick) {
        this.button = button;
        this.eventState = eventState;
        this.isDoubleClick = isDoubleClick;
    }

    public boolean isClick(MouseButton button) {
        return eventState && this.button == button;
    }

    public boolean isDoubleClick(){
        return isDoubleClick;
    }

    public boolean isCompleteClick(MouseButton button) {
        return !eventState && this.button == button;
    }

    public boolean isMouseOver() {
        return eventState && button == null;
    }

    public boolean isMouseOff() {
        return !eventState && button == null;
    }
}
