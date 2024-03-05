package bricker.gameobjects;

import bricker.utills.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
/**
 * Represents a text GameObject in the game.
 */
public class Text extends GameObject {
    private final Counter heartsLeft;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private final GameObjectCollection gameObjects;
    private final TextRenderable textRenderable;
    /**
     * Constructs a new Text object.
     *
     * @param topLeftCorner       the top left corner position of the text
     * @param dimensions          the dimensions of the text
     * @param textRenderable      the text renderable object for rendering the text
     * @param gameObjects         the collection of GameObjects in the game
     * @param heartsLeft          the counter for remaining hearts/lives
     */
    public Text(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable textRenderable,
                GameObjectCollection gameObjects, Counter heartsLeft) {
        super(topLeftCorner, dimensions, textRenderable);
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.textRenderable = textRenderable;
        this.gameObjects = gameObjects;
        this.heartsLeft = heartsLeft;
        generateText();
    }
    /**
     * Generates the text based on the number of remaining hearts/lives.
     */
    public void generateText() {
        textRenderable.setString(String.valueOf(heartsLeft.value()));
        GameObject text = new GameObject(topLeftCorner, dimensions, textRenderable);
        if (heartsLeft.value() == Constants.FOUR_LIFE ||
            heartsLeft.value() == Constants.THR_LIFE) {
            textRenderable.setColor(Color.green);
        } else if (heartsLeft.value() == Constants.TWO_LIFE) {
            textRenderable.setColor(Color.yellow);
        } else if (heartsLeft.value() == Constants.ONE_LIFE) {
            textRenderable.setColor(Color.red);
        }
        gameObjects.addGameObject(text, Layer.UI);
    }


}
