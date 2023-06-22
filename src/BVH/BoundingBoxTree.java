package BVH;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Tube;
import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;
/**
 * BoundingBoxTree represents a hierarchical data structure used for efficient spatial subdivision
 * and collision detection in a scene containing intersectable objects.
 */
public class BoundingBoxTree extends Intersectable {
    private Box root = new Box(null, new AABB(new Point(0, 0, 0), new Point(0, 0, 0)));
    /**
     * Constructs an empty BoundingBoxTree.
     */
    public BoundingBoxTree() {
    }
    /**
     * Calculates the combined axis-aligned bounding box (AABB) for a list of intersectable objects.
     *
     * @param geometries The list of intersectable objects.
     * @return The combined AABB.
     */
    public static AABB calculateCombinedAABB(List<Intersectable> geometries) {
        AABB combinedAABB = null;

        for (Intersectable geometry : geometries) {
            AABB geometryAABB = geometry.calculateAABB();
            if (combinedAABB == null) {
                combinedAABB = geometryAABB;
            } else {
                combinedAABB.expand(geometryAABB);
            }
        }
        return combinedAABB;
    }
    /**
     * Calculates the AABB of the BoundingBoxTree.
     *
     * @return The AABB of the BoundingBoxTree.
     */
    public AABB calculateAABB() {
        if (root == null) {
            return null;
        }
        return root.getAABB();
    }
    public void buildBoxes(List<Intersectable> geometries) {
        double min = Double.NEGATIVE_INFINITY;
        double max = Double.POSITIVE_INFINITY;
        root = new Box(null, new AABB(new Point(min, min, min), new Point(max, max, max)));
        for (Intersectable item: geometries) {
            root.addChild(new Box(item,item.calculateAABB()));
        }
    }
    /**
     * Builds the hierarchy of the BoundingBoxTree using a list of intersectable objects.
     *
     * @param geometries The list of intersectable objects.
     */
    public void buildHierarchy(List<Intersectable> geometries) {
        root = new Box(null, new AABB(new Point(0, 0, 0), new Point(0, 0, 0)));
        // Build the hierarchy recursively
        buildHierarchyRecursive(geometries, root);
    }

    private void buildHierarchyRecursive(List<Intersectable> geometries, Box node) {

        for (Intersectable item : geometries) {
            node.insertGeometry(item, item.calculateAABB());
        }
    }
    /**
     * Adds an intersectable geometry to the BoundingBoxTree.
     *
     * @param geometry The intersectable geometry to add.
     */
    public void addGeometry(Intersectable geometry) {
        AABB geometryAABB = geometry.calculateAABB();
        root.insertGeometry(geometry, geometryAABB);
    }

