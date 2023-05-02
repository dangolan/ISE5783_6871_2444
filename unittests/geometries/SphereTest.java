package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a JUnit test class for the geometries.Sphere class
 */

class SphereTest {

    /**
     * * Test for normal vector of Sphere {@link Sphere#getNormal}
     */

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Test ==============
        // We create a Sphere object with a center at (0,0,0) and a radius of 1 unit.
        // Then we call the getNormal() method on the Sphere object with the point (0,0,1) as an argument,
        // which should be on the surface of the sphere.
        // We then check if the returned normal vector is equal to (0,0,1), which is the normal vector at the point (0,0,1)
        // on a unit sphere centered at the origin.
        // If it's not, the test fails.
        // ============ Equivalence Partitions Tests ==============
        Sphere s1 = new Sphere(new Point(0, 0, 0), 1);
        Vector normal = new Vector(1, 0, 0);
        assertEquals(normal, s1.getNormal(new Point(1, 0, 0)), "Bad normal for sphere");
    }
}
