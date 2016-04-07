package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The abstract superclass from which all geometric shapes should be derived.
 * The default methods for geometric shapes are for drawing the shape itself and
 * for checking if the shape contains a specific point. The implementation of
 * the {@linkplain #draw(BWRaster)} is the assumed drawing method for all
 * geometric shapes and it is advised that a subclass overrides this method.
 * This extending classes should provide the
 * {@linkplain #containsPoint(int, int)} implementation, as the abstract
 * geometric shape itself is unable to determine if it contains a point.
 *
 * @author Mario Bobic
 * @see AbstractQuadrangle
 * @see Rectangle
 * @see Square
 * @see AbstractOval
 * @see Ellipse
 * @see Circle
 */
public abstract class GeometricShape {

	/**
	 * This method draws a filled image of the geometric shape (not only its
	 * outline). The default method is the assumed drawing method for all
	 * geometric shapes that checks the whole raster of points. It is
	 * recommended that all subclasses override this method.
	 * 
	 * @param r the raster on which points are drawn
	 */
	public void draw(BWRaster r) {
		int width = r.getWidth();
		int height = r.getHeight();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (this.containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}
	
	/**
	 * Checks if specified (x, y) point belongs to this geometric shape and
	 * returns a <tt>boolean</tt> value of the result. The general contract for
	 * this method is that it must return false only if the location is outside
	 * of the geometric shape. Otherwise it must return true.
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return true if the specified point belongs to the geometric shape 
	 */
	public abstract boolean containsPoint(int x, int y);

}
