package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
     * Computes the normal vector at a given point on the surface of the Sphere.
     * This method calculates the normal vector at the specified point on the surface of the Sphere. The normal vector
     * represents the direction perpendicular to the surface at that point.
     *
     * @param p The point at which the normal vector is to be computed.
     * @return The normal vector at the specified point on the surface of the Sphere.
     */
    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    /**
     * Finds the intersection points between this Sphere and a given Ray.
     * This method calculates the intersection points between the current Sphere object and the specified Ray. The method
     * returns a list of Point objects representing the intersection points if they exist. If there are no intersections
     * between the Ray and the Sphere, the method returns null.
     *
     * @param ray The Ray object to find the intersections with.
     * @return A list of Point objects representing the intersection points, or null if no intersections exist.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        if (p0.equals(center))
            return List.of(ray.getPoint(radius));

        Vector u = center.subtract(p0);
        double tm = ray.getDir().dotProduct(u);
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = alignZero(radiusSquared - dSquared);
        if (thSquared <= 0) return null; // outside ot tangent to the sphere
        double th = Math.sqrt(thSquared); // always positive

        double t1 = alignZero(tm + th); // always t1 > t2
        if (t1 <= 0) return null;

        double t2 = alignZero(tm - th);
        return t2 <= 0 ? List.of(ray.getPoint(t1)) : List.of(ray.getPoint(t1), ray.getPoint(t2));
    }

    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }

}
