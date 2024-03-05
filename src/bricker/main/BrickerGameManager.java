package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.utills.*;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Manages the Bricker game logic and entities.
 * <p>
 * The BrickerGameManager class extends the GameManager
 * and serves as the core component
 * for managing the game logic, entities, and interactions
 * in the Bricker game. It initializes
 * the game environment, generates game objects, handles
 * collisions, and updates the game state.
 */
public class BrickerGameManager extends GameManager {
    private final Vector2 windowDimensions;
    private final int bricksInRow;
    private final int amountOfRows;
    private Ball ball;
    private WindowController windowController;
    private Counter lifeLeft;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Counter bricksLeftToHit;
    private UserInputListener inputListener;
    private Counter extraPaddleCounter;
    private HeartDrawer heartDrawer;
    private final Counter doubleActionCounter = new Counter();
    private CollisionStrategyFactory CollisionStrategyFactory;
    private Counter cameraCounter;

    /**
     * Entry point for starting the Bricker game.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        // Verify the input - decide default or costume game
        if (args.length != Constants.INPUT_ROUND) {
            new BrickerGameManager(Constants.WINDOW_TITLE,
                    Constants.WINDOWS_DIMENSIONS).run();
        } else {
            int bricksInRow = Integer.parseInt(args[Constants.COLS]);
            int amountOfRows = Integer.parseInt(args[Constants.ROWS]);
            new BrickerGameManager(
                    Constants.WINDOW_TITLE,
                    Constants.WINDOWS_DIMENSIONS,
                    bricksInRow,
                    amountOfRows).run();
        }
    }

    /**
     * Constructs a BrickerGameManager with default settings.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.bricksInRow = Constants.DEFAULT_BRICKS_COL;
        this.amountOfRows = Constants.DEFAULT_BRICKS_ROW;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Constructs a BrickerGameManager with custom brick layout settings.
     * <p>
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param bricksInRow      The number of bricks in each row.
     * @param amountOfRows     The number of rows of bricks.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
                              int bricksInRow, int amountOfRows) {
        super(windowTitle, windowDimensions);
        this.bricksInRow = bricksInRow;
        this.amountOfRows = amountOfRows;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Initializes the Bricker game.
     * <p>
     * This method sets up the initial state of the game,
     * including player lives,
     * image and sound resources, input handling, camera,
     * and collision strategies.
     * <p>
     * It initializes:
     * 1.counters for player lives
     * 2.camera control
     * 3.bricks left to hit
     * 4.extra paddles.
     * It creates a CollisionStrategyFactory to generate collision strategies.
     * Finally, it calls the generateGame method to set up the game environment.
     *
     * @param imageReader      The ImageReader instance to load images.
     * @param soundReader      The SoundReader instance to load sounds.
     * @param inputListener    The UserInputListener instance to handle user input.
     * @param windowController The WindowController instance to
     *                         manage the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.lifeLeft = new Counter(Constants.INITIAL_LIFE_AMOUNT);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.cameraCounter = new Counter(Constants.ZERO);
        this.bricksLeftToHit = new Counter(amountOfRows * bricksInRow);
        this.windowController = windowController;
        this.extraPaddleCounter = new Counter(Constants.ZERO);
        this.CollisionStrategyFactory = new
                CollisionStrategyFactory(
                        doubleActionCounter,
                        gameObjects(),
                        this,
                        imageReader,
                        soundReader,
                        lifeLeft,
                        Constants.BALL_SPEED,
                        inputListener,
                        extraPaddleCounter,
                        cameraCounter,
                        Constants.heartDimensions,
                        windowDimensions);

        generateGame();
    }

    /**
     * Generates the initial game entities.
     * <p>
     * This method initializes the game environment by
     * creating the initial game entities,
     * including the ball, text display, paddle, walls,
     * background, bricks, and hearts.
     */
    private void generateGame() {

        generateBall(windowDimensions);

        generatePaddle(imageReader, inputListener);

        generateWalls(windowDimensions);

        generateBackGround(imageReader, windowDimensions);

        generateBricks(windowDimensions);

        generateLifeDisplay();
    }

