package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 * Geometries' class.
 * Represents a collection of geometries.
 */
public class Geometries implements Intersectable{
    private final List<Intersectable> geometriesInScene;

    /**
     * A default constructor that create new empty arrayList intersectable-geometries
     */
    public Geometries(){
        //we have chosen in ArrayList because this class will work in better running time when the application demands storing the data and accessing it.
        geometriesInScene = new LinkedList<>();
    }

    /**
     Constructor that receives list of geometries and puts them in a new arrayList.
     @param geometries The geometries to add to the list.
     */
    public Geometries(Intersectable... geometries){
        geometriesInScene = new LinkedList<>(Arrays.asList(geometries));
    }

    /**
     Adds the given geometries to the list of geometries in the scene.
     @param geometries The geometries to add to the list.
     */
    public void add(Intersectable... geometries){
        if (geometries != null) {
            geometriesInScene.add((Intersectable) List.of(geometries));
        }
    }

    /**
     * Finds a list of intersections between the geometries in the scene and a given ray.
     * This method calculates the intersection points between the geometries in the scene and the specified Ray. It iterates
     * through the list of geometries in the scene, invoking the findIntersections() method of each intersectable geometry.
     * It collects all the intersection points from each geometry and returns them as a list. If there are no intersections
     * or the scene has no geometries, the method returns null.
     * @param ray The Ray object to find the intersections with.
     * @return A list of Point objects representing the intersection points, or null if no intersections exist or the scene has no geometries.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        boolean is_have_intersections = false;

        for (Intersectable intersect : geometriesInScene) {
            if (intersect.findIntersections(ray) != null) {
                is_have_intersections = true;
                break;
            }
        }
        if (is_have_intersections) {
            List<Point> listIntersect = new LinkedList<>();

            for (Intersectable intersect : geometriesInScene) {
                for (Point point : intersect.findIntersections(ray))
                    if (point != null) {
                        listIntersect.add(point);
                    }
            }
            return listIntersect;
        }
        return null;
    }
}
