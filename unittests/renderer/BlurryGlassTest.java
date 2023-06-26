package renderer;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.white;

public class BlurryGlassTest {
    @Test
    public void testBlurryGlass() {

        Scene scene = new Scene("pictureForBonus").setBackground(new Color(0, 0, 0));
        Vector vTo = new Vector(0, 1, 0);
        Camera camera = new Camera(new Point(0, -500, 0).add(vTo.scale(-13)), vTo, new Vector(0, 0, 1))
                .setVPSize(200d, 200).setVPDistance(1000);
        ;
        Material material1 = new Material().setKd(0.4).setKs(1).setShininess(40).setKt(0).setKr(0);
        Material material = new Material().setKd(0.4).setKs(1).setShininess(50).setKt(0).setKr(0.5).setKs(0.5);
        scene.setAmbientLight(new AmbientLight(new Color(gray).reduce(2), new Double3(0.15)));


        for (int i = -6; i < 10; i += 4) {
            scene.geometries.add(
                    new Sphere(new Point(5 * i, 5, 3),5).setMaterial(material1).setEmission(new Color(102, 178, 255)),
                    new Polygon(new Point(5 * i - 4, -5, -11), new Point(5 * i - 4, -5, 5), new Point(5 * i + 4, -5, 5),
                            new Point(5 * i + 4, -5, -11)).setEmission(new Color(250, 235, 215).reduce(2.5))
                            .setMaterial(new Material().setKd(0.001).setKs(0.002).setShininess(1).setKt(0.95)
                                    .setBlurGlass(i == 6 ? 1 : 20, 0.3 * (i + 9), 1))

            );
        }

        Plane pln = new Plane(new Point(0, 0, -11), new Vector(0, 0, 1));

        pln.setMaterial(material).setEmission(new Color(0, 0, 0));
        scene.geometries.add(pln);
        scene.lights.add(new DirectionalLight(new Color(white).reduce(1.3), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(white).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKl(0.6));

        ImageWriter imageWriter = new ImageWriter("blurryGlass", 2000, 2000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new ForwardRayTracer(scene)) //
                .renderImage() //
                .writeToImage();

    }
}
