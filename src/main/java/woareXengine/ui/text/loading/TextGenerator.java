package woareXengine.ui.text.loading;

import java.io.File;
import java.util.Map;

public class TextGenerator {
    public final File textureFile;
    public final Map<Integer, Glyph> charData;

    protected TextGenerator(File textureFile, Map<Integer, Glyph> charData) {
        this.textureFile = textureFile;
        this.charData = charData;
    }
}
