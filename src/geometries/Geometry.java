package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometrical object in a three-dimensional space.
 * It provides a method to get the normal vector at a given point on the object.
 */
public interface Geometry extends Intersectable {

    /**
     * Returns the normal vector of the Geometry object at the specified point.
     *
     * @param point the point at which the normal vector is to be computed
     * @return the normal vector at the specified point
     */
    abstract public Vector getNormal(Point point);
}
