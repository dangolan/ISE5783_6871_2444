package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * The Intersectable interface represents a geometric entity that can be intersected by a ray.
 * Implementing classes provide methods to find intersections between the geometry and a given ray.
 */
public abstract class Intersectable {

    /**
     * @param ray ray intersecting the geometry
     * @return list of intersection points
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /** Intersectable interface for geometry bodies to calculate intersections points between ray and body
     * @param ray parameter of class ray to check cuttings
     * @return List<Point>
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersection points between the given ray and the geometries in the scene.
     * @param ray the ray to intersect with the geometries
     * @return a list of intersection points between the ray and the geometries,
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }
    /**
     * Represents a point of intersection between a ray and a geometry in a scene.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructs a new GeoPoint object.
         * @param geometry the geometry that was intersected
         * @param point the point of intersection
         */
        public  GeoPoint(Geometry geometry, Point point)
        {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof GeoPoint other)
                return this.geometry.equals(other.geometry) && this.point.equals(other.point);
            return false;
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
