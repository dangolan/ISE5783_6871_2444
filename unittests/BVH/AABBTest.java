package BVH;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AABB class.
 */
class AABBTest {
    /**
     * Test method for {@link AABB#calculateAABBVolume()}}.
     */
    @Test
    void calculateAABBVolume() {
        // Create an AABB
        AABB aabb = new AABB(new Point(0, 0, 0), new Point(2, 3, 4));

        // Calculate the volume
        double volume = aabb.calculateAABBVolume();

        // TC01:  Assert the expected volume
        assertEquals(24.0, volume);
    }

    /**
     * Test method for {@link AABB#intersectsWith(Ray)}}.
     */
    @Test
    void intersectsWith() {
        // Create an AABB
        AABB aabb = new AABB(new Point(0, 0, 0), new Point(2, 2, 2));

        // Create a ray
        Ray ray = new Ray(new Point(1, 1, -1), new Vector(0, 0, 1));

        // TC01: Check intersection
        assertTrue(aabb.intersectsWith(ray));
    }

    /**
     * Test method for {@link AABB#expand(AABB)}}.
     */
    @Test
    void expand() {
        // Create an AABB
        AABB aabb = new AABB(new Point(0, 0, 0), new Point(2, 2, 2));

        // Create another AABB to expand with
        AABB otherAABB = new AABB(new Point(1, 1, 1), new Point(3, 3, 3));

        // Expand the AABB
        aabb.expand(otherAABB);

        // TC01: Assert the new minimum and maximum points
        assertEquals(new Point(0, 0, 0), aabb.getMinPoint());
        assertEquals(new Point(3, 3, 3), aabb.getMaxPoint());
    }

    /**
     * Test method for {@link AABB#contains(Point)}}.
     */
    @Test
    void contains() {
        // Create an AABB
        AABB aabb = new AABB(new Point(0, 0, 0), new Point(2, 2, 2));

        // Create a point inside the AABB
        Point insidePoint = new Point(1, 1, 1);

        // Create a point outside the AABB
        Point outsidePoint = new Point(3, 3, 3);

        // TC01: Check containment
        assertTrue(aabb.contains(insidePoint));
        assertFalse(aabb.contains(outsidePoint));
    }

    /**
     * Test method for {@link AABB#contains(AABB)}}.
     */
    @Test
    void testContains() {
        // Create an AABB
        AABB aabb1 = new AABB(new Point(0, 0, 0), new Point(2, 2, 2));

        // Create another AABB inside the first AABB
        AABB aabb2 = new AABB(new Point(1, 1, 1), new Point(2, 2, 2));

        // Create another AABB outside the first AABB
        AABB aabb3 = new AABB(new Point(3, 3, 3), new Point(4, 4, 4));

        // TC01: Check containment
        assertTrue(aabb1.contains(aabb2));
        assertFalse(aabb1.contains(aabb3));
    }

    /**
     * Test method for {@link AABB#isOverlapping(AABB)}}.
     */
    @Test
    void isOverlapping() {
        // Create an AABB
        AABB aabb1 = new AABB(new Point(0, 0, 0), new Point(2, 2, 2));

        // Create an overlapping AABB
        AABB aabb2 = new AABB(new Point(1, 1, 1), new Point(3, 3, 3));

        // Create a non-overlapping AABB
        AABB aabb3 = new AABB(new Point(3, 3, 3), new Point(4, 4, 4));

        // TC01: Check overlap
        assertTrue(aabb1.isOverlapping(aabb2));
        assertFalse(aabb1.isOverlapping(aabb3));
    }

    /**
     * Test method for {@link AABB#isAABBClose(AABB, double)}}.
     */
    @Test
    void isAABBClose() {
        // Create an AABB
        AABB aabb1 = new AABB(new Point(0, 0, 0), new Point(2, 2, 2));

        // Create a close AABB
        AABB aabb2 = new AABB(new Point(1, 1, 1), new Point(3, 3, 3));

        // Create a far AABB
        AABB aabb3 = new AABB(new Point(10, 10, 10), new Point(12, 12, 12));

        // TC01: Check closeness with a threshold of 5
        assertTrue(aabb1.isAABBClose(aabb2, 5));
        assertFalse(aabb1.isAABBClose(aabb3, 5));
    }
}
