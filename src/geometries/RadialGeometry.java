package geometries;

/**
 * class for all the shapes with a radius
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * The radius of the radial geometry.
     */
    final protected double radius;
    /**
     * The squared value of the radius of the radial geometry.
     */
    final protected double radiusSquared;

    /**
     * constructor
     *
     * @param r the radius
     */
    public RadialGeometry(double r) {
        this.radius = r;
        this.radiusSquared = r * r;
    }

    /**
     * getter
     *
     * @return the radius
     */
    @SuppressWarnings("unused")
    public double getRadius() {
        return radius;
    }
}
