package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a ray in 3D space, defined by a starting point and a normalized direction vector.
 */
public class Ray {
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

    /**
     * Determines if the specified object is equal to this Ray object.
     *
     * @param o The object to compare with this Ray object.
     * @return true if the objects are equal, false otherwise.
     */
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

    public Point findClosestPoint(List<Point> points){
        if (points.isEmpty())
            return null;

        Point closestPoint = points.get(0);
        double distance = closestPoint.distanceSquared(this.p0);
        for (Point point : points) {
            double d = point.distanceSquared(this.p0);
            if(distance > d)
            {
                closestPoint = point;
                distance = d;
            }
        }
        return closestPoint;
    }
    
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
