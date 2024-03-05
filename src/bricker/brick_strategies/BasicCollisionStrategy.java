package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

/**
 * Basic collision strategy for removing game objects on collision.
 *
 * <p>The BasicCollisionStrategy class implements the CollisionStrategy interface
 * and provides a simple collision strategy where the associated game object
 * is removed from the game upon collision with another object.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a BasicCollisionStrategy.
     *
     * @param gameObjects The collection of game objects to manage.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the collision event by removing the associated game object.
     *
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        gameObjects.removeGameObject(thisObj);
    }
}
