package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * A class for representing a triangle that inherits from Polygon
 */
public class Triangle extends Polygon {
    /**
     * constructor that get 3 points and use the polygon constructor
     *
     * @param vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    /**
     *
     * @param ray
     * @return intersection if they exist
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        List<Point> intersection_point = plane.findIntersections(ray);
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double t1 = v.dotProduct(n1);
        double t2 = v.dotProduct(n2);
        double t3 = v.dotProduct(n3);


        if ((t1>0 && t2>0 && t3>0) || (t1<0 && t2<0 && t3<0))
            return intersection_point;

        return null;

    }
}
