package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for Plane class
 */
class PlaneTest {

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void Constructor() {

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
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01 test if normal vector is correct
        Plane p1 = new Plane(new Point(0, 0, 0), new Point(0, 5, 0), new Point(5, 0, 0));
        Vector normal = new Vector(0, 0, 1);
        assertTrue(normal.equals(p1.getNormal(new Point(1, 1, 0))) ||
                normal.equals(p1.getNormal(new Point(-1, -1, 0))), "bad normal to plane");
    }
}