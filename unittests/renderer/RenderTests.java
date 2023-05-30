package renderer;

import org.junit.jupiter.api.Test;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 */
public class RenderTests {

   /**
    * Produce a scene with basic 3D model and render it into a png image with a grid
    */
   @Test
   public void basicRenderTwoColorTest() {
       Scene scene = new Scene("Test scene")
               .setAmbientLight(new AmbientLight(new Color(255, 191, 191), new Double3(1, 1, 1)))
               .setBackground(new Color(75, 127, 90));
       scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
               new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)),
               new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)),
               new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)));
       Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
               .setVPDistance(100).setVPSize(500, 500)
               .setImageWriter(new ImageWriter("base render test", 1000, 1000)).setRayTracer(new RayTracerBasic(scene));
       camera.renderImage();
       camera.printGrid(100, new Color(YELLOW));
       camera.writeToImage();
   }
    /**
     * Performs a basic multicolor rendering test.
     * Renders a scene with different colored geometries using a camera.
     * The scene includes a sphere and three triangles with different missive colors.
     * The rendered image is saved to an image file.
     * <p>The camera is positioned at the origin, looking towards the negative z-axis,
     * with the up direction along the positive y-axis. The view plane is located at a distance
     * of 100 units from the camera, with a size of 500x500 units. The image resolution is set to
     * 1000x1000 pixels.</p>
     * <p>This test verifies the correct rendering of the scene and the generation of the output image.</p>
     */
    @Test
    public void basicRenderMultiColorTest() {
    Scene scene = new Scene("Test scene")//
    .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2)));
    scene.geometries.add(new Sphere(new Point(0, 0, -100), 50),
            new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new
    Point(-100, 100, -100)).setEmission(new Color(GREEN)),
    new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new
    Point(-100, -100, -100)).setEmission(new Color(RED)),
    new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
    Point(100, -100, -100)).setEmission(new Color(BLUE)));
    Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
    .setVPDistance(100).setVPSize(500, 500).setImageWriter(new ImageWriter("color render test", 1000, 1000))
    .setRayTracer(new RayTracerBasic(scene));
    camera.renderImage();
    camera.printGrid(100, new Color(WHITE));camera.writeToImage();
    }

    /* Test for XML based scene - for bonus
   @Test
   public void basicRenderXml() {
      Scene  scene  = new Scene("XML Test scene");
      // enter XML file name and parse from XML file into scene object
      // using the code you added in appropriate packages
      // ...
      // NB: unit tests is not the correct place to put XML parsing code

      Camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))     //
         .setVPDistance(100)                                                                //
         .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
         .setRayTracer(new RayTracerBasic(scene));
      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }
   */
}
