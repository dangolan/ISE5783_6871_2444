package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class heir from the Tube class
 */
public class Cylinder extends Tube {
    final private double height;

    /**
     * constructor
     *
     * @param height  the height
     * @param axisRay the Ray
     * @param radius  the radius
     */
    public Cylinder(double height, Ray axisRay, double radius) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * The normal of the cylinder
     *
     * @param p point
     * @return The normal of the cylinder
     */
    public Vector getNormal(Point p) {

        return null;
    }
}
