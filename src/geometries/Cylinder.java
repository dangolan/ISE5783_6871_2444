package geometries;

import primitives.*;

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
     * @param p point on cylinder
     * @return The normal of the cylinder in this point
     */
    public Vector getNormal(Point p) {

        Point o = axisRay.getP0();
        Vector v = axisRay.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = Util.alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (Util.isZero(t) || Util.isZero(height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return p.subtract(o).normalize();
    }
}
