package hr.fer.zemris.java.graphics.shapes;

/**
 * The class Circle extends {@linkplain AbstractOval} and is a
 * {@link GeometricShape geometric shape} that represents a circle. A circle is
 * specified by the <tt>x</tt> and <tt>y</tt> coordinates of its center and its
 * <tt>radius</tt>. This class offers one constructor and appropriate getters
 * and setters.
 *
 * @author Mario Bobic
 */
public class Circle extends AbstractOval {

    /**
     * Constructs a new instance of Circle with the specified <tt>x</tt> and
     * <tt>y</tt> coordinates of its center and the specified <tt>radius</tt>.
     *
     * @param cx the x coordinate of the center
     * @param cy the y coordinate of the center
     * @param radius the radius of the circle
     */
    public Circle(int cx, int cy, int radius) {
        super(cx, cy, radius, radius);
    }

    /**
     * Returns the radius of the circle.
     *
     * @return the radius of the circle
     */
    public int getRadius() {
        return super.getRadiusX();
    }

    /**
     * Sets the radius of the circle.
     *
     * @param radius the radius of the circle to be set
     */
    public void setRadius(int radius) {
        super.setRadiusX(radius);
        super.setRadiusY(radius);
    }

}
