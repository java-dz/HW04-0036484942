package hr.fer.zemris.java.graphics.raster;

/**
 * This class is an implementation for all raster devices of fixed width and
 * height for which each pixel can be painted with only two colors: black (when
 * pixel is turned off) and white (when pixel is turned on).
 *
 * @author Mario Bobic
 */
public class BWRasterMem implements BWRaster {
	
	/** The width dimension of this raster. */
	private int width;
	/** The height dimension of this raster. */
	private int height;
	/**
	 * A two-dimensional array that has state <tt>true</tt> when a pixel at
	 * <tt>[x][y]</tt> coordinate is turned on and <tt>false</tt> if not.
	 */
	private boolean[][] pixel;
	/** True if the flip mode is on, false if it is off. */
	private boolean flipped;
	
	/**
	 * Constructs a new instance of BWRasterMem with the specified width and
	 * height. Both width and height must be greater than 0.
	 * All pixels are initially turned off.
	 * 
	 * @param width the width dimension of this raster
	 * @param height the height dimension of this raster
	 */
	public BWRasterMem(int width, int height) {
		checkSize(width, "Invalid width: " + width);
		checkSize(height, "Invalid height: " + height);
		
		this.width = width;
		this.height = height;
		pixel = new boolean[width][height];
	}
	
	/**
	 * Checks if the size given to the constructor is less than <tt>1</tt> and
	 * throws an {@linkplain IllegalArgumentException} with the specified
	 * <tt>message</tt> if the test returns true.
	 * 
	 * @param size width or height given to the constructor
	 * @param message the detail message for creating the exception
	 * @throws IllegalArgumentException if <tt>size &lt; 1</tt>
	 */
	private static void checkSize(int size, String message) {
		if (size < 1) {
			throw new IllegalArgumentException(message);
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void clear() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixel[i][j] = false;
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		checkPixel(x, y);
		pixel[x][y] = flipped ? !pixel[x][y] : true;
	}

	@Override
	public void turnOff(int x, int y) {
		checkPixel(x, y);
		pixel[x][y] = false;
	}

	@Override
	public void enableFlipMode() {
		flipped = true;
	}

	@Override
	public void disableFlipMode() {
		flipped = false;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		checkPixel(x, y);
		return pixel[x][y];
	}
	
	/**
	 * Checks if the <tt>x</tt> and <tt>y</tt> coordinates are <b>not</b> in
	 * limits of the raster, or more formally, if:
	 * <tt>x &lt; 0 || x &gt;= width</tt> or
	 * <tt>y &lt; 0 || y &gt;= height</tt>
	 * and throws an {@linkplain IllegalArgumentException} if the test returns
	 * true.
	 * <p>
	 * This method is used by a variety of other methods in this class to check
	 * if the specified pixel coordinates are valid.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @throws IllegalArgumentException if <tt>size &lt; 1</tt>
	 */
	private void checkPixel(int x, int y) {
		if (   x < 0 || x >= width
			|| y < 0 || y >= height) {
			throw new IllegalArgumentException(
				"Invalid pixel: (" + x + ", " + y + ")");
		}
	}

}
