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
     * @param p0 the start point
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
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
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
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }
}
