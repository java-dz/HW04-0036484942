package hr.fer.zemris.java.graphics.shapes;

/**
 * The class Rectangle extends {@linkplain AbstractQuadrangle} and is a
 * {@link GeometricShape geometric shape} that represents a rectangle. A
 * rectangle is specified by the <tt>x</tt> and <tt>y</tt> coordinates of its
 * uppermost leftmost corner and its <tt>width</tt> and <tt>height</tt>.
 * This class offers one constructor and appropriate getters and setters.
 *
 * @author Mario Bobic
 */
public class Rectangle extends AbstractQuadrangle {

    /**
     * Constructs a new instance of Rectangle with the specified <tt>x</tt> and
     * <tt>y</tt> coordinates of its uppermost leftmost corner and the specified
     * <tt>width</tt> and <tt>height</tt>.
     * <p>
     * The given <tt>x</tt> and <tt>y</tt> parameters may be any integer, while
     * the <tt>w</tt> and <tt>h</tt> parameters must be positive integers.
     *
     * @param x the x coordinate of the uppermost leftmost corner of the rectangle
     * @param y the y coordinate of the uppermost leftmost corner of the rectangle
     * @param w the width of the rectangle
     * @param h the height of the rectangle
     */
    public Rectangle(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public int getWidth() {
        return super.getWidth();
    }

    /**
     * Sets the width of the rectangle.
     *
     * @param w the width of the rectangle to be set
     */
    public void setWidth(int w) {
        super.setWidth(w);
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public int getHeight() {
        return super.getHeight();
    }

    /**
     * Sets the height of the rectangle.
     *
     * @param h the height of the rectangle to be set
     */
    public void setHeight(int h) {
        super.setHeight(h);
    }

}
