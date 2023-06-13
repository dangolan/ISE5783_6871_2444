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
public class Plane extends Geometry {
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
        this.p0 = p0;
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
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Point p1 = ray.getP0();
        if (p0.equals(p1))
            return null;

        Vector v = ray.getDir();
        Vector p0Q = p0.subtract(p1);
        double numerator = alignZero(normal.dotProduct(p0Q));
        if (isZero(numerator))
            return null;

        double denominator = alignZero(normal.dotProduct(v));
        if (isZero(denominator))
            return null;

        double t = alignZero(numerator / denominator);
        if (alignZero(t - maxDistance) > 0)
            return null;
        return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
    }

    @Override
    public String toString() {
        return "Plane [p0=" + p0 + ", normal=" + normal + "]";
    }
}
