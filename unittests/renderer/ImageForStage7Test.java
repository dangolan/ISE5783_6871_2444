package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Class to render final image
 */
public class ImageForStage7Test {

    private final Scene scene = new Scene("Test scene");
    private final Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(200, 200).setVPDistance(1000) //
            .setRayTracer(new ForwardRayTracer(scene));


    /**
     * Render final image
     */
    @Test
    public void renderFinalImage() {
        createRoom();
        createLights();
        createTable();
        createChess();
        scene.setAmbientLight(new AmbientLight(new Color(white), new Double3(0.1)));
        camera.setImageWriter(new ImageWriter("FinalImage", 1000, 1000)) //
                .setRayTracer(new ForwardRayTracer(scene))
                .renderImage()
                .writeToImage();

    }

    /**
     * function creates room
     */
    public void createRoom() {

        var MaterialToRoom = new Material().setKd(0.7).setKs(0.3);

        //floor
        Point A = new Point(-70, -70, 300);
        Point B = new Point(70, -70, 300);
        Point C = new Point(70, -70, -400);
        Point D = new Point(-70, -70, -400);

        var floor = new Polygon(A, B, C, D);
        floor.setEmission(new Color(255, 218, 179)).setMaterial(MaterialToRoom);
        scene.geometries.add(floor);


        final double Y = -69.9;
        final int gridSize = 18;
        final int xOffset = -70;
        final int zOffset = 300;
        final int step = 40;

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < gridSize; j++) {
                if ((i + j) % 2 == 0) {
                    double x = i * 10 + xOffset;
                    double z = -j * step + zOffset;

                    Point p1 = new Point(x, Y, z);
                    Point p2 = new Point(x, Y, z + step);
                    Point p3 = new Point(x + 10, Y, z + step);
                    Point p4 = new Point(x + 10, Y, z);

                    Polygon polygon = new Polygon(p1, p2, p3, p4);
                    polygon.setEmission(new Color(BLACK)).setMaterial(MaterialToRoom);

                    scene.geometries.add(polygon);
                }
            }
        }
        //end of floor


        scene.geometries.add(
                //left wall
                new Polygon(new Point(-70, 70, 300), new Point(-70, -70, 300), new Point(-70, -70, -400), new Point(-70, 70, -400)).setEmission(new Color(235, 206, 91).reduce(2)) //
                        .setMaterial(MaterialToRoom),
                //ceiling
                new Polygon(new Point(70, 70, 300), new Point(-70, 70, 300), new Point(-70, 70, -400), new Point(70, 70, -400)).setEmission(new Color(223, 209, 163).reduce(3)) //
                        .setMaterial(MaterialToRoom.setShininess(100)),
                //right wall
                new Polygon(new Point(70, -70, 300), new Point(70, 70, 300), new Point(70, 70, -400), new Point(70, -70, -400)).setEmission(new Color(235, 206, 91).reduce(2)) //
                        .setMaterial(MaterialToRoom.setShininess(100)),
                // back wall
                new Polygon(new Point(70, 70, -400), new Point(-70, 70, -400), new Point(-70, -70, -400), new Point(70, -70, -400)).setEmission(new Color(235, 206, 91).reduce(2.5)) //
                        .setMaterial(new Material().setKs(0.3).setKr(0.5).setShininess(100))

        );
    }

    /**
     * function creates lights
     */
    public void createLights() {

        scene.setBackground(new Color(WHITE));

        Point lamp1Case = new Point(-20, 67, 60);
        Point lamp2Case = new Point(20, 67, 300);
        Point lamp1 = new Point(-20, 67, 60);
        Point lamp2 = new Point(20, 67, 300);

        scene.lights.add(new SpotLight(new Color(255, 100, 100).reduce(3), lamp1Case, new Vector(0, -1, 0.25)));
        scene.lights.add(new PointLight(new Color(255, 100, 100).reduce(3), lamp1Case));

        scene.lights.add(new SpotLight(new Color(255, 100, 100).reduce(3), lamp2Case, new Vector(0, -1, -0.25)));
        scene.lights.add(new PointLight(new Color(255, 100, 100).reduce(3), lamp2Case));

        scene.lights.add(new DirectionalLight(new Color(255, 100, 100).reduce(6), new Vector(0, -0.3, -1)));

        scene.geometries.add(new Sphere(lamp1, 3).setEmission(new Color(white)).setMaterial(new Material().setKt(0.9).setShininess(100)));

        scene.geometries.add(new Sphere(lamp2, 3).setEmission(new Color(white)).setMaterial(new Material().setKt(0.9).setShininess(100)));

        scene.geometries.add(new Sphere(lamp1Case, 7).setEmission(new Color(255, 100, 100)).setMaterial(new Material().setKt(0.6).setShininess(100)));

        scene.geometries.add(new Sphere(lamp2Case, 7).setEmission(new Color(255, 100, 100)).setMaterial(new Material().setKt(0.6).setShininess(100)));

//        scene.geometries.add(new Cylinder(40, new Ray(lamp1Case.add(new Vector(0, 7, 0)), new Vector(0, 1, 0)), 1).setEmission(new Color(153, 76, 0)).setMaterial(new Material().setKs(1).setKd(0.4).setShininess(100)));
//
//        scene.geometries.add(new Cylinder(40, new Ray(lamp2Case.add(new Vector(0, 7, 0)), new Vector(0, 1, 0)), 1).setEmission(new Color(153, 76, 0)).setMaterial(new Material().setKs(1).setKd(0.4).setShininess(100)));


    }

    /**
     * function creates table
     */
    public void createTable() {
        Point A = new Point(-20, -20, 600);
        Point B = new Point(-20, -20, 100);
        Point C = new Point(20, -20, 100);
        Point D = new Point(20, -20, 600);

        Point E = new Point(-20, -23, 600);
        Point F = new Point(-20, -23, 100);
        Point G = new Point(20, -23, 100);
        Point H = new Point(20, -23, 600);

        scene.geometries.add(new Polygon(A, B, C, D).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Polygon(E, F, G, H).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Polygon(A, B, F, E).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Polygon(C, D, H, G).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Polygon(A, E, H, D).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Polygon(B, F, G, C).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3))

        );

        //add table feet
        scene.geometries.add(new Cylinder(100, new Ray(E.add(new Vector(5, 0, -5)), new Vector(0, -1, 0)), 1).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Cylinder(100, new Ray(F.add(new Vector(5, 0, 1)), new Vector(0, -1, 0)), 1).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Cylinder(100, new Ray(G.add(new Vector(-5, 0, 10)), new Vector(0, -1, 0)), 1).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3)), new Cylinder(100, new Ray(H.add(new Vector(-5, 0, -5)), new Vector(0, -1, 0)), 1).setEmission(new Color(75, 61, 4)).setMaterial(new Material().setKd(0.7).setKs(0.3))

        );


    }

    /**
     * function creates chess board
     */
    public void createChess() {
        Point A = new Point(-10, -19.9, 500);
        Point B = new Point(-10, -19.9, 300);
        Point C = new Point(10, -19.9, 300);
        Point D = new Point(10, -19.9, 500);

        scene.geometries.add(
                //white part of board
                new Polygon(A, B, C, D).setEmission(new Color(WHITE))//
                        .setMaterial(new Material().setKd(0.7).setKs(0.3))

        );

        //create board squares
        double y = -19.8;
        for (double i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    scene.geometries.add(new Polygon(new Point(i * 2.5 - 10, y, j * 25 + 300), new Point(i * 2.5 - 10, y, j * 25 + 300 + 25), new Point(i * 2.5 + 2.5 - 10, y, j * 25 + 300 + 25), new Point(i * 2.5 + 2.5 - 10, y, j * 25 + 300)).setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.7).setKs(0.3)));
                }

            }
        }

        //create black board pieces
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                scene.geometries.add(new Sphere(new Point(i * 2.5 - 11.25, y, j * 25 + 300 + 13.75), 0.8).setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.7).setKs(0.3)));

            }
        }

        //create white board pieces
        for (int i = 7; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                scene.geometries.add(new Sphere(new Point(i * 2.5 - 11.25, y, j * 25 + 300 + 13.75), 0.8).setEmission(new Color(235, 206, 91)).setMaterial(new Material().setKd(0.7).setKs(0.3)));

            }
        }


    }
}
