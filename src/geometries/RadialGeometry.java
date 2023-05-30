package geometries;

/**
 * class for all the shapes with a radius
 */
public abstract class RadialGeometry extends Geometry {
    final protected double radius;
    final protected double radiusSquared;

    /**
     * constructor
     * @param r the radius
     */
    public RadialGeometry(double r) {
        this.radius = r;
        this.radiusSquared = r * r;
    }

    /**
     * getter
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}
