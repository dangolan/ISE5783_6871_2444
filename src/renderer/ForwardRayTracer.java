package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * RayTracerBasic class that extends the RayTracer class
 */
public class ForwardRayTracer extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * constructor that calls super constructor
     *
     * @param scene the scene to trace through
     */
    public ForwardRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Given a ray, find the closest point of intersection with the scene, and return the color of that point
     *
     * @param ray The ray that we're tracing.
     * @return The color of the closest point.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null)
            return scene.background;

        return calcColor(closestPoint, ray);
    }

    /**
     * calculate the color that needed to be returned from the pixel.
     *
     * @param gp  the point to calculate the color for.
     * @param ray the ray to pass to the function that summarise all the effects of the light sources.
     * @return the color to paint the pixel.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
    }


    /**
     * the entrance function to the recursive process of calculating the reflective effect and refractive effect.
     *
     * @param gp    the point of intersection that need the color calculation.
     * @param ray   the ray from the camera to that point.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the color of the pixel with all the refractions and reflections.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double vn = v.dotProduct(n);
        if (isZero(vn)) return scene.background;
        Color color = calcLocalEffects(gp, v, n, vn, k);
        return level == 1 ? color : color.add(calcGlobalEffects(gp, v, n, vn, level, k));
    }

    /**
     * It finds the closest intersection point of a ray with the scene's geometries
     *
     * @param ray The ray that we want to find the closest intersection to.
     * @return The closest intersection point.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        //returns closest point
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * function calculates local effects of color on point
     *
     * @param geoPoint geometry point to color
     * @param v        direction of the ray that intersects
     * @param n        normal to the geometry surface at the intersection point
     * @param vn       dot-product of (v,n)
     * @param k        k value
     * @return color
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Vector v, Vector n, double vn, Double3 k) {
        Color color = geoPoint.geometry.getEmission();
        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector lightVector = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(lightVector));
            if (nl * vn > 0) {
                Double3 ktr = transparency(geoPoint, lightSource, lightVector, n);

                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
                            lightIntensity.scale(calcSpecular(material, n, lightVector, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * calculating a global effect color
     *
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @param kx    the attenuation factor of reflection or transparency
     * @return the calculated color.
     */
    private Color calcGlobalEffect(int level, Double3 kx, Double3 k, Ray ray) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;

        GeoPoint reflectedPoint = findClosestIntersection(ray);
        return (reflectedPoint == null ? scene.background
                : calcColor(reflectedPoint, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * calculating the color in the global scene, tells what more points we need to
     * check for the color calculations.
     *
     * @param gp    the point of the intersection.
     * @param v     direction of the ray that intersects
     * @param n     normal to the geometry surface at the intersection point
     * @param vn    dot-product of (v,n)
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the calculated color.
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, Vector n, double vn, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Ray reflectedRay = constructReflectedRay(gp, n, v, vn);
        Ray refractedRay = constructRefractedRay(gp, n, v);
        return calcGlobalEffect(level, material.kr, k, reflectedRay)
                .add(calcGlobalEffect(level, material.kt, k, refractedRay));
    }

    /**
     * function calculates specular color
     *
     * @param material    material of geometry
     * @param normal      normal of geometry
     * @param lightVector light vector
     * @param nl          dot product of normal and light vector
     * @param vector      direction of ray
     * @return specular color
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double cosTheta = alignZero(-vector.dotProduct(reflectedVector));
        return cosTheta <= 0 ? Double3.ZERO : material.ks.scale(Math.pow(cosTheta, material.nShininess));

    }

    /**
     * function calculates diffusive color
     *
     * @param material material of geometry
     * @param nl       dot product of normal and light vector
     * @return diffusive color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kd.scale(Math.abs(nl));
    }

    /**
     * function will construct a reflection ray
     *
     * @param gp     geometry point to check
     * @param normal normal vector
     * @param vector direction of ray to point
     * @param vn     dot-product of (v,n)
     * @return reflection ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector normal, Vector vector, double vn) {
        Vector reflectedVector = vector.subtract(normal.scale(2 * vn));
        return new Ray(gp.point, reflectedVector, normal);
    }

    /**
     * Construct and return a refracted ray
     *
     * @param gp The GeoPoint of intersection between the ray and the object
     * @param v  the vector from the point to the light source
     * @param n  the normal vector of the point of intersection
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, n, v);
    }

    /**
     * function will return double that represents transparency
     *
     * @param geoPoint    geometry point to check
     * @param lightSource light source
     * @param l           light vector
     * @param n           normal vector
     * @return transparency value
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        double distance = lightSource.getDistance(geoPoint.point);
        var intersections = scene.geometries.findGeoIntersections(lightRay, distance);
        Double3 ktr = Double3.ONE;
        if (intersections == null) return ktr;

        for (GeoPoint intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kt);





            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }
}