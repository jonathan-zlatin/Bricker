package bricker.utills;

import danogl.util.Vector2;

public class Constants {
    /**
     * Constants class contains constant values used throughout the application.
     */
    // Tags for game objects
    public static final String WALL_TAG = "Wall";
    public static final String TEMP_PADDLE_TAG = "TempPaddle";
    public static final String PADDLE_TAG = "Paddle";
    public static final String BALL_TAG = "Ball";
    public static final String PUCK_TAG = "Puck";
    public static final String FLOOR_TAG = "Floor";
    public static final String CEILING_TAG = "Ceiling";
    public static final String FALL_HEART_TAG = "FallHeart";


    // File paths for images and sounds
    public static final String BRICK_PNG = "assets/brick.png";
    public static final String HEART_PNG = "assets/heart.png";
    public static final String PADDLE_PNG = "assets/paddle.png";
    public static final String BALL_PNG = "assets/ball.png";
    public static final String SOUND_PUCK_PATH = "assets/blop_cut_silenced.wav";
    public static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
    public static final String BACKGROUND_PNG = "assets/DARK_BG2_small.jpeg";
    public static final String BLOP_SOUND = "assets/blop_cut_silenced.wav";


    // Game dimensions and configurations
    public static final String WINDOW_TITLE = "Bricks with Zlat and Niss";
    public static final String WIN_MSG = "You Win! Play again?";
    public static final String LOSE_MSG = "You Lose! Play again?";
    public static final int DOUBLE_ACTION_MAX = 3;
    public static final int WITH_DOUBLE = 5;
    public static final int WITHOUT_DOUBLE = 4;
    public static final int PUCK_BALLS = 2;
    public static final int INPUT_ROUND = 4;
    public static final int DEFAULT_BOUND = 10;
    public static final int MAX_HEART_AMOUNT = 4;
    public static final int TURNS_FOR_PADDLE = 4;
    public static final float MOVEMENT_SPEED = 300;
    public static final int HEART_SPEED = 100;
    public static final int LEFT_WALL = 40;
    public static final int RIGHT_WALL = 460; // considering the wall and the paddle
    public static final int BALL_SPEED = 200;
    public static final float BRICK_LENGTH = 15;
    public static final int INITIAL_LIFE_AMOUNT = 3;
    public static final int DEFAULT_BRICKS_ROW = 7;
    public static final int DEFAULT_BRICKS_COL = 8;
    public static final int TURN_TO_SHUT_CAMERA = 4;
    public static final int COLS = 2;
    public static final int ROWS = 3;
    public static final float FACTOR_1 = 1.2f;
    public static final float FACTOR_2 = 0.75f;
    public static final float HALF_SCREEN = 2;
    public static final int MAX_HEARTS = 4;
    public static final int FOUR_LIFE = 4;
    public static final int THR_LIFE = 3;
    public static final int TWO_LIFE = 2;
    public static final int ONE_LIFE = 1;


    // Dimensions and positions
    public static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    public static final Vector2 heartDimensions = new Vector2(30, 30);
    public static final Vector2 HEART_POSITION = new Vector2(45, 750);
    public static final Vector2 HEART_DIMENSIONS = new Vector2(30, 30);
    public static final Vector2 WINDOWS_DIMENSIONS = new Vector2(600, 800);
    public static final Vector2 BALL_DIMENSIONS = new Vector2(20, 20);
    public static final Vector2 TEXT_LOCATION = new Vector2(90, 740);
    public static final Vector2 TEXT_DIMENSIONS = new Vector2(30, 30);
    public static final Vector2 PADDLE_T_LEFT_CORNER = new Vector2(300, 750);


    // Padding and spacing
    public static final float HALF_PADDING = 20;
    public static final float PADDING = 40;
    public static final float DOUBLE_PADDING = 80;
    public static final float FOUR_PADDING = 160;
    public static final float SPACES = ((float) 3 / 2);
    public static final int ZERO = 0;
    public static final float HALF = 0.5f;

}

