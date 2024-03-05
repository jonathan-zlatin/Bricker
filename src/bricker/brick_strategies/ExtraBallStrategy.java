package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;

/**
 * The ExtraBallStrategy class is a decorator for CollisionStrategy,
 * which adds functionality to split a ball into multiple pucks
 * upon collision with certain game objects.
 * It extends the CollisionStrategyDecorator class.
 */
public class ExtraBallStrategy extends CollisionStrategyDecorator {

    private final int speed;
    private final Counter heartsLeft;
    private final SoundReader soundReader;
    private final ImageReader imageReader;
    private final GameObjectCollection gameObjects;
    private final CollisionStrategy decorator;

    /**
     * Constructor for ExtraBallStrategy class.
     * @param gameObjects Collection of game objects
     * @param imageReader Image reader for game objects
     * @param soundReader Sound reader for game objects
     * @param heartsLeft Counter to track the number of hearts left
     * @param speed Speed of the pucks
     * @param decorator Decorated collision strategy
     */
    public ExtraBallStrategy(GameObjectCollection gameObjects, ImageReader imageReader,
                             SoundReader soundReader, Counter heartsLeft, int speed,
                             CollisionStrategy decorator) {
        super(decorator);
        this.decorator = decorator;
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.heartsLeft = heartsLeft;
        this.speed = speed;
    }

    /**
     * Method invoked upon collision with another game object.
     * It adds functionality to split a ball into multiple pucks
     * and removes the current object upon collision.
     * @param thisObj The game object with this collision strategy
     * @param otherObj The other game object involved in the collision
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        decorator.onCollision(thisObj, otherObj);

        // Load the puck image and collision sound
        Renderable puckImage = imageReader.readImage(Constants.PUCK_IMAGE_PATH, true);
        Sound collisioionSound = soundReader.readSound(Constants.SOUND_PUCK_PATH);

        // Remove the current object from the game
        gameObjects.removeGameObject(thisObj);

        // Create multiple puck objects as a result of collision
        for (int i = 0; i < Constants.PUCK_BALLS; i++) {
            Puck puck = new Puck(otherObj.getCenter(),
                    (otherObj.getDimensions().mult(Constants.FACTOR_2)),
                    puckImage, collisioionSound, gameObjects, heartsLeft);

            // Add the new puck object to the game
            gameObjects.addGameObject(puck);
        }
    }
}
