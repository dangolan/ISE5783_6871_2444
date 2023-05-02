package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a JUnit test class for the Tube class, which represents a cylinder-shaped object in 3D space.
 */

class TubeTest {

    /**
     * This test method tests the getNormal() method of the Tube class. It creates a Tube object with a center line
     * defined by a Ray object (with origin at (0, 0, 1) and direction (0, 0, 1)) and a radius of 2 units.
     * Then it calls the getNormal() method on the Tube object with a Point object (0, 2, 2) as an argument,
     * which should be on the surface of the tube. It then checks if the returned normal vector is equal to (0, 1, 0).
     * If it's not, the test fails with the message "get normal() wrong value"
     * {@link geometries.Tube#getNormal(primitives.Point)}.
     */

    @Test
    void getNormal() {
        Tube t = new Tube(new Ray(new Point(0, 0, -3), new Vector(0, 0, 1)), 3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Test the result of getNormal for tube is proper

        Point p1 = new Point(0, -3, 1);
        assertEquals(new Vector(0, -1, 0), t.getNormal(p1), "bad Normal for Tube");

        // =============== Boundary Values Tests ==================

        // TC11: Test the result for point in front of head ray
        Point p2 = new Point(3, 0, -3);
        assertEquals(new Vector(1, 0, 0), t.getNormal(p2), "bad tube normal for point in front of ray");
    }
}
