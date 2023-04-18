package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The PlaneTest class test the geometries.Plane class {@link  geometries.Plane}
 */
class PlaneTest {

    /**
     * Tests the getNormal() method of the Plane class.
     * This method verifies that the getNormal() method returns a normal vector of unit length
     * that is orthogonal to the plane itself.{@link  Plane#getNormal()}
     */
    @Test
    void testGetNormal() {
        Plane p = new Plane(new Point(1, 2, 3), new Point(4, 5, 6), new Point(7, 8, 9));
        Vector normal = p.getNormal();

        // Assert that the magnitude of the normal vector is 1
        assertEquals(1.0, normal.length());

        // Assert that the normal vector is orthogonal to the plane
        assertTrue(normal.dotProduct(p.getP0().subtract(normal)) < 1e-6);

    }

}