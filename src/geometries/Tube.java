package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class A class for representing an infinite cylinder that inherits Radial Geometric
 */
public class Tube extends RadialGeometry {

    protected final Ray axisRay;

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
        return p.subtract(axisRay.getPoint(t)).normalize();
    }

    /**
     * Finds the intersection points between this Cylinder and a given Ray.
     * This method calculates the intersection points between the current Cylinder object and the specified Ray. The method
     * returns a list of Point objects representing the intersection points, if any exist. If there are no intersections
     * or the Ray is parallel to the axisRay of the Cylinder, the method returns null.
     *
     * @param ray The Ray object to find the intersections with.
     * @return A list of Point objects representing the intersection points, or null if no intersections exist.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Point pa = this.axisRay.getP0();
        Vector va = this.axisRay.getDir();
        double a, b, c; //coefficients for quadratic equation ax^2 + bx + c
        Vector vecA = v;

        try {
            double vva = v.dotProduct(va); //(v,va)

            if (!isZero(vva)) vecA = v.subtract(va.scale(vva)); //v-(v,va)va
            a = vecA.lengthSquared(); //(v-(v,va)va)^2 (always positive)
        } catch (IllegalArgumentException e) {
            return null; //if a=0 there are no intersections because Ray is parallel to axisRay
        }
        try {
            Vector deltaP = p0.subtract(pa); //p0-pa
            Vector deltaPMinusDeltaPVaVa = deltaP;
            double deltaPVa = deltaP.dotProduct(va); //(delP,va)va)

            if (!isZero(deltaPVa)) deltaPMinusDeltaPVaVa = deltaP.subtract(va.scale(deltaPVa)); //(delP-(delP,va)va)
            b = 2 * (vecA.dotProduct(deltaPMinusDeltaPVaVa)); //2(v-(v,va)va,delP-(delP,va)va)
            c = deltaPMinusDeltaPVaVa.lengthSquared() - this.radius; //(delP-(delP,va)va)^2 - r^2
        } catch (IllegalArgumentException e) {
            b = 0;
            c = -1 * this.radius;
        }

        double discriminator = alignZero(b * b - 4 * a * c); //discriminator: b^2 - 4ac

        if (discriminator <= 0) return null; //there are no intersections because Ray is parallel to axisRay

        double sqrtDiscriminator = Math.sqrt(discriminator); // always positive
        // "a" and square root of discriminator are always positive, than t1 > t2 (always)
        double t1 = alignZero(-b + sqrtDiscriminator) / (2 * a); // it is greater than t2
        if (t1 <= 0) return null;

        double t2 = alignZero(-b - sqrtDiscriminator) / (2 * a);
        return t2 <= 0 ? List.of(ray.getPoint(t1)) : List.of(ray.getPoint(t1), ray.getPoint(t2));
    }

    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }
}
