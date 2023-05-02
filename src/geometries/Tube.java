package geometries;
import primitives.*;

/**
 * class A class for representing an infinite cylinder that inherits Radial Geometric
 */
public class Tube extends RadialGeometry {

    protected Ray axisRay;
    /**
     * constructor
     *
     * @param axisRay the Ray
     * @param radius  the radius
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }
    /**
     * The normal of the Tube
     *
     * @param p point
     * @return The normal of the cylinder
     */
    public Vector getNormal(Point p) {
        return null;
    }
    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }
}
