package bricker.gameobjects;

import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Objects;

/**
 * The Heart class represents a game object in the game that appears as a heart.
 */
public class Heart extends GameObject {
    private final GameObjectCollection gameObjects;

    // Counter to keep track of the number of hearts remaining
    private final Counter heartsLeft;

    /**
     * Constructs a Heart object.
     *
     * @param topLeftCorner The position of the top-left corner of the heart
     * @param dimensions    The dimensions of the heart
     * @param renderable    The renderable object used to render the heart
     * @param gameObjects   The collection of game objects in the game
     * @param heartsLeft    The counter to keep track of the number of hearts remaining
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                           GameObjectCollection gameObjects, Counter heartsLeft) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
        this.heartsLeft = heartsLeft;

    }

    /**
     * Determines whether this heart object should collide with another game object.
     *
     * @param other The other game object
     * @return true if this heart should collide with the other object, false otherwise
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (Objects.equals(other.getTag(), Constants.PADDLE_TAG) ||
                Objects.equals(other.getTag(), Constants.FLOOR_TAG)) &&
                Objects.equals(this.getTag(), Constants.FALL_HEART_TAG);
    }


    /**
     * Handles the behavior of this heart object when it collides with another game object.
     *
     * @param other     The other game object
     * @param collision The collision information
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (Objects.equals(other.getTag(), Constants.PADDLE_TAG))
            if (heartsLeft.value() < Constants.MAX_HEART_AMOUNT)
                heartsLeft.increment();
        // Remove the heart from the game
        gameObjects.removeGameObject(this);

    }
}
