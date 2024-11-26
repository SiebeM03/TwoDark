package game.resources;

import engine.ecs.GameObject;
import engine.ecs.Sprite;
import engine.ecs.Transform;
import engine.ecs.components.SpriteRenderer;
import engine.graphics.Window;
import engine.util.Settings;
import game.resources.types.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
    private List<Resource> resources;
    private Resource selectedResource;

    public void init() {
        resources = new ArrayList<>();
        resources.add(new Wood());
        resources.add(new Stone());
        resources.add(new Metal());
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    /**
     * Creates a game object for every Resource in {@link #resources} and configures all sprites and their renderers.
     * Each game object is also added to the scene.
     */
    public void addResourceGameObjectsToScene() {
        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            SpriteRenderer resourceSpriteRenderer = new SpriteRenderer();
            Sprite resourceSprite = resource.getSprite();
            resourceSpriteRenderer.setSprite(resourceSprite);

            Vector2f scale = new Vector2f(resourceSprite.getWidth(), resourceSprite.getHeight());
            Vector2f position = new Vector2f(
                    (float) (Settings.PROJECTION_WIDTH - scale.x) / 2 + (i - 1) * 200,
                    (float) (Settings.PROJECTION_HEIGHT - scale.y) / 2
            );

            GameObject resourceObject = new GameObject(resource.getClass().getName(), new Transform(position, scale), 1);
            resourceObject.addComponent(resourceSpriteRenderer);
            Window.getScene().addGameObjectToScene(resourceObject);
        }
    }

    public void imgui() {
        ImGui.begin("Resource objects");
        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            Sprite sprite = resource.getSprite();
            float spriteWidth = sprite.getWidth() / 2;
            float spriteHeight = sprite.getHeight() / 2;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                if (selectedResource == resource) {
                    selectedResource = null;
                } else {
                    selectedResource = resource;
                }
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < resources.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }
        ImGui.end();

        ImGui.begin("Resource data");
        if (selectedResource == null) {
            ImGui.text("Select a resource first");
        } else {
            ImGui.text(selectedResource.getClass().getSimpleName());
            ImGui.text(selectedResource.getAmount() + "");
        }
        ImGui.end();
    }
}
