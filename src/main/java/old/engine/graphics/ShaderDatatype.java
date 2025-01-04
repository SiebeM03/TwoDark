package old.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;

public enum ShaderDatatype {
    INT(1, Integer.BYTES, GL_INT),
    INT2(2, 2 * Integer.BYTES, GL_INT),
    INT3(3, 3 * Integer.BYTES, GL_INT),
    INT4(4, 4 * Integer.BYTES, GL_INT),
    FLOAT(1, Float.BYTES, GL_FLOAT),
    FLOAT2(2, 2 * Float.BYTES, GL_FLOAT),
    FLOAT3(3, 3 * Float.BYTES, GL_FLOAT),
    FLOAT4(4, 4 * Float.BYTES, GL_FLOAT),
    MAT3(9, 3 * 3 * Float.BYTES, GL_FLOAT),
    MAT4(16, 4 * 4 * Float.BYTES, GL_FLOAT);

    /**
     * Number of FLOATS or INTS
     */
    public final int count;
    /**
     * Number of bytes
     */
    public final int sizeInBytes;
    /**
     * OpenGL expected type
     */
    public final int openglType;

    ShaderDatatype(int count, int sizeInBytes, int openglType) {
        this.count = count;
        this.sizeInBytes = sizeInBytes;
        this.openglType = openglType;
    }
}
