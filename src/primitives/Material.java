package primitives;

/**
 * this class represents the different materials of the surfaces
 * and the reflection of a light component on it,
 * in three known values: diffusion, specular, and shininess.
 */
public class Material {

    /**
     * kd - diffuse component, represents the scattering of light rays to all directions from the surface
     */
    public Double3 kd = Double3.ZERO;

    /**
     * ks - specular component, represents the reflectance of the light source over the surface
     */
    public Double3 ks = Double3.ZERO;

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
     * Sets the shininess value of the material.
     *
     * @param nShininess the shininess value to set
     * @return a reference to this Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}

