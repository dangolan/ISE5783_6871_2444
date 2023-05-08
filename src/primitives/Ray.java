package primitives;

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
     * @param p0  the start point
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * getter
     * @return the point 0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter
     * @return the direction vector
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Determines if the specified object is equal to this Ray object.
     * @param o The object to compare with this Ray object.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }
    /**
     * Calculates the point along the line at a given parameter value.
     * This method computes the point on the line defined by this Line object at the specified parameter value. The parameter
     * value represents the distance along the line from the starting point (p0) in the direction of the line (dir).
     * @param t The parameter value indicating the distance along the line.
     * @return The point on the line at the given parameter value.
     */
    public Point getPoint(double t)
    {
        return p0.add(dir.scale(t));
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
