package geometries;
import primitives.Point;
import primitives.Vector;

/**
 The Plane class represents a plane in a three-dimensional space. It implements the Geometry interface
 and provides a constructor to create a plane object with a specified point and normal vector.
 */
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
     * @throws IllegalArgumentException when normal is 0
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

    /**
     Indicates whether some other object is "equal to" this one. The method compares this point with
     the specified object for equality based on the point's coordinates and normal vector.
     @param o the object to compare with this point
     @return true if the specified object is equal to this point; false otherwise.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Point p) && p0.equals(p) && normal.equals(normal);
    }
    @Override
    public String toString() {
        return "Plane [p0=" + p0 + ", normal=" + normal + "]";
    }

}
