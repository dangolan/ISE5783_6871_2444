package primitives;

public class Vector extends Point {
    /**
     * constructor to create a vector
     *
     * @param x
     * @param y
     * @param z
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
     * @param scalar
     * @return new vector Multiplication by scalar
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * dot product between two vectors (scalar product)
     *
     * @param other
     * @return new vector
     */
    public double dotProduct(Vector other) {
        return other.xyz.d1 * xyz.d1 +
                other.xyz.d2 * xyz.d2 +
                other.xyz.d3 * xyz.d3;
    }

    /**
     * cross product between two vectors
     *
     * @param other
     * @return the vector result from the cross product
     */

    public Vector crossProduct(Vector other) {

        double x = xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2;
        double y = xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3;
        double z = xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1;
        return new Vector(x,y,z);

    }

    /**
     * @return the length Squared of vector
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * @return the sqrt of length Squared (the length of vector)
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * @return Normalized vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce((length())));
    }

    @Override
    public String toString() {
        return "Vector " + xyz;
    }

}
