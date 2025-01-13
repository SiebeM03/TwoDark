package woareXengine.rendering.quadRenderer;

import woareXengine.mainEngine.EngineFiles;
import woareXengine.openglWrapper.shaders.Shader;

public class QuadShader extends Shader {

    private static String SHADER_PATH = EngineFiles.ENGINE_FOLDER_NAME + "/rendering/quadRenderer/quad.glsl";

    public QuadShader() {
        super(SHADER_PATH);
        super.use();
        super.detach();
    }
}
