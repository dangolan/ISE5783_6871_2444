package primitives;

/**
 * Import necessary classes
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 Define a class called PointTest to test point class {@link primitives.Point}
 */
class PointTest {
    /**
     Initialize two points p1 and p2
     */
    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(1, 1, 1);

    /**
     Define a test for subtract method
     */
    @Test
    void testSubtract() {
        /**
         Check that subtract method returns a vector with expected values
         */
        assertEquals(new Vector(0, 1, 2), p1.subtract(p2), "add doesn't work properly");
    }

    /**
     Define a test for add method
     */
    @Test
    void testAdd() {
        // Create a vector and a point
        Vector v = new Vector(1, 2, 3);
        Point point = new Point(1, 1, 1);
        // Check that add method returns a point with expected values
        assertEquals(new Point(2, 3, 4), point.add(v), "add doesn't work properly");
    }

    /**
     Define a test for distanceSquared method
     */
    @Test
    void testDistanceSquared() {
        // Check that distanceSquared method returns the expected distance
        assertEquals(p1.distanceSquared(p2), 5, "distanceSquared doesnt work properly");
    }

    /**
     Define a test for distance method
     */
    @Test
    void testDistance() {
        /**
         Check that distance method returns the expected distance
         */
        assertEquals(Math.sqrt(5), p1.distance(p2), "distance doesn't work properly");
    }
}
