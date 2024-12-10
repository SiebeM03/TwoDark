package scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.ecs.Component;
import engine.ecs.GameObject;
import engine.ecs.serialization.ComponentSerializer;
import engine.ecs.serialization.GameObjectSerializer;
import engine.ecs.serialization.dataStructures.Data;
import engine.ecs.serialization.dataStructures.ResourceData;
import engine.ecs.serialization.dataStructures.ToolData;
import engine.graphics.Camera;
import engine.graphics.Window;
import engine.graphics.renderer.DefaultRenderer;
import engine.graphics.renderer.PickingRenderer;
import engine.graphics.renderer.Renderer;
import engine.util.Layer;
import imgui.ImGui;
import testGame.resources.Resource;
import testGame.tools.Tool;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Scene {

    public DefaultRenderer renderer = new DefaultRenderer();
    public PickingRenderer pickingRenderer = new PickingRenderer();
    protected Camera camera;
    private boolean isRunning = false;

    protected List<GameObject> gameObjects = new ArrayList<>();
    protected List<GameObject> gameObjectsToAdd = new ArrayList<>();
    protected List<GameObject> gameObjectsToRemove = new ArrayList<>();

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

    // =================================================================================================================
    // RENDER
    // =================================================================================================================
    public void addToRenderers(GameObject go) {
        this.renderer.add(go);
        if (go.zIndex() == Layer.INTERACTION) {
            this.pickingRenderer.add(go);
        }
    }

    public void removeFromRenderers(GameObject go) {
        this.renderer.remove(go);
        if (go.zIndex() == Layer.INTERACTION) {
            this.pickingRenderer.remove(go);
        }
    }

    public void render() {
        this.renderer.render();
        this.pickingRenderer.render();
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


    // =================================================================================================================
    // SAVE AND LOAD
    // =================================================================================================================
    public void saveExit() {
        if (!Window.get().loadFromFiles()) {
            return;
        }
        Gson levelGson = new GsonBuilder()
                                 .setPrettyPrinting()
                                 .registerTypeAdapter(Component.class, new ComponentSerializer())
                                 .registerTypeAdapter(GameObject.class, new GameObjectSerializer())
                                 .create();
        Gson dataGson = new GsonBuilder()
                                .setPrettyPrinting()
                                .create();

        List<GameObject> gameObjectsWithoutUI = gameObjects.stream().filter(go -> go.zIndex() != Layer.NO_INTERACTION).toList();
        List<ResourceData> resources = gameObjects.stream()
                                               .map(go -> go.getComponent(Resource.class))
                                               .filter(Objects::nonNull)
                                               .map(r -> new ResourceData(r.getUid(), r.name(), r.amount(), r.getClass().getCanonicalName()))
                                               .toList();

        List<ToolData> tools = gameObjects.stream()
                                       .map(go -> go.getComponent(Tool.class))
                                       .filter(Objects::nonNull)
                                       .map(t -> new ToolData(t.getUid(), t.name(), t.level(), t.getClass().getCanonicalName()))
                                       .toList();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(levelGson.toJson(gameObjectsWithoutUI));
            writer.close();


            FileWriter writerUI = new FileWriter("data.txt");
            writerUI.write(dataGson.toJson(new Data(resources, tools)));
            writerUI.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (!Window.get().loadFromFiles()) {
            levelLoaded = false;
            return;
        }
        // For now, we will just load the GameObjects and Components from our dev scene
        loadGameObjectsAndComponents();
        loadGameData();
    }

    /**
     * <ol>
     * <li>Loads all GameObjects and Components from level.txt</li>
     * <li>Adds all Components to the corresponding GameObject</li>
     * <li>Adds all GameObjects to the scene</li>
     * </ol>
     *
     * @return true if the file was read, false if the file was empty.
     */
    private boolean loadGameObjectsAndComponents() {
        Gson gson = new GsonBuilder()
                            .setPrettyPrinting()
                            .registerTypeAdapter(Component.class, new ComponentSerializer())
                            .registerTypeAdapter(GameObject.class, new GameObjectSerializer())
                            .create();
        String levelFile = "";

        try {
            levelFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!levelFile.equals("")) {
            int maxGoId = -1;
            int maxCompId = -1;
            GameObject[] objs = gson.fromJson(levelFile, GameObject[].class);
            for (GameObject go : objs) {
                addGameObjectToScene(go);

                for (Component c : go.getAllComponents()) {
                    if (c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }
                }
                if (go.getUid() > maxGoId) {
                    maxGoId = go.getUid();
                }
            }

            // Update the ID_COUNTER values for GameObject and Component
            maxGoId++;
            maxCompId++;
            GameObject.init(maxGoId);
            Component.init(maxCompId);

            processPendingModifications();

            this.levelLoaded = true;
            return true;
        } else {
            return false;
        }
    }


    /**
     * Loads all game data from data.txt and updates the corresponding Components.
     *
     * @return true if the file was read, false if the file was empty.
     */
    private boolean loadGameData() {
        Gson gson = new GsonBuilder()
                            .setPrettyPrinting()
                            .create();

        String dataFile = "";

        try {
            dataFile = new String(Files.readAllBytes(Paths.get("data.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!dataFile.equals("")) {
            Data data = gson.fromJson(dataFile, Data.class);
            for (ResourceData rd : data.resources()) {
                int uid = rd.uid();
                for (GameObject go : gameObjects) {
                    Resource r = go.getComponent(Resource.class);
                    if (r != null && r.getUid() == uid) {
                        r.setName(rd.name());
                        r.setAmount(rd.amount());
                    }
                }
            }
            for (ToolData td : data.tools()) {
                int uid = td.uid();
                for (GameObject go : gameObjects) {
                    Tool t = go.getComponent(Tool.class);
                    if (t != null && t.getUid() == uid) {
                        t.setName(td.name());
                        t.setLevel(td.level());
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
