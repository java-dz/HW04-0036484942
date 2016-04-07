package hr.fer.zemris.java.graphics;

import hr.fer.zemris.java.graphics.raster.*;
import hr.fer.zemris.java.graphics.shapes.*;
import hr.fer.zemris.java.graphics.views.*;

/**
 * A demonstration class that shows the usage of black and white raster,
 * geometric shapes and the raster views of different implementations.
 *
 * @author Marko Cupic
 */
public class Example {

	/**
	 * Program entry point
	 * 
	 * @param args not used in this example
	 */
	public static void main(String[] args) {
		Rectangle rect1 = new Rectangle(0, 0, 4, 4);
		Rectangle rect2 = new Rectangle(1, 1, 2, 2);
		
		BWRaster raster = new BWRasterMem(6, 5);
		raster.enableFlipMode();
		
		rect1.draw(raster);
		rect2.draw(raster);
		
		RasterView view = new SimpleRasterView();
		view.produce(raster);
		view.produce(raster);
		
		System.out.println();
		
		RasterView view2 = new SimpleRasterView('X','_');
		view2.produce(raster);
	}
	
}
