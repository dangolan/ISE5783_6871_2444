package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * this is a class that represents the environmental lightning in a scene
 */
public class AmbientLight extends Light {

    /**
     * Constructs an AmbientLight object with the specified intensity and ambient coefficient.
     * @param ia the intensity of the ambient light
     * @param ka the ambient coefficient representing the reflectance properties of the objects in the scene
     */
    public AmbientLight(Color ia, Double3 ka) {
        super(ia.scale(ka));
    }

    /**
     * A constant representing no ambient light (black).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


}