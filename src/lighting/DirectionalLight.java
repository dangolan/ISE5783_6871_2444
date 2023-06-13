package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * representation if a directional light that has a direction, intensity and no attenuation
 */
public class DirectionalLight extends Light implements LightSource {
    private final Vector direction;

    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity the intensity of the directional light
     * @param direction the direction of the light rays
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }
    @Override
    public Vector getL(Point point) {
        return this.direction;
    }
    @Override
    public double getDistance(Point point) { return Double.POSITIVE_INFINITY; }

}