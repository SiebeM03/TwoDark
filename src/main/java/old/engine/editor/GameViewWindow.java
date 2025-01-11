package old.engine.editor;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import old.engine.graphics.Window;
import old.engine.listeners.MouseListener;
import org.joml.Vector2f;

public class GameViewWindow {

    private static float leftX, rightX, topY, bottomY;

    public static void imgui() {
        ImGui.begin("Game Viewport", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);

        ImVec2 windowSize = getLargestSizeForViewport();
        ImVec2 windowPos = getCenteredPositionForViewport(windowSize);

        ImGui.setCursorPos(windowPos.x, windowPos.y);   // Makes it so ImGui positions the next component (viewport in this case) on the given position

        ImVec2 topLeft = new ImVec2();
        ImGui.getCursorScreenPos(topLeft);
        topLeft.x -= ImGui.getScrollX();
        topLeft.y -= ImGui.getScrollY();

        leftX = topLeft.x;
        bottomY = topLeft.y;
        rightX = topLeft.x + windowSize.x;
        topY = topLeft.y + windowSize.y;

        int framebufferTextureId = Window.getScene().defaultRenderer().getFramebuffer().getTextureId();
        ImGui.image(framebufferTextureId, windowSize.x, windowSize.y, 0, 1, 1, 0);

        MouseListener.setGameViewPortPos(new Vector2f(topLeft.x, topLeft.y));
        MouseListener.setGameViewPortSize(new Vector2f(windowSize.x, windowSize.y));

        ImGui.end();
    }

    private static ImVec2 getLargestSizeForViewport() {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float aspectWidth = windowSize.x;
        float aspectHeight = aspectWidth / Window.getTargetAspectRatio();
        if (aspectHeight > windowSize.y) {
            // Switch to pillarbox mode
            aspectHeight = windowSize.y;
            aspectWidth = aspectHeight * Window.getTargetAspectRatio();
        }
        return new ImVec2(aspectWidth, aspectHeight);
    }

    private static ImVec2 getCenteredPositionForViewport(ImVec2 aspectSize) {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float viewportX = (windowSize.x / 2.0f) - (aspectSize.x / 2.0f);
        float viewportY = (windowSize.y / 2.0f) - (aspectSize.y / 2.0f);

        return new ImVec2(viewportX + ImGui.getCursorPosX(), viewportY + ImGui.getCursorPosY());
    }

    /**
     * Check if the mouse is within the game view window.
     *
     * @return true if the mouse is within the game view window, false otherwise.
     */
    public static boolean getWantCaptureMouse() {
        return MouseListener.getX() >= leftX &&
                       MouseListener.getX() <= rightX &&
                       MouseListener.getY() >= bottomY &&
                       MouseListener.getY() <= topY;
    }
}
