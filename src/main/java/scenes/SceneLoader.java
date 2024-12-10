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
import engine.graphics.Window;
import engine.util.Layer;
import testGame.resources.Resource;
import testGame.tools.Tool;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class SceneLoader {
    public static void saveScene(Scene scene) {
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

        List<GameObject> gameObjectsWithoutUI = scene.gameObjects.stream().filter(go -> go.zIndex() != Layer.NO_INTERACTION).toList();
        List<ResourceData> resources = scene.gameObjects.stream()
                                               .map(go -> go.getComponent(Resource.class))
                                               .filter(Objects::nonNull)
                                               .map(r -> new ResourceData(r.getUid(), r.name(), r.amount(), r.getClass().getCanonicalName()))
                                               .toList();

        List<ToolData> tools = scene.gameObjects.stream()
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

    public static void loadScene(Scene scene) {
        if (!Window.get().loadFromFiles()) {
            scene.levelLoaded = false;
            return;
        }
        // For now, we will just load the GameObjects and Components from our dev scene
        loadGameObjectsAndComponents(scene);
        loadGameData(scene);
    }

    /**
     * Loads all GameObjects and Components from level.txt
     * <p>
     * Adds all Components to the corresponding GameObject
     * <p>
     * Adds all GameObjects to the scene
     *
     * @return true if the file was read, false if the file was empty.
     */
    private static boolean loadGameObjectsAndComponents(Scene scene) {
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
                scene.addGameObjectToScene(go);

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

            scene.processPendingModifications();

            scene.levelLoaded = true;
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
    private static boolean loadGameData(Scene scene) {
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
                for (GameObject go : scene.gameObjects) {
                    Resource r = go.getComponent(Resource.class);
                    if (r != null && r.getUid() == uid) {
                        r.setName(rd.name());
                        r.setAmount(rd.amount());
                    }
                }
            }
            for (ToolData td : data.tools()) {
                int uid = td.uid();
                for (GameObject go : scene.gameObjects) {
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
