package bricker.brick_strategies;
import bricker.main.BrickerGameManager;
import bricker.utills.Constants;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

/**
 * The CollisionStrategyFactory class is responsible for creating
 * various collision strategies based on random selection.
 * It utilizes different collision strategies such as:
 * 1. ExtraBallStrategy,
 * 2. ChangeCamera,
 * 3. ExtraPaddle,
 * 4. ExtraLife,
 * 5. and DoubleAction.
 */
public class CollisionStrategyFactory {
    private final Vector2 windowDimensions;
    private final Counter doubleActionCounter;
    private final GameObjectCollection gameObjectCollection;
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Counter lifeLeft;
    private final int ballSpeed;
    private final UserInputListener userInputListener;
    private final Counter extraPaddleCounter;
    private final Vector2 heartDimensions;
    private final Counter cameraCounter;
    /**
     * Constructor for CollisionStrategyFactory class.
     * @param doubleActionCounter Counter for double action collisions
     * @param gameObjectCollection Collection of game objects
     * @param brickerGameManager Game manager for the Bricker game
     * @param imageReader Reader for game images
     * @param soundReader Reader for game sounds
     * @param lifeLeft Counter for remaining lives
     * @param ballSpeed Speed of the ball
     * @param userInputListener Listener for user input
     * @param extraPaddleCounter Counter for extra paddles
     * @param cameraCounter Counter for camera operations
     * @param heartDimensions Dimensions of the heart object
     * @param windowDimensions Dimensions of the game window
     */
    public CollisionStrategyFactory(Counter doubleActionCounter,
                                    GameObjectCollection gameObjectCollection,
                                    BrickerGameManager brickerGameManager,
                                    ImageReader imageReader,
                                    SoundReader soundReader,
                                    Counter lifeLeft, int ballSpeed,
                                    UserInputListener userInputListener,
                                    Counter extraPaddleCounter,
                                    Counter cameraCounter,
                                    Vector2 heartDimensions,
                                    Vector2 windowDimensions) {
        this.doubleActionCounter = doubleActionCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.lifeLeft = lifeLeft;
        this.ballSpeed = ballSpeed;
        this.userInputListener = userInputListener;
        this.extraPaddleCounter = extraPaddleCounter;
        this.heartDimensions = heartDimensions;
        this.windowDimensions = windowDimensions;
        this.cameraCounter = cameraCounter;
    }

    /**
     * Generates a random collision strategy based on the
     * specified bound and basic collision strategy.
     * @param bound The upper bound for random selection
     * @param basicCollisionStrategy The basic collision strategy to decorate
     * @return A randomly selected collision strategy
     */
    public CollisionStrategy RandomCollisionStrategy(int bound, CollisionStrategy basicCollisionStrategy) {
        // Generate a random number within the specified bound
        int strategyType = new Random().nextInt(bound);

        // Based on the random number, choose a collision strategy
        switch (strategyType) {
            case 0: return new ExtraBallStrategy(
                    gameObjectCollection, imageReader,
                    soundReader, lifeLeft, ballSpeed, basicCollisionStrategy);
            case 1: return new ChangeCamera(brickerGameManager,
                    gameObjectCollection,windowDimensions,cameraCounter, basicCollisionStrategy);
            case 2: return new ExtraPaddle(gameObjectCollection,
                    imageReader, userInputListener,
                    extraPaddleCounter, basicCollisionStrategy, windowDimensions);
            case 3: return new ExtraLife(gameObjectCollection, heartDimensions,
                    imageReader.readImage(Constants.HEART_PNG, true),
                    lifeLeft, basicCollisionStrategy);
            case 4: doubleActionCounter.increment(); return doubleAction();
            default: return basicCollisionStrategy;
        }
    }

    /**
     * Generates a double action collision strategy.
     * <p>
     * This method selects two distinct collision strategies,
     * combining them into a single collision effect.
     *
     * @return A double action collision strategy.
     */
    private CollisionStrategy doubleAction() {
        int bound = Constants.WITH_DOUBLE;
        CollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(gameObjectCollection);

        // Determine the bound based on the current value of doubleActionCounter
        if (doubleActionCounter.value() % (Constants.DOUBLE_ACTION_MAX - 1) == 0) {
            bound = Constants.WITHOUT_DOUBLE;
        }

        // Apply RandomCollisionStrategy with the determined bound
        basicCollisionStrategy = RandomCollisionStrategy(bound, basicCollisionStrategy);

        // If the bound was changed, apply RandomCollisionStrategy again
        if (doubleActionCounter.value() % (Constants.DOUBLE_ACTION_MAX - 1) == 0) {
            basicCollisionStrategy = RandomCollisionStrategy(bound, basicCollisionStrategy);
        }

        return basicCollisionStrategy;
    }
}
