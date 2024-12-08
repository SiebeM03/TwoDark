package scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.ecs.Component;
import engine.ecs.GameObject;
import engine.ecs.serialization.*;
import engine.ecs.serialization.dataStructures.Data;
import engine.ecs.serialization.dataStructures.ResourceData;
import engine.ecs.serialization.dataStructures.ToolData;
import engine.graphics.Camera;
import engine.graphics.Window;
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

    protected Renderer renderer = new Renderer();
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

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjectsToAdd.add(go);
            go.start();
            this.renderer.add(go);
        }
    }

    public void removeGameObjectFromScene(GameObject go) {
        if (!isRunning) {
            gameObjectsToRemove.remove(go);
        } else {
            gameObjectsToRemove.remove(go);
            this.renderer.remove(go);
        }
    }

    public abstract void update();

    public abstract void render();

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Called at the end of the frame to add and remove GameObjects from the scene.
     */
    public void endFrame() {
        gameObjects.addAll(gameObjectsToAdd);
        gameObjects.removeAll(gameObjectsToRemove);
        gameObjectsToAdd.clear();
        gameObjectsToRemove.clear();
    }

    public Camera camera() {
        return this.camera;
    }

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
