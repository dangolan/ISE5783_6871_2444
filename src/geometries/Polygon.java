package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 */
public class Polygon implements Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();

        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     * Computes the normal vector at a given point on the surface of the ConvexPolygon.
     * This method calculates the normal vector at the specified point on the surface of the ConvexPolygon. The normal vector
     * represents the direction perpendicular to the surface at that point. Since the ConvexPolygon is planar, the normal
     * vector is the same across all points on the surface.
     * @param point The point at which the normal vector is to be computed.
     * @return The normal vector of the ConvexPolygon, which is the same for all points on the surface.
     */
    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    /**
     * Finds the intersection point between this ConvexPolygon and a given Ray.
     * This method calculates the intersection point between the current ConvexPolygon object and the specified Ray. The method
     * returns a list containing the intersection point if it exists. If there are no intersections or the Ray does not intersect
     * the ConvexPolygon, the method returns null.
     * @param ray The Ray object to find the intersection with.
     * @return A list containing the intersection point, or null if no intersection exists.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = this.plane.findIntersections(ray);

        // if there is no Intersections at all in the plane
        if (intersections == null)
            return null;

        int numOfVertices = vertices.size();
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();

        Vector v1 = vertices.get(numOfVertices - 1).subtract(p0);
        Vector v2 = vertices.get(0).subtract(p0);

        Vector n = v1.crossProduct(v2).normalize();
        double vn = dir.dotProduct(n);
        boolean positive = vn > 0;

        if (isZero(vn))
            return null;

        for (int i = 1; i < numOfVertices; ++i) {
            v1 = v2;
            v2 = vertices.get(i).subtract(p0);
            n = v1.crossProduct(v2).normalize();
            vn = dir.dotProduct(n);

            //no intersection
            if (isZero(vn))
                return null;

            //not the same sign
            if (vn > 0 != positive)
                return null;
        }
        return List.of(intersections.get(0));
    }
}
