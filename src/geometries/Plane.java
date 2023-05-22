package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a plane in a three-dimensional space. It implements the Geometry interface
 * and provides a constructor to create a plane object with a specified point and normal vector.
 */
public class Plane implements Geometry {
    final private Point p0;
    final private Vector normal;

    /**
     * constructor
     *
     * @param p0     the point
     * @param normal the vector that normal to the plane
     */
    public Plane(Point p0, Vector normal) {
        super();
        this.p0 = p0;
        this.normal = normal;
    }

    /**
     * constructor that get 3 points and set one of them to witch the plane lays
     * and calculate the normal vector
     *
     * @param p0 point 0
     * @param p1 point 1
     * @param p2 point 2
     * @throws IllegalArgumentException when normal is 0
     */
    public Plane(Point p0, Point p1, Point p2) {
        super();
        this.p0 = p0;// Associated point in which the plane lays
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        this.normal = (p1.subtract(p0).crossProduct(p2.subtract(p0))).normalize();
    }

    /**
     * getter
     *
     * @return the point 0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter
     *
     * @return the normal vector
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * getter
     *
     * @param p the point
     * @return the normal vector
     */
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * Finds the intersection point between this Plane and a given Ray.
     * This method calculates the intersection point between the current Plane object and the specified Ray. The method
     * returns a list containing the intersection point if it exists. If there is no intersection or the Ray is parallel to
     * the Plane, the method returns null.
     *
     * @param ray The Ray object to find the intersection with.
     * @return A list containing the intersection point, or null if no intersection exists.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point point0 = ray.getP0();
        Vector v = ray.getDir();
        if (p0.equals(point0))
            return null;

        Vector p0_q = p0.subtract(point0);
        double numerator = normal.dotProduct(p0_q);
        if (isZero(numerator)) return null;

        double denominator = normal.dotProduct(v);
        if (isZero(denominator)) return null;

        double t = alignZero(numerator / denominator);
        return t <= 0 ? null : List.of(ray.getPoint(t));
    }

    @Override
    public String toString() {
        return "Plane [p0=" + p0 + ", normal=" + normal + "]";
    }
}
