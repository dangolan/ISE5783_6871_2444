package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Intersectable interface represents a geometric entity that can be intersected by a ray.
 * Implementing classes provide methods to find intersections between the geometry and a given ray.
 */
public interface Intersectable {
    /**
     * Finds a list of intersections between the geometry and a given ray.
     * This method calculates the intersection points between the implementing geometry object and the specified Ray. The method
     * returns a list of Point objects representing the intersection points if they exist. If there are no intersections or the
     * Ray does not intersect the geometry, the method returns an empty list.
     *
     * @param ray The Ray object to find the intersections with.
     * @return A list of Point objects representing the intersection points, or an empty list if no intersections exist.
     */
    abstract public List<Point> findIntersections(Ray ray);
}
