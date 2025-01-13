package woareXengine.rendering.uiRenderer;

import woareXengine.mainEngine.EngineFiles;
import woareXengine.openglWrapper.shaders.Shader;

public class UiShader extends Shader {
    private static String SHADER_PATH = EngineFiles.ENGINE_FOLDER_NAME + "/rendering/uiRenderer/ui.glsl";

    public UiShader() {
        super(SHADER_PATH);
        super.use();
        super.detach();
    }
}
