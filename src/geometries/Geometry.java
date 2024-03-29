package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometrical object in a three-dimensional space.
 * It provides a method to get the normal vector at a given point on the object.
 */
public abstract class Geometry extends Intersectable {

    private Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * @return emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of this geometry.
     *
     * @param emission the color to set as the emission color
     * @return a reference to this geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Gets the material of this geometry.
     *
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of this geometry.
     *
     * @param material the material to set for this geometry
     * @return a reference to this geometry object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Returns the normal vector of the Geometry object at the specified point.
     *
     * @param point the point at which the normal vector is to be computed
     * @return the normal vector at the specified point
     */
    abstract public Vector getNormal(Point point);
}
