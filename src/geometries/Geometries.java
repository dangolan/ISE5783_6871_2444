package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import primitives.*;

/**
 * Geometries' class.
 * Represents a collection of geometries.
 */
public class Geometries implements Intersectable{
    private List<Intersectable> geometriesInScene;

    /**
     * A default constructor that create new empty arrayList intersectable-geometries
     */
    public Geometries(){
        //we have chosen in ArrayList because this class will work in better running time when the application demands storing the data and accessing it.
        geometriesInScene = new ArrayList<Intersectable>();
    }

    /**
     * Constructor that receives list of geometries and put them in new arrayList
     * @param geometries
     * */
    public Geometries(Intersectable... geometries){
        geometriesInScene =  new ArrayList<Intersectable>(Arrays.asList(geometries));
    }

    /**
     * A function that add the geometries the receive to the list.
     * @param geometries
     * */
    public void add(Intersectable... geometries){
        if (geometries != null) {
            geometriesInScene.addAll(Arrays.asList(geometries));
        }
    }

    /**
     * Finds a list of intersections between the geometries in the scene and a given ray.
     * This method calculates the intersection points between the geometries in the scene and the specified Ray. It iterates
     * through the list of geometries in the scene, invoking the findIntersections() method of each intersectable geometry.
     * It collects all the intersection points from each geometry and returns them as a list. If there are no intersections
     * or the scene has no geometries, the method returns null.
     * @param myRay The Ray object to find the intersections with.
     * @return A list of Point objects representing the intersection points, or null if no intersections exist or the scene has no geometries.
     */
    @Override
    public List<Point> findIntersections(Ray myRay) {
        if(this.geometriesInScene.isEmpty())
            return null;
        List<Point> temp = new ArrayList<Point>();
        for (Intersectable intersectable : geometriesInScene) {
            List<Point> intersection = intersectable.findIntersections(myRay);
            if (intersection != null)
                temp.addAll(intersection);
        }
        if (temp.isEmpty())
            return null;
        return temp;
    }
}
