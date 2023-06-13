package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * represents a ray tracer traces rays through a scene and provides its color
 */
public abstract class RayTracerBase {
    /**
     * The scene object representing the scene being rendered.
     */
    protected final Scene scene;

    /**
     * Constructs a RayTracerBase object with the specified scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * tracing a ray through a scene and finding the color of the object closest to the head of the ray
     *
     * @param ray the ray to trace the scene with
     * @return the co;or of the object the ray 'sees' first
     */
    public abstract Color traceRay(Ray ray);
}