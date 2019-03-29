package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The abstract superclass that represents an oval which can be specified by the
 * <tt>x</tt> and <tt>y</tt> coordinates of its center and its
 * <tt>horizontal</tt> and <tt>vertical</tt> radius.
 * <p>
 * This class offers a <tt>protected</tt> constructor with the arguments as
 * specified above and appropriate getter and setter methods.
 * <p>
 * Note that some methods are declared <tt>public</tt> as they make sense to be
 * used by all subclasses of this class, and some are <tt>protected</tt> as only
 * the usage of some subclasses can have sense.
 *
 * @author Mario Bobic
 * @see Ellipse
 * @see Circle
 */
public abstract class AbstractOval extends GeometricShape {

    /** The x coordinate of the center. */
    protected int cx;
    /** The y coordinate of the center. */
    protected int cy;
    /** The horizontal radius of the oval. */
    protected int rx;
    /** The vertical radius of the oval. */
    protected int ry;

    /**
     * Initializes the specified <tt>x</tt> and <tt>y</tt> coordinates of its
     * center and the specified horizontal (<tt>rx</tt>) radius and vertical (
     * <tt>ry</tt>) radius of the oval.
     *
     * @param cx the x coordinate of the center
     * @param cy the y coordinate of the center
     * @param rx the horizontal radius of the oval
     * @param ry the vertical radius of the oval
     */
    protected AbstractOval(int cx, int cy, int rx, int ry) {
        super();
        this.cx = cx;
        this.cy = cy;
        setRadiusX(rx);
        setRadiusY(ry);
    }

    /**
     * Returns the x coordinate of the center.
     *
     * @return the x coordinate of the center
     */
    public int getCenterX() {
        return cx;
    }

    /**
     * Sets the x coordinate of the center.
     *
     * @param cx the x coordinate of the center to be set
     */
    public void setCenterX(int cx) {
        this.cx = cx;
    }

    /**
     * Returns the y coordinate of the center.
     *
     * @return the y coordinate of the center
     */
    public int getCenterY() {
        return cy;
    }

    /**
     * Sets the y coordinate of the center.
     *
     * @param cy the y coordinate of the center to be set
     */
    public void setCenterY(int cy) {
        this.cy = cy;
    }

    /**
     * Returns the horizontal radius of the oval.
     *
     * @return the horizontal radius of the oval
     */
    protected int getRadiusX() {
        return rx;
    }

    /**
     * Sets the horizontal radius of the oval.
     *
     * @param rx the horizontal radius of the oval to be set
     */
    protected void setRadiusX(int rx) {
        this.rx = processRadius(rx);
    }

    /**
     * Returns the vertical radius of the oval.
     *
     * @return the vertical radius of the oval
     */
    protected int getRadiusY() {
        return ry;
    }

    /**
     * Sets the vertical radius of the oval.
     *
     * @param ry the vertical radius of the oval to be set
     */
    protected void setRadiusY(int ry) {
        this.ry = processRadius(ry);
    }

    /**
     * Checks if the <tt>radius</tt> is greater than zero, since the negative
     * radius does not make sense as it must be a positive integer.
     * Throws an {@linkplain IllegalArgumentException} if the test returns true
     * or returns the processed <tt>radius</tt> parameter if all goes successfully.
     *
     * @param radius the specified radius
     * @return the processed <tt>radius</tt> parameter if all goes successfully
     */
    private static int processRadius(int radius) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must not less than 1.");
        }
        return radius;
    }

    /**
     * Draws a filled image of the object derived from this class. By pretending
     * this object is a rectangle, the drawing starts either from the uppermost
     * leftmost corner or 0 for the coordinate that is negative and ends either
     * on the lowermost rightmost corner or {@link BWRaster#getWidth()
     * r.getWidth()} / {@link BWRaster#getHeight() r.getHeight()} for the
     * coordinate that is outside of raster bounds.
     */
    public void draw(BWRaster r) {
        int startX = Math.max(cx - rx, 0);
        int startY = Math.max(cy - ry, 0);

        int endX = Math.min(cx + rx +1, r.getWidth());
        int endY = Math.min(cy + ry +1, r.getHeight());

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                if (this.containsPoint(x, y)) {
                    r.turnOn(x, y);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns true if the point specified by <tt>x</tt> and <tt>y</tt>
     * coordinates belong to the oval.
     */
    @Override
    public boolean containsPoint(int x, int y) {
        double arg1 = 1.0*(x - cx)*(x - cx) / (rx*rx);
        double arg2 = 1.0*(y - cy)*(y - cy) / (ry*ry);
        return (arg1 + arg2) <= 1;
    }

}
