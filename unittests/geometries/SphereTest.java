package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a JUnit test class for the geometries.Sphere class
 */
class SphereTest {

    /**
     * Test for normal vector of Sphere {@link Sphere#getNormal}
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
        //TC01:There is a simple single test here
        Sphere s1 = new Sphere(new Point(0, 0, 0), 1);
        Vector normal = new Vector(1, 0, 0);
        assertEquals(normal, s1.getNormal(new Point(1, 0, 0)), "Bad normal for sphere");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)} (Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        Point p0 = new Point(-1, 0, 0);
        List<Point> result = sphere.findIntersections(new Ray(p0, new Vector(3, 1, 0)));
        assertNotNull(result, "Empty list...");
        assertEquals(2, result.size(), "Wrong number of points");
        if (p0.distanceSquared(result.get(1)) < p0.distanceSquared(result.get(0)))
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere at two points");

        // TC03: Ray starts inside the sphere (1 point)
        Point p3 = new Point(0.6666666666666667, 0.6666666666666667, 0.6666666666666667);
        List<Point> result1 = sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0.5),
                new Vector(1.077350269, 1.077350269, 1.077350269)));
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(List.of(p3), result1, "Ray crosses sphere at one point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(-3, -1, 0))),
                "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // TC05: Ray starts at sphere and goes inside (1 point)
        Point p4 = new Point(1.577350269149021, 0.5773502691829591, 0.577350269236897);
        List<Point> result2 = sphere.findIntersections(new Ray(new Point(1.267261242, 0.5345224838, 0.8017837257),
                new Vector(0.3100890272, 0.04282778539, -0.2244334565)));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(p4), result2, "Ray crosses sphere at one point");

        // TC06: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p3, new Vector(1, 1, 1))),
                "Ray's line out of sphere");

        // TC07: Ray starts before the sphere (2 points)
        Point p5 = new Point(1, 0, -1);
        Point p6 = new Point(1, 0, 1);
        p0 = new Point(1, 0, -2);
        List<Point> result3 = sphere.findIntersections(new Ray(p0, new Vector(0, 0, 1)));
        assertNotNull(result3, "Empty list...");
        assertEquals(2, result3.size(), "Wrong number of points");
        if (p0.distanceSquared(result3.get(1)) < p0.distanceSquared(result3.get(0)))
            result3 = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p5, p6), result3, "Ray crosses sphere at two points");

        // TC08: Ray starts at sphere and goes inside (1 point)
        List<Point> result4 = sphere.findIntersections(new Ray(p5, new Vector(0, 0, 1)));
        assertEquals(1, result4.size(), "Wrong number of points");
        assertEquals(List.of(p6), result4, "Ray crosses sphere at one point");

        // TC09: Ray starts inside (1 point)
        List<Point> result5 = sphere.findIntersections(new Ray(new Point(1, 0, -0.5), new Vector(0, 0, 1)));
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(List.of(p6), result5, "Ray crosses sphere at one point");

        // TC10: Ray starts at the center (1 point)
        List<Point> result6 = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(List.of(p6), result6, "Ray crosses sphere at one point");

        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p6, new Vector(0, 0, 1))),
                "Ray's line out of sphere");

        // TC12: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 2), new Vector(0, 0, 1))),
                "Ray's line out of sphere");

        // TC13: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 0, 0))),
                "Ray's line out of sphere");

        // TC14: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(p6, new Vector(0, 0, 1))),
                "Ray's line out of sphere");

        // TC15: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 1), new Vector(0, 0, 1))),
                "Ray's line out of sphere");

        // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1))),
                "Ray's line out of sphere");
    }
}
