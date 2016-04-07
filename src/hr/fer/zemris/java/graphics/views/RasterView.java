package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This interface is an abstraction for all raster views.
 * <p>
 * Raster views can help defer the cost of precise drawing view recomputing when
 * editing a large model. For example, opening a drawing document and converting
 * all views to raster, the drawing document displays the changes without
 * waiting for a full recompute.
 * <p>
 * Classes that implement this interface should offer an appropriate method for
 * producing the raster view.
 *
 * @author Mario Bobic
 */
public interface RasterView {

	/**
	 * Produces the view of the specified raster and returns an object that
	 * represents the produced view. It is up to an implementing class to decide
	 * how this method should be implemented.
	 * 
	 * @param raster raster whose view is to be produced
	 * @return an object that represents the produced view
	 */
	Object produce(BWRaster raster);
	
}
