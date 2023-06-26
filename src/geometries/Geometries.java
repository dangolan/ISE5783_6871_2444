package geometries;

import BVH.AABB;
import BVH.BoundingBoxTree;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Geometries' class.
 * Represents a collection of geometries.
 */
public class Geometries extends Intersectable {
    private final BoundingBoxTree boundingBoxTree = new BoundingBoxTree();
    private final List<Intersectable> geometriesInScene = new LinkedList<>();
    private boolean buildBoxes = false;

    /**
     * A default constructor that create new empty arrayList intersectable-geometries
     */
    public Geometries() {
    }

    /**
     * Constructor that receives list of geometries and puts them in a new arrayList.
     *
     * @param geometries The geometries to add to the list.
     */
    public Geometries(Intersectable... geometries) {
        geometriesInScene.addAll(List.of(geometries));
    }

    public AABB calculateAABB() {
        return boundingBoxTree.calculateAABB();
    }

    /**
     * Adds the given geometries to the list of geometries in the scene.
     *
     * @param geometries The geometries to add to the list.
     */
    public void add(Intersectable... geometries) {
        geometriesInScene.addAll(List.of(geometries));

    }
    /**
     * Builds the hierarchy of intersectable objects using the provided geometries.
     * This method sets the flag to enable building bounding boxes and invokes the
     * {@link BoundingBoxTree#buildHierarchy(List)}} method to construct the hierarchy.
     *
     * @param geometries the array of intersectable objects to be included in the hierarchy.
     */
    public void buildHierarchy(Intersectable... geometries) {
        buildBoxes = true;
        boundingBoxTree.buildHierarchy(geometriesInScene);

    }
    /**
     * Builds the bounding boxes for the intersectable objects in the scene.
     * This method sets the flag to enable building bounding boxes and invokes the
     * {@link BoundingBoxTree#buildHierarchy(List)}} method to construct the boxes.
     */
    public void buildBoxes() {
        buildBoxes = true;
        boundingBoxTree.buildBoxes(geometriesInScene);
    }

    /**
     * Finds the intersection points between the given ray and the geometries in the scene.
     *
     * @param ray the ray to intersect with the geometries
     * @return a list of intersection points between the ray and the geometries,
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result;
        if (buildBoxes) {
            result = boundingBoxTree.findGeoIntersections(ray, maxDistance);

            if (result.isEmpty()) {
                return null;
            }
        } else {

            result = null;
            for (Intersectable item : geometriesInScene) {
                var itemList = item.findGeoIntersections(ray);
                if (itemList != null) {
                    if (result == null)
                        result = new LinkedList<>();
                    for (GeoPoint point : itemList) {
                        if (alignZero(point.point.distance(ray.getP0()) - maxDistance) <= 0)
                            result.add(point);
                    }
                }
            }
        }
        return result;
    }

}
