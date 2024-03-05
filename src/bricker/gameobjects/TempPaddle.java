package bricker.gameobjects;

import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Objects;
/**
 * Represents a temporary paddle GameObject in
 * the game, extending the Paddle class.
 */
public class TempPaddle extends Paddle {
    private final Counter extraPaddleCounter;
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a new TempPaddle object.
     *
     * @param topLeftCorner          the top left
     *                               corner position of
     *                               the temporary paddle
     * @param dimensions             the dimensions of the
     *                               temporary paddle
     * @param renderable             the renderable object for
     *                               rendering the temporary paddle
     * @param inputListener          the input listener for
     *                               controlling the temporary paddle
     * @param extraPaddleCounter     the counter for extra paddles
     */
    public TempPaddle(Vector2 topLeftCorner,
                      Vector2 dimensions,
                      Renderable renderable,
                      UserInputListener inputListener,
                      GameObjectCollection gameObjects,
                      Counter extraPaddleCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener);
        this.gameObjects = gameObjects;

        this.setTag(Constants.TEMP_PADDLE_TAG);
        this.extraPaddleCounter = extraPaddleCounter;
    }
    /**
     * Handles collision with other GameObjects.
     *
     * @param other     the other GameObject involved in the collision
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject other,
                                 Collision collision) {
        super.onCollisionEnter(other, collision);
        if (Objects.equals(other.getTag(), Constants.BALL_TAG) ||
                Objects.equals(other.getTag(),
                Constants.PUCK_TAG)) {

            this.extraPaddleCounter.decrement();
            if(this.extraPaddleCounter.value()==0){
                gameObjects.removeGameObject(this);
                this.extraPaddleCounter.reset();
            }
        }

    }
}
