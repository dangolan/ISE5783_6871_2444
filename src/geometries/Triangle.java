package geometries;
import primitives.Point;
public class Triangle extends Polygon{
    /**
     * constructor that get 3 points and use the polygon constructor
     *
     * @param vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }
}
