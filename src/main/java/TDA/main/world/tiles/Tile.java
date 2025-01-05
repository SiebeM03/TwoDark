package TDA.main.world.tiles;

import org.joml.Vector2f;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Transform;


public abstract class Tile {
    protected Vector2f normalizedCoordinates;
    public Transform transform;
    public Quad renderObject;
}
