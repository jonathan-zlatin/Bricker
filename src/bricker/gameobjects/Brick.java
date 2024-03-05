package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Objects;

/**
 * Represents a brick GameObject in the game.
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private boolean isDestroyed = false;
    private final Counter bricksLeftToHit;

    /**
     * Constructs a new Brick object.
     *
     * @param topLeftCorner     the top left corner position of the brick
     * @param dimensions        the dimensions of the brick
     * @param renderable        the renderable object for rendering the brick
     * @param collisionStrategy the collision strategy for the brick
     * @param bricksLeftToHit   the counter for remaining hits required to destroy the brick
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 Renderable renderable, CollisionStrategy collisionStrategy,
                 Counter bricksLeftToHit) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksLeftToHit = bricksLeftToHit;
    }

    /**
     * Handles collision with other GameObjects.
     *
     * @param otherObj     the other GameObject involved in the collision
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject otherObj, Collision collision) {
        if (isDestroyed)
            return;
        super.onCollisionEnter(otherObj, collision);
        if (Objects.equals(otherObj.getTag(), Constants.BALL_TAG) ||
                (Objects.equals(otherObj.getTag(), Constants.PUCK_TAG))) {
            collisionStrategy.onCollision(this, otherObj);
            isDestroyed = true;
            bricksLeftToHit.decrement();
        }
    }
}


