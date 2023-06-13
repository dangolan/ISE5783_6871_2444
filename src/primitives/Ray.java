package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a ray in 3D space, defined by a starting point and a normalized direction vector.
 */
public class Ray {
    private static final double DELTA = 0.1;

    /**
     * starting point of the ray
     */
    final Point p0;
    /**
     * ray normalized direction vector
     */
    final Vector dir;

    /**
     * constructor
     *
     * @param p0  the start point
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }
    /**
     * Constructor that moves the ray by DELTA
     * @param p0 point
     * @param direction direction (must be normalized)
     * @param normal normal
     */
    public Ray(Point p0, Vector direction, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : - DELTA);
        this.p0 = p0.add(delta);
        this.dir = direction;
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
     * @return the direction vector
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Ray other &&
                this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    /**
     * Calculates the point along the line at a given distance from the ray head.
     *
     * @param t the distance
     * @return the calculated point
     */
    public Point getPoint(double t) {
        return isZero(t) ? p0 : p0.add(dir.scale(t));
    }

    /**
     * Finds the closest point from a list of points.
     *
     * @param points the list of points to search from
     * @return the closest point, or null if the list is null or empty
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint to the head of the ray among the given list of intersections.
     *
     * @param intersections the list of GeoPoints to search from
     * @return the closest GeoPoint to the head of the ray, or null if the list is null or empty
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        GeoPoint closestpoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (GeoPoint geoPoint : intersections) {
            double ptDistance = geoPoint.point.distanceSquared(p0);
            if (ptDistance < minDistance) {
                minDistance = ptDistance;
                closestpoint = geoPoint;
            }
        }
        return closestpoint;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
