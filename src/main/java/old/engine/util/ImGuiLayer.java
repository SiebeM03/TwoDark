package old.engine.util;

import old.engine.editor.GameViewWindow;
import old.engine.graphics.Window;
import old.engine.listeners.KeyListener;
import old.engine.listeners.MouseListener;
import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.*;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import old.scenes.Scene;

import static org.lwjgl.glfw.GLFW.*;

public class ImGuiLayer {
    private final long glfwWindow;
    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

    // Configuration constants
    private static final String FONT_PATH = "src/assets/fonts/segoeui.ttf";
    private static final float FONT_SIZE = 20.0f;
    private static final String GL_VERSION = "#version 330 core";

    public ImGuiLayer(long glfwWindow) {
        this.glfwWindow = glfwWindow;
    }

    public void initImGui() {
        ImGui.createContext();
        initImGuiConfig();
        initCallbacks();
        loadFonts();
        imGuiGlfw.init(glfwWindow, false);
        imGuiGl3.init(GL_VERSION);
    }

    public void update(Scene currentScene) {
        startFrame();
        imGuiGl3.newFrame();
        ImGui.newFrame();

        setupDockSpace();
        renderWindows(currentScene);
        ImGui.end();    // End dock space

        ImGui.render();
        endFrame();
    }

    private void initImGuiConfig() {
        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename("imgui.ini");
        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.setConfigFlags(ImGuiConfigFlags.DockingEnable);
    }

