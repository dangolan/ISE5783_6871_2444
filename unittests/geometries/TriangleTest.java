package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    /**
     * Test method for normal to triangle
     */
    @Test
    public void getNormal() {
        Vector v = new Vector(1, 1, 1);
        Point p = new Point(2,2,2);
        Triangle triangle = new Triangle(new Point(0,0,0),new Point(0,0,1),new Point(0,1,0) );
        Vector normal = plane.getNormal(p);
        assertEquals(v.scale(-1), normal);
    }

    /**
     * Test method for triangle intersection point
     */
    @Test
    public void findIntersections() {
        Triangle tri=new Triangle(new Point3D(1,1,0),new Point3D(1,4,0), new Point3D(4,1,0));
        // ============ Equivalence Partitions Tests ==============
        // TC01 Intersection point inside Triangle
        Ray r1=new Ray(new Point3D(2,2,-1),new Vector(0,0,1));
        List<Intersectable.GeoPoint> result1=tri.findIntersections(r1);
        assertEquals("Wrong number of intersection points",1,result1.size());

        // TC02 Intersection point outside Triangle against edge
        Ray r2=new Ray(new Point3D(4,3,-1),new Vector(0,0,1));
        List<Intersectable.GeoPoint> result2=tri.findIntersections(r2);
        int size;
        if(result2==null)
            size=0;
        else
            size=result2.size();
        assertEquals("Wrong number of intersection points",0,size);

        // TC03 Intersection point outside Triangle against vertex
        Ray r3=new Ray(new Point3D(0.5,0.5,-1),new Vector(0,0,1));
        List<Intersectable.GeoPoint> result3=tri.findIntersections(r3);
        if(result3==null)
            size=0;
        else
            size=result3.size();
        assertEquals("Wrong number of intersection points",0,size);

        // ============ Boundary Value Tests ==============
        // TC04 Intersection point on the edge of the Triangle
        Ray r4=new Ray(new Point3D(2,1,-1),new Vector(0,0,1));
        List<Intersectable.GeoPoint> result4=tri.findIntersections(r4);
        if(result4==null)
            size=0;
        else
            size=result4.size();
        assertEquals("Wrong number of intersection points",0,size);

        // TC05 Intersection point in vertex
        Ray r5=new Ray(new Point3D(1,1,-1),new Vector(0,0,1));
        List<Intersectable.GeoPoint> result5=tri.findIntersections(r5);
        if(result5==null)
            size=0;
        else
            size=result5.size();
        assertEquals("Wrong number of intersection points",0,size);

        // TC06 Intersection point on edges continuation
        Ray r6=new Ray(new Point3D(1,8,0),new Vector(0,0,1));
        List<Intersectable.GeoPoint> result6=tri.findIntersections(r6);
        if(result6==null)
            size=0;
        else
            size=result6.size();
        assertEquals("Wrong number of intersection points",0,size);
    }
}
