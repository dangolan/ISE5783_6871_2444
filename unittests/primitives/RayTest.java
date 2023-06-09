package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class contains tests for the {@link Ray} class, specifically for the {@link Ray#getPoint(double)} and
 * {@link Ray#findClosestPoint(List)} methods.
 *
 * @see Ray
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: t is not zero
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        assertEquals(new Point(1, 0, 0), ray.getPoint(1));

        // =============== Boundary Values Tests ==================
        //TC02: t is zero
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        assertEquals(new Point(0, 0, 0), ray.getPoint(0));
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(2, 5, 1));
        Point a = new Point(3, 7, 4);
        Point b = new Point(5, 12, 5);
        Point c = new Point(15, 37, 10);
        // ============ Equivalence Partitions Tests ==============

        // TC01 the closest point is in the middle of the list
        List<Point> points = new LinkedList<>();
        points.add(b);
        points.add(a);
        points.add(c);
        assertEquals(a, ray.findClosestPoint(points),
                "the closest point is at the end of the list- didn't return the right point");

        // =============== Boundary Values Tests ==================

        // TC11 list of points is empty
        assertNull(ray.findClosestPoint(new LinkedList<>()), "list of points is empty- didn't return null");

        // TC12 the closest point is the first point in the list
        points = new LinkedList<>();
        points.add(a);
        points.add(b);
        points.add(c);
        assertEquals(a, ray.findClosestPoint(points),
                "the closest point is the first point in the list- didn't return the right point");

        // TC13 the closest point is at the end of the list
        points = new LinkedList<>();
        points.add(b);
        points.add(c);
        points.add(a);
        assertEquals(a, ray.findClosestPoint(points),
                "the closest point is at the end of the list- didn't return the right point");
    }
}