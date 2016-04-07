package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The abstract superclass that represents a quadrangle which can be specified
 * by the <tt>x</tt> and <tt>y</tt> coordinates of its uppermost leftmost corner
 * and its <tt>width</tt> and <tt>height</tt>.
 * <p>
 * This class offers a <tt>protected</tt> constructor with the arguments as
 * specified above and appropriate getter and setter methods.
 * <p>
 * Note that some methods are declared <tt>public</tt> as they make sense to be
 * used by all subclasses of this class, and some are <tt>protected</tt> as only
 * the usage of some subclasses can have sense.
 *
 * @author Mario Bobic
 * @see Rectangle
 * @see Square
 */
public abstract class AbstractQuadrangle extends GeometricShape {

	/** The x coordinate of the uppermost leftmost corner of the quadrangle. */
	protected int x;
	/** The y coordinate of the uppermost leftmost corner of the quadrangle. */
	protected int y;
	/** The width of the quadrangle. */
	protected int w;
	/** The height of the quadrangle. */
	protected int h;
	
	/**
	 * Initializes the specified <tt>x</tt> and <tt>y</tt> coordinates of its
	 * uppermost leftmost corner and the specified <tt>width</tt> and
	 * <tt>height</tt> of this quadrangle.
	 * 
	 * @param x the x coordinate of the uppermost leftmost corner of the quadrangle
	 * @param y the y coordinate of the uppermost leftmost corner of the quadrangle
	 * @param w the width of the quadrangle
	 * @param h the height of the quadrangle
	 */
	protected AbstractQuadrangle(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		setWidth(w);
		setHeight(h);
	}

	/**
	 * Returns the x coordinate of the uppermost leftmost corner of the
	 * quadrangle.
	 * 
	 * @return the x coordinate of the uppermost leftmost corner
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the uppermost leftmost corner of the quadrangle.
	 * 
	 * @param x the x coordinate of the uppermost leftmost corner to be set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate of the uppermost leftmost corner of the
	 * quadrangle.
	 * 
	 * @return the y coordinate of the uppermost leftmost corner
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the uppermost leftmost corner of the quadrangle.
	 * 
	 * @param y the y coordinate of the uppermost leftmost corner to be set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the width of the quadrangle.
	 * 
	 * @return the width of the quadrangle
	 */
	protected int getWidth() {
		return w;
	}

	/**
	 * Sets the width of the quadrangle.
	 * 
	 * @param w the width of the quadrangle to be set
	 */
	protected void setWidth(int w) {
		this.w = processSize(w);
	}

	/**
	 * Returns the height of the quadrangle.
	 * 
	 * @return the height of the quadrangle
	 */
	protected int getHeight() {
		return h;
	}

	/**
	 * Sets the height of the quadrangle.
	 * 
	 * @param h the height of the quadrangle to be set
	 */
	protected void setHeight(int h) {
		this.h = processSize(h);
	}
	
	/**
	 * Checks if the <tt>size</tt> dimension is greater than zero, since the
	 * <tt>width</tt> and <tt>height</tt> parameters must be positive integers.
	 * Throws an {@linkplain IllegalArgumentException} if the test returns true
	 * or returns the processed <tt>size</tt> parameter if all goes successfully.
	 * 
	 * @param size the specified dimension
	 * @return the processed <tt>size</tt> parameter if all goes successfully
	 */
	private static int processSize(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Invalid dimension: " + size);
		}
		return size;
	}
	
	/**
	 * Draws a filled image of the object derived from this class starting
	 * either from the uppermost leftmost corner or 0 for the coordinate that is
	 * negative and ending either on the lowermost rightmost corner or
	 * {@link BWRaster#getWidth() r.getWidth()} / {@link BWRaster#getHeight()
	 * r.getHeight()} for the coordinate that is outside of raster bounds.
	 */
	@Override
	public void draw(BWRaster r) {
		int startX = Math.max(x, 0);
		int startY = Math.max(y, 0);
		
		int endX = Math.min(x + w, r.getWidth());
		int endY = Math.min(y + h, r.getHeight());
		
		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				r.turnOn(x, y);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Returns true if the point specified by <tt>x</tt> and <tt>y</tt>
	 * coordinates belong to the quadrangle.
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x) return false;
		if (y < this.y) return false;
		if (x >= this.x+w) return false;
		if (y >= this.y+h) return false;

		return true;
	}

}
