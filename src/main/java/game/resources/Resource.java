package game.resources;

import engine.ecs.Sprite;

public abstract class Resource {
    public double amount;
    private double baseGain;

    /**
     * Get the index of the sprite in the sprite sheet
     */
    public abstract int getSpriteIndex();

    public abstract Sprite getSprite();

    public abstract double calculateGain();

    public void click() {

    }
}
