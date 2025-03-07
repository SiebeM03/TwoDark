package woareXengine.ui.text.basics;

import woareXengine.openglWrapper.textures.Texture;
import woareXengine.ui.text.loading.TextGenerator;
import woareXengine.util.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Font {
    private final Id id;
    private final Texture fontAtlas;
    private final TextGenerator generator;
    public static Map<Font, List<Text>> texts = new HashMap<>();

    public Font(Texture fontAtlas, TextGenerator generator) {
        id = new Id();
        this.fontAtlas = fontAtlas;
        this.generator = generator;
    }

    public Texture getFontAtlas() {
        return fontAtlas;
    }

    public TextGenerator getTextGenerator() {
        return generator;
    }

    public Text createText(String text) {
        return createText(text, 1);
    }

    public Text createText(String text, float scale) {
        Text t = new Text(text, this, scale);
        Font.texts.computeIfAbsent(this, k -> new ArrayList<>()).add(t);
        return t;
    }

    public static void removeText(Text text) {
        Font.texts.get(text.font).remove(text);
    }
}
