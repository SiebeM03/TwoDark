package old.scenes;

import old.engine.ecs.GameObject;
import old.engine.graphics.Camera;
import old.engine.graphics.renderer.*;
import old.engine.ui.RenderableComponent;
import old.engine.ui.Text;
import old.engine.ui.UIComponent;
import old.engine.util.Layer;
import old.engine.util.ModifiableList;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    public DefaultRenderer renderer = new DefaultRenderer();
    public PickingRenderer pickingRenderer = new PickingRenderer();
    public TextRenderer textRenderer = new TextRenderer();
    public UIRenderer uiRenderer = new UIRenderer();
    protected Camera camera;
    private boolean isRunning = false;

    protected ModifiableList<GameObject> gameObjects = new ModifiableList<>();
    protected ModifiableList<UIComponent> uiComponents = new ModifiableList<>();
    protected List<Text> texts = new ArrayList<>();

    protected GameObject activeGameObject = null;
    /**
     * A boolean that shows whether GameObjects have been read from a file. This can be used to prevent scenes from initializing GameObjects that have already been created by reading the file.
     */
    protected boolean levelLoaded = false;


    // =================================================================================================================
    // INITIALIZATION
    // =================================================================================================================
    public Scene() {
        this.renderer.init();
        this.pickingRenderer.init();
        this.textRenderer.init();
        this.uiRenderer.init();
    }

    public void init() {
    }

    /**
     * Starts the scene by calling the start method of all GameObjects in the scene and adding them to the renderer.
     */
    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            addToRenderers(go);
        }
        isRunning = true;
    }


    // =================================================================================================================
    // GAME OBJECT MANAGEMENT
    // =================================================================================================================

    /**
     * Queues a GameObject to be added to the scene at the end of the frame.
     */
    public void addGameObjectToScene(GameObject go) {
        gameObjects.add(go);
    }

    /**
     * Queues a GameObject to be removed from the scene at the end of the frame.
     */
    public void removeGameObjectFromScene(GameObject go) {
        gameObjects.remove(go);
    }

    /**
     * Called at the end of the frame to apply all queued modifications to the {@link #gameObjects} list.
     */
    public void processPendingModifications() {
        for (GameObject go : gameObjects.getRemoveTasks()) {
            if (isRunning) {
                removeFromRenderers(go);
            }
        }
        for (GameObject go : gameObjects.getAddTasks()) {
            if (isRunning) {
                go.start();
                addToRenderers(go);
            }
        }
        gameObjects.applyChanges();


        for (UIComponent c : uiComponents.getRemoveTasks()) {
            if (c instanceof RenderableComponent) {
                this.uiRenderer.remove((RenderableComponent) c);
            }
        }
        for (UIComponent c : uiComponents.getAddTasks()) {
            if (c instanceof RenderableComponent) {
                this.uiRenderer.add((RenderableComponent) c);
            }
        }
        uiComponents.applyChanges();
    }

    public void addTextToScene(Text t) {
        this.textRenderer.add(t);
        this.texts.add(t);
    }

    public void removeTextFromScene(Text t) {
        this.textRenderer.remove(t);
        this.texts.remove(t);
    }

    public void addUIComponent(UIComponent c) {
        System.out.println("Adding UI component");
        this.uiComponents.add(c);
        if (c instanceof RenderableComponent) {
            this.uiRenderer.add((RenderableComponent) c);
        }
    }


    // =================================================================================================================
    // UPDATE
    // =================================================================================================================

    /**
     * Called once per frame to update things that are not added to the {@link #gameObjects} list.
     */
    public abstract void update();

    public void updateGameObjects() {
        for (GameObject go : this.gameObjects) {
            go.update();
        }
    }

    public void updateUI() {
        for (Text t : this.texts) {
            t.update();
        }
        for (UIComponent c : this.uiComponents) {
            c.update();
        }
    }

    // =================================================================================================================
    // RENDER
    // =================================================================================================================

    /**
     * Adds a GameObject to all the required renderers.
     */
    private void addToRenderers(GameObject go) {
        this.renderer.add(go);
        if (go.zIndex() == Layer.INTERACTION) {
            this.pickingRenderer.add(go);
        }
    }

    /**
     * Removes a GameObject from all the required renderers.
     */
    private void removeFromRenderers(GameObject go) {
        this.renderer.remove(go);
        if (go.zIndex() == Layer.INTERACTION) {
            this.pickingRenderer.remove(go);
        }
    }

    public void render() {
        this.pickingRenderer.render();
        this.renderer.render();
        this.uiRenderer.render();
        this.textRenderer.render();
    }


    public boolean isRunning() {
        return isRunning;
    }

    public Camera camera() {
        return this.camera;
    }

    public Renderer defaultRenderer() {
        return this.renderer;
    }

    public PickingRenderer pickingRenderer() {
        return this.pickingRenderer;
    }


    // =================================================================================================================
    // IMGUI
    // =================================================================================================================
    public void sceneImgui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    public void imgui() {

    }
}
