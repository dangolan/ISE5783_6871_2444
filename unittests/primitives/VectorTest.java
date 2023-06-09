package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple dotProduct test
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals(-28d, v1.dotProduct(v2), 0.00001, "dotProduct() wrong value");

        // =============== Boundary Values Tests ==================

        // TC11: dotProduct for orthogonal vectors
        Vector v3 = new Vector(0, 3, -2);
        assertEquals(0d, v1.dotProduct(v3), 0.00001, "dotProduct() for orthogonal vectors is not zero");
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
        assertEquals(0, v12.dotProduct(v2), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(tmp1), "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals((new Vector(1, -2, 1)), v12, "ERROR: crossProduct() wrong result length");

        //TC02:simple test
        Vector v3 = new Vector(-1, -1, -1);
        Vector v4 = new Vector(-1, -2, -3);
        Vector tmp2 = new Vector(-1, -1, -1);
        Vector v34 = v3.crossProduct(v4);
        assertEquals(0, v34.dotProduct(v4), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, () -> v3.crossProduct(tmp2), "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(new Vector(1, -2, 1), v34, "ERROR: crossProduct() wrong result length");

        //TC03:simple test
        Vector v5 = new Vector(1, 1, 1);
        Vector v6 = new Vector(-1, -2, -3);
        Vector tmp3 = new Vector(1, 1, 1);
        Vector v56 = v5.crossProduct(v6);
        assertEquals(0, v56.dotProduct(v6), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
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

        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v9 = new Vector(-2, -4, -6);
        Vector v10 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class,
                () -> v10.crossProduct(v9), "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for the {@link Vector#lengthSquared()} method of the Vector class. <p>
     * Tests if the method returns the squared length of the vector.
     * Uses equivalence partitioning testing strategy.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:simple test
        Vector v1 = new Vector(1, 1, 1);
        assertEquals(3, v1.lengthSquared(), 0.0000001, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test case for to subtract() method of the Vector class.
     * This test verifies the correctness of to subtract() method by using different test scenarios.
     * It covers equivalence partitions and boundary values.
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============

        //TC01:simple test
        assertEquals(new Vector(0, 1, 4), v1.subtract(new Vector(1, 1, -1)), "ERROR:wrong result of add function");
        // =============== Boundary Values Tests ==================

        // TC11: test if Vector -itself throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "ERROR: Vector - itself does not throw an exception");

    }

    /**
     * Test method for the {@link Vector#length()} method of the Vector class. <p>
     * Tests if the method returns the length of the vector.
     * Uses equivalence partitioning testing strategy.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============

        //TC01:simple test
        Vector v1 = new Vector(1, 1, 1);
        assertEquals(Math.sqrt(3), v1.length(), 0.0000001, "ERROR: length() wrong result length");
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
        assertEquals(1, u.length(), 0.000001, "the normalized vector is not a unit vector");

        // TC02: parallel to the original one correctness
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u), "the normalized vector is not parallel to the original one");
    }
}