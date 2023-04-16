package geometries;
import primitives.*;
public abstract class RadialGeometry implements Geometry {
    final protected double radius;
    /**
     * constructor
     * @param R the radius
     */
    public RadialGeometry(double R){
        this.radius = R;
    }
    /**
     * getter
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}
