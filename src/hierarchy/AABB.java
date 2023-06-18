package hierarchy;

import primitives.Point;
import primitives.Ray;

public class AABB {
    private Point minPoint;
    private Point maxPoint;

    public AABB(Point minPoint, Point maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }
    public double calculateAABBSize() {

        double length = maxPoint.getX() - minPoint.getX();
        double width = maxPoint.getY() - minPoint.getY();
        double height = maxPoint.getZ() - minPoint.getZ();

        return  width * height;
    }

    public boolean intersectsWith(Ray ray) {
        double tMin = Double.NEGATIVE_INFINITY;
        double tMax = Double.POSITIVE_INFINITY;

        double originX = ray.getP0().getX();
        double originY = ray.getP0().getY();
        double originZ = ray.getP0().getZ();

        double dirX = ray.getDir().getX();
        double dirY = ray.getDir().getY();
        double dirZ = ray.getDir().getZ();

        // Check intersection with X slabs
        double invDirX = 1.0 / dirX;
        double t1 = (minPoint.getX() - originX) * invDirX;
        double t2 = (maxPoint.getX() - originX) * invDirX;

        if (invDirX < 0) {
            double temp = t1;
            t1 = t2;
            t2 = temp;
        }

        tMin = Math.max(tMin, t1);
        tMax = Math.min(tMax, t2);

        if (tMin > tMax) {
            return false;
        }

        // Check intersection with Y slabs
        double invDirY = 1.0 / dirY;
        t1 = (minPoint.getY() - originY) * invDirY;
        t2 = (maxPoint.getY() - originY) * invDirY;

        if (invDirY < 0) {
            double temp = t1;
            t1 = t2;
            t2 = temp;
        }

        tMin = Math.max(tMin, t1);
        tMax = Math.min(tMax, t2);

        if (tMin > tMax) {
            return false;
        }

        // Check intersection with Z slabs
        double invDirZ = 1.0 / dirZ;
        t1 = (minPoint.getZ() - originZ) * invDirZ;
        t2 = (maxPoint.getZ() - originZ) * invDirZ;

        if (invDirZ < 0) {
            double temp = t1;
            t1 = t2;
            t2 = temp;
        }

        tMin = Math.max(tMin, t1);
        tMax = Math.min(tMax, t2);

        if (tMin > tMax) {
            return false;
        }

        return true;
    }



    public void expand(AABB other) {
        // Expand the AABB by including the bounds of another AABB
        Point otherMin = other.getMinPoint();
        Point otherMax = other.getMaxPoint();

        double newMinX = Math.min(minPoint.getX(), otherMin.getX());
        double newMinY = Math.min(minPoint.getY(), otherMin.getY());
        double newMinZ = Math.min(minPoint.getZ(), otherMin.getZ());

        double newMaxX = Math.max(maxPoint.getX(), otherMax.getX());
        double newMaxY = Math.max(maxPoint.getY(), otherMax.getY());
        double newMaxZ = Math.max(maxPoint.getZ(), otherMax.getZ());

        minPoint = new Point(newMinX, newMinY, newMinZ);
        maxPoint = new Point(newMaxX, newMaxY, newMaxZ);
    }

    public boolean contains(Point point) {
        // Check if the AABB contains the given point
        double pointX = point.getX();
        double pointY = point.getY();
        double pointZ = point.getZ();

        return pointX >= minPoint.getX() && pointX <= maxPoint.getX() &&
                pointY >= minPoint.getY() && pointY <= maxPoint.getY() &&
                pointZ >= minPoint.getZ() && pointZ <= maxPoint.getZ();
    }
    public boolean contains(AABB other) {
        Point otherMinPoint = other.getMinPoint();
        Point otherMaxPoint = other.getMaxPoint();

        return contains(otherMinPoint) && contains(otherMaxPoint);
    }

    public boolean isOverlapping(AABB other) {
        // Check if this AABB is overlapping with another AABB
        return maxPoint.getX() >= other.minPoint.getX() && minPoint.getX() <= other.maxPoint.getX() &&
                maxPoint.getY() >= other.minPoint.getY() && minPoint.getY() <= other.maxPoint.getY() &&
                maxPoint.getZ() >= other.minPoint.getZ() && minPoint.getZ() <= other.maxPoint.getZ();
    }
    public boolean isAABBClose(AABB aabb2, double threshold) {
        // Calculate the center points of AABB1
        Point center1 = new Point(
                (minPoint.getX() + maxPoint.getX()) / 2,
                (minPoint.getY() + maxPoint.getY()) / 2,
                (minPoint.getZ() + maxPoint.getZ()) / 2
        );

        // Calculate the center points of AABB2
        Point center2 = new Point(
                (aabb2.getMinPoint().getX() + aabb2.getMaxPoint().getX()) / 2,
                (aabb2.getMinPoint().getY() + aabb2.getMaxPoint().getY()) / 2,
                (aabb2.getMinPoint().getZ() + aabb2.getMaxPoint().getZ()) / 2
        );

        // Calculate the distance between the center points
        double distance = center1.distance(center2);

        // Compare the distance with the threshold
        return distance <= threshold;
    }



    public Point getMinPoint() {
        return minPoint;
    }

    public Point getMaxPoint() {
        return maxPoint;
    }

    // Other methods specific to AABB
    // ...
}

