package geometries;

import BVH.AABB;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
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
        size = vertices.length;
        if (size < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);

        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return;

        Vector n = plane.getNormal();

        Vector edge1 = vertices[size - 1].subtract(vertices[size - 2]);
        Vector edge2 = vertices[0].subtract(vertices[size - 1]);

        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < size; ++i) {
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }
    /**
     * Calculates the Axis-Aligned Bounding Box (AABB) for the BoundingBoxTree.
     * The AABB is defined by minimum and maximum points in 3D space.
     *
     * @return The AABB of the BoundingBoxTree.
     */
    @Override
    public AABB calculateAABB() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Point vertex : vertices) {
            double x = vertex.getX();
            double y = vertex.getY();
            double z = vertex.getZ();

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            minZ = Math.min(minZ, z);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            maxZ = Math.max(maxZ, z);
        }

        return new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }


    /**
     * Helper method to find the intersection points between the given ray and the geometry.
     *
     * @param ray the ray intersecting the geometry
     * @return a list of GeoPoint objects representing the intersection points,
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray, maxDistance);
        if (planeIntersections == null)
            return null;

        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        Point P1 = vertices.get(1);
        Point P2 = vertices.get(0);

        Vector v1 = P0.subtract(P1);
        Vector v2 = P0.subtract(P2);

        double sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
        if (isZero(sign)) {
            return null;
        }

        boolean positive = sign > 0;

        for (int i = vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = P0.subtract(vertices.get(i));

            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            if (isZero(sign))
                return null;

            if (positive != (sign > 0))
                return null;
        }

        planeIntersections.get(0).geometry = this;
        return planeIntersections;
    }
}
