package bricker.brick_strategies;


import bricker.gameobjects.TempPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.utills.*;
/**
 * The ExtraPaddle class is a decorator for
 * CollisionStrategy, which adds functionality to
 * create an additional temporary paddle
 * upon collision with certain game objects.
 * It extends the CollisionStrategyDecorator class.
 */
public class ExtraPaddle extends CollisionStrategyDecorator {
    private final ImageReader imageReader;
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final Counter extraPaddleCounter;
    private final CollisionStrategy decorator;

    /**
     * Constructor for ExtraPaddle class.
     * @param gameObjects Collection of game objects
     * @param imageReader Image reader for game objects
     * @param inputListener User input listener
     * @param extraPaddleCounter Counter to track
     *                          the availability of extra paddles
     * @param decorator Decorated collision strategy
     * @param windowDimensions Dimensions of the game window
     */
    public ExtraPaddle(GameObjectCollection gameObjects,
                       ImageReader imageReader,
                       UserInputListener inputListener,
                       Counter extraPaddleCounter,
                       CollisionStrategy decorator,
                       Vector2 windowDimensions) {
        super(decorator);
        this.decorator = decorator;
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.extraPaddleCounter = extraPaddleCounter;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Method invoked upon collision with another game object.
     * It adds functionality to create an additional temporary paddle
     * and removes the current object upon collision.
     * @param thisObj The game object with this collision strategy
     * @param otherObj The other game object involved in the collision
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        decorator.onCollision(thisObj, otherObj);
        if (extraPaddleCounter.value() == 0) {
            Vector2 boardCenter = new Vector2(
                    windowDimensions.x() / Constants.HALF_SCREEN,
                    windowDimensions.y() / Constants.HALF_SCREEN);
            Renderable paddleImage =
                    imageReader.readImage(
                            Constants.PADDLE_PNG,
                            true);
            TempPaddle tempPaddle =
                    new TempPaddle(boardCenter,
                            Constants.PADDLE_DIMENSIONS,
                            paddleImage, inputListener,
                            gameObjects,extraPaddleCounter);
            gameObjects.addGameObject(tempPaddle);
            extraPaddleCounter.increaseBy(Constants.TURNS_FOR_PADDLE);
        }
        gameObjects.removeGameObject(thisObj);
    }
}