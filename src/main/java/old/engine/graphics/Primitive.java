package old.engine.graphics;

import org.lwjgl.opengl.GL11;

import java.util.function.BiConsumer;

/**
 * Enum representing different types of primitives that can be rendered
 */
public enum Primitive {
    QUAD(4, 6, GL11.GL_TRIANGLES, (elements, index) -> {
        // 6 comes from the fact that every quad uses 6 indices (2 triangles)
        int offsetArrayIndex = 6 * index;
        // 4 comes from the fact that every quad uses 4 vertices
        // For triangle 1 it's 0, 1, 2, 3
        // For triangle 2 it's 4, 5, 6, 7 (vertices from triangle 1 + (4 * index) with index = 1)
        int offset = 4 * index;

        // 3, 2, 0, 0, 2, 1        7, 6, 4, 4, 6, 5
        // Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        // Triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }),
    LINE(2, 2, GL11.GL_LINES, (elements, index) -> {
        // 2 comes from the fact that every line uses 2 indices
        int offsetArrayIndex = 2 * index;
        // 2 comes from the fact that every line uses 2 vertices
        int offset = 2 * index;

        elements[offsetArrayIndex] = offset;
        elements[offsetArrayIndex + 1] = offset + 1;
    });


    /**
     * Number of vertices in the primitive
     */
    public final int vertexCount;
    /**
     * Number of elements in the primitive
     */
    public final int elementCount;
    /**
     * Primitive ID that opengl expects
     */
    public final int openglPrimitive;
    /**
     * Puts index data in the provided int buffer
     */
    public final BiConsumer<int[], Integer> elementCreation;

    Primitive(int vertexCount, int elementCount, int openglPrimitive, BiConsumer<int[], Integer> elementCreation) {
        this.vertexCount = vertexCount;
        this.elementCount = elementCount;
        this.openglPrimitive = openglPrimitive;
        this.elementCreation = elementCreation;
    }
}