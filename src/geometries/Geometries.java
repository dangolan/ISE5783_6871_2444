package geometries;

import hierarchy.AABB;
import hierarchy.BoundingBoxTree;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Geometries' class.
 * Represents a collection of geometries.
 */
public class Geometries extends Intersectable {
    private final BoundingBoxTree boundingBoxTree  = new BoundingBoxTree();;
    private final List<Intersectable> geometriesInScene = new LinkedList<>();

    public AABB calculateAABB() {
        return boundingBoxTree.calculateAABB();
    }

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
    public Geometries(Intersectable... geometries) {boundingBoxTree.buildHierarchy(List.of(geometries));
    }

    /**
     * Adds the given geometries to the list of geometries in the scene.
     *
     * @param geometries The geometries to add to the list.
     */
    public void add(Intersectable... geometries) {
        boundingBoxTree.buildHierarchy(List.of(geometries));

    }

    /**
     * Finds the intersection points between the given ray and the geometries in the scene.
     *
     * @param ray the ray to intersect with the geometries
     * @return a list of intersection points between the ray and the geometries,
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = boundingBoxTree.findGeoIntersections(ray, maxDistance);

        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

}
