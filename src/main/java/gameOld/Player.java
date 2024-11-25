package gameOld;

import engine.listeners.KeyListener;
import engine.util.Debouncer;
import gameOld.resources.Metal;
import gameOld.resources.Resource;
import gameOld.resources.Stone;
import gameOld.resources.Wood;
import gameOld.tools.Axe;
import gameOld.tools.Pickaxe;
import gameOld.tools.Tool;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static Player instance;
    // Potentially add statistics

    private Debouncer printDebouncer = new Debouncer(1f);
    private Debouncer upgradeDebouncer = new Debouncer(0.5f);

    private boolean paused = false;

    private List<Resource> resources;
    private List<Tool> tools;


    public static Player get() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void init() {
        // Resources
        Resource wood = new Wood();
        Resource stone = new Stone();
        Resource metal = new Metal();

        resources = new ArrayList<>();
        resources.add(wood);
        resources.add(stone);
        resources.add(metal);

        // Tools
        Tool axe = new Axe(wood, stone);
        Tool pickaxe = new Pickaxe(metal);

        tools = new ArrayList<>();
        tools.add(axe);
        tools.add(pickaxe);
    }

    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_P)) paused = !paused;
        if (paused) return;

        for (Resource resource : resources) {
            resource.update(dt);
        }

        if (upgradeDebouncer.shouldTrigger(dt, false)) {
            if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_1)) {
                tools.get(0).levelUp();
                upgradeDebouncer.reset();
            } else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_2)) {
                tools.get(1).levelUp();
                upgradeDebouncer.reset();
            }
        }

        if (printDebouncer.shouldTrigger(dt)) {
            System.out.println(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resources:\n");
        for (Resource resource : resources) {
            sb.append(resource.toString()).append("\n");
        }
        sb.append("Tools:\n");
        for (Tool tool : tools) {
            sb.append(tool.toString()).append("\n");
        }
        return sb.toString();
    }
}
