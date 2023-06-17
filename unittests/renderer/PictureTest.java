package renderer;


import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;

import java.util.LinkedList;
import java.util.List;
/**
 * The PictureTest class is a test class for rendering a picture using the Ray Tracing algorithm.
 * It sets up the scene, camera, lights, and objects to create a desired picture and renders it.
 */
public class PictureTest {
    /**
     * Initializes a list of Sphere objects to be added to the scene.
     *
     * @return a list of Sphere objects representing the spheres in the scene
     */
    private List<Sphere>  initializeBalls() {

        List<Sphere> balls = new LinkedList<>();
        Material material = new Material().setKd(0.4).setKs(1).setShininess(100).setKt(0).setKr(0.9);

        for(double i = 0,j = 6; i <500 ;i+=1,j+=0.04){
            double x = Math.cos(i);
            double y = Math.sin(i);
            balls.add((Sphere) new Sphere(new Point(i*y, i*x, (i*2 - 150)), j).setMaterial(material).setEmission(new Color(255,0,0)));
        }
        return balls;
    }
    /**
     * Test method for rendering the picture.
     */
    @Test
    public void PictureTest() {

        Scene scene = new Scene("pictureForBonus").setBackground(new Color(0,0,0));
        Camera camera = new Camera(new Point(0, -600, 10), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setKd(0.4).setKs(1).setShininess(50).setKt(0).setKr(0.5).setKs(0.5);
        Material material1 = new Material().setKd(0.4).setKs(1).setShininess(100).setKt(0.5).setKr(0);
        SpotLight light = new SpotLight(new Color(255, 255, 255), new Point(0, -50, 25), new Vector(0, 2, -1));
        SpotLight light2 = new SpotLight(new Color(255, 255, 255), new Point(0, 50, 25), new Vector(0, -2, -1));
        light.setKc(0).setKl(0.01).setKq(0.05);
        light.setNarrowBeam(5);
        light2.setKc(0).setKl(0.01).setKq(0.05);
        light2.setNarrowBeam(5);

        DirectionalLight directionalLight1 = new DirectionalLight(new Color(100, 100, 100), new Vector(0, 0, -1));
        DirectionalLight directionalLight2 = new DirectionalLight(new Color(100, 100, 100), new Vector(1, 0, 0));
        DirectionalLight directionalLight3 = new DirectionalLight(new Color(100, 100, 100), new Vector(-1, 0, 0));
        PointLight pointLight = new PointLight(new Color(255,255,255),new Point(200,50,-100));


        scene.lights.add(light);
        scene.lights.add(directionalLight1);
        scene.lights.add(directionalLight2);
        scene.lights.add(directionalLight3);
        scene.lights.add(pointLight);

        Sphere sphere = new Sphere(new Point(0, 0, 220), 130);
        sphere.setMaterial(material1).setEmission(new Color(102,178,255));

        Plane pln = new Plane(new Point(100,-100,-150),new Vector(0,0,1));

        pln.setMaterial(material).setEmission(new Color(0,0,0));

        scene.geometries.add(pln, sphere);

        for (Sphere item : initializeBalls()) {
            scene.geometries.add(item);

        }
        camera.setImageWriter(new ImageWriter("pictureForBonus", 1000, 1000))
                .setRayTracer(new ForwardRayTracer(scene))
                .renderImage()
                .writeToImage();
    }
}