    private void initCallbacks() {
        glfwSetKeyCallback(glfwWindow, this::keyCallback);
        glfwSetCharCallback(glfwWindow, this::charCallback);
        glfwSetMouseButtonCallback(glfwWindow, this::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, this::scrollCallback);
        final ImGuiIO io = ImGui.getIO();
        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = glfwGetClipboardString(glfwWindow);
                if (clipboardString != null) {
                    return clipboardString;
                } else {
                    return "";
                }
            }
        });
        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String s) {
                glfwSetClipboardString(glfwWindow, s);
            }
        });
    }

    private void loadFonts() {
        final ImFontAtlas fontAtlas = ImGui.getIO().getFonts();
        final ImFontConfig fontConfig = new ImFontConfig();
        fontAtlas.addFontFromFileTTF(FONT_PATH, FONT_SIZE, fontConfig);
        fontConfig.destroy();
        fontAtlas.build();
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods) {
        final ImGuiIO io = ImGui.getIO();
        io.addKeyEvent(getImGuiKey(key), action != GLFW_RELEASE);
        if (!io.getWantCaptureKeyboard()) {
            KeyListener.keyCallback(window, key, scancode, action, mods);
        } else {
            KeyListener.keyCallback(window, key, scancode, GLFW_PRESS, mods);
        }
    }

    private void charCallback(long window, int c) {
        if (c != GLFW_KEY_DELETE) {
            ImGui.getIO().addInputCharacter(c);
        }
    }

    private void mouseButtonCallback(long window, int button, int action, int mods) {
        final ImGuiIO io = ImGui.getIO();
        io.setMouseDown(new boolean[]{
                button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE,
                button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE,
                button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE,
                button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE,
                button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE
        });
        if (!io.getWantCaptureMouse() && io.getMouseDown(1)) {
            ImGui.setWindowFocus(null);
        }
        if (!io.getWantCaptureMouse() || GameViewWindow.getWantCaptureMouse()) {
            MouseListener.mouseButtonCallback(window, button, action, mods);
        }
    }

    private void scrollCallback(long window, double xOffset, double yOffset) {
        final ImGuiIO io = ImGui.getIO();
        io.addMouseWheelEvent((float) xOffset, (float) yOffset);

        if (!io.getWantCaptureMouse()) {
            MouseListener.mouseScrollCallback(window, xOffset, yOffset);
        }
    }


    private void startFrame() {
        double[] mousePosX = {0};
        double[] mousePosY = {0};
        glfwGetCursorPos(glfwWindow, mousePosX, mousePosY);

        final ImGuiIO io = ImGui.getIO();
        float[] winWidth = {Window.getWidth()};
        float[] winHeight = {Window.getHeight()};
        io.setDisplaySize(winWidth[0], winHeight[0]);
        io.setDisplayFramebufferScale(1f, 1f);
        io.setMousePos((float) mousePosX[0], (float) mousePosY[0]);
        io.setDeltaTime(Engine.deltaTime());

        updateCursor();
    }

    private void endFrame() {
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    private void updateCursor() {
        int imguiCursor = ImGui.getMouseCursor();
        glfwSetCursor(glfwWindow, mouseCursors[imguiCursor]);
        glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    private void setupDockSpace() {
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);
        ImGui.setNextWindowSize(Window.getWidth(), Window.getHeight());
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0);

        int windowFlags = ImGuiWindowFlags.NoDocking |
                                  ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
                                  ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
                                  ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

        ImGui.begin("Dockspace Demo", new ImBoolean(true), windowFlags);
        ImGui.popStyleVar(2);
        ImGui.dockSpace(ImGui.getID("Dockspace"));
    }

    private void renderWindows(Scene currentScene) {
        currentScene.sceneImgui();
        ImGui.showDemoWindow();
        GameViewWindow.imgui();
    }

    private static int getImGuiKey(int key) {
        return switch (key) {
            case GLFW_KEY_TAB -> ImGuiKey.Tab;
            case GLFW_KEY_LEFT -> ImGuiKey.LeftArrow;
            case GLFW_KEY_RIGHT -> ImGuiKey.RightArrow;
            case GLFW_KEY_UP -> ImGuiKey.UpArrow;
            case GLFW_KEY_DOWN -> ImGuiKey.DownArrow;
            case GLFW_KEY_PAGE_UP -> ImGuiKey.PageUp;
            case GLFW_KEY_PAGE_DOWN -> ImGuiKey.PageDown;
            case GLFW_KEY_HOME -> ImGuiKey.Home;
            case GLFW_KEY_END -> ImGuiKey.End;
            case GLFW_KEY_INSERT -> ImGuiKey.Insert;
            case GLFW_KEY_DELETE -> ImGuiKey.Delete;
            case GLFW_KEY_BACKSPACE -> ImGuiKey.Backspace;
            case GLFW_KEY_SPACE -> ImGuiKey.Space;
            case GLFW_KEY_ENTER -> ImGuiKey.Enter;
            case GLFW_KEY_ESCAPE -> ImGuiKey.Escape;
            case GLFW_KEY_APOSTROPHE -> ImGuiKey.Apostrophe;
            case GLFW_KEY_COMMA -> ImGuiKey.Comma;
            case GLFW_KEY_MINUS -> ImGuiKey.Minus;
            case GLFW_KEY_PERIOD -> ImGuiKey.Period;
            case GLFW_KEY_SLASH -> ImGuiKey.Slash;
            case GLFW_KEY_SEMICOLON -> ImGuiKey.Semicolon;
            case GLFW_KEY_EQUAL -> ImGuiKey.Equal;
            case GLFW_KEY_LEFT_BRACKET -> ImGuiKey.LeftBracket;
            case GLFW_KEY_BACKSLASH -> ImGuiKey.Backslash;
            case GLFW_KEY_RIGHT_BRACKET -> ImGuiKey.RightBracket;
            case GLFW_KEY_GRAVE_ACCENT -> ImGuiKey.GraveAccent;
            case GLFW_KEY_CAPS_LOCK -> ImGuiKey.CapsLock;
            case GLFW_KEY_SCROLL_LOCK -> ImGuiKey.ScrollLock;
            case GLFW_KEY_NUM_LOCK -> ImGuiKey.NumLock;
            case GLFW_KEY_PRINT_SCREEN -> ImGuiKey.PrintScreen;
            case GLFW_KEY_PAUSE -> ImGuiKey.Pause;
            case GLFW_KEY_KP_0 -> ImGuiKey.Keypad0;
            case GLFW_KEY_KP_1 -> ImGuiKey.Keypad1;
            case GLFW_KEY_KP_2 -> ImGuiKey.Keypad2;
            case GLFW_KEY_KP_3 -> ImGuiKey.Keypad3;
            case GLFW_KEY_KP_4 -> ImGuiKey.Keypad4;
            case GLFW_KEY_KP_5 -> ImGuiKey.Keypad5;
            case GLFW_KEY_KP_6 -> ImGuiKey.Keypad6;
            case GLFW_KEY_KP_7 -> ImGuiKey.Keypad7;
            case GLFW_KEY_KP_8 -> ImGuiKey.Keypad8;
            case GLFW_KEY_KP_9 -> ImGuiKey.Keypad9;
            case GLFW_KEY_KP_DECIMAL -> ImGuiKey.KeypadDecimal;
            case GLFW_KEY_KP_DIVIDE -> ImGuiKey.KeypadDivide;
            case GLFW_KEY_KP_MULTIPLY -> ImGuiKey.KeypadMultiply;
            case GLFW_KEY_KP_SUBTRACT -> ImGuiKey.KeypadSubtract;
            case GLFW_KEY_KP_ADD -> ImGuiKey.KeypadAdd;
            case GLFW_KEY_KP_ENTER -> ImGuiKey.KeypadEnter;
            case GLFW_KEY_KP_EQUAL -> ImGuiKey.KeypadEqual;
            case GLFW_KEY_LEFT_SHIFT -> ImGuiKey.LeftShift;
            case GLFW_KEY_LEFT_CONTROL -> ImGuiKey.LeftCtrl;
            case GLFW_KEY_LEFT_ALT -> ImGuiKey.LeftAlt;
            case GLFW_KEY_LEFT_SUPER -> ImGuiKey.LeftSuper;
            case GLFW_KEY_RIGHT_SHIFT -> ImGuiKey.RightShift;
            case GLFW_KEY_RIGHT_CONTROL -> ImGuiKey.RightCtrl;
            case GLFW_KEY_RIGHT_ALT -> ImGuiKey.RightAlt;
            case GLFW_KEY_RIGHT_SUPER -> ImGuiKey.RightSuper;
            case GLFW_KEY_MENU -> ImGuiKey.Menu;
            case GLFW_KEY_0 -> ImGuiKey._0;
            case GLFW_KEY_1 -> ImGuiKey._1;
            case GLFW_KEY_2 -> ImGuiKey._2;
            case GLFW_KEY_3 -> ImGuiKey._3;
            case GLFW_KEY_4 -> ImGuiKey._4;
            case GLFW_KEY_5 -> ImGuiKey._5;
            case GLFW_KEY_6 -> ImGuiKey._6;
            case GLFW_KEY_7 -> ImGuiKey._7;
            case GLFW_KEY_8 -> ImGuiKey._8;
            case GLFW_KEY_9 -> ImGuiKey._9;
            case GLFW_KEY_A -> ImGuiKey.A;
            case GLFW_KEY_B -> ImGuiKey.B;
            case GLFW_KEY_C -> ImGuiKey.C;
            case GLFW_KEY_D -> ImGuiKey.D;
            case GLFW_KEY_E -> ImGuiKey.E;
            case GLFW_KEY_F -> ImGuiKey.F;
            case GLFW_KEY_G -> ImGuiKey.G;
            case GLFW_KEY_H -> ImGuiKey.H;
            case GLFW_KEY_I -> ImGuiKey.I;
            case GLFW_KEY_J -> ImGuiKey.J;
            case GLFW_KEY_K -> ImGuiKey.K;
            case GLFW_KEY_L -> ImGuiKey.L;
            case GLFW_KEY_M -> ImGuiKey.M;
            case GLFW_KEY_N -> ImGuiKey.N;
            case GLFW_KEY_O -> ImGuiKey.O;
            case GLFW_KEY_P -> ImGuiKey.P;
            case GLFW_KEY_Q -> ImGuiKey.Q;
            case GLFW_KEY_R -> ImGuiKey.R;
            case GLFW_KEY_S -> ImGuiKey.S;
            case GLFW_KEY_T -> ImGuiKey.T;
            case GLFW_KEY_U -> ImGuiKey.U;
            case GLFW_KEY_V -> ImGuiKey.V;
            case GLFW_KEY_W -> ImGuiKey.W;
            case GLFW_KEY_X -> ImGuiKey.X;
            case GLFW_KEY_Y -> ImGuiKey.Y;
            case GLFW_KEY_Z -> ImGuiKey.Z;
            case GLFW_KEY_F1 -> ImGuiKey.F1;
            case GLFW_KEY_F2 -> ImGuiKey.F2;
            case GLFW_KEY_F3 -> ImGuiKey.F3;
            case GLFW_KEY_F4 -> ImGuiKey.F4;
            case GLFW_KEY_F5 -> ImGuiKey.F5;
            case GLFW_KEY_F6 -> ImGuiKey.F6;
            case GLFW_KEY_F7 -> ImGuiKey.F7;
            case GLFW_KEY_F8 -> ImGuiKey.F8;
            case GLFW_KEY_F9 -> ImGuiKey.F9;
            case GLFW_KEY_F10 -> ImGuiKey.F10;
            case GLFW_KEY_F11 -> ImGuiKey.F11;
            case GLFW_KEY_F12 -> ImGuiKey.F12;
            default -> ImGuiKey.None;
        };
    }
}
