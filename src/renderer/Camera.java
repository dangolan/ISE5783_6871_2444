package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera {
    /**
     * a class that represents a camera.
     */
    private final Point p0;
    private final Vector vUp;
    private final Vector vTo;
    private final Vector vRight;
    private double width;
    private double height;
    private double distance;

    //region getters
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
     * Constructs a camera with the specified position and orientation vectors.
     *
     * @param p0  The position of the camera.
     * @param vTo The direction vector pointing towards the target.
     * @param vUp The up vector indicating the camera's vertical orientation.
     * @throws IllegalArgumentException If the vTo and vUp vectors are not orthogonal.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("constructor threw - vUp and vTo are not orthogonal");

        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Sets the width and height of the viewport.
     *
     * @param width  The width of the viewport.
     * @param height The height of the viewport.
     * @return This Camera object.
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    //endregion

    /**
     * Sets the distance between the camera and the viewport.
     *
     * @param distance The distance between the camera and the viewport.
     * @return This Camera object.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }
    //endregion

    //region constructRay

    /**
     * constructs a ray from the camera through pixel i,j.
     *
     * @param nX number of pixels on the width of the view plane.
     * @param nY number of pixels on the height of the view plane.
     * @param j  location of the pixel in the X direction.
     * @param i  location of the pixel in the Y direction.
     * @return the constructed ray - from p0 through the wanted pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));     // center of the view plane
        double ry = height / nY;                      // Ratio - pixel height
        double rx = width / nX;                       // Ratio - pixel width

        double yJ = alignZero(-(i - (nY - 1) / 2d) * ry);       // move pc Yi pixels
        double xJ = alignZero((j - (nX - 1) / 2d) * rx);        // move pc Xj pixels

        Point pij = pc;
        if (!isZero(xJ)) pij = pij.add(vRight.scale(xJ));
        if (!isZero(yJ)) pij = pij.add(vUp.scale(yJ));

        return new Ray(p0, pij.subtract(p0));
    }
    //endregion
}