    /**
     * Generates the text display for player lives.
     * <p>
     * This method creates a text display to show the
     * number of lives remaining for the player.
     * It utilizes the current value of the life counter
     * to update the text content dynamically.
     */
    private Text generateText() {
        TextRenderable textRenderable = new
                TextRenderable(String.valueOf(lifeLeft.value()));
        return new Text(
                Constants.TEXT_LOCATION,
                Constants.TEXT_DIMENSIONS,
                textRenderable,
                gameObjects(),
                lifeLeft);
    }

    /**
     * Generates bricks within the game environment.
     * <p>
     * This method creates bricks arranged in rows and columns,
     * based on the specified dimension of the game window.
     * It iterates through each position in the grid,
     * assigning a collision strategy and adding the brick
     * to the game objects' collection.
     *
     * @param windowDimensions The dimensions of the game window.
     */
    private void generateBricks(Vector2 windowDimensions) {
        // Read the brick image
        Renderable brickImage = imageReader.readImage(Constants.BRICK_PNG, false);

        // Calculate the width of each brick
        float brickWidth = (windowDimensions.x() - Constants.FOUR_PADDING) / bricksInRow;

        // Generate bricks row by row
        for (int i = 0; i < amountOfRows; i++) {
            for (int j = 0; j < bricksInRow; j++) {
                // Create collision strategy for the brick
                BasicCollisionStrategy basic = new BasicCollisionStrategy(gameObjects());
                CollisionStrategy collisionStrategy = CollisionStrategyFactory.RandomCollisionStrategy(
                        Constants.DEFAULT_BOUND, basic);

                // Calculate position of the brick
                Vector2 brickPosition = new Vector2(j * brickWidth + Constants.DOUBLE_PADDING,
                        i * Constants.BRICK_LENGTH + Constants.DOUBLE_PADDING);

                // Create a new brick object
                Brick brick = new Brick(brickPosition,
                        new Vector2(brickWidth, Constants.BRICK_LENGTH),
                        brickImage, collisionStrategy, bricksLeftToHit);

                // Reset doubleActionCounter if needed
                if (doubleActionCounter.value() != Constants.ZERO) {
                    doubleActionCounter.reset();
                }

                // Add the brick to the game objects
                gameObjects().addGameObject(brick);
            }
        }
    }


    /**
     * Generates the background GameObject and adds it
     * to the game objects collection.
     *
     * @param imageReader      The image reader to load
     *                         the background image.
     * @param windowDimensions The dimensions of the
     *                         game window.
     */
    private void generateBackGround(ImageReader imageReader, Vector2 windowDimensions) {
        // Read the background image
        Renderable backGroundImage = imageReader.readImage(Constants.BACKGROUND_PNG, false);

        // Calculate the screen center
        Vector2 screenCenter = new Vector2(
                windowDimensions.x() / Constants.HALF_SCREEN,
                windowDimensions.y() / Constants.HALF_SCREEN);

        // Create the background object
        GameObject background = new GameObject(screenCenter,
                new Vector2(windowDimensions.x(), windowDimensions.y()),
                backGroundImage);

        // Set properties of the background object
        background.setCenter(screenCenter);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Add the background object to the game objects with appropriate layer
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    /**
     * Generates walls (left, right, ceiling, floor) around the
     * game area and adds them to the game objects collection.
     *
     * @param windowDimensions The dimensions of the game window.
     */
    private void generateWalls(Vector2 windowDimensions) {
        // Generate left wall
        generateWall(Vector2.ZERO,
                new Vector2(Constants.PADDING, windowDimensions.y()),
                Constants.WALL_TAG, Color.red);

        // Generate right wall
        generateWall(new Vector2(windowDimensions.x() - Constants.PADDING, Constants.ZERO),
                new Vector2(Constants.PADDING, windowDimensions.y()),
                Constants.WALL_TAG, Color.red);

        // Generate ceiling
        generateWall(Vector2.ZERO,
                new Vector2(windowDimensions.x(), Constants.PADDING),
                Constants.CEILING_TAG, Color.red);

        // Generate floor
        generateWall(new Vector2(Constants.ZERO, windowDimensions.y() - Constants.HALF_PADDING),
                new Vector2(windowDimensions.x(), Constants.HALF_PADDING),
                Constants.FLOOR_TAG, null);
    }

    /**
     * Generates a wall GameObject and adds it
     * to the game objects collection.
     * <p>
     * @param topLeftCorner The top-left corner position of the wall.
     * @param dimensions    The dimensions of the wall.
     * @param tag           The tag to assign to the wall GameObject.
     * @param color         The color of the wall (null if no color).
     */
    private void generateWall(Vector2 topLeftCorner, Vector2 dimensions, String tag, Color color) {
        // Create a new wall GameObject
        GameObject wall = new GameObject(topLeftCorner, dimensions,
                color != null ? new RectangleRenderable(color) : null);

        // Set the tag for the wall
        wall.setTag(tag);

        // Add the wall to the game objects
        gameObjects().addGameObject(wall);
    }



    /**
     * Generates the life display consisting of hearts
     * and text representing player lives.
     * Creates a text object, generates a heart drawer,
     * and draws the hearts.
     */
    private void generateLifeDisplay() {
        Text text = generateText();
        generateHeartDrawer(text);
        heartDrawer.Draw();
    }

    /**
     * Generates a HeartDrawer object for managing the display of
     * hearts representing player lives.
     *
     * @param text The Text object for displaying the life count.
     */
    private void generateHeartDrawer(Text text) {
        Renderable heartsImage = imageReader.readImage( Constants.HEART_PNG, true);

        // initiate Heart Drawer
        this.heartDrawer = new HeartDrawer(
                Constants.HEART_POSITION,
                Constants.HEART_DIMENSIONS,
                lifeLeft, heartsImage,
                gameObjects(), text);
    }

    /**
     * Generates and adds a paddle GameObject to
     * the game objects collection.
     *
     * @param imageReader   The image reader to
     *                      load the paddle image.
     * @param inputListener The user input listener
     *                      for paddle control.
     */
    private void generatePaddle(ImageReader imageReader, UserInputListener inputListener) {
        // Define the top-left corner of the paddle
        Vector2 paddleTLeftCorner = Constants.PADDLE_T_LEFT_CORNER;

        // Read the paddle image
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_PNG, true);

        // Create a new paddle object
        Paddle paddle = new Paddle(paddleTLeftCorner, Constants.PADDLE_DIMENSIONS, paddleImage, inputListener);

        // Add the paddle to the game objects
        gameObjects().addGameObject(paddle);
    }


