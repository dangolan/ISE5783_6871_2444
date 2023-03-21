package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    protected Point center;
    /**
     * constructor
     * @param center the center of a Sphere
     * @param radius the radius
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }
    /**
     * getter
     * @return the normal
     */
    public Vector getNormal(Point p) {

        return null;
    }
    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }

}
