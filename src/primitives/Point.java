package primitives;

public class Point {

    protected final Double3 xyz;

    /**
     * secondary constructor for Point 3D
     *
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);

    }

    /**
     * primary constructor for point
     *
     * @param _xyz Double3 value gor x, y, z axis
     */
    public Point(Double3 _xyz) {
        xyz = _xyz;
    }

    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * @param other
     * @return d = ((x2 = x1) * (x2 = x1) + (y2 - y1) * (y2 - y1)  + (z2 = z1 ) * (z2 = z1 ))
     */
    public double distanceSquared(Point other) {

            double dx = xyz.d1 - other.xyz.d1;
            double dy = xyz.d2 - other.xyz.d2;
            double dz = xyz.d3 - other.xyz.d3;

            return dx*dx + dy*dy + dz*dz;
    }

    /**
     * Calculates the distance from this point to another point
     * @param other another point
     * @return the distance value
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Point p) && xyz.equals(p.xyz);
    }

    @Override
    public String toString() { return "Point " + xyz;}


}
