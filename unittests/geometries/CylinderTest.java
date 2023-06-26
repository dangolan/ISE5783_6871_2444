package geometries;

import BVH.AABB;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Cylinder class
 */
class CylinderTest {

    /**
     * * Test for normal vector of cylinder {@link Cylinder#getNormal}
     */
    @Test
    void getNormalTest() {
        Cylinder cylinder = new Cylinder(3d, new Ray(new Point(1, 1, 0), new Vector(0, 0, 1)), 3d);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test with point on the top of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 1, 3)), "Bad normal to the top of the cylinder");
        //TC02: Test with point on the bottom of the cylinder
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(1, 1, 0)), "Bad normal to the bottom of the cylinder");

        //TC03: Test with point on the side of the cylinder
        assertEquals(new Vector(0, -1, 0), cylinder.getNormal(new Point(1, 0, 1)), "Bad normal to the side of the cylinder");

        // =============== Boundary Values Tests ==================
        //TC11: Test with point on the top edge of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 0, 3)), "Bad normal to the top-edge of the cylinder");

        //TC12: Test with point on the bottom edge of the cylinder
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0, 1, 0)), "Bad normal to the bottom-edge of the cylinder");
    }

    /**
     * Test method for {@link Cylinder#calculateAABB()}.
     */
    @Test
    void calculateAABB() {
        // Create a cylinder
        double height = 10.0;
        Ray axisRay = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        double radius = 5.0;
        Cylinder cylinder = new Cylinder(height, axisRay, radius);

        // Calculate the AABB
        AABB aabb = cylinder.calculateAABB();

        // Define the expected minimum and maximum points
        Point expectedMin = new Point(-radius, -height / 2.0, -radius);
        Point expectedMax = new Point(radius, height / 2.0, radius);

        // Assert the minimum and maximum points of the AABB
        assertEquals(expectedMin, aabb.getMinPoint());
        assertEquals(expectedMax, aabb.getMaxPoint());
    }

}