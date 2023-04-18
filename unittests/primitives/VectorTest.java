package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;


class VectorTest {

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(1, 1, 1);
        v1 = v1.add(v2);
        assertEquals((new Vector(2, 2, 2)), v1, "add() wrong result");

        Vector v3 = new Vector(-1, -1, -1);
        Vector v4 = new Vector(-1, -1, -1);
        v3 = v3.add(v4);
        assertEquals(new Vector(-2, -2, -2), v3, "add() wrong result");

        Vector v5 = new Vector(2, 2, 2);
        Vector v6 = new Vector(-1, -1, -1);
        v5 = v5.add(v6);
        assertEquals(new Vector(1, 1, 1), v5, "add() wrong result");

        Vector v7 = new Vector(-1, -1, -1);
        Vector v8 = new Vector(2, 2, 2);
        v7 = v7.add(v8);
        assertEquals(new Vector(1, 1, 1), v7, "add() wrong result");

        // =============== Boundary Values Tests ==================
        try {
            Vector v9 = new Vector(-1,-1,-1);
            Vector v10 = new Vector(1,1,1);
            v9.add(v10);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e) {
            assertTrue(e.getMessage()!= null);
        }
    }

    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============

        Vector v1 = new Vector(1,1,1);
        v1 = v1.scale(-1);
        assertEquals(new Vector(-1,-1,-1), v1, "scale() wrong result");
        Vector v2 = new Vector(1,1,1);
        v2 = v2.scale(2);
        assertEquals(new Vector(2,2,2), v2, "scale() wrong result");
        Vector v3 = new Vector(-1,-1,-1);
        v3 = v3.scale(2);
        assertEquals(new Vector(-2,-2,-2),v3);
        Vector v4 = new Vector(-1,-1,-1);
        v4 = v4.scale(-1);
        assertEquals(new Vector(1,1,1), v4, "scale() wrong result");

        // =============== Boundary Values Tests ==================
        try {
            Vector v9=new Vector(-1,-1,-1);
            v9.scale(0);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null);
        }
    }

    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(1, 1, 1);
        assertTrue((Double.compare(v1.dotProduct(v2),(1+1+1))==0), "ERROR: dotProduct() wrong value");

        Vector v3 = new Vector(-1, -1, -1);
        Vector v4 = new Vector(-1, -1, -1);
        assertTrue((Double.compare(v3.dotProduct(v4),(1+1+1))==0), "ERROR: dotProduct() wrong value");

        Vector v5 = new Vector(2, 2, 2);
        Vector v6 = new Vector(-1, -1, -1);
        assertTrue((Double.compare(v5.dotProduct(v6),(-2+-2+-2))==0), "ERROR: dotProduct() wrong value");

        Vector v7 = new Vector(-1, -1, -1);
        Vector v8 = new Vector(2, 2, 2);
        assertTrue((Double.compare(v7.dotProduct(v8),(-2+-2+-2))==0), "ERROR: dotProduct() wrong value");

        Vector v9 = new Vector(1,0,1);
        Vector v10 = new Vector(-1,0,1);
        assertTrue((Double.compare(v9.dotProduct(v10),0))==0, "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(1,2,3);
        Vector tmp1 = new Vector(1, 1, 1);
        Vector v12 = v1.crossProduct(v2);
        assertTrue(isZero(v12.dotProduct(v2)),"ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, ()-> v1.crossProduct(tmp1) , "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals((new Vector(1, -2,1)), v12, "ERROR: crossProduct() wrong result length");

        Vector v3 = new Vector(-1, -1, -1);
        Vector v4 = new Vector(-1, -2, -3);
        Vector tmp2 = new Vector(-1, -1, -1);
        Vector v34 = v3.crossProduct(v4);
        assertTrue(isZero(v34.dotProduct(v4)),"ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, ()-> v3.crossProduct(tmp2) , "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(new Vector(1,-2,1), v34, "ERROR: crossProduct() wrong result length");

        Vector v5 = new Vector(1, 1, 1);
        Vector v6 = new Vector(-1, -2, -3);
        Vector tmp3 = new Vector(1, 1, 1);
        Vector v56 = v5.crossProduct(v6);
        assertTrue(isZero(v56.dotProduct(v6)),"ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, ()-> v5.crossProduct(tmp3) , "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(new Vector(-1,2,-1), v56, "ERROR: crossProduct() wrong result length");

        Vector v7 = new Vector(-1, -1, -1);
        Vector v8 = new Vector(1, 2, 3);
        Vector tmp4 = new Vector(-1, -1, -1);
        Vector v78 = v7.crossProduct(v8);
        assertTrue(isZero(v78.dotProduct(v8)),"ERROR: crossProduct() result is not orthogonal to its operands");
        assertThrows(IllegalArgumentException.class, ()-> v7.crossProduct(tmp4) , "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(new Vector(-1, 2, -1), v78, "ERROR: crossProduct() wrong result length");

        // =============== Boundary Values Tests ==================
        try {
            Vector v9=new Vector(-1,-1,-1);
            Vector v10=new Vector(-2,-2,-2);
            Vector v11 = v9.crossProduct(v10);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null);
        }
    }
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1=new Vector(1,1,1);
        assertTrue(Double.compare(v1.lengthSquared(),3) == 0, "ERROR: lengthSquared() wrong value");
        Vector v2=new Vector(-1,-1,-1);
        assertTrue(Double.compare(v2.lengthSquared(),3) == 0, "ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1=new Vector(1,1,1);
        assertTrue(Double.compare(v1.length(),Math.sqrt(3)) == 0, "ERROR: crossProduct() wrong result length");
        Vector v2=new Vector(-1,-1,-1);
        assertTrue(Double.compare(v2.length(),Math.sqrt(3)) == 0, "ERROR: crossProduct() wrong result length");
    }

    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector u = v1.normalize();

        // TC01: unit vector correctness
        assertTrue(isZero(u.length() - 1), "the normalized vector is not a unit vector");

        // TC02: parallel to the original one correctness
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u), "the normalized vector is not parallel to the original one");

        // TC03: not opposite to the original one
        assertTrue(v1.dotProduct(u) >= 0, "the normalized vector is opposite to the original one");}
}