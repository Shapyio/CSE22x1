import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class Homework37_1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Homework37_1() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Initialize variables
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // File input stream
        SimpleReader input = new SimpleReader1L(args[0]);
        // File output stream
        SimpleWriter output = new SimpleWriter1L(args[1]);

        /*
         * Put your main program code here
         */
        //While the input file has a stream of text it continues to add to the Queue
        while (!input.atEOS()) {
            String tempS = input.nextLine();
            output.print(tempS);
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();

        input.close();
        output.close();
    }

}
