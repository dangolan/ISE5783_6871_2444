package lighting;

import primitives.Color;

/**
 * Represents a light source in a scene.
 */
abstract class Light {
    /**
     * Original intensity of the light - I<sub>0</sub>
     */
    protected final Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Retrieves the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
