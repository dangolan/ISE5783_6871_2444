package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * The PlaneTest class test the geometries.Plane class {@link  geometries.Plane}
 */
class PlaneTest {

    /**
     * Tests the constructor of the plan
     */
    @Test
    void testConstructors() {
        // ensure there are no exceptions
        assertDoesNotThrow(() -> new Plane(new Point(1, 2, 3), new Point(4, 5, 6), new Point(1, 0, 9)),"exception was thrown 3 point constructor");
        // ensure there are exceptions throw
        assertThrows(IllegalArgumentException.class,() -> new Plane(new Point(1, 2, 3), new Point(4, 5, 6), new Point(7, 8, 9)),"No exception was thrown on vector 0 in 3 point constructor");
    }
    /**
     * Tests the getNormal() method of the Plane class.
     * This method verifies that the getNormal() method returns a normal vector of unit length
     * that is orthogonal to the plane itself.{@link  Plane#getNormal()}
     */
    @Test
    void testGetNormal() {
        //creates a valid new Plane object using the points (1, 2, 3), (4, 5, 6), and (1, 0, 9) as parameters, and obtains its normal vector using the getNormal() method.
        Vector normal = new Plane(new Point(1, 2, 3), new Point(4, 5, 6), new Point(1, 0, 9)).getNormal();
        //asserts that the length of the normal vector is equal to 1 with a tolerance of 0.000001, which ensures that the vector has been correctly normalized.
        assertEquals(1.0, normal.length(),0.000001,"the vector length not 1");
        // computes the dot product of the normal vector and a vector obtained by subtracting the points (1, 2, 3) and (4, 5, 6), which should be equal to 0 if the normal vector is indeed orthogonal to the plane.
        assertEquals(0,normal.dotProduct(new Point(1, 2, 3).subtract(new Point(4, 5, 6))),0.00001,"the normal not ortogonal to the plan");

    }

}