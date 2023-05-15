package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

/**
 * Cylinder class heir from the Tube class
 */
public class Cylinder extends Tube {
    final private double height;

    /**
     * constructor
     * @param height  the height
     * @param axisRay the Ray
     * @param radius  the radius
     */
    public Cylinder(double height, Ray axisRay, double radius) {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    /**
     * The normal of the cylinder
     * @param p point on cylinder
     * @return The normal of the cylinder in this point
     */
    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        if (p.equals(p0)) {
            return v.scale(-1);
        }

        double t = v.dotProduct(p.subtract(p0));

        if (isZero(t)) {
            return v.scale(-1);
        }
        if (isZero(t - height)) {
            return v;
        }
        Point o = p0.add(v.scale(t));
        return p.subtract(o).normalize();
    }
}
