package engine.ui;

import engine.ecs.Component;

public abstract class MouseEventConsumer extends Component {
    public abstract void onClick();

    public abstract void onHover();

    public abstract void onEnter();

    public abstract void onLeave();
}
