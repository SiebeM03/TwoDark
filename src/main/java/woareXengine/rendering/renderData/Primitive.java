package woareXengine.rendering.renderData;

import org.lwjgl.opengl.GL11;

import java.util.function.BiConsumer;

/**
 * Enum representing different types of primitives that can be rendered
 */
public enum Primitive {
    QUAD(4, 6, GL11.GL_TRIANGLES, (elements, index) -> {
        int vertexStartIndex = 6 * index;
        int offset = 4 * index;

        elements[vertexStartIndex] = offset + 3;
        elements[vertexStartIndex + 1] = offset + 2;
        elements[vertexStartIndex + 2] = offset + 0;

        elements[vertexStartIndex + 3] = offset;
        elements[vertexStartIndex + 4] = offset + 2;
        elements[vertexStartIndex + 5] = offset + 1;
    }),
    ;


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