package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.MissingResourceException;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * a class that represents a camera.
 */
public class Camera {
    private final Point p0;
    private final Vector vUp;
    private final Vector vTo;
    private final Vector vRight;
    private double width;
    private double height;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    @SuppressWarnings("unused")
    public Point getP0() {
        return p0;
    }
    @SuppressWarnings("unused")
    public Vector getVUp() {
        return vUp;
    }
    @SuppressWarnings("unused")
    public Vector getVTo() {
        return vTo;
    }
    @SuppressWarnings("unused")
    public Vector getVRight() {
        return vRight;
    }
    @SuppressWarnings("unused")
    public double getWidth() {
        return width;
    }
    @SuppressWarnings("unused")
    public double getHeight() {
        return height;
    }
    @SuppressWarnings("unused")
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the image writer for the camera.
     * @param imageWriter the image writer to set
     * @return a reference to this Camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the viewport size (width and height) for the camera.
     * @param width the width of the viewport
     * @param height the height of the viewport
     * @return a reference to this Camera object
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance from the camera to the viewport.
     * @param distance the distance to set
     * @return a reference to this Camera object
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Sets the ray tracer for the camera.
     * @param rayTracer the ray tracer to set
     * @return a reference to this Camera object
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Constructs a camera with the specified parameters.
     * @param p0 the camera position
     * @param vTo the direction the camera is facing
     * @param vUp the up direction of the camera
     * @throws IllegalArgumentException if vUp and vTo are not orthogonal
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("constructor threw - vUp and vTo are not orthogonal");
        }
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * constructs a ray from the camera through pixel i,j.
     * @param nX number of pixels on the width of the view plane.
     * @param nY number of pixels on the height of the view plane.
     * @param j  location of the pixel in the X direction.
     * @param i  location of the pixel in the Y direction.
     * @return the constructed ray - from p0 through the wanted pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));
        double ry = height / nY;
        double rx = width / nX;
        double yJ = alignZero(-(i - (nY - 1) / 2d) * ry);
        double xJ = alignZero((j - (nX - 1) / 2d) * rx);

        Point pij = pc;
        if (!isZero(xJ)) pij = pij.add(vRight.scale(xJ));
        if (!isZero(yJ)) pij = pij.add(vUp.scale(yJ));
        return new Ray(p0, pij.subtract(p0));
    }

    /**
     * render the image and fill the pixels with the desired colors
     * using the ray tracer to find the colors
     * and the image writer to color the pixels
     * @throws MissingResourceException if one of the fields are uninitialized
     */
    public Camera renderImage() {
        if (imageWriter == null || rayTracer == null || width == 0 || height == 0 || distance == 0) {
            throw new MissingResourceException("Camera is missing some fields", "Camera", "field");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(j, i,
                        rayTracer.traceRay(
                                constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i)));
            }
        }
        return this;
    }

    /**
     * print a grid on the image without running over the original image
     * @param interval the size of the grid squares
     * @param color    the color of the grid
     * @throws MissingResourceException throws when Camera is missing some fields
     */
    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null)
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        for (int i = 0; i < imageWriter.getNy(); i++)
            for (int j = 0; j < imageWriter.getNx(); j++)
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
    }

    /**
     * create the image file using the image writer
     */
    public void writeToImage() {
        if (this.imageWriter == null)
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        imageWriter.writeToImage();
    }

}