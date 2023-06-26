package BVH;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BoundingBoxTree class.
 */
class BoundingBoxTreeTest {

    /**
     * Test method for {@link BoundingBoxTree#buildHierarchy(List)}}.
     */
    @Test
    void buildHierarchy() {
        // Create a BoundingBoxTree
        BoundingBoxTree tree = new BoundingBoxTree();

        // Create a list of geometries
        List<Intersectable> geometries = new ArrayList<>();
        for (int i = 0; i < 300; i += 5) {
            geometries.add(new Sphere(new Point(0, 0, i), 3d));
        }

        // Build the hierarchy
        tree.buildHierarchy(geometries);

        // TC01: Verify the hierarchy by checking the AABB
        AABB rootAABB = tree.calculateAABB();
        assertNotNull(rootAABB); // Verify that the root AABB is not null

        int numChildNodes = tree.getRoot().getNumOfShapes();
        assertEquals(geometries.size(), numChildNodes, "Number of child and leaf nodes");

    }

    /**
     * Test method for {@link BoundingBoxTree#addGeometry(Intersectable)}}.
     */
    @Test
    void addGeometry() {
        // Create a BoundingBoxTree
        BoundingBoxTree tree = new BoundingBoxTree();

        // Create a geometry
        Intersectable geometry = new Plane(new Point(1, 1, 1), new Vector(1, 1, 1)); // Create a geometry

        // Add the geometry to the tree
        tree.addGeometry(geometry);

        // TC01: Verify that the geometry is added to the tree
        AABB rootAABB = tree.calculateAABB();
        assertNotNull(rootAABB); // Verify that the root AABB is not null

        // Perform assertions to validate the addition
        assertTrue(tree.getRoot().getChildren().size() > 0, "Number of child nodes");

        AABB geometryAABB = geometry.calculateAABB();
        assertTrue(rootAABB.contains(geometryAABB), "Geometry AABB inclusion");

    }

    /**
     * Test method for {@link BoundingBoxTree#removeGeometry(Intersectable)}}.
     */
    @Test
    void removeGeometry() {
        // Create a BoundingBoxTree
        BoundingBoxTree tree = new BoundingBoxTree();

        /* Create a list of geometries */
        List<Intersectable> geometries = new ArrayList<>();
        Sphere ForDelete = new Sphere(new Point(0, 0, 60), 3d);
        for (int i = 0; i < 50; i += 5) {
            geometries.add(new Sphere(new Point(0, 0, i), 3d));
            geometries.add(new Sphere(new Point(i, 0, i), 3d));
        }
        geometries.add(ForDelete);

        // Add the geometry to the tree
        tree.buildHierarchy(geometries);

        // TC01: Verify that the geometry is added to the tree
        assertEquals(21, tree.getRoot().getNumOfShapes(), "Number of child nodes after removal");

        // Remove the geometry from the tree
        tree.removeGeometry(ForDelete);

        // Verify that the geometry is removed from the tree
        assertEquals(20, tree.getRoot().getNumOfShapes(), "Number of child nodes after removal");

    }

    /**
     * Test method for {@link BoundingBoxTree#calculateCombinedAABB(List)}}.
     */
    @Test
    void calculateCombinedAABB() {
        // Create a list of geometries
        List<Intersectable> geometries = new ArrayList<>();
        for (int i = 0; i < 50; i += 5) {
            geometries.add(new Sphere(new Point(0, 0, i), 3d));
            geometries.add(new Sphere(new Point(i, 0, i), 3d));
        }

        // Calculate the combined AABB
        AABB combinedAABB = BoundingBoxTree.calculateCombinedAABB(geometries);

        // Perform assertions to verify the combined AABB

        // TC01: Check if the combined AABB is not null
        assertNotNull(combinedAABB, "Combined AABB is not null");

        for (Intersectable geometry : geometries) {
            AABB geometryAABB = geometry.calculateAABB();
            assertTrue(combinedAABB.contains(geometryAABB) || combinedAABB.isOverlapping(geometryAABB),
                    "Combined AABB contains or overlaps with individual geometry AABB");
        }
    }
}