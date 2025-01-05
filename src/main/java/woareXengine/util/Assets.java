package woareXengine.util;

import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.openglWrapper.textures.Texture;

import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

    public static Shader getShader(String filePath) {
        if (shaders.containsKey(filePath)) {
            return Assets.shaders.get(filePath);
        } else {
            Shader shader = new Shader(filePath);
            shader.compile();
            shaders.put(filePath, shader);
            return shader;
        }
    }

    public static Texture getTexture(String filePath) {
        if (Assets.textures.containsKey(filePath)) {
            return Assets.textures.get(filePath);
        } else {
            Texture texture = new Texture(filePath);
            Assets.textures.put(filePath, texture);
            return texture;
        }
    }
}