    /**
     * Generates and adds a ball GameObject to
     * the game objects collection.
     *
     * @param windowDimensions The dimensions of the game window.
     */
    private void generateBall(Vector2 windowDimensions) {
        // Read the ball image
        Renderable ballImage = imageReader.readImage(Constants.BALL_PNG, true);

        // Read the collision sound
        Sound collisionSound = soundReader.readSound(Constants.BLOP_SOUND);

        // Create a new Ball object
        this.ball = new Ball(windowDimensions.mult(Constants.HALF),
                Constants.BALL_DIMENSIONS, ballImage, collisionSound, lifeLeft);

        // Add the ball to the game objects
        gameObjects().addGameObject(ball);
    }


    /**
     * Updates the game state and handles various
     * game events such as updating the
     * display of hearts, resetting the camera,
     * and checking win or lose conditions.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Generate text and draw hearts if needed
        if (heartDrawer.getHeartOnBoard() != lifeLeft.value()) {
            heartDrawer.Draw();
        }

        // Check if camera needs to be reset
        if (cameraCounter.value() > 0 && ball.getCollisionCounter() >= cameraCounter.value()) {
            this.setCamera(null);
            cameraCounter.reset();
        }

        // Check win or lose conditions
        String prompt = "";
        if (bricksLeftToHit.value() == Constants.ZERO ||
            inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt += Constants.WIN_MSG;
            newGame(prompt);
        } else if (lifeLeft.value() == Constants.ZERO) {
            prompt += Constants.LOSE_MSG;
            newGame(prompt);
        }
    }

    /**
     * Initiates a new game session based
     * on the provided prompt message.
     * If the user confirms the prompt, the
     * game is reset with initial life amount.
     * If the user declines, the game window
     * is closed.
     *
     * @param prompt The message prompt displayed to the user.
     */
    private void newGame(String prompt) {
        // Open a yes/no dialog with the provided prompt
        if (windowController.openYesNoDialog(prompt)) {
            // If the user selects "Yes", reset the game
            lifeLeft = new Counter(Constants.INITIAL_LIFE_AMOUNT);
            windowController.resetGame();
        } else {
            // If the user selects "No", close the window
            windowController.closeWindow();
        }
    }

}
