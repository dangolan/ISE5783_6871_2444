package scene;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import primitives.Color;
/**
 * Represents a scene that contains objects, background color, and ambient light.
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    /**
     * construct a scene. giving default values to all the fields
     */
    public Scene(String name) {
        this.geometries = new Geometries();
        this.name = name;
        this.background = Color.BLACK;
        this.ambientLight = AmbientLight.NONE;
    }
    /**
     * Sets the background color of the scene.
     *
     * @param background the background color to set
     * @return the updated Scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries the geometries to set
     * @return the updated Scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    /**
     * Adds a geometry to the scene.
     *
     * @param geometry the geometry to add
     * @return the updated Scene object
     */
    public Scene addGeometry(Intersectable geometry) {
        geometries.add(geometry);
        return this;
    }
    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light to set
     * @return the updated Scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
}