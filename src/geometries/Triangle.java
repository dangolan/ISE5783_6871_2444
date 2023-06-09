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
     * @param p1 one point
     * @param p2 second point
     * @param p3 three point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray,maxDistance);
        if (planeIntersections == null)
            return null;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = this.vertices.get(0).subtract(p0);
        Vector v2 = this.vertices.get(1).subtract(p0);
        double s1 = alignZero(v.dotProduct(v1.crossProduct(v2)));
        if (s1 == 0) return null;

        Vector v3 = this.vertices.get(2).subtract(p0);
        double s2 = alignZero(v.dotProduct(v2.crossProduct(v3)));
        if (s1 * s2 <= 0) return null;

        double s3 = alignZero(v.dotProduct(v3.crossProduct(v1)));
        if (s1 * s3 <= 0) return null;

        planeIntersections.get(0).geometry = this;
        return planeIntersections;
    }
}
