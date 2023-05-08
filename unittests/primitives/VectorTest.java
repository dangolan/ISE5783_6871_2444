package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class for testing the {@link Vector} class.
 */
class VectorTest {

    /**
     * Tests the {@link Vector#add(Vector)} method.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that add is proper
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(1, 1, 1);
        v1 = v1.add(v2);
        assertEquals((new Vector(2, 2, 2)), v1, "TC01: add() wrong result");

        // TC02: Test that add is proper
        Vector v3 = new Vector(-1, -1, -1);
        Vector v4 = new Vector(-1, -1, -1);
        v3 = v3.add(v4);
        assertEquals(new Vector(-2, -2, -2), v3, "TC02: add() wrong result");

        // TC03: Test that add is proper
        Vector v5 = new Vector(2, 2, 2);
        Vector v6 = new Vector(-1, -1, -1);
        v5 = v5.add(v6);
        assertEquals(new Vector(1, 1, 1), v5, "TC03: add() wrong result");

        // TC04: Test that add is proper
        Vector v7 = new Vector(-1, -1, -1);
        Vector v8 = new Vector(2, 2, 2);
        v7 = v7.add(v8);
        assertEquals(new Vector(1, 1, 1), v7, "TC04: add() wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Test v1 plus -v1 throw exception
        Vector v9 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> v9.add(new Vector(-1, -2, -3)), "TC11: add() gave wrong result");
    }

    /**
     * Tests the {@link Vector#scale(double)} method.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that the scaled vector has a scaled length
        Vector v1 = new Vector(1, 1, 1);
        v1 = v1.scale(-1);
        assertEquals(new Vector(-1, -1, -1), v1, "TC01: scale() wrong result");

        // TC02: Test that the scaled vector has a scaled length
        Vector v2 = new Vector(1, 1, 1);
        v2 = v2.scale(2);
        assertEquals(new Vector(2, 2, 2), v2, "TC02: scale() wrong result");

        // TC03: Test that the scaled vector has a scaled length
        Vector v3 = new Vector(-1, -1, -1);
        v3 = v3.scale(2);
        assertEquals(new Vector(-2, -2, -2), v3, "TC03: scale() wrong result");

        // TC04: Test that the scaled vector has a scaled length
        Vector v4 = new Vector(-1, -1, -1);
        v4 = v4.scale(-1);
        assertEquals(new Vector(1, 1, 1), v4, "TC04: scale() wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: Test that scaling a vector by 0 throws an exception
        Vector v5 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> v5.scale(0), "TC11: scale() by 0 gave wrong result");
    }

    /**
     * Tests the {@link Vector#dotProduct(Vector)} method.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(1, 1, 1);
        assertEquals((Double.compare(v1.dotProduct(v2), (1 + 1 + 1)) == 0), true, "ERROR: dotProduct() wrong value");

        Vector v3 = new Vector(-1, -1, -1);
        Vector v4 = new Vector(-1, -1, -1);
        assertEquals((Double.compare(v3.dotProduct(v4), (1 + 1 + 1)) == 0), true, "ERROR: dotProduct() wrong value");

        Vector v5 = new Vector(2, 2, 2);
        Vector v6 = new Vector(-1, -1, -1);
        assertEquals((Double.compare(v5.dotProduct(v6), (-2 + -2 + -2)) == 0), true, "ERROR: dotProduct() wrong value");

        Vector v7 = new Vector(-1, -1, -1);
        Vector v8 = new Vector(2, 2, 2);
        assertEquals((Double.compare(v7.dotProduct(v8), (-2 + -2 + -2)) == 0), true, "ERROR: dotProduct() wrong value");

        Vector v9 = new Vector(1, 0, 1);
        Vector v10 = new Vector(-1, 0, 1);
        assertEquals(0, (Double.compare(v9.dotProduct(v10), 0)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Tests the {@link Vector#crossProduct(Vector)} method.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============

        //TC01:simple test
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(1, 2, 3);
        Vector tmp1 = new Vector(1, 1, 1);
        Vector v12 = v1.crossProduct(v2);
        assertEquals(0,v12.dotProduct(v2), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(tmp1), "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals((new Vector(1, -2, 1)), v12, "ERROR: crossProduct() wrong result length");

        //TC02:simple test
        Vector v3 = new Vector(-1, -1, -1);
        Vector v4 = new Vector(-1, -2, -3);
        Vector tmp2 = new Vector(-1, -1, -1);
        Vector v34 = v3.crossProduct(v4);
        assertEquals(0,v34.dotProduct(v4), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, () -> v3.crossProduct(tmp2), "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(new Vector(1, -2, 1), v34, "ERROR: crossProduct() wrong result length");

        //TC03:simple test
        Vector v5 = new Vector(1, 1, 1);
        Vector v6 = new Vector(-1, -2, -3);
        Vector tmp3 = new Vector(1, 1, 1);
        Vector v56 = v5.crossProduct(v6);
        assertEquals(0,v56.dotProduct(v6), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, () -> v5.crossProduct(tmp3), "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(new Vector(-1, 2, -1), v56, "ERROR: crossProduct() wrong result length");

        //TC04:simple test
        Vector v7 = new Vector(-1, -1, -1);
        Vector v8 = new Vector(1, 2, 3);
        Vector tmp4 = new Vector(-1, -1, -1);
        Vector v78 = v7.crossProduct(v8);
        assertEquals(0, v78.dotProduct(v8), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, () -> v7.crossProduct(tmp4), "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(new Vector(-1, 2, -1), v78, "ERROR: crossProduct() wrong result length");

        // =============== Boundary Values Tests ==================
        try {
            Vector v9 = new Vector(-1, -1, -1);
            Vector v10 = new Vector(-2, -2, -2);
            Vector v11 = v9.crossProduct(v10);
            fail("Vector (0,0,0) not valid");
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    /**
     * Test method for the {@link Vector#lengthSquared()} method of the Vector class.
     * <p>
     * Tests if the method returns the squared length of the vector.
     * Uses equivalence partitioning testing strategy.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        Vector v1 = new Vector(1, 1, 1);
        assertEquals(0, Double.compare(v1.lengthSquared(), 3), "ERROR: lengthSquared() wrong value");
        Vector v2 = new Vector(-1, -1, -1);
        assertEquals(0, Double.compare(v2.lengthSquared(), 3), "ERROR: lengthSquared() wrong value");
    }

    @Test
    void testSubtract() throws IllegalAccessException{
        Vector v1 =new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        assertEquals(new Vector(0, 1, 4),v1.subtract(new Vector(1, 1, -1)), "ERROR:wrong result of add function");
        // =============== Boundary Values Tests ==================
        // TC01: test if Vector -itself throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "ERROR: Vector - itself does not throw an exception");

    }

    /**
     * Test method for the {@link Vector#length()} method of the Vector class.
     * <p>
     * Tests if the method returns the length of the vector.
     * Uses equivalence partitioning testing strategy.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        assertTrue(Double.compare(v1.length(), Math.sqrt(3)) == 0, "ERROR: crossProduct() wrong result length");
        Vector v2 = new Vector(-1, -1, -1);
        assertTrue(Double.compare(v2.length(), Math.sqrt(3)) == 0, "ERROR: crossProduct() wrong result length");
    }

    /**
     * Tests the normalize method of the Vector class.
     * {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector u = v1.normalize();

        // TC01: unit vector correctness
        assertEquals( 0,Double.compare(u.length(), 1), "the normalized vector is not a unit vector");

        // TC02: parallel to the original one correctness
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u), "the normalized vector is not parallel to the original one");

        // TC03: not opposite to the original one
        assertEquals(v1.dotProduct(u) >= 0, true, "the normalized vector is opposite to the original one");
    }
}