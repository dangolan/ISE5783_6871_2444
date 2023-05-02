package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        Point O;
        if (t == 0)
            O = axisRay.getP0();
        else
            O = axisRay.getP0().add(axisRay.getDir().scale(t));
        return p.subtract(O).normalize();
    }

    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }
}
