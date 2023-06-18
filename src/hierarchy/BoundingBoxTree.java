package hierarchy;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Tube;
import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class BoundingBoxTree extends Intersectable {
    private Box root = new Box(null,new AABB(new Point(0,0,0),new Point(0,0,0)));
    private static AABB totalAABB;
    private static int numOfShapes = 0;

    public BoundingBoxTree() {

    }
    public AABB calculateAABB() {
        if (root == null) {
            return null;
        }
        return root.getAABB();
    }

    public void buildHierarchy(List<Intersectable> geometries) {

        // Build the hierarchy recursively
        buildHierarchyRecursive(geometries, root);
    }

    private void buildHierarchyRecursive(List<Intersectable> geometries, Box node) {

            for (Intersectable item : geometries) {
                if (!(item instanceof Plane) && !( item instanceof Tube)) {
                    if (totalAABB == null){
                        totalAABB = item.calculateAABB();
                    }else{
                        totalAABB.expand(item.calculateAABB());
                    }
                    numOfShapes++;
                }
                node.insertGeometry(item,item.calculateAABB());
            }
    }
    public static double calculateDistance(){
        return totalAABB.calculateAABBSize()  / (3000 * numOfShapes);
    }

    public void addGeometry(Intersectable geometry) {
        AABB geometryAABB = geometry.calculateAABB();
        root.insertGeometry(geometry, geometryAABB);
    }

    public void removeGeometry(Intersectable geometry) {
        root.removeGeometry(geometry);
    }

    public void updateGeometry(Intersectable geometry) {
        AABB geometryAABB = geometry.calculateAABB();
        root.updateGeometry(geometry, geometryAABB);
    }

    // Utility methods

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

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> intersections = new ArrayList<>();
        findIntersectionsRecursive(ray, root, intersections,maxDistance);
        return intersections;
    }

    private void findIntersectionsRecursive(Ray ray, Box node, List<GeoPoint> intersections , double maxDistance) {
        if (!node.getAABB().intersectsWith(ray)) {
            return;
        }

        if (node.isLeaf()) {
            // Leaf node, check intersection with the associated geometry
            List<GeoPoint> geometryIntersections = node.getGeometry().findGeoIntersections(ray,maxDistance);
            if (geometryIntersections != null) {
                intersections.addAll(geometryIntersections);
            }
        } else {
            // Internal node, traverse child nodes recursively
            for (Box child : node.getChildren()) {
                findIntersectionsRecursive(ray, child, intersections,maxDistance);
            }
        }
    }
    public static class Box {
        private final List<Box> children;
        private  Intersectable geometry; // Only applicable for leaf nodes
        private AABB aabb;

        public Box() {
            children = new ArrayList<>();
            geometry = null;
            aabb = null;
        }

        public Box(Intersectable geometry, AABB aabb) {
            children = new ArrayList<>();
            this.geometry = geometry;
            this.aabb = aabb;
        }

        public Intersectable getGeometry() {
            return geometry;
        }

        public void addChild(Box child) {
            children.add(child);
        }

        public void removeChild(Box child) {
            children.remove(child);
        }

        public void insertGeometry(Intersectable otherGeometry, AABB geometryAABB) {
            if (otherGeometry instanceof Plane ||otherGeometry instanceof Tube) {
                addChild(new Box(otherGeometry, geometryAABB));
                aabb.expand(geometryAABB);
            }

            System.out.print(" * ");
            if (isLeaf() && this.geometry != null) {
                    // Create a new internal node and convert the leaf node into a child of the internal node
                    Box internalNode = new Box(this.geometry,this.geometry.calculateAABB());
                    children.clear();
                    this.addChild(internalNode);
                    this.geometry = null;
                    this.addChild(new Box(otherGeometry,otherGeometry.calculateAABB()));
                    aabb.expand(geometryAABB); // Update the AABB of the internal nod
                    System.out.print("\n");

            } else {
                // Find the child node that fully contains the geometry's AABB
                for (Box child : children) {
                    if (child.geometry instanceof Plane ||child.geometry instanceof Tube) {
                        continue;
                    }
                    if ((child.getAABB().contains(geometryAABB) || child.getAABB().isOverlapping(geometryAABB)) || (child.children.size() < 10 && aabb.isAABBClose(geometryAABB,calculateDistance())) ) {
                        aabb.expand(geometryAABB);
                        child.insertGeometry(otherGeometry, geometryAABB);
                        return;
                    }
                }

                // If no child fully contains or overlaps the geometry's AABB, create a new leaf node
                addChild(new Box(otherGeometry, geometryAABB));
                aabb.expand(geometryAABB); // Update the AABB of the current node
                System.out.print("\n");
            }
        }



        public void removeGeometry(Intersectable geometry) {
            if (isLeaf() && this.geometry == geometry) {
                // Remove the leaf node associated with the geometry
                children.clear();
            } else {
                // Recursively remove the geometry from child nodes
                for (Box child : children) {
                    child.removeGeometry(geometry);
                }
            }
        }

        public void updateGeometry(Intersectable geometry, AABB newAABB) {
            if (isLeaf() && this.geometry == geometry) {
                // Update the AABB for the leaf node
                aabb = newAABB;
            } else {
                // Recursively update the geometry in child nodes
                for (Box child : children) {
                    child.updateGeometry(geometry, newAABB);
                }
            }
        }

        public List<Box> getChildren() {
            return children;
        }

        public boolean isLeaf() {
            return children.isEmpty();
        }

        public AABB getAABB() {
            return aabb;
        }
    }

}

