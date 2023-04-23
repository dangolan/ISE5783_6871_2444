package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

// This is a JUnit test class for the Tube class, which represents a cylinder-shaped object in 3D space.

class TubeTest {

    // This test method tests the getNormal() method of the Tube class. It creates a Tube object with a center line
    // defined by a Ray object (with origin at (0, 0, 1) and direction (0, 0, 1)) and a radius of 2 units.
    // Then it calls the getNormal() method on the Tube object with a Point object (0, 2, 2) as an argument,
    // which should be on the surface of the tube. It then checks if the returned normal vector is equal to (0, 1, 0).
    // If it's not, the test fails with the message "get normal() wrong value".

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        Ray ray = new Ray(new Point(1, 1, 1),new Vector(1,0,0));
        Tube tube = new Tube(ray, 5);
        Vector vector=new Vector(0,1,0);
        assertEquals(vector, tube.getNormal(new Point(2,6,1)), "found wrong normal to the tube");

        // =============== Boundary Values Tests ==================
        //TC11:test normal of a point that parallel to the origin point of the tube
        assertEquals(vector, tube.getNormal(new Point(1,6,1)), "found wrong normal to the tube");
        //TC12:test if the get normal function throws an exception for point on the ray
        assertThrows(IllegalArgumentException.class,()-> tube.getNormal(new Point(6,1,1)),
                "point cannot be on the tube axis");


    }
}
