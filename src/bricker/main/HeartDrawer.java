//package bricker.main;
//
//import bricker.gameobjects.Heart;
//import bricker.utills.Constants;
//import danogl.collisions.GameObjectCollection;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Counter;
//import danogl.util.Vector2;
//
///**
// * Manages the drawing of hearts representing player lives.
// *
// * <p>The HeartDrawer class handles the display of heart objects representing
// * the remaining lives of the player in the game. It updates the display based
// * on the current number of lives left.
// */
//public class HeartDrawer {
//    private final Vector2 initialPosition;
//    private final Counter heartsLeft;
//    private final Renderable heartImage;
//    private final GameObjectCollection gameObjects;
//    private final Vector2 dimensions;
//    private final Heart[] sideHearts;
//    private int heartOnBoard;
//
//    /**
//     * Constructs a HeartDrawer.
//     *
//     * @param initialPosition The initial position of the hearts.
//     * @param dimensions The dimensions of the hearts.
//     * @param heartsLeft The counter for the remaining hearts.
//     * @param heartImage The renderable image for the heart object.
//     * @param gameObjects The collection of game objects.
//     */
//    public HeartDrawer(Vector2 initialPosition, Vector2 dimensions,
//                       Counter heartsLeft, Renderable heartImage,
//                       GameObjectCollection gameObjects) {
//        this.initialPosition = initialPosition;
//        this.dimensions = dimensions;
//        this.heartsLeft = heartsLeft;
//        this.heartImage = heartImage;
//        this.gameObjects = gameObjects;
//        this.sideHearts = new Heart[Constants.MAX_HEARTS];
//    }
//
//    /**
//     * Draws the hearts representing player lives.
//     *
//     * <p>This method updates the display of hearts based on the current
//     * number of lives left. It removes existing heart objects from the
//     * game and creates new ones to reflect the updated number of lives.
//     */
//    public void Draw() {
//        if (heartOnBoard == heartsLeft.value()) {
//            return;
//        }
//
//        for (int i = 0; i < Constants.MAX_HEARTS; i++) {
//            gameObjects.removeGameObject(sideHearts[i]);
//        }
//
//        drawHearts();
//    }
//
//    /**
//     * Iterates over the number of lives left and creates new heart
//     * objects at appropriate positions on the game board.
//     */
//    private void drawHearts() {
//        float jump = dimensions.y() * Constants.SPACES;
//        float dx = initialPosition.x();
//        float dy = initialPosition.y();
//        for (int i = 0; i < heartsLeft.value(); i++) {
//            Heart heart = new Heart(new Vector2(dx, dy - i * jump),
//                    dimensions, heartImage, gameObjects, heartsLeft);
//            sideHearts[i] = heart;
//            gameObjects.addGameObject(heart);
//        }
//        heartOnBoard = heartsLeft.value();
//    }
//}

package bricker.main;

import bricker.gameobjects.Heart;
import bricker.gameobjects.Text;
import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Manages the drawing of hearts representing player lives.
 *
 * <p>The HeartDrawer class handles the display of heart objects representing
 * the remaining lives of the player in the game. It updates the display based
 * on the current number of lives left.
 */
public class HeartDrawer {
    private final Vector2 initialPosition;
    private final Counter heartsLeft;
    private int heartOnBoard;
    private final Renderable heartImage;
    private final GameObjectCollection gameObjects;
    private final Vector2 dimensions;
    private final Heart[] sideHearts;
    private final Text text;

    /**
     * Constructs a HeartDrawer.
     *
     * @param initialPosition The initial position of the hearts.
     * @param dimensions The dimensions of the hearts.
     * @param heartsLeft The counter for the remaining hearts.
     * @param heartImage The renderable image for the heart object.
     * @param gameObjects The collection of game objects.
     */
    public HeartDrawer(Vector2 initialPosition, Vector2 dimensions,
                       Counter heartsLeft, Renderable heartImage,
                       GameObjectCollection gameObjects,
                       Text text) {
        this.initialPosition = initialPosition;
        this.dimensions = dimensions;
        this.heartsLeft = heartsLeft;
        this.heartImage = heartImage;
        this.gameObjects = gameObjects;
        this.sideHearts = new Heart[Constants.MAX_HEARTS];
        this.text = text;
    }

    /**
     * Draws the hearts representing player lives.
     *
     * <p>This method updates the display of hearts based on the current
     * number of lives left. It removes existing heart objects from the
     * game and creates new ones to reflect the updated number of lives.
     * It also updates the text object to display the remaining lives.
     */
    public void Draw() {
        // Check if the number of hearts on the board matches the current number of hearts
        if (heartOnBoard == heartsLeft.value()) {
            return; // No need to redraw if they are the same
        }

        for (int i = 0; i < Constants.MAX_HEARTS; i++) {
            gameObjects.removeGameObject(sideHearts[i]);
        }

        drawHearts();
        text.generateText();
    }

    /**
     * Iterates over the number of lives left and creates new heart
     * objects at appropriate positions on the game board.
     */
    private void drawHearts() {
        // Calculate vertical jump between hearts
        float jump = dimensions.y() * Constants.SPACES;

        // Initialize starting position
        float dx = initialPosition.x();
        float dy = initialPosition.y();

        // Loop through each heart to draw
        for (int i = 0; i < heartsLeft.value(); i++) {
            // Create a new Heart object
            Heart heart = new Heart(new Vector2(dx, dy - i * jump),
                    dimensions, heartImage, gameObjects, heartsLeft);
            sideHearts[i] = heart;
            gameObjects.addGameObject(heart);
        }
        heartOnBoard = heartsLeft.value();
    }
    /**
     * return number of hearts on board
     */
    public int getHeartOnBoard(){
        return this.heartOnBoard;
    }
}