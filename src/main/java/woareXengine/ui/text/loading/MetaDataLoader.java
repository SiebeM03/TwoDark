package woareXengine.ui.text.loading;

import woareXengine.ui.text.basics.Text;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MetaDataLoader {
    private static final char QUOTE_CHAR = '^';
    private static final int QUOTE_REPLACE = (int) QUOTE_CHAR;
    private static final int QUOTE_ASCII = 34;
    private static final int SPACE_ASCII = 32;

    // Indices for each padding in the padding array
    private static final int PAD_TOP = 0;
    private static final int PAD_LEFT = 1;
    private static final int PAD_BOTTOM = 2;
    private static final int PAD_RIGHT = 3;

    private static final int DESIRED_PADDING = 0;

    private static final String SPLITTER = " ";
    private static final String NUMBER_SEPARATOR = ",";

    private float scaleConversion;

    /** Array of padding values for top, left, bottom, right respectively */
    private int[] padding;
    /** Sum of left and right padding */
    private int paddingWidth;
    /** Sum of top and bottom padding */
    private int paddingHeight;
    /** Moves all characters up or down relative to the line center */
    private int yAdjustment;
    private File textureFile;

    private float spaceWidth;

    private BufferedReader reader;
    private final Map<String, String> values = new HashMap<>();

    private final File metaFile;

    public MetaDataLoader(File metaDataFile) {
        this.metaFile = metaDataFile;
    }

    public TextGenerator loadMetaData() {
        openFile();
        loadPaddingData();
        loadLineSize();
        int imageSize = getValueOfVariable("scaleW");
        loadTextureFileName();
        Map<Integer, Glyph> charData = loadCharacterData(imageSize);
        close();
        return new TextGenerator(textureFile, charData);
    }


    private boolean processNextLine() {
        values.clear();
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line == null) {
            return false;
        }
        for (String part : line.split(SPLITTER)) {
            String[] valuePairs = part.split("=");
            if (valuePairs.length == 2) {
                values.put(valuePairs[0], valuePairs[1].replaceAll("\"", ""));
            }
        }
        return true;
    }

    private int getValueOfVariable(String variable) {
        String value = values.get(variable);
        if (value == null) return 0;
        return Integer.parseInt(value);
    }

    private int[] getValuesOfVariable(String variable) {
        String[] numbers = values.get(variable).split(NUMBER_SEPARATOR);
        int[] actualValues = new int[numbers.length];
        for (int i = 0; i < actualValues.length; i++) {
            actualValues[i] = Integer.parseInt(numbers[i]);
        }
        return actualValues;
    }

    /** Closes the font file after finishing reading */
    private void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile() {
        try {
            reader = new BufferedReader(new FileReader(metaFile));
        } catch (Exception e) {
            assert false : "MetaDataLoader: Couldn't load meta data for font.";
            e.printStackTrace();
        }
    }

    private void loadPaddingData() {
        processNextLine();
        this.padding = getValuesOfVariable("padding");
        this.paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
        this.paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
    }

    private void loadLineSize() {
        processNextLine();
        int lineHeight = getValueOfVariable("lineHeight") - paddingHeight;
        this.scaleConversion = Text.LINE_HEIGHT_PIXELS / (float) lineHeight;
        this.yAdjustment = getValueOfVariable("yAdjustment");
    }

    /** Loads the texture file (.png), make sure that the metaFile and the textureFile are located in the same folder */
    private void loadTextureFileName() {
        processNextLine();
        String texFileName = values.get("file");
        File parentFile = metaFile.getParentFile();
        this.textureFile = new File(parentFile, texFileName);
    }

    private Map<Integer, Glyph> loadCharacterData(int imageSize) {
        processNextLine();
        int count = getValueOfVariable("count");
        Map<Integer, Glyph> charData = new HashMap<>();
        for (int i = 0; i < count; i++) {
            processNextLine();
            processNextCharacter(imageSize, charData);
        }
        return charData;
    }

    private void processNextCharacter(int imageSize, Map<Integer, Glyph> charData) {
        Glyph g = loadGlyph(imageSize);
        if (g == null || g.id == QUOTE_REPLACE) return;
        if (g.id == QUOTE_ASCII) {
            charData.put(QUOTE_REPLACE, g);
        }
        charData.put(g.id, g);
    }

    private Glyph loadGlyph(int imageSize) {
        int id = getValueOfVariable("id");
        if (id == SPACE_ASCII) {
            this.spaceWidth = (getValueOfVariable("xadvance") - paddingWidth) * scaleConversion;
            return null;
        }
        double xTex = ((double) getValueOfVariable("x") + (padding[PAD_LEFT] - DESIRED_PADDING)) / imageSize;
        double yTex = ((double) getValueOfVariable("y") + (padding[PAD_TOP] - DESIRED_PADDING)) / imageSize;
        int width = getValueOfVariable("width") - (paddingWidth - (2 * DESIRED_PADDING));
        int height = getValueOfVariable("height") - (paddingHeight - (2 * DESIRED_PADDING));
        double texWidth = (double) width / imageSize;
        double texHeight = (double) height / imageSize;

        double xOff = (getValueOfVariable("xoffset") + padding[PAD_LEFT] - DESIRED_PADDING) * scaleConversion;
        double yOff = (getValueOfVariable("yoffset") + padding[PAD_TOP] - DESIRED_PADDING) * scaleConversion;
        double quadWidth = width * scaleConversion;
        double quadHeight = height * scaleConversion;
        double xAdvance = (getValueOfVariable("xadvance") - paddingWidth) * scaleConversion;
        double centerOffset = yAdjustment * scaleConversion;


        if (id == (int) 'g') {
            System.out.println(yOff);
        }

        return new Glyph(id, xTex, yTex, texWidth, texHeight, xOff, yOff - centerOffset, quadWidth, quadHeight, xAdvance);
    }
}
