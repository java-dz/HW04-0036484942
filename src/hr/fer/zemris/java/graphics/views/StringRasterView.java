package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * A string raster view that creates a string representation of the view.
 * <p>
 * This class offers two constructors. The first constructor accepts two
 * characters: a character that represents a pixel that is <tt>on</tt> and a
 * character that represents a pixel that is <tt>off</tt>. The second
 * constructor is the default constructor that uses the <tt>*</tt> character
 * for a pixel that is <tt>on</tt>, and the <tt>.</tt> character for a pixel
 * that is <tt>off</tt>.
 *
 * @author Mario Bobic
 */
public class StringRasterView extends SimpleRasterView {

    /**
     * Constructs a new StringRasterView object with the specified characters
     * which represent a pixel that <tt>on</tt> and a pixel that is <tt>off</tt>,
     * respectively.
     *
     * @param pixelOn character representation of a pixel that is on
     * @param pixelOff character representation of a pixel that is off
     */
    public StringRasterView(char pixelOn, char pixelOff) {
        super(pixelOn, pixelOff);
    }

    /**
     * Constructs a new StringRasterView object with characters <tt>*</tt>
     * and <tt>.</tt> which represent a pixel that <tt>on</tt> and a pixel that
     * is <tt>off</tt>, respectively.
     */
    public StringRasterView() {
        super();
    }

    /**
     * Produces the view of the specified raster in a String format with the
     * <tt>pixelOn</tt> character as a representation of a pixel that is turned
     * on and the <tt>pixelOff</tt> character as a representation of a pixel that
     * is turned off.
     *
     * @param raster raster whose view is to be produced
     * @return a string representation of the view
     */
    @Override
    public Object produce(BWRaster raster) {
        return produceRaster(raster);
    }

}
