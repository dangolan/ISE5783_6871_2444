package lighting;

import primitives.Color;

/**
 *
 */
//TODO javadoc
abstract class Light {
    /**
     *
     */
    private Color intensity;

    /**
     * constructor
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * get. intensity don't have set.
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