    /**
     * Removes an intersectable geometry from the BoundingBoxTree.
     *
     * @param geometry The intersectable geometry to remove.
     */
    public void removeGeometry(Intersectable geometry) {
        root.removeGeometry(geometry);
    }
    /**
     * Finds the geometric intersections of a ray with the objects in the BoundingBoxTree.
     *
     * @param ray         The ray to intersect with.
     * @param maxDistance The maximum distance for intersections.
     * @return A list of geometric intersections.
     */
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = new ArrayList<>();
        findIntersectionsRecursive(ray, root, intersections, maxDistance);
        return intersections;
    }

    private void findIntersectionsRecursive(Ray ray, Box node, List<GeoPoint> intersections, double maxDistance) {
        if (!node.getAABB().intersectsWith(ray)) {
            return;
        }

        if (node.isLeaf()) {
            // Leaf node, check intersection with the associated geometry
            List<GeoPoint> geometryIntersections = node.getGeometry().findGeoIntersections(ray, maxDistance);
            if (geometryIntersections != null) {
                intersections.addAll(geometryIntersections);
            }
        } else {
            // Internal node, traverse child nodes recursively
            for (Box child : node.getChildren()) {
                findIntersectionsRecursive(ray, child, intersections, maxDistance);
            }
        }
    }
    /**
     * Gets the root of the Tree.
     *
     * @return root of the Tree.
     */
    public Box getRoot() {
        return root;
    }

    /**
     * Represents a bounding box (Box) used in the BoundingBoxTree hierarchy.
     */
    public static class Box {
        private final List<Box> children;
        private int numOfShapes = 0;
        private Intersectable geometry; // Only applicable for leaf nodes
        private AABB aabb;
        /**
         * Constructs an empty Box.
         */
        public Box() {
            children = new ArrayList<>();
            geometry = null;
            aabb = null;
        }
        /**
         * Constructs a Box with the given geometry and AABB.
         *
         * @param geometry The geometry associated with the Box.
         * @param aabb     The AABB of the Box.
         */
        public Box(Intersectable geometry, AABB aabb) {
            children = new ArrayList<>();
            this.geometry = geometry;
            this.aabb = aabb;
        }
        /**
         * Gets the geometry associated with the Box.
         *
         * @return The geometry associated with the Box.
         */
        public Intersectable getGeometry() {
            return geometry;
        }
        /**
         * Gets the number of shapes contained in the Box.
         *
         * @return The number of shapes.
         */
        public int getNumOfShapes() {
            return numOfShapes;
        }
        /**
         * Adds a child Box to the current Box.
         *
         * @param child The child Box to add.
         */
        public void addChild(Box child) {
            children.add(child);
        }
        /**
         * Removes a child Box from the current Box.
         *
         * @param child The child Box to remove.
         */
        public void removeChild(Box child) {
            children.remove(child);
        }
        /**
         * Inserts a geometry into the Box with its associated AABB.
         *
         * @param otherGeometry The geometry to insert.
         * @param geometryAABB  The AABB of the geometry.
         */
        public void insertGeometry(Intersectable otherGeometry, AABB geometryAABB) {

            if (isInfinity(otherGeometry)) {
                addChild(new Box(otherGeometry, geometryAABB));
                aabb.expand(geometryAABB);
                return;
            }
            if (isLeaf() && this.geometry != null) {
                // Create a new internal node and convert the leaf node into a child of the internal node
                Box internalNode = new Box(this.geometry, this.geometry.calculateAABB());
                children.clear();
                this.addChild(internalNode);
                this.geometry = null;
                this.addChild(new Box(otherGeometry, geometryAABB));
                numOfShapes += 2;
            } else {
                // Find the child node that fully contains the geometry's AABB
                for (Box child : children) {
                    if (child.geometry instanceof Plane || child.geometry instanceof Tube) {
                        continue;
                    }
                    if ((child.getAABB().contains(geometryAABB) || child.getAABB().isOverlapping(geometryAABB)) || (child.children.size() < 10 && aabb.isAABBClose(geometryAABB, calculateDistance()))) {
                        aabb.expand(geometryAABB);
                        numOfShapes++;
                        child.insertGeometry(otherGeometry, geometryAABB);
                        return;
                    }
                }

                // If no child fully contains or overlaps the geometry's AABB, create a new leaf node
                addChild(new Box(otherGeometry, geometryAABB));
                numOfShapes++;
            }
            aabb.expand(geometryAABB); // Update the AABB of the current node
        }
        /**
         * Calculates the distance of the Box based on its AABB volume and the number of shapes.
         *
         * @return The calculated distance.
         */
        public double calculateDistance() {
            return aabb.calculateAABBVolume() / (1000000000 * numOfShapes);
        }
        /**
         * Checks if the given geometry is an infinite plane.
         *
         * @param geometry The intersectable geometry.
         * @return True if the geometry is an infinite plane, false otherwise.
         */
        private boolean isInfinity(Intersectable geometry) {
            return geometry instanceof Plane;
        }
        /**
         * Removes a geometry from the Box and its child Boxes.
         *
         * @param geometry The geometry to remove.
         * @return True if the geometry was removed, false otherwise.
         */
        public boolean removeGeometry(Intersectable geometry) {
            boolean b;
            if (isLeaf()) {
                return false;
            }
            // Recursively remove the geometry from child nodes
            for (Box child : children) {
                if (child.geometry == geometry) {
                    children.remove(child);
                    numOfShapes--;
                    return true;
                }
                b = child.removeGeometry(geometry);
                if (b) {
                    numOfShapes--;
                    return b;
                }
            }
            return false;
        }
        /**
         * Gets the list of child Boxes.
         *
         * @return The list of child Boxes.
         */
        public List<Box> getChildren() {
            return children;
        }
        /**
         * Checks if the Box is a leaf node.
         *
         * @return True if the Box is a leaf node, false otherwise.
         */
        public boolean isLeaf() {
            return children.isEmpty();
        }
        /**
         * Gets the AABB of the Box.
         *
         * @return The AABB of the Box.
         */
        public AABB getAABB() {
            return aabb;
        }
    }

}

