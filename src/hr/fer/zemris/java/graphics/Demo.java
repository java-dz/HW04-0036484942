package hr.fer.zemris.java.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.graphics.raster.*;
import hr.fer.zemris.java.graphics.shapes.*;
import hr.fer.zemris.java.graphics.views.*;

/**
 * A demonstration class that shows the usage of {@link BWRaster black and white
 * raster}, {@link GeometricShape geometric shapes} and the {@link RasterView
 * raster views} of different implementations.
 * <p>
 * Program expects that the user provides either a single command line argument
 * or two arguments. In case that user provides a single argument, its value is
 * interpreted as both width and height of the raster. Otherwise, the first
 * argument is treated as the width of the raster and the second argument as the
 * height of the raster. In case there are zero arguments or more than two
 * arguments this program writes an appropriate message and returns. In case the
 * specified command line arguments can not be interpreted as numbers (or are
 * inappropriate), again an appropriate message is written and the program
 * returns.
 * <p>
 * The user is expected to tell the program what he wants to create by typing,
 * one shape per line, or by redirecting a textual document to the program.
 * <p>
 * The first line must contain a number of shapes that follow, where the maximum
 * number of shapes can be <tt>7</tt>. If the user inputs a number greater than
 * the maximum number of shapes, it is then reset to the maximum number of
 * shapes and all lines after than that are discarded.
 * <p>
 * The input of each geometric shape is parsed and processed. If there any
 * errors in the input, the program writes an appropriate message and continues
 * accepting input. If this was a document error, and if because of it a
 * sufficient amount of shapes cannot be parsed, or in other words if the end of
 * the stream has been reached, the program writes an appropriate message and
 * returns.
 * <p>
 * Once the specified amount of shapes have been parsed, the program creates a
 * {@linkplain SimpleRasterView} and produces the result.
 *
 * @author Mario Bobic
 */
public class Demo {

    /** A constant defining the FLIP keyword. */
    private static final String FLIP = "FLIP";
    /** A constant defining the RECTANGLE keyword. */
    private static final String RECTANGLE = "RECTANGLE";
    /** A constant defining the SQUARE keyword. */
    private static final String SQUARE = "SQUARE";
    /** A constant defining the ELLIPSE keyword. */
    private static final String ELLIPSE = "ELLIPSE";
    /** A constant defining the CIRCLE keyword. */
    private static final String CIRCLE = "CIRCLE";

    /** True if the flip mode is on, false if it is off. */
    private static boolean flipped;

    /** A collection used for storing shapes. */
    private static ArrayIndexedCollection shapes;
    /** The width dimension of the raster provided from the command line. */
    private static int width;
    /** The height dimension of the raster provided from the command line. */
    private static int height;

    /**
     * Program entry point.
     *
     * @param args arguments from the command line
     * @throws IOException if an unexpected I/O exception occurs
     */
    /*
     * I have put files example1.txt, ..., example4.txt in this project,
     * with each being more complex than the previous one.
     * You can copy&paste text from these documents or run them by
     * redirecting the txt file from the terminal by writing:
     * java -cp bin hr.fer.zemris.java.graphics.Demo 50 < exampleN.txt
     * Note that these examples are meant to be drawn on 50x50 raster.
     */
    public static void main(String[] args) throws IOException {
        initArguments(args);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int count = parseInt(reader.readLine());
        if (count < 1) {
            return;
        }

        shapes = new ArrayIndexedCollection(count);

        do {
            String line = reader.readLine();
            boolean success = processInput(line);
            if (!success) {
//                System.exit(-1);
                continue;
            }
            count--;
        } while (count > 0);

        reader.close();

        /* Drawing begins. */
        BWRaster raster = new BWRasterMem(width, height);
        for (Object shape : shapes.toArray()) {
            if (shape == null) {
                if (flipped) {
                    raster.disableFlipMode();
                    flipped = false;
                } else {
                    raster.enableFlipMode();
                    flipped = true;
                }
            } else {
                ((GeometricShape) shape).draw(raster);
            }
        }

        RasterView view = new SimpleRasterView();
        view.produce(raster);
    }

