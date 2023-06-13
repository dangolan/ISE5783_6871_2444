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
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }
    /**
     * Finds the intersections between a ray and geometric objects, up to a maximum distance.
     * This method searches for intersections up to a distance of positive infinity.
     *
     * @param ray the ray for which to find intersections
     * @return a list of GeoPoint objects representing the intersections between the ray and geometric objects
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    /**
     * Finds the intersections between a ray and geometric objects, up to a maximum distance.
     *
     * @param ray         the ray for which to find intersections
     * @param maxDistance the maximum distance up to which to search for intersections
     * @return a list of GeoPoint objects representing the intersections between the ray and geometric objects
     */

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    /**
     * Helper method to find the intersections between a ray and geometric objects, up to a maximum distance.
     *
     * @param ray         the ray for which to find intersections
     * @param maxDistance the maximum distance up to which to search for intersections
     * @return a list of GeoPoint objects representing the intersections between the ray and geometric objects
     */

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * Represents a point of intersection between a ray and a geometry in a scene.
     */
    public static class GeoPoint {
        /**
         * The geometry object associated with the point.
         */
        public Geometry geometry;

        /**
         * The point of intersection between the ray and the geometry.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint object.
         *
         * @param geometry the geometry that was intersected
         * @param point    the point of intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof GeoPoint other)
                return this.geometry == other.geometry && this.point.equals(other.point);
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
