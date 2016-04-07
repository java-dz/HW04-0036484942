package hr.fer.zemris.java.graphics.raster;

/**
 * This interface is an abstraction for all raster devices of fixed width and
 * height for which each pixel can be painted with only two colors: black (when
 * pixel is turned off) and white (when pixel is turned on).
 * <p>
 * Classes that implement this interface should offer appropriate methods for
 * handling and creating a raster image as specified below.
 *
 * @author Mario Bobic
 */
public interface BWRaster {

	/**
	 * Returns the width dimension of the raster.
	 * 
	 * @return the width dimension of the raster
	 */
	int getWidth();
	
	/**
	 * Returns the height dimension of the raster.
	 * 
	 * @return the height dimension of the raster
	 */
	int getHeight();
	
	/**
	 * Turns off <b>all</b> pixels in the raster. By the time this method ends,
	 * <tt>width*height</tt> pixels would be turned off.
	 */
	void clear();
	
	/**
	 * Turns on a <b>single</b> pixel specified by the <tt>x</tt> and <tt>y</tt>
	 * coordinates. The <tt>x</tt> and <tt>y</tt> coordinates must be in limits
	 * of the raster, or more formally:
	 * <tt>x &gt;= 0 &amp;&amp; x &lt; width</tt> and
	 * <tt>y &gt;= 0 &amp;&amp; y &lt; height</tt>
	 * <p>
	 * If the pixel is not within raster limits, this method throws an
	 * {@linkplain IllegalArgumentException}.
	 * <p>
	 * If the <i>flip mode</i> is on, this method <b>flips</b> or <b>toggles</b>
	 * the pixel at the specified coordinates.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @throws IllegalArgumentException if the pixel is not within raster limits
	 * @see #enableFlipMode()
	 * @see #disableFlipMode()
	 */
	void turnOn(int x, int y);
	
	/**
	 * Turns off a <b>single</b> pixel specified by the <tt>x</tt> and <tt>y</tt>
	 * coordinates. The <tt>x</tt> and <tt>y</tt> coordinates must be in limits
	 * of the raster, or more formally:
	 * <tt>x &gt;= 0 &amp;&amp; x &lt; width</tt> and
	 * <tt>y &gt;= 0 &amp;&amp; y &lt; height</tt>
	 * <p>
	 * If the pixel is not within raster limits, this method throws an
	 * {@linkplain IllegalArgumentException}.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @throws IllegalArgumentException if the pixel is not within raster limits
	 */
	void turnOff(int x, int y);
	
	/**
	 * Enables the flip mode of the raster.
	 * <p>
	 * By enabling the flip mode, the call of the {@linkplain #turnOn} method
	 * flips (toggles) the pixel at the specified location (if it was turned on,
	 * it must be turned off, and if it was turned off, it must be turned on).
	 * <p>
	 * Calling this method repeatedly has no effect, as the first call already
	 * set the flip mode to enabled.
	 */
	void enableFlipMode();
	
	/**
	 * Disables the flip mode of the raster.
	 * <p>
	 * By disabling the flip mode, the effects of {@linkplain #enableFlipMode()}
	 * are aborted in future sense. The call of the {@linkplain #turnOn} method
	 * restores back to setting its specified pixel exclusively on.
	 * <p>
	 * Calling this method repeatedly has no effect, as the first call already
	 * set the flip mode to disabled.
	 */
	void disableFlipMode();
	
	/**
	 * Returns true if the pixel specified by the x and y coordinates is turned
	 * on. False otherwise.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @return true if the specified pixel is turned on
	 */
	boolean isTurnedOn(int x, int y);

}
