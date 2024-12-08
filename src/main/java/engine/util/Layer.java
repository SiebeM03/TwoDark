package engine.util;

/**
 * The layer class is used to store the different options for z-indexes in the game.
 */
public final class Layer {
    /** The layer for the background */
    public static final int BACKGROUND = 0;
    /** The layer for the default game objects */
    public static final int DEFAULT = 100;
    /** The layer for game objects that can be interacted with */
    public static final int INTERACTION = 200;
    /** The layer for game objects that can't be interacted with, such as tooltips */
    public static final int NO_INTERACTION = 500;
    /** The layer for game objects that are always on top */
    public static final int TOP = 1000;
    /** The layer for game objects that are not serialized */
    public static final int NO_SERIALIZE = 2000;
    /** The layer for game objects that are never rendered */
    public static final int NO_RENDER = 10000;
}
