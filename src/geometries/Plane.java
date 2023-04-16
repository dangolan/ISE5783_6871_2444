package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    final private Point p0;
    final private Vector normal;
    /**
     * constructor
     * @param p0 the point
     * @param normal the vector that normal to the plane
     */
    public Plane(Point p0, Vector normal) {
        super();
        this.p0 = p0;
        this.normal = normal;
    }
    /**
     * constructor that get 3 points and set one of them to witch the plane lays
     * and calculate the normal vector
     * @param p0 point 0
     * @param p1 point 1
     * @param p2 point 2
     * @throws IllegalArgumentException when ....
     */
    public Plane(Point p0, Point p1,Point p2) {
        super();
        this.p0 = p0;/* Associated point in which the plane lays*/
        this.normal = p1.subtract(p0).crossProduct(p2.subtract(p0)).normalize();

    }

    /**
     * getter
     * @return the point 0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter
     * @return the normal vector
     */
    public Vector getNormal() {
        return normal;
    }
    /**
     * getter
     * @param p the point
     * @return the normal vector
     */
    public Vector getNormal(Point p) {
        return normal;
    }

    public boolean equals(Plane o) {
        if (this == o) return true;
        return p0.equals(o.p0) && normal.equals(o.normal);
    }
    @Override
    public String toString() {
        return "Plane [p0=" + p0 + ", normal=" + normal + "]";
    }

}
