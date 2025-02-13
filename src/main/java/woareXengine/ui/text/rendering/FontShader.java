package woareXengine.ui.text.rendering;

import woareXengine.mainEngine.EngineFiles;
import woareXengine.openglWrapper.shaders.Shader;

public class FontShader extends Shader {
    private static String SHADER_PATH = EngineFiles.ENGINE_FOLDER_NAME + "/ui/text/rendering/text.glsl";

    public FontShader() {
        super(SHADER_PATH);
        super.use();
        super.detach();
    }
}
