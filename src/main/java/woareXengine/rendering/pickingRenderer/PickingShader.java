package woareXengine.rendering.pickingRenderer;

import woareXengine.mainEngine.EngineFiles;
import woareXengine.openglWrapper.shaders.Shader;

public class PickingShader extends Shader {

    private static String SHADER_PATH = EngineFiles.ENGINE_FOLDER_NAME + "/rendering/pickingRenderer/picking.glsl";

    public PickingShader() {
        super(SHADER_PATH);
        super.use();
        super.detach();
    }
}
