package geometries;

import BVH.AABB;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder class heir from the Tube class
 */
public class Cylinder extends Tube {
    final private double height;

    /**
     * constructor
     *
     * @param height  the height
     * @param axisRay the Ray
     * @param radius  the radius
     */
    public Cylinder(double height, Ray axisRay, double radius) {
        super(axisRay, radius);
        this.height = height;
    }
    /**
     * Calculates the Axis-Aligned Bounding Box (AABB) for the BoundingBoxTree.
     * The AABB is defined by minimum and maximum points in 3D space.
     *
     * @return The AABB of the BoundingBoxTree.
     */
    @Override
    public AABB calculateAABB() {
        double radius = getRadius();
        Point center = axisRay.getP0();

        double minX = center.getX() - radius;
        double minY = center.getY() - (height / 2.0);
        double minZ = center.getZ() - radius;
        double maxX = center.getX() + radius;
        double maxY = center.getY() + (height / 2.0);
        double maxZ = center.getZ() + radius;

        return new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }


    /**
     * getter for height of the Cylinder.
     *
     * @return The height of the Cylinder.
     */
    public double getHeight() {
        return height;
    }

    /**
     * The normal of the cylinder
     *
     * @param p point on cylinder
     * @return The normal of the cylinder in this point
     */
    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        if (p.equals(p0)) return v.scale(-1);

        double t = v.dotProduct(p.subtract(p0));
        if (isZero(t)) return v.scale(-1);
        if (isZero(t - height)) return v;

        return super.getNormal(p);
    }
    /**
     *
     * @param ray the ray
     * @param maxDistance the distance
     * @return List Of GeoPoint
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // The procedure is as follows:
        // P1 and P2 in the cylinder, the center of the bottom and upper bases
        Point p1 = axisRay.getP0();
        Point p2 = axisRay.getPoint(height);
        Vector Va = axisRay.getDir();


        List<GeoPoint> list = super.findGeoIntersectionsHelper(ray,maxDistance);

        // the intersections with the cylinder
        List<GeoPoint> result = new LinkedList<>();

        // Step 1 - checking if the intersections with the tube are points on the cylinder
        if (list != null) {
            for (GeoPoint p : list) {
                if (Va.dotProduct(p.point.subtract(p1)) > 0 && Va.dotProduct(p.point.subtract(p2)) < 0)
                    result.add(0, p);
            }
        }

        // Step 2 - checking the intersections with the bases

        // cannot be more than 2 intersections
        if(result.size() < 2) {
            //creating 2 planes for the 2 bases
            Plane bottomBase = new Plane(p1, Va);
            Plane upperBase = new Plane(p2, Va);
            GeoPoint p;

            // ======================================================
            // intersection with the bases:

            // intersections with the bottom bases
            list = bottomBase.findGeoIntersections(ray);

            if (list != null) {
                p = list.get(0);
                // checking if the intersection is on the cylinder base
                if (p.point.distanceSquared(p1) < radius * radius)
                    result.add(p);
            }

            // intersections with the upper bases
            list = upperBase.findGeoIntersections(ray);

            if (list != null) {
                p = list.get(0);
                //checking if the intersection is on the cylinder base
                if (p.point.distanceSquared(p2) < radius * radius)
                    result.add(p);
            }
        }
        // return null if there are no intersections.
        return result.size() == 0 ? null : result;
    }

}
