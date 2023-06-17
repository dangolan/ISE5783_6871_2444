package hierarchy;

import geometries.Intersectable;
import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class BoundingBoxTree extends Intersectable {
    private Box root = new Box(null,new AABB(new Point(0,0,0),new Point(0,0,0)));

    public BoundingBoxTree() {

    }
    public AABB calculateAABB() {
        if (root == null) {
            return null;
        }
        return root.getAABB();
    }

    public void buildHierarchy(List<Intersectable> geometries) {
        // Create an empty tree
        root = new Box(null,new AABB(new Point(0,0,0),new Point(0,0,0)));

        // Calculate the combined AABB for all geometries
        AABB combinedAABB = calculateCombinedAABB(geometries);

        // Build the hierarchy recursively
        buildHierarchyRecursive(geometries, root, combinedAABB);
    }

    private void buildHierarchyRecursive(List<Intersectable> geometries, Box node, AABB nodeAABB) {
        if (geometries.size() == 1) {
            // Create a leaf node for the geometry
            Intersectable geometry = geometries.get(0);

            // Insert the leaf node into the tree
            node.insertGeometry(geometry, nodeAABB);
        } else {
//            // Partition the geometries into subgroups
//            List<List<Intersectable>> subgroups = partitionGeometries(geometries);

            for (Intersectable item : geometries) {
                // Create a new internal node

//                // Calculate the combined AABB for the subgroup
//                AABB subgroupAABB = calculateCombinedAABB(subgroup);
//
//                // Recursively build the hierarchy for the subgroup
//                buildHierarchyRecursive(subgroup, internalNode, subgroupAABB);

                // Insert the internal node into the tree
                node.insertGeometry(item,item.calculateAABB());
            }
        }
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

    private AABB calculateCombinedAABB(List<Intersectable> geometries) {
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

    private List<List<Intersectable>> partitionGeometries(List<Intersectable> geometries) {
        // Implement your preferred partitioning scheme here
        // For simplicity, let's assume a basic partitioning scheme where each geometry is in its own subgroup

        List<List<Intersectable>> subgroups = new ArrayList<>();
        for (Intersectable geometry : geometries) {
            List<Intersectable> subgroup = new ArrayList<>();
            subgroup.add(geometry);
            subgroups.add(subgroup);
        }

        return subgroups;
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
            if (isLeaf() && this.geometry != null) {
                    // Create a new internal node and convert the leaf node into a child of the internal node
                    Box internalNode = new Box(this.geometry,this.geometry.calculateAABB());
                    children.clear();
                    this.addChild(internalNode);
                    this.geometry = null;
                    addChild(new Box(otherGeometry,otherGeometry.calculateAABB()));
                    aabb.expand(geometryAABB); // Update the AABB of the internal node

            } else {
                // Find the child node that fully contains the geometry's AABB
                for (Box child : children) {
                    if (child.getAABB().contains(geometryAABB) || child.getAABB().isOverlapping(geometryAABB)) {
                        aabb.expand(geometryAABB);
                        child.insertGeometry(otherGeometry, geometryAABB);
                        return;
                    }
                }

                // If no child fully contains or overlaps the geometry's AABB, create a new leaf node
                addChild(new Box(otherGeometry, geometryAABB));
                aabb.expand(geometryAABB); // Update the AABB of the current node
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

