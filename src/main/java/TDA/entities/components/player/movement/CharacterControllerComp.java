package TDA.entities.components.player.movement;

import TDA.entities.main.Component;
import TDA.entities.components.interactions.ColliderComp;
import TDA.entities.components.rendering.QuadComp;
import TDA.main.GameManager;
import TDA.main.controls.PlayerControls;
import org.joml.Math;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;

import static TDA.main.configs.PlayerConfigs.*;

public class CharacterControllerComp extends Component {
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
        // TODO - this is a temporary fix for the diagonal movement speed being faster than the horizontal/vertical movement speed, check if Vector2f.normalize() is the correct way to fix this
        float normalizedMoveDistance = (playerControls.isSprintHeld() ? SPRINT_SPEED : MOVE_SPEED) / Math.sqrt(Math.abs(moveDirection.x) + Math.abs(moveDirection.y));
        float addX = normalizedMoveDistance * moveDirection.x * dt;
        float addY = normalizedMoveDistance * moveDirection.y * dt;
        this.addX = addX;
        this.addY = addY;

        ColliderComp collider = entity.getComponent(ColliderComp.class);
        if (collider != null) {
            float newX = entity.transform.getX() + addX;
            float adjustedX = collider.getXCollisionAdjustment(newX);
            entity.transform.setX(adjustedX);

            float newY = entity.transform.getY() + addY;
            float adjustedY = collider.getYCollisionAdjustment(newY);
            entity.transform.setY(adjustedY);
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
                entity.getComponent(QuadComp.class).quad.horizontalFlip();
            }
        }
        if (moveDirection.x == -1) {
            if (facingRight) {
                facingRight = false;
                entity.getComponent(QuadComp.class).quad.horizontalFlip();
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
