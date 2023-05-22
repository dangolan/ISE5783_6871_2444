package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A class for representing a triangle that inherits from Polygon
 */
public class Triangle extends Polygon {
    /**
     * constructor that get 3 points and use the polygon constructor
     *
     * @param vertices
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectionPoint = plane.findIntersections(ray);
        if (intersectionPoint == null) return null;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double t1 = alignZero(v.dotProduct(n1));
        if (t1 == 0) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double t2 = alignZero(v.dotProduct(n2));
        if (t1 * t2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double t3 = alignZero(v.dotProduct(n3));
        if (t1 * t3 <= 0) return null;

        return intersectionPoint;
    }
}
