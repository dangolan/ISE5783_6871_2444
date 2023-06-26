package primitives;

/**
 * this class represents the different materials of the surfaces
 * and the reflection of a light component on it,
 * in three known values: diffusion, specular, and shininess.
 */
public class Material {
    /**
     * Number of rays used for the blur glass effect.
     */
    public int numOfRays = 1;

    /**
     * Distance parameter for the blur glass effect.
     */
    public double blurGlassDistance = 1;

    /**
     * Radius parameter for the blur glass effect.
     */
    public double blurGlassRadius = 1;

    /**
     * kd - diffuse component, represents the scattering of light rays to all directions from the surface
     */
    public Double3 kd = Double3.ZERO;

    /**
     * ks - specular component, represents the reflectance of the light source over the surface
     */
    public Double3 ks = Double3.ZERO;
    /**
     * Kt - transparency component
     * 0.0 is opaque
     * 1.0 is clear
     */
    public Double3 kt = Double3.ZERO;

    /**
     * Kr - reflection component
     * 0.0 is matte
     * 1.0 is very reflexive
     */
    public Double3 kr = Double3.ZERO;

    /**
     * Shininess - how shiny the material is
     */
    public int nShininess = 0;

    /**
     * set (According to the builder design template)
     *
     * @param ks typed double
     * @return this material
     */
    public Material setKs(double ks) {
        this.ks = new Double3(ks);
        return this;
    }

    /**
     * set (According to the builder design template)
     *
     * @param kd typed double
     * @return this material
     */
    public Material setKd(double kd) {
        this.kd = new Double3(kd);
        return this;
    }

    /**
     * set (According to the builder design template)
     *
     * @param ks typed Double3
     * @return this material
     */
    public Material setKs(Double3 ks) {
        this.ks = ks;
        return this;
    }

    /**
     * set (According to the builder design template)
     *
     * @param kd typed Double3
     * @return this material
     */
    public Material setKd(Double3 kd) {
        this.kd = kd;
        return this;
    }

    /**
     * set (According to the builder design template)
     *
     * @param kt typed double
     * @return this material
     */
    public Material setKt(double kt) {
        this.kt = new Double3(kt);
        return this;
    }

    /**
     * set (According to the builder design template)
     *
     * @param kr typed double
     * @return this material
     */
    public Material setKr(double kr) {
        this.kr = new Double3(kr);
        return this;
    }

    /**
     * set (According to the builder design template)
     *
     * @param kt typed Double3
     * @return this material
     */
    public Material setKt(Double3 kt) {
        this.kt = kt;
        return this;
    }

    /**
     * set (According to the builder design template)
     *
     * @param kr typed Double3
     * @return this material
     */
    public Material setKr(Double3 kr) {
        this.kr = kr;
        return this;
    }

    /**
     * Sets the shininess value of the material.
     *
     * @param nShininess the shininess value to set
     * @return a reference to this Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    /**
     * Sets the properties for applying a blur effect to a glass material.
     *
     * @param numOfRays the number of rays to be used for the blur effect. Must be greater than or equal to 1.
     * @param distance the distance parameter for the blur effect. Must be greater than 0.
     * @param radius the radius parameter for the blur effect. Must be greater than 0.
     * @return the modified Material object with the blur effect properties set.
     * @throws IllegalArgumentException if any of the input arguments are invalid.
     */
    public Material setBlurGlass(int numOfRays, double distance, double radius) {

        if (numOfRays < 1 || distance <= 0 || radius <= 0)
            throw new IllegalArgumentException("illegal argument in setBlurGlass ");

        this.numOfRays = numOfRays;
        this.blurGlassDistance = distance;
        this.blurGlassRadius = radius;

        return this;
    }

}

