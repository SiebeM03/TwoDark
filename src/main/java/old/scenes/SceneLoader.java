package old.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import old.engine.ecs.Component;
import old.engine.ecs.GameObject;
import old.engine.ecs.serialization.ComponentSerializer;
import old.engine.ecs.serialization.GameObjectSerializer;
import old.engine.ecs.serialization.dataStructures.Data;
import old.engine.ecs.serialization.dataStructures.ResourceData;
import old.engine.ecs.serialization.dataStructures.ToolData;
import old.engine.graphics.Window;
import old.testGame.resources.Resource;
import old.testGame.resources.ResourceManager;
import old.testGame.resources.types.Metal;
import old.testGame.resources.types.Stone;
import old.testGame.resources.types.Wood;
import old.testGame.tools.Tool;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SceneLoader {
    public static void saveScene(Scene scene) {
        if (!Window.get().loadFromFiles()) {
            return;
        }
//        Gson levelGson = new GsonBuilder()
//                                 .setPrettyPrinting()
//                                 .registerTypeAdapter(Component.class, new ComponentSerializer())
//                                 .registerTypeAdapter(GameObject.class, new GameObjectSerializer())
//                                 .create();
        Gson dataGson = new GsonBuilder()
                                .setPrettyPrinting()
                                .create();

//        List<GameObject> gameObjectsWithoutUI = scene.gameObjects.stream().filter(go -> go.zIndex() != Layer.NO_INTERACTION).toList();
        List<ResourceData> resources = ResourceManager.getResources().stream()
                                               .map(r -> new ResourceData(-1, r.name(), r.amount(), r.getClass().getCanonicalName()))
                                               .toList();

        List<ToolData> tools = new ArrayList<>();
//        List<ToolData> tools = scene.gameObjects.stream()
//                                       .map(go -> go.getComponent(Tool.class))
//                                       .filter(Objects::nonNull)
//                                       .map(t -> new ToolData(t.getUid(), t.name(), t.level(), t.getClass().getCanonicalName()))
//                                       .toList();

        try {
//             FileWriter writer = new FileWriter("level.txt");
//            writer.write(levelGson.toJson(gameObjectsWithoutUI));
//            writer.close();

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
//        loadGameObjectsAndComponents(scene);
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
                try {
                    String className = rd.type();
                    Class<?> clazz = Class.forName(className);

                    Object instance = clazz.getDeclaredConstructor().newInstance();

                    Resource resource = (Resource) instance;
                    resource.setName(rd.name());
                    resource.setAmount(rd.amount());
                    ResourceManager.addResource(resource);
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    System.err.println("Error creating instance: " + e.getMessage());
                }
            }
            for (ToolData td : data.tools()) {
                try {
                    String className = td.type();
                    Class<?> clazz = Class.forName(className);

                    Object instance = clazz.getDeclaredConstructor().newInstance();

                    Tool tool = (Tool) instance;
                    tool.setName(td.name());
                    tool.setLevel(td.level());
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    System.err.println("Error creating instance: " + e.getMessage());
                }
            }
            return true;
        } else {
            ResourceManager.addResource(new Wood());
            ResourceManager.addResource(new Metal());
            ResourceManager.addResource(new Stone());

            return false;
        }
    }
}
