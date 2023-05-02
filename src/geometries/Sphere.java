package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Sphere class is part of the geometries package and represents
 * a three-dimensional sphere defined by a center point and a radius.
 * It extends the abstract RadialGeometry class, which represents any geometry with a radius.
 * The Sphere class includes a constructor that takes a Point object for the center
 * of the sphere and a double value for the radius. It also includes a getNormal
 * method, which takes a Point object and returns the normal vector to the sphere at that point
 * (currently returning null, as the method is not yet implemented), and an overridden
 * toString method that returns a String representation of the sphere, including its center and radius.
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor
     *
     * @param center the center of a Sphere
     * @param radius the radius
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;

    }

    /**
     * getter
     *
     * @return the normal
     */
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    /**
     * Returns a string representation of the Sphere object.
     *
     * @return A string representation of the Sphere object.
     */
    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }

}
