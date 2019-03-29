package hr.fer.zemris.java.graphics.shapes;

/**
 * The class Ellipse extends {@linkplain AbstractOval} and is a
 * {@link GeometricShape geometric shape} that represents an ellipse. An ellipse
 * is specified by the <tt>x</tt> and <tt>y</tt> coordinates of its center and
 * its <tt>horizontal</tt> and <tt>vertical</tt> radius. This class offers one
 * constructor and appropriate getters and setters.
 *
 * @author Mario Bobic
 */
public class Ellipse extends AbstractOval {

    /**
     * Constructs a new instance of Ellipse with the specified <tt>x</tt> and
     * <tt>y</tt> coordinates of its center and the specified horizontal
     * (<tt>rx</tt>) and vertical (<tt>ry</tt>) radius.
     *
     * @param cx the x coordinate of the center
     * @param cy the y coordinate of the center
     * @param rx the horizontal radius of the ellipse
     * @param ry the vertical radius of the ellipse
     */
    public Ellipse(int cx, int cy, int rx, int ry) {
        super(cx, cy, rx, ry);
    }

    /**
     * Returns the horizontal radius of the ellipse.
     *
     * @return the horizontal radius of the ellipse
     */
    protected int getRadiusX() {
        return super.getRadiusX();
    }

    /**
     * Sets the horizontal radius of the ellipse.
     *
     * @param rx the horizontal radius of the ellipse to be set
     */
    protected void setRadiusX(int rx) {
        super.setRadiusX(rx);
    }

    /**
     * Returns the vertical radius of the ellipse.
     *
     * @return the vertical radius of the ellipse
     */
    protected int getRadiusY() {
        return super.getRadiusY();
    }

    /**
     * Sets the vertical radius of the ellipse.
     *
     * @param ry the vertical radius of the ellipse to be set
     */
    protected void setRadiusY(int ry) {
        super.setRadiusY(ry);
    }

}
