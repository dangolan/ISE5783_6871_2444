package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(1, 1, 1), new Point(0, 0, 0));
        Vector vector = new Vector(0.0D, -0.7071067811865475D, 0.7071067811865475D);
        assertEquals(vector, triangle.getNormal(new Point(0, 0, 0)), "Bad normal to triangle");
    }
}