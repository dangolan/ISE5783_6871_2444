package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class A class for representing an infinite cylinder that inherits Radial Geometric
 */
public class Tube extends RadialGeometry {

    protected final Ray axisRay;

    /**
     * constructor
     *
     * @param axisRay the Ray
     * @param radius  the radius
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * The normal of the Tube
     * @param p point
     * @return The normal of the cylinder
     */
    public Vector getNormal(Point p) {
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        return p.subtract(axisRay.getPoint(t)).normalize();
    }

    /**
     * @param ray ray intersecting the geometry
     * @return
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Point pa = this.axisRay.getP0();
        Vector va = this.axisRay.getDir();
        double a, b, c;
        Vector vecA = v;
        try {
            double vva = v.dotProduct(va);
            if (!isZero(vva)) vecA = v.subtract(va.scale(vva));
            a = vecA.lengthSquared();
        } catch (IllegalArgumentException e) {
            return null;
        }
        try {
            Vector deltaP = p0.subtract(pa);
            Vector deltaPMinusDeltaPVaVa = deltaP;
            double deltaPVa = deltaP.dotProduct(va);
            if (!isZero(deltaPVa)) deltaPMinusDeltaPVaVa = deltaP.subtract(va.scale(deltaPVa));
            b = 2 * (vecA.dotProduct(deltaPMinusDeltaPVaVa));
            c = deltaPMinusDeltaPVaVa.lengthSquared() - this.radius;
        } catch (IllegalArgumentException e) {
            b = 0;
            c = -1 * this.radius;
        }

        double discriminator = alignZero(b * b - 4 * a * c);
        if (discriminator <= 0) return null;

        double sqrtDiscriminator = Math.sqrt(discriminator);
        double t1 = alignZero(-b + sqrtDiscriminator) / (2 * a);
        double t2 = alignZero(-b - sqrtDiscriminator) / (2 * a);

        if (t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
        if (t1 > 0) return List.of(new GeoPoint(this, ray.getPoint(t1)));
        if (t2 > 0) return List.of(new GeoPoint(this, ray.getPoint(t2)));
        return null; //if there are no positive solutions
    }

    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }
}
