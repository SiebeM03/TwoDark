package TDA.entities.ecs.components;

import TDA.entities.ecs.Component;
import TDA.main.GameManager;
import TDA.main.controls.PlayerControls;
import org.joml.Math;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;

import static TDA.entities.player.PlayerConfigs.*;
import static TDA.entities.player.PlayerConfigs.MOVE_SPEED;

public class CharacterController extends Component {
    private static final PlayerControls playerControls = GameManager.gameControls.playerControls;

    // JUMPING
    private float jumpTime = 0;
    private float originalY;
    private float currentJumpHeight = 0;

    // MOVEMENT
    private Vector2f moveDirection = new Vector2f();
    private boolean facingRight = true;
    public float addX, addY;


    @Override
    public void update() {
        if (isGrounded()) {
            if (playerControls.isMoveUpHeld()) {
                moveDirection.y += 1;
            }
            if (playerControls.isMoveDownHeld()) {
                moveDirection.y += -1;
            }
            if (playerControls.isMoveLeftHeld()) {
                moveDirection.x += -1;
            }
            if (playerControls.isMoveRightHeld()) {
                moveDirection.x += 1;
            }
            move();

            if (playerControls.isJumpPressed()) {
                this.jumpTime = 0;
                this.originalY = entity.transform.getY();
                updateJump();
            }
        } else {
            updateJump();
        }
    }


    private void move() {
        if (moveDirection.x == 0 && moveDirection.y == 0) return;
        float dt = Engine.getDelta();
        float normalizedMoveDistance = (playerControls.isSprintHeld() ? SPRINT_SPEED : MOVE_SPEED) / Math.sqrt(Math.abs(moveDirection.x) + Math.abs(moveDirection.y));
        float addX = normalizedMoveDistance * moveDirection.x * dt;
        float addY = normalizedMoveDistance * moveDirection.y * dt;
        this.addX = addX;
        this.addY = addY;

        Collider collider = entity.getComponent(Collider.class);
        if (collider != null) {
            if (!collider.willXCollide(entity.transform.getX() + addX)) {
                entity.transform.addX(addX);
            }
            if (!collider.willYCollide(entity.transform.getY() + addY)) {
                entity.transform.addY(addY);
            }
        } else {
            entity.transform.addX(addX);
            entity.transform.addY(addY);
        }

        updateCharacterFacingDirection();
        moveDirection.set(0);
    }

    private void updateCharacterFacingDirection() {
        if (moveDirection.x == 1) {
            if (!facingRight) {
                facingRight = true;
                entity.getComponent(QuadComponent.class).quad.horizontalFlip();
            }
        }
        if (moveDirection.x == -1) {
            if (facingRight) {
                facingRight = false;
                entity.getComponent(QuadComponent.class).quad.horizontalFlip();
            }
        }
    }

    private void updateJump() {
        this.jumpTime += Engine.getDelta();
        this.currentJumpHeight = (4 * JUMP_HEIGHT / (JUMP_DURATION * JUMP_DURATION)) * jumpTime * (JUMP_DURATION - jumpTime);

        if (isGrounded()) {
            entity.transform.setY(originalY);
        } else {
            entity.transform.setY(originalY + currentJumpHeight);
        }
    }

    public boolean isGrounded() {
        return currentJumpHeight <= 0;
    }
}
