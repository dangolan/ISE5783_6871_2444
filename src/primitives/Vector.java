package primitives;

import static primitives.Util.isZero;

/**
 * The Vector class represents a three-dimensional vector in Euclidean space.
 * It extends the Point class and inherits its x, y, and z coordinates.
 * A vector has direction and magnitude, but no position in space.
 * This class provides constructors to create vectors from three coordinates,
 * as well as methods for vector addition, subtraction, dot product, cross product,
 * scalar multiplication, normalization
 */
public class Vector extends Point {

    /**
     * Constructs a vector with the specified x, y, and z coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * constructor to create a vector
     *
     * @param xyz the point
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector(0,0,0) is not allowed");
        }
    }

    /**
     * add this vector to another one
     *
     * @param other the second vector
     * @return new vector from this vector to the other vector
     */
    public Vector add(Vector other) {
        return new Vector(xyz.add(other.xyz));
    }

    /**
     * Multiplication of a vector by a scalar
     *
     * @param scalar multiplied by this scalar
     * @return new vector Multiplication by scalar
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * dot product between two vectors (scalar product)
     *
     * @param other the second vector
     * @return scalar of the dot product
     */
    public double dotProduct(Vector other) {
        return other.xyz.d1 * xyz.d1 +
                other.xyz.d2 * xyz.d2 +
                other.xyz.d3 * xyz.d3;
    }

    /**
     * cross product between two vectors
     *
     * @param other the second vector
     * @return the vector result from the cross product
     */
    public Vector crossProduct(Vector other) {

        double x = xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2;
        double y = xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3;
        double z = xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1;
        return new Vector(x, y, z);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }

    /**
     * Returns the length squared of the vector.
     *
     * @return the length squared of the vector
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Returns the length of the vector.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new vector that is the normalized version of this vector (i.e., with the same direction, but a length of 1).
     *
     * @return a new vector that is the normalized version of this vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce((length())));
    }

    /**
     Creates a normal vector based on the current vector.

     @return A new Vector object representing the normal vector.
     */
    public Vector createNormal() {
        if (isZero(this.getX()))
            return new Vector(1, 0, 0);

        return new Vector(this.getY(), -this.getX(), 0).normalize();
    }

    @Override
    public String toString() {
        return "Vector " + xyz;
    }

}
