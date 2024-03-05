package bricker.gameobjects;

import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Objects;
import java.util.Random;
/**
 * Represents a ball GameObject in the game.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private final Counter heartLeft;
    private final Vector2 topLeftCorner;
    private final int speed;
    private final Counter collisionCounter = new Counter();
    /**
     * Constructs a new Ball object.
     *
     * @param topLeftCorner   the top left corner position of the ball
     * @param dimensions      the dimensions of the ball
     * @param renderable      the renderable object for rendering the ball
     * @param collisionSound  the sound to play on collision
     * @param heartsLeft      the counter for remaining hearts/lives
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound,
                Counter heartsLeft) {
        super(topLeftCorner, dimensions, renderable);
        this.setTag(Constants.BALL_TAG);
        this.collisionSound = collisionSound;
        this.heartLeft = heartsLeft;
        this.topLeftCorner = topLeftCorner;
        this.speed = Constants.BALL_SPEED;
        setDirection();
    }

    /**
     * Handles collision with other GameObjects.
     *
     * @param other     the other GameObject involved in the collision
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (Objects.equals(other.getTag(), Constants.FLOOR_TAG) &&
            Objects.equals(this.getTag(), Constants.BALL_TAG)) {
            heartLeft.decrement();
            if (heartLeft.value() > 0) {
                repositionBall();
                return;
            }
        }

        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionCounter.increment();
        collisionSound.play();
    }
    /**
     * Repositions the ball to its initial position.
     */
    private void repositionBall() {
        this.setCenter(topLeftCorner);
        this.setDirection();
    }
    /**
     * Gets the current collision count.
     *
     * @return the collision count
     */
    public int getCollisionCounter() {
        return collisionCounter.value();
    }
    /**
     * Sets a random initial direction for the ball.
     */
    private void setDirection() {
        float ballVelX = speed;
        float ballVelY = speed;
        Random random = new Random();
        if (random.nextBoolean())
            ballVelX *= -1;
        if (random.nextBoolean())
            ballVelY *= -1;
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }

}
