package woareXengine.rendering.quadRenderer;

import java.util.ArrayList;
import java.util.List;

public class QuadData {
    private static QuadData instance = null;

    private List<Quad> quadList = new ArrayList<>();
    private boolean isDirty = true;

    private float[] vertexArray;


    public static QuadData get() {
        if (instance == null) instance = new QuadData();
        return instance;
    }


    public void add(Quad quad) {
        quadList.add(quad);
        setDirty();
    }

    public void remove(Quad quad) {
        quadList.remove(quad);
        setDirty();
    }


    public void generateVertexArray(float[] dest) {

    }

    private void generateVertices(float[] vertices, int index) {

    }


    public void setDirty() {
        isDirty = true;
    }

    public void setClean() {
        isDirty = false;
    }

}
