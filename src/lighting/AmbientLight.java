package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * this is a class that represents the environmental lightning in a scene
 */
public class AmbientLight {
    Color intensity;

    /**
     * construct the ambient light using a color, and it's attenuation factor
     *
     * @param ia the base intensity of the light
     * @param ka the attenuation factor of the intensity for each rgb color
     */
    public AmbientLight(Color ia, Double3 ka) {
        intensity = ia.scale(ka);
    }

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}