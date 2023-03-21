package geometries;
import primitives.Vector;
import primitives.Point;
public interface Geometry {
    abstract public Vector getNormal(Point point);
}
