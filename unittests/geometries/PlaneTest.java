package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Test for Plane class
 */
class PlaneTest {

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void testConstructor() {

        // =============== Boundary Values Tests ==================

        // TC01: two points are equal
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 1), new Point(0, 0, 2)),
                "constructed Plane with equal points");

        // TC02L: points are all on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 3), new Point(0, 0, 2)),
                "constructed plane must have Vectors in different directions");

    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    /**
     * Test method for {@link Plane#getNormal(Point)} (primitives.Point)}.
     */
    @Test
    void testgetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01 test if normal vector is of length 1 and orthogonal to two vectors in the plane
        Plane p1 = new Plane(new Point(0, 0, 0), new Point(0, 5, 0), new Point(5, 0, 0));
        Vector normal = new Vector(0, 0, 1);
        assertEquals(1, normal.length(), 0.000001, "normal is not of length 1");
        assertEquals(0, normal.dotProduct(new Point(0, 0, 0).subtract(new Point(5, 0, 0))), 0.000001);
        assertEquals(0, normal.dotProduct(new Point(0, 0, 0).subtract(new Point(0, 5, 0))), 0.000001);
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)}.
     */
    @Test
    void testFindIntsersections() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));
        List<Point> result;
        Ray r;
        Point p;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        r = new Ray(new Point(0, 0, 2), new Vector(3, 0, -2));
        p = new Point(1.5, 0, 1);
        result = pl.findIntersections(r);
        assertEquals(1, result.size(), "Ray intersects the plane - wrong number of intersections");
        assertEquals(p, result.get(0), "Ray intersects the plane - wrong Point of intersection");

        // TC02: Ray does not intersect the plane
        r = new Ray(new Point(0, 0, 2), new Vector(1, 1, 1));
        result = pl.findIntersections(r);
        assertNull(result, "Ray does not intersect the plane - found an intersection");

        // =============== Boundary Values Tests ==================

        // TC03: Ray included in the plane
        r = new Ray(new Point(1, 1, 1), new Vector(1, 1, 0));
        result = pl.findIntersections(r);
        assertNull(result, "Ray included in the plane - found and intersection");

        // TC04: Ray not included in the plane
        r = new Ray(new Point(1, 1, 2), new Vector(1, 1, 0));
        result = pl.findIntersections(r);
        assertNull(result, "Ray not included in the plane - found and intersection");

        // TC05: Ray starts before the plane
        r = new Ray(new Point(1, 0, -1), new Vector(0, 0, 1));
        result = pl.findIntersections(r);
        p = new Point(1, 0, 1);
        assertEquals(1, result.size(), "Ray starts before the plane - didn't find an intersection");
        assertEquals(List.of(p), result, "Ray starts before the plane - wrong Point of intersection");

        // TC06: Ray starts on the plane
        r = new Ray(new Point(1, 0, 1), new Vector(0, 0, 1));
        result = pl.findIntersections(r);
        assertNull(result, "Ray starts on the plane - found and intersection");

        // TC07: Ray starts after the plane
        r = new Ray(new Point(1, 0, 2), new Vector(0, 0, 1));
        result = pl.findIntersections(r);
        assertNull(result, "Ray starts after the plane - found and intersection");

        // TC08: Ray starts on the plane and not orthogonal nor parallel
        r = new Ray(new Point(1, 0, 1), new Vector(1, 0, -1));
        result = pl.findIntersections(r);
        assertNull(result, "Ray starts on the plane and not orthogonal nor parallel - found and intersection");

        // TC09: Ray starts on the reference point of the plane and not orthogonal nor parallel
        r = new Ray(new Point(0, 0, 1), new Vector(1, 0, -3));
        result = pl.findIntersections(r);
        assertNull(result, "Ray starts on the reference point of the plane and not orthogonal nor parallel - found and intersection");
    }
}