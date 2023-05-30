package lighting;

import primitives.Color;

/**
 * Represents a light source in a scene.
 */
abstract class Light {
    private Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Retrieves the intensity of the light.
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
