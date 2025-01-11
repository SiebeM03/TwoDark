package woareXengine.util;

public class Id {
    private static int ID_COUNTER = 0;

    private final int id;

    public Id() {
        id = ID_COUNTER++;
    }

    public int getId() {
        return id;
    }
}
