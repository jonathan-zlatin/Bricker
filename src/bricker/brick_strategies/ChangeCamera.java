package bricker.brick_strategies;


import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;


import java.util.Objects;

/**
 * Collision strategy decorator for changing the camera focus.
 *
 * <p> The ChangeCamera class is a collision strategy decorator that changes
 * the camera focus when a collision between a ball and another
 * game object occurs.It sets the camera to follow
 * the ball for a specified duration before reverting to its default state.
 */
public class ChangeCamera extends CollisionStrategyDecorator {
    private final Vector2 windowDimensions;
    private final BrickerGameManager gameManager;
    private final GameObjectCollection gameObjects;
    private final CollisionStrategy decorator;
    private final Counter cameraCounter;

    /**
     * Constructs a ChangeCamera collision strategy decorator.
     *
     * @param gameManager The game manager responsible for managing game state.
     * @param gameObjects The collection of game objects.
     * @param windowDimensions The dimensions of the game window.
     * @param cameraCounter The counter for managing camera duration.
     * @param decorator The collision strategy to decorate.
     */
    public ChangeCamera(BrickerGameManager gameManager,
                        GameObjectCollection gameObjects,
                        Vector2 windowDimensions,
                        Counter cameraCounter,
                        CollisionStrategy decorator
                        ) {
        super(decorator);
        this.decorator = decorator;
        this.windowDimensions = windowDimensions;
        this.cameraCounter = cameraCounter;
        this.gameManager = gameManager;
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the collision event and changes the camera focus if necessary.
     *
     * @param thisObj The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        decorator.onCollision(thisObj, otherObj);
        // Check if the collision involves a ball object
        // and the game camera is not already set
        if(Objects.equals(otherObj.getTag(), Constants.BALL_TAG) && gameManager.camera() == null) {
            Ball ball = (Ball)otherObj;
            cameraCounter.increaseBy(ball.getCollisionCounter() + Constants.TURN_TO_SHUT_CAMERA);
            gameManager.setCamera(new Camera(
                    otherObj,
                    Vector2.ZERO,
                    windowDimensions.mult(Constants.FACTOR_1),
                    windowDimensions));
        }
        gameObjects.removeGameObject(thisObj);
    }
}

