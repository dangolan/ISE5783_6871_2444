package geometries;

/**
 * class for all the shapes with a radius
 */
public abstract class RadialGeometry implements Geometry {
    final protected double radius;

    /**
     * constructor
     * @param r the radius
     */
    public RadialGeometry(double r) {
        this.radius = r;
    }

    /**
     * getter
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}
