package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable {
    abstract public List<Point> findIntersections (Ray ray);
}
