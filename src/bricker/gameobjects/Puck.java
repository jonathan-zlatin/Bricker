package bricker.gameobjects;

import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Objects;
import java.util.Random;
/**
 * Represents a puck GameObject in the game, extending the Ball class.
 */
public class Puck extends Ball {
    private final int speed;
    private final Sound collisionSound;
    private final GameObjectCollection gameObjects;
    /**
     * Constructs a new Puck object.
     *
     * @param topLeftCorner       the top left corner position of the puck
     * @param dimensions          the dimensions of the puck
     * @param renderable          the renderable object for rendering the puck
     * @param collisionSound      the sound to play on collision
     * @param gameObjects         the collection of GameObjects in the game
     * @param heartsLeft          the counter for remaining hearts/lives
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound, GameObjectCollection gameObjects,
                Counter heartsLeft) {
        super(topLeftCorner, dimensions, renderable, collisionSound, heartsLeft);
        this.setTag(Constants.PUCK_TAG);
        this.collisionSound = collisionSound;
        this.gameObjects = gameObjects;
        this.speed = Constants.BALL_SPEED;
        setDirection();
    }
    /**
     * Sets a random initial direction for the puck.
     */
    private void setDirection() {
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float puckVelX = (float) Math.cos(angle) * speed;
        float puckVelY = (float) Math.sin(angle) * speed;
        this.setVelocity(new Vector2(puckVelX, puckVelY));
    }
    /**
     * Handles collision with other GameObjects.
     *
     * @param other     the other GameObject involved in the collision
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (Objects.equals(other.getTag(), Constants.FLOOR_TAG)) {
            gameObjects.removeGameObject(this);
            return;
        }
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }
}
