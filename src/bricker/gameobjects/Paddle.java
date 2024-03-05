package bricker.gameobjects;

import bricker.utills.Constants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
/**
 * Represents a paddle GameObject in the game.
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    /**
     * Constructs a new Paddle object.
     *
     * @param topLeftCorner   the top left corner position of the paddle
     * @param dimensions      the dimensions of the paddle
     * @param renderable      the renderable object for rendering the paddle
     * @param inputListener   the input listener for controlling the paddle
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.setTag(Constants.PADDLE_TAG);
    }
    /**
     * Updates the paddle's position based on user input.
     *
     * @param deltaTime     the time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (this.getTopLeftCorner().x() < Constants.LEFT_WALL) {
            this.setTopLeftCorner(new Vector2(Constants.LEFT_WALL, this.getTopLeftCorner().y()));
            movementDir = movementDir.add(Vector2.ZERO);
        } else if (this.getTopLeftCorner().x() > Constants.RIGHT_WALL) {
            this.setTopLeftCorner(new Vector2(Constants.RIGHT_WALL, this.getTopLeftCorner().y()));
            movementDir = movementDir.add(Vector2.ZERO);
        } else if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        } else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(Constants.MOVEMENT_SPEED));
    }
}
