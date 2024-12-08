package engine.graphics;

import engine.util.Settings;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * The Camera class handles rendering transformations such as projection and view transformations,
 * allowing the scene to be rendered relative to the camera's position and settings.
 */
public class Camera {

    /** The projection matrix defining the camera's orthographic projection. */
    private Matrix4f projectionMatrix;

    /** The view matrix representing the camera's position and orientation in the scene. */
    private Matrix4f viewMatrix;

    /** The inverse of the projection matrix, used for reverse transformations. */
    private Matrix4f inverseProjection;

    /** The inverse of the view matrix, used for reverse transformations. */
    private Matrix4f inverseView;

    /** The position of the camera in the 2D scene. */
    public Vector2f position;

    /** The dimensions of the camera's projection in world units. */
    private Vector2f projectionSize = new Vector2f(Settings.PROJECTION_WIDTH, Settings.PROJECTION_HEIGHT);

    /**
     * Creates a new Camera instance at the specified position and initializes its matrices.
     *
     * @param position the initial position of the camera
     */
    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        this.inverseView = new Matrix4f();
        adjustProjection();
    }

    /**
     * Adjusts the projection matrix to fit the current {@link #projectionSize}.
     * <p>
     * The matrix is configured for an orthographic projection.
     */
    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, projectionSize.x, 0.0f, projectionSize.y, 0.0f, 100.0f);
        projectionMatrix.invert(inverseProjection);
    }

    /**
     * Calculates and returns the view matrix based on the camera's current position.
     *
     * @return the view matrix representing the camera's view transformation
     */
    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(
                new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp
        );
        this.viewMatrix.invert(inverseView);
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getInverseProjection() {
        return this.inverseProjection;
    }

    public Matrix4f getInverseView() {
        return this.inverseView;
    }

    public Vector2f getProjectionSize() {
        return this.projectionSize;
    }
}
