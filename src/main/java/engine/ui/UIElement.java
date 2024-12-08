package engine.ui;

import engine.ecs.Component;


/**
 * Class that represents a UI element in the game engine. This class is a component that can be attached to a GameObject.
 *
 * @implNote This class requires the GameObject to have a MouseEventConsumer component attached to it.
 */
public class UIElement extends Component {

    private transient final EventHandler eventHandler;

    public UIElement() {
        this.eventHandler = new EventHandler(this);
    }

    public MouseEventConsumer getConsumer() {
        if (this.gameObject == null) return null;
        return this.gameObject.getComponent(MouseEventConsumer.class);
    }

    @Override
    public void update() {
        this.eventHandler.update();
    }
}
