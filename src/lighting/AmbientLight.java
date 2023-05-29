package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * this is a class that represents the environmental lightning in a scene
 */
//TODO javadoc
public class AmbientLight extends Light {


    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


}