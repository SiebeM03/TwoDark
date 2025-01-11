package woareXengine.rendering.debug;

import TDA.main.GameManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import woareXengine.mainEngine.Engine;
import woareXengine.openglWrapper.shaders.Shader;
import woareXengine.util.Assets;
import woareXengine.util.Color;
import woareXengine.util.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugDraw {
    private static int MAX_LINES = 500;

    private static List<Line2D> lines = new ArrayList<>();
    /**
     * The vertex array that will be uploaded to the GPU
     * <p>It has 6 floats per vertex (3 for position, 3 for color), 2 vertices per line</p>
     */
    private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static Shader shader = Assets.getShader("src/assets/shaders/debugLine2D.glsl");

    private static int vaoID;
    private static int vboID;

    private static boolean started = false;

    /**
     * Initialize a buffer on the GPU of a certain size (size of {@link #vertexArray} in this case).
     * <p>This makes sure we have space on the GPU to upload all the vertices</p>
     */
    public static void start() {
        // Generate the VAO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create the VBO and buffer some memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Enable the vertex array attributes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glLineWidth(2.0f);
    }

    /**
     * Check and see if we need to remove any lines and remove them if we do.
     */
    public static void beginFrame() {
        if (!started) {
            start();
            started = true;
        }

        // Remove dead lines
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).beginFrame() < 0) {
                lines.remove(i);
                i--;
            }
        }
    }

    /**
     * Create lines and upload them to the GPU inside the vbo
     */
    public static void draw() {
        if (lines.size() == 0) return;

        int index = 0;
        for (Line2D line : lines) {
            for (int i = 0; i < 2; i++) {
                Vector2f position = i == 0 ? line.getFrom() : line.getTo();
                Vector3f color = line.getColor();

                // Load position
                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = -10.0f;

                // Load color
                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;
                index += 6;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));  // vertexArray has size 500 * 6 * 2, we actually only have lines.size() * 6 * 2 values that we need to send to the GPU

        // Use custom shader
        shader.use();
        shader.uploadMat4f("uProjection", GameManager.currentScene.camera.getProjectionMatrix());
        shader.uploadMat4f("uView", GameManager.currentScene.camera.getViewMatrix());

        // Bind the vao
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the batch
        glDrawArrays(GL_LINES, 0, lines.size() * 6 * 2);

        // Disable location
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // Unbind shader
        shader.detach();
    }

    // =================================================================================================================
    // Add Line2D methods
    // =================================================================================================================
    public static void addLine2D(Vector2f from, Vector2f to) {
        addLine2D(from, to, Color.GREEN, 1);
    }

    public static void addLine2D(Vector2f from, Vector2f to, Color color) {
        addLine2D(from, to, color, 1);
    }

    public static void addLine2D(Vector2f from, Vector2f to, Color color, int lifetime) {
        if (!Engine.instance().debugging) return;
        if (lines.size() >= MAX_LINES) return;
        DebugDraw.lines.add(new Line2D(from, to, color, lifetime));
    }

    // =================================================================================================================
    // Add Box2D methods
    // =================================================================================================================
    public static void addBox2D(Vector2f center, Vector2f dimensions) {
        addBox2D(center, dimensions, 0, Color.GREEN, 1);
    }

    public static void addBox2D(Vector2f center, Vector2f dimensions, float rotation) {
        addBox2D(center, dimensions, rotation, Color.GREEN, 1);
    }

    public static void addBox2D(Vector2f center, Vector2f dimensions, float rotation, Color color) {
        addBox2D(center, dimensions, rotation, color, 1);
    }

    public static void addBox2D(Vector2f center, Vector2f dimensions, float rotation, Color color, int lifetime) {
        Vector2f min = new Vector2f(center).sub(new Vector2f(dimensions).div(2.0f));    // Bottom left corner
        Vector2f max = new Vector2f(center).add(new Vector2f(dimensions).div(2.0f));    // Top right corner

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), // Bottom left
                new Vector2f(min.x, max.y), // Top left
                new Vector2f(max.x, max.y), // Top right
                new Vector2f(max.x, min.y)  // Bottom right
        };

        if (rotation != 0.0f) {
            for (Vector2f vertex : vertices) {
                MathUtils.rotate(vertex, rotation, center);
            }
        }

        addLine2D(vertices[0], vertices[1], color, lifetime);
        addLine2D(vertices[1], vertices[2], color, lifetime);
        addLine2D(vertices[2], vertices[3], color, lifetime);
        addLine2D(vertices[3], vertices[0], color, lifetime);
    }

    // =================================================================================================================
    // Add Circle methods
    // =================================================================================================================
    public static void addCircle(Vector2f center, float radius) {
        addCircle(center, radius, Color.GREEN, 1);
    }

    public static void addCircle(Vector2f center, float radius, Color color) {
        addCircle(center, radius, color, 1);
    }

    public static void addCircle(Vector2f center, float radius, Color color, int lifetime) {
        int CIRCLE_SEGMENTS = 20;
        Vector2f[] points = new Vector2f[CIRCLE_SEGMENTS];
        int increment = 360 / points.length;
        int currentAngle = 0;

        for (int i = 0; i < points.length; i++) {
            Vector2f tmp = new Vector2f(radius, 0);
            MathUtils.rotate(tmp, currentAngle, new Vector2f());
            points[i] = new Vector2f(tmp).add(center);

            if (i > 0) {
                addLine2D(points[i - 1], points[i], color, lifetime);
            }
            currentAngle += increment;
        }
        addLine2D(points[points.length - 1], points[0], color, lifetime);
    }
}
