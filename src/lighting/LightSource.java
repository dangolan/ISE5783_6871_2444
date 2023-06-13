package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a light source in a scene.
 */
public interface LightSource {
    /**
     * Retrieves the intensity of the light at a specific point.
     *
     * @param p the point at which to calculate the light intensity
     * @return the intensity of the light at the specified point
     */
    public Color getIntensity(Point p);

    /**
     * Retrieves the direction of the light from a specific point.
     *
     * @param p the point from which to calculate the light direction
     * @return the direction of the light from the specified point
     */
    public Vector getL(Point p);

    /**
     * Retrieves the distance between the light source and a specific point.
     *
     * @param point the point for which to calculate the distance to the light source
     * @return the distance between the light source and the specified point
     */
    public double getDistance(Point point);



}