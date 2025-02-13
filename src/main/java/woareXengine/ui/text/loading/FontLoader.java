package woareXengine.ui.text.loading;

import woareXengine.openglWrapper.textures.Texture;
import woareXengine.ui.text.basics.Font;
import woareXengine.util.Assets;

import java.io.File;

public class FontLoader {
    public static Font load(String filepath) {
        File fontMetaFile = new File(filepath);
        TextGenerator generator = new MetaDataLoader(fontMetaFile).loadMetaData();
        Texture texture = Assets.getTexture(generator.textureFile.getPath());
        return new Font(texture, generator);
    }
}
