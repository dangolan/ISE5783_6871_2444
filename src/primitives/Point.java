package primitives;

/**
 * This class represents a point in a 3D Cartesian coordinate system.
 * It holds the point's x, y, and z coordinates as a Double3 object.
 */
public class Point {
    /**
     * The origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(0, 0, 0);
    /**
     * The internal representation of the point's coordinates.
     */
    protected final Double3 xyz;

    /**
     * secondary constructor for Point 3D
     *
     * @param x first coordinate of the point
     * @param y second coordinate of the point
     * @param z three coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);

    }

    /**
     * primary constructor for point
     *
     * @param xyz Double3 value gor x, y, z axis
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtracts another point from this point, returning a vector that represents the difference.
     *
     * @param p the point to subtract from this point
     * @return a vector representing the difference between this point and the given point
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Adds a vector to this point, returning a new point that represents the sum.
     *
     * @param vector the vector to add to this point
     * @return a new point representing the sum of this point and the given vector
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Computes the squared distance between this point and another point.
     *
     * @param other the other point to compute the distance to
     * @return the squared distance between this point and the other point
     */
    public double distanceSquared(Point other) {
        double dx = xyz.d1 - other.xyz.d1;
        double dy = xyz.d2 - other.xyz.d2;
        double dz = xyz.d3 - other.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the distance from this point to another point
     *
     * @param other another point
     * @return the distance value
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Checks if this point is equal to the given object.
     * Two points are considered equal if their x, y, and z coordinates are equal.
     *
     * @param o The object to compare to this point
     * @return true if this point is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Point p) && xyz.equals(p.xyz);
    }

    @Override
    public String toString() {
        return "Point " + xyz;
    }

    /**
     * Retrieves the x-coordinate of this point.
     * This method returns the x-coordinate of this point, which is the value stored in the d1 field of the xyz object.
     *
     * @return The x-coordinate of this point.
     */
    public double getX() {
        return this.xyz.d1;
    }

    /**
     * Retrieves the y-coordinate of this point.
     * This method returns the y-coordinate of this point, which is the value stored in the d2 field of the xyz object.
     *
     * @return The y-coordinate of this point.
     */
    public double getY() {
        return this.xyz.d2;
    }

    /**
     * Retrieves the z-coordinate of this point.
     * This method returns the z-coordinate of this point, which is the value stored in the d3 field of the xyz object.
     *
     * @return The z-coordinate of this point.
     */
    public double getZ() {
        return this.xyz.d3;
    }
}
