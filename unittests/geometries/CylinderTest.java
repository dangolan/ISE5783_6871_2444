package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * * Unit tests for geometries.Cylinder class
 */
class CylinderTest {

    /**
     * * Test for normal vector of cylinder {@link Cylinder#getNormal}
     */
    @Test
    void getNormal() {

        Cylinder t = new Cylinder(1,new Ray(new Point(1,0,0), new Vector(0,1,0)), 1);
        assertEquals(new Vector(0, 1, 0),t.getNormal(new Point(2, 0, 0)),"Wrong normal to Cylinder");

    }
}