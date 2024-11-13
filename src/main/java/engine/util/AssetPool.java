package engine.util;

import engine.ecs.SpriteSheet;
import engine.graphics.Shader;
import engine.graphics.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, SpriteSheet> spriteSheets = new HashMap<>();

    public static Shader getShader(String filePath) {
        File file = new File(filePath);
        if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
            return AssetPool.shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(filePath);
            shader.compile();
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String filePath) {
        File file = new File(filePath);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(filePath);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpriteSheet(String filePath, SpriteSheet spriteSheet) {
        File file = new File(filePath);
        if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())) {
            AssetPool.spriteSheets.put(file.getAbsolutePath(), spriteSheet);
        }
    }

    public static SpriteSheet getSpriteSheet(String filePath) {
        File file = new File(filePath);
        if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())) {
            assert false : "Error: Tried to access spritesheet '" + filePath + "' and it has not been added to asset pool";
        }
        return AssetPool.spriteSheets.getOrDefault(file.getAbsolutePath(), null);
    }
}
