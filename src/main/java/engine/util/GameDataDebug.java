package engine.util;

import game.GameManager;
import game.resources.Resource;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

import java.util.List;

/**
 * A class that opens an ImGui window that displays all important data. Useful for development purposes.
 */
public class GameDataDebug {
    public static void imgui() {
        ImGui.begin("Debug panel");

        if (ImGui.collapsingHeader("Resources", ImGuiTreeNodeFlags.DefaultOpen)) {
            List<Resource> resources = GameManager.get().getResourceManager().getResources();
            for (Resource resource : resources) {
                ImGui.text(resource.getClass().getSimpleName() + ": " + resource.getAmount());
            }
        }

        ImGui.end();
    }
}
