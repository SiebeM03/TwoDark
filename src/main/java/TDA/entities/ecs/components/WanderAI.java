package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.debug.DebugDraw;
import woareXengine.util.Color;
import woareXengine.util.Logger;
import woareXengine.util.MathUtils;

public class WanderAI extends Component {
    private Vector2f destination;
    private State currentState = State.IDLE;
    private float idleTime = 0;

    @Override
    public void init() {
        destination = new Vector2f();
    }

    @Override
    public void update() {
        if (MathUtils.randomChance(0.0001f)) {
            Logger.debug("IDLING");
            currentState = State.IDLE;
        } else if (idleTime > 5 && MathUtils.randomChance(0.01f)) {
            Logger.debug("WANDERING");
            idleTime = 0;
            currentState = State.WANDER;
        }

        switch (currentState) {
            case IDLE -> idle();
            case WANDER -> wander();
        }
    }

    private void idle() {
        idleTime += Engine.getDelta();
    }


    private void wander() {
        DebugDraw.addLine2D(entity.transform.getCenter(), destination, Color.RED);

        if (MathUtils.dist(entity.transform.getCenter(), destination) < 50 || MathUtils.randomChance(0.001f)) {
            destination = new Vector2f(destination.x + MathUtils.randomInRange(200, 500, true), destination.y + MathUtils.randomInRange(200, 500, true));
        }

        Vector2f direction = new Vector2f(destination).sub(entity.transform.getCenter()).normalize();
        entity.transform.getPosition().add(direction.mul(Engine.getDelta() * 50));
    }


    private enum State {
        IDLE,
        WANDER,
        SEEK
    }
}
