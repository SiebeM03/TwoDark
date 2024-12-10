package scenes;

import engine.ecs.GameObject;
import engine.graphics.Camera;
import engine.graphics.renderer.DefaultRenderer;
import engine.graphics.renderer.PickingRenderer;
import engine.graphics.renderer.Renderer;
import engine.graphics.renderer.TextRenderer;
import engine.ui.Text;
import engine.util.Layer;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    public DefaultRenderer renderer = new DefaultRenderer();
    public PickingRenderer pickingRenderer = new PickingRenderer();
    public TextRenderer textRenderer = new TextRenderer();
    protected Camera camera;
    private boolean isRunning = false;

    protected List<GameObject> gameObjects = new ArrayList<>();
    protected List<GameObject> gameObjectsToAdd = new ArrayList<>();
    protected List<GameObject> gameObjectsToRemove = new ArrayList<>();
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
        gameObjectsToAdd.add(go);
    }

    /**
     * Queues a GameObject to be removed from the scene at the end of the frame.
     */
    public void removeGameObjectFromScene(GameObject go) {
        gameObjectsToRemove.add(go);
    }

    /**
     * Called at the end of the frame to apply all queued modifications to the {@link #gameObjects} list.
     */
    public void processPendingModifications() {
        for (GameObject go : gameObjectsToAdd) {
            gameObjects.add(go);
            if (isRunning) {
                go.start();
                addToRenderers(go);
            }
        }
        gameObjectsToAdd.clear();

        for (GameObject go : gameObjectsToRemove) {
            gameObjects.remove(go);
            if (isRunning) {
                removeFromRenderers(go);
            }
        }
        gameObjectsToRemove.clear();
    }

    public void addTextToScene(Text t) {
        this.textRenderer.add(t);
        this.texts.add(t);
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
        this.textRenderer.render();
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
