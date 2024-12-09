package engine.ecs;

import engine.ui.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    /**
     * Global counter that keeps track of the amount of ID's that have been generated for GameObjects.
     */
    private static int ID_COUNTER = 0;
    /**
     * A unique UID that is given to each instance of the GameObject class (or subclasses).
     *
     * @implNote Equals -1 when no actual UID is given yet to the component.
     */
    private int uid = -1;

    public String name;
    private List<Component> components;
    public Transform transform;
    /**
     * The zIndex of the GameObject. This is used to determine the order in which GameObjects are rendered.
     *
     * @implNote A zIndex of 999 is used for the UI layer, this layer is ignored by the picker system (see {@link engine.editor.PickingTexture}).
     */
    private int zIndex;

    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;

        this.uid = ID_COUNTER++;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component";
                }
            }
        }

        return null;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.generateId();
        this.components.add(c);
        c.gameObject = this;
    }

    public void update() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update();
        }
    }

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void imgui() {
        for (Component c : components) {
            c.imgui();
        }
    }

    public int zIndex() {
        return this.zIndex;
    }

    public int getUid() {
        return this.uid;
    }

    public Transform getTransform() {
        return this.transform;
    }

    /**
     * Make sure ID_COUNTER is not zero after loading levels. This prevents new GameObjects to get a UID that is already taken by another GameObject.
     *
     * @param maxId The maximum ID that is currently in use by a GameObject.
     */
    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }
}
