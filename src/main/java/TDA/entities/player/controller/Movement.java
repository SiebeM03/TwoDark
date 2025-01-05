package TDA.entities.player.controller;

import TDA.entities.player.Player;
import TDA.main.GameManager;
import TDA.main.controls.PlayerControls;
import org.joml.Math;
import org.joml.Vector2f;
import woareXengine.util.Layer;

import static TDA.entities.player.PlayerConfigs.*;


public class Movement {
    private static final PlayerControls playerControls = GameManager.gameControls.playerControls;

    private Player player;

    public boolean isJumping = false;
    float jumpTime = 0;
    float originalY;

    public float addX = 0;
    public float addY = 0;

    public boolean isSprinting = false;
    Vector2f dir = new Vector2f(0, 0);

    public boolean facingRight = true;

    public Movement(Player player) {
        this.player = player;
    }

    public void update(float dt) {
        this.addX = 0;
        this.addY = 0;

        if (!isJumping) {
            if (playerControls.isMoveUpHeld()) {
                dir.y = 1;
            }
            if (playerControls.isMoveDownHeld()) {
                dir.y = -1;
            }
            if (playerControls.isMoveLeftHeld()) {
                dir.x = -1;
                if (facingRight) {
                    player.renderObject.horizontalFlip();
                    facingRight = false;
                }
            }
            if (playerControls.isMoveRightHeld()) {
                dir.x = 1;
                if (!facingRight) {
                    player.renderObject.horizontalFlip();
                    facingRight = true;
                }
            }

            if (!dir.equals(new Vector2f(0, 0))) {
                float normalizedMoveDistance = (playerControls.isSprintHeld() ? SPRINT_SPEED : MOVE_SPEED) / Math.sqrt(Math.abs(dir.x) + Math.abs(dir.y));
                this.addX = normalizedMoveDistance * dir.x * dt;
                this.addY = normalizedMoveDistance * dir.y * dt;

                float newX = this.player.transform.getX() + addX;
                float newY = this.player.transform.getY() + addY;
                if (!this.player.collisionBox.willXCollide(newX)) {
                    this.player.transform.setX(newX);
                }
                if (!this.player.collisionBox.willYCollide(newY)) {
                    this.player.transform.setY(newY);
                }

                dir.set(0);
            }


            if (playerControls.isJumpPressed()) {
                isJumping = true;
                jumpTime = 0;
                originalY = player.transform.getY();
            }
        } else {
            jumpTime += dt;
            float jumpHeight = getJumpHeight(jumpTime);
            if (jumpHeight <= 0) {
                isJumping = false;
                jumpTime = 0;
                player.transform.setY(originalY);

                // TODO this is a hack to fix the player render order after jumping
                // when the originalY is behind other objects the player will be rendered in front of them while jumping
                player.renderObject.zIndex = Layer.FOREGROUND;
            } else {
                player.transform.setY(originalY + jumpHeight);
                player.renderObject.zIndex = 100;
            }
        }
    }

    private float getJumpHeight(float time) {
        return (float) (4 * JUMP_HEIGHT / (JUMP_DURATION * JUMP_DURATION)) * jumpTime * (JUMP_DURATION - jumpTime);
    }
}
