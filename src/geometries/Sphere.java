package geometries;

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
     * @param center the center of a Sphere
     * @param radius the radius
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;

    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        if(p0.equals(center))
            return List.of(new GeoPoint(this, center.add(v.scale(radius))));
        Vector u = center.subtract(p0);
        double tm = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if(d>=radius)
            return null;

        double th = alignZero(Math.sqrt(radius*radius -d*d));
        if (th<=0)
            return null;

        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);
        if (t1 > 0 && t2 > 0)
        {
            GeoPoint p1 = new GeoPoint(this, p0.add(v.scale(t1)));
            GeoPoint p2 = new GeoPoint(this,p0.add(v.scale(t2)));
            return List.of(p1,p2);
        }
        if (t1 > 0)
        {
            GeoPoint p1 = new GeoPoint(this, ray.getPoint(t1));
            return List.of(p1);
        }
        if (t2 > 0)
        {
            GeoPoint p2 = new GeoPoint(this, ray.getPoint(t2));
            return List.of(p2);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }
}
