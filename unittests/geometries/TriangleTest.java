package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for Triangle class
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle# Triangle.getNormal(Point).}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for getting a normal to a triangle
        Point p1 = new Point(1, 0, 1);
        Point p2 = new Point(0, 1, 5);
        Point p3 = new Point(2, 0, 1);
        Triangle t1 = new Triangle(p1, p2, p3);
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        Vector v3 = t1.getNormal(null);
        assertEquals(0, v3.dotProduct(v1), "Triangle getNormal() TC01.1 failed");
        assertEquals(0, v3.dotProduct(v2), "Triangle getNormal() TC01.2 failed");
        assertEquals(1, v3.length(), "Triangle getNormal() TC01.3 failed");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 ray passes through triangle
        Ray ray = new Ray(new Point(3, 3, 2), new Vector(-1, -1, -4));
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(1, 5, 0), new Point(6, 0, 0));
        assertEquals(1, triangle.findIntersections(ray).size());
        assertEquals(new Point(2.5, 2.5, 0), triangle.findIntersections(ray).get(0));

        // TC02 ray misses triangle on one side
        ray = new Ray(new Point(3, 3, 2), new Vector(1, 1, -4));
        assertNull(triangle.findIntersections(ray));

        // TC03 ray misses triangle on two side
        ray = new Ray(new Point(3, 3, 2), new Vector(-5, 5.5, -4));
        assertNull(triangle.findIntersections(ray));

        // =============== Boundary Values Tests ==================
        // TC11 ray intersects vertex
        ray = new Ray(new Point(1, 0, 3), new Vector(0, 0, -1));
        assertNull(triangle.findIntersections(ray));

        // TC12 ray intersects edge
        ray = new Ray(new Point(1, 0, 3), new Vector(1, 0, -6));
        assertNull(triangle.findIntersections(ray));

        // TC13 ray intersects edge continuation imaginary line
        ray = new Ray(new Point(0.5, 0, 3), new Vector(0, 0, -1));
        assertNull(triangle.findIntersections(ray));
    }
}