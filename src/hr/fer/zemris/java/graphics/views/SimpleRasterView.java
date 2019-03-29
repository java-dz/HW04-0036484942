package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * A simple raster view that prints the raster drawing on the screen when the
 * {@linkplain #produce(BWRaster)} method is called.
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
public class SimpleRasterView implements RasterView {

    /** The default character representation of a pixel that is on. */
    public static final char DEFAULT_ON = '*';
    /** The default character representation of a pixel that is off. */
    public static final char DEFAULT_OFF = '.';

    /** The user-specified character representation of a pixel that is on. */
    private final char pixelOn;
    /** The user-specified character representation of a pixel that is off. */
    private final char pixelOff;

    /**
     * Constructs a new SimpleRasterView object with the specified characters
     * which represent a pixel that <tt>on</tt> and a pixel that is <tt>off</tt>,
     * respectively.
     *
     * @param pixelOn character representation of a pixel that is on
     * @param pixelOff character representation of a pixel that is off
     */
    public SimpleRasterView(char pixelOn, char pixelOff) {
        this.pixelOn = pixelOn;
        this.pixelOff = pixelOff;
    }

    /**
     * Constructs a new SimpleRasterView object with characters <tt>*</tt>
     * and <tt>.</tt> which represent a pixel that <tt>on</tt> and a pixel that
     * is <tt>off</tt>, respectively.
     */
    public SimpleRasterView() {
        this(DEFAULT_ON, DEFAULT_OFF);
    }

    /**
     * Produces the view of the specified raster and prints it out on the
     * screen. This method returns <tt>null</tt> as the produced view has been
     * printed out on the screen.
     *
     * @param raster raster whose view is to be produced
     * @return <tt>null</tt>
     */
    @Override
    public Object produce(BWRaster raster) {
        System.out.println(produceRaster(raster));
        return null;
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
    protected String produceRaster(BWRaster raster) {
        int width = raster.getWidth();
        int height = raster.getHeight();
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(raster.isTurnedOn(x, y) ? pixelOn : pixelOff);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

}
