package TDA.entities.player;

import TDA.entities.player.controller.Deadzone;

public class PlayerConfigs {
    // MOVEMENT
    public static final float MOVE_SPEED = 1000;
    public static final float SPRINT_SPEED = 2000;
    public static final float JUMP_HEIGHT = 100;
    public static final float JUMP_DURATION = 1;

    // CAMERA
    public static final Deadzone DEADZONE = new Deadzone(0.2f, 0.4f);
}
