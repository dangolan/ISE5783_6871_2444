package geometries;

import hierarchy.AABB;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Sphere class is part of the geometries package and represents
 * a three-dimensional sphere defined by a center point and a radius.
 * It extends the abstract RadialGeometry class, which represents any geometry with a radius.
 * The Sphere class includes a constructor that takes a Point object for the center
 * of the sphere and a double value for the radius. It also includes a getNormal
 * method, which takes a Point object and returns the normal vector to the sphere at that point
 * (currently returning null, as the method is not yet implemented), and an overridden
 * toString method that returns a String representation of the sphere, including its center and radius.
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor
     *
     * @param center the center of a Sphere
     * @param radius the radius
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;

    }
    @Override
    public AABB calculateAABB() {
        double radius = getRadius();
        double centerX = center.getX();
        double centerY = center.getY();
        double centerZ = center.getZ();

        double minX = centerX - radius;
        double minY = centerY - radius;
        double minZ = centerZ - radius;
        double maxX = centerX + radius;
        double maxY = centerY + radius;
        double maxZ = centerZ + radius;

        return new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }


    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getP0();
        if (p0.equals(center))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));

        Vector v = ray.getDir();
        Vector u = center.subtract(p0);
        double tm = v.dotProduct(u);
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = radiusSquared - dSquared;
        if (alignZero(thSquared) <= 0)
            return null;

        double th = Math.sqrt(thSquared);
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        if (alignZero(t1 - maxDistance) > 0) return null;

        if (alignZero(t2 - maxDistance) > 0)
            return t1 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t1)));
        return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2)))
                : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }

    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }
}
