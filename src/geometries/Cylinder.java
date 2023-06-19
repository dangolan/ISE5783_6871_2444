package geometries;

import BVH.AABB;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

}