    /**
     * Initializes the <tt>width</tt> and <tt>length</tt> arguments by parsing
     * the command line integer(s). In case there are zero arguments or more
     * than two arguments this method writes an appropriate message and
     * terminates the program. In case the specified command line arguments can
     * not be interpreted as numbers (or are inappropriate), again an
     * appropriate message is written and the program is terminated.
     *
     * @param args arguments from the command line
     */
    private static void initArguments(String[] args) {
        if (args.length == 2) {
            width = parseInt(args[0]);
            height = parseInt(args[1]);
        } else if (args.length == 1) {
            width = height = parseInt(args[0]);
        } else {
            System.err.println("Expected 1 or 2 arguments.");
            System.exit(2);
        }
        if (width < 1 || height < 1) {
            System.err.println("Arguments must be greater than 0.");
            System.exit(3);
        }
    }

    /**
     * Processes the input from the <tt>line</tt> argument. If the input is not
     * <tt>null</tt> or an empty line, this method delegates processing to the
     * {@link #parseGeometricShape parsing} method which adds the parsed shape
     * to the collection if the parsing is successful and returns true. If the
     * criteria is not met, or if the parsing is unsuccessful, this method
     * writes an appropriate message to the standard error output stream and
     * returns false.
     *
     * @param line line to be processed
     * @return true if the processing can be done, false otherwise
     */
    private static boolean processInput(String line) {
        if (line == null) {
            System.err.println("Reached end of the stream.");
            System.exit(4);
        }
        line = line.replaceAll("\\s+", " ").trim();
        if (line.isEmpty()) {
            System.err.println("Please enter a non-empty input.");
            return false;
        }

        String[] arguments = line.split(" ");
        boolean success = parseGeometricShape(arguments);
        if (!success) {
            System.err.println("Invalid input: " + line);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Parses the geometric shape contained in the specified string array
     * argument and adds the parsed shape to the collection. Returns true if the
     * geometric shape is parsed successfully and false if the parsing fails.
     *
     * @param args arguments of the geometric shape to be parsed
     * @return true if the parsing goes successfully, false otherwise
     */
    private static boolean parseGeometricShape(String[] args) {
        try {
            switch(args[0].toUpperCase()) {
            case FLIP:
                shapes.add(null);
                return true;
            case RECTANGLE:
                int xR = parseInt(args[1]);
                int yR = parseInt(args[2]);
                int wR = parseInt(args[3]);
                int hR = parseInt(args[4]);
                shapes.add(new Rectangle(xR, yR, wR, hR));
                return true;
            case SQUARE:
                int xS = parseInt(args[1]);
                int yS = parseInt(args[2]);
                int sideS = parseInt(args[3]);
                shapes.add(new Square(xS, yS, sideS));
                return true;
            case ELLIPSE:
                int cxE = parseInt(args[1]);
                int cyE = parseInt(args[2]);
                int aE = parseInt(args[3]);
                int bE = parseInt(args[4]);
                shapes.add(new Ellipse(cxE, cyE, aE, bE));
                return true;
            case CIRCLE:
                int cxC = parseInt(args[1]);
                int cyC = parseInt(args[2]);
                int rC = parseInt(args[3]);
                shapes.add(new Circle(cxC, cyC, rC));
                return true;
            default:
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parses the string argument as a signed integer. The characters in the
     * string must all be digits, except that the first character may be an
     * ASCII minus sign. In case the string argument can not be parsed as an
     * integer, an appropriate message is printed out on the standard error
     * output stream and the program is terminated.
     *
     * @param s a string containing the integer representation to be parsed
     * @return the integer value represented by the string argument
     */
    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer: " + s);
            System.exit(1);
            return 0; // just to make the compiler happy
        }
    }

}
