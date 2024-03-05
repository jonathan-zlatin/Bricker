package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface representing a collision strategy.
 *
 * <p>The CollisionStrategy interface defines the contract for implementing collision strategies
 * in the Bricker game. Classes that implement this interface are responsible for handling collision
 * events between game objects.
 */
public interface CollisionStrategy {

    /**
     * Handles a collision event between two game objects.
     *
     * @param thisObj The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);

}
