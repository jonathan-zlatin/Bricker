package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Abstract class representing a collision strategy decorator.
 *
 * <p>CollisionStrategyDecorator implements the CollisionStrategy interface and provides a base
 * for implementing specific collision strategy decorators in the Bricker game. This class enables
 * the use of the decorator pattern, allowing for dynamic addition of behavior to collision strategies.
 */
public abstract class CollisionStrategyDecorator implements CollisionStrategy{
    /** The wrapped collision strategy instance to be decorated. */
    private final CollisionStrategy collisionDecoratorInterface;
    /**
     * Constructs a CollisionStrategyDecorator with
     * the given collision strategy interface to decorate.
     *
     * @param collisionDecoratorInterface The collision strategy interface to decorate.
     */
    public CollisionStrategyDecorator(CollisionStrategy collisionDecoratorInterface) {
        this.collisionDecoratorInterface = collisionDecoratorInterface;
    }

    /**
     * Passes the collision event to the wrapped collision strategy instance for processing.
     *
     * @param thisObj The GameObject associated with this collision strategy.
     * @param otherObj The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        collisionDecoratorInterface.onCollision(thisObj,otherObj);
    }
}
