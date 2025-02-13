package woareXengine.ui.common.dropdown;

import woareXengine.ui.components.MouseListener;

public class Option {
    public final String text;
    public final MouseListener listener;

    public Option(String text, MouseListener listener) {
        this.text = text;
        this.listener = listener;
    }
}
