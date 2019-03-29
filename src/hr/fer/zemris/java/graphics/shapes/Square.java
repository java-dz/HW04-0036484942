package hr.fer.zemris.java.graphics.shapes;

/**
 * The class Square extends {@linkplain AbstractQuadrangle} and is a
 * {@link GeometricShape geometric shape} that represents a square. A
 * square is specified by the <tt>x</tt> and <tt>y</tt> coordinates of its
 * uppermost leftmost corner and its <tt>size</tt>.
 * This class offers one constructor and appropriate getters and setters.
 *
 * @author Mario Bobic
 */
public class Square extends AbstractQuadrangle {

    /**
     * Constructs a new instance of Square with the specified <tt>x</tt> and
     * <tt>y</tt> coordinates of its uppermost leftmost corner and the specified
     * <tt>size</tt>.
     * <p>
     * The given <tt>x</tt> and <tt>y</tt> parameters may be any integer, while
     * the <tt>size</tt> parameter must be a positive integer.
     *
     * @param x the x coordinate of the uppermost leftmost corner of the square
     * @param y the y coordinate of the uppermost leftmost corner of the square
     * @param size the size of the square
     */
    public Square(int x, int y, int size) {
        super(x, y, size, size);
    }

    /**
     * Returns the size of the square.
     *
     * @return the size of the square
     */
    public int getSize() {
        return super.getWidth();
    }

    /**
     * Sets the size of the square.
     *
     * @param size the size of the square to be set
     */
    public void setSize(int size) {
        super.setWidth(size);
        super.setHeight(size);
    }

}
