package lighting;

import primitives.*;

/**
 * a representation of a point light - an ordinary lamp
 */
public class PointLight extends Light implements LightSource{
    private final Point position;
    private double kc = 1;
    private double kl = 0;
    private double kq = 0;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     * @param intensity the intensity of the point light
     * @param position the position of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Vector getL(Point point){
        return point.subtract(this.position).normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        double d = point.distance(this.position);
        return this.getIntensity().reduce (kc + kl * d + kq * d * d);
    }

    /**
     * set the kc attenuation factor
     * @param kc the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKc(double kc) {
        this.kc = kc;
        return this;
    }

    /**
     * set the kl attenuation factor
     * @param kl the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKl(double kl) {
        this.kl = kl;
        return this;
    }

    /**
     * set the kq attenuation factor
     * @param kq the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKq(double kq) {
        this.kq = kq;
        return this;
    }
}