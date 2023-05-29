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

    public List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return findGeoIntersectionsHelper(ray);
    }
    /**
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * GeoPoint constructor
         * @param geometry paramater
         * @param point parmater
         */
        public  GeoPoint(Geometry geometry, Point point)
        {
            this.geometry = geometry;
            this.point = point;
        }

        //return if two GeoPoints are equals
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
