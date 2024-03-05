package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Heart;
import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Objects;

/**
 * The ExtraLife class is a decorator for CollisionStrategy,
 * which adds functionality to generate an extra life (heart)
 * upon collision with a ball. It extends the
 * CollisionStrategyDecorator class.
 */
public class ExtraLife extends CollisionStrategyDecorator {
    private final GameObjectCollection gameObjects;
    private final Renderable heartImage;
    private final Vector2 heartDimensions;
    private final Counter heartsOnBoard;
    private final CollisionStrategy decorator;

    /**
     * Constructor for ExtraLife class.
     * @param gameObjects Collection of game objects
     * @param heartDimensions Dimensions of the heart object
     * @param heartImage Image for the heart object
     * @param heartsOnBoard Counter to track the number of hearts on the board
     * @param decorator Decorated collision strategy
     */
    public ExtraLife(GameObjectCollection gameObjects,
                     Vector2 heartDimensions,
                     Renderable heartImage,
                     Counter heartsOnBoard,
                     CollisionStrategy decorator) {
        super(decorator);
        this.decorator = decorator;
        this.heartDimensions = heartDimensions;
        this.heartImage = heartImage;
        this.gameObjects = gameObjects;
        this.heartsOnBoard = heartsOnBoard;
    }

    /**
     * Generates a heart object at the specified position.
     * @param center Center position of the heart
     * @param sizes Dimensions of the heart
     * @param heartsImage Image for the heart object
     * @param heartsOnBoard Counter to track the number of hearts on the board
     */
    private void generateHeart(Vector2 center, Vector2 sizes,
                               Renderable heartsImage,
                               Counter heartsOnBoard) {

        // Generate Heart - Set Direction and Tag
        Heart heart = new Heart(center, sizes, heartsImage, gameObjects, heartsOnBoard);
        heart.setVelocity(new Vector2(0, Constants.HEART_SPEED));
        heart.setTag(Constants.FALL_HEART_TAG);

        // Add the Heart to the Board
        gameObjects.addGameObject(heart);
    }

    /**
     * Method invoked upon collision with another game object.
     * It adds functionality to generate an extra life (heart)
     * and removes the current object upon collision with a ball.
     * @param thisObj The game object with this collision strategy
     * @param otherObj The other game object involved in the collision
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        decorator.onCollision(thisObj, otherObj);

        // Verify if the other object is from 'Ball' kind
        if (Objects.equals(otherObj.getTag(), Constants.BALL_TAG) ||
            (Objects.equals(otherObj.getTag(), Constants.PUCK_TAG))){

            // than generate hearts
            generateHeart(thisObj.getCenter(), heartDimensions, heartImage, heartsOnBoard);
            gameObjects.removeGameObject(thisObj);
        }
    }
}
