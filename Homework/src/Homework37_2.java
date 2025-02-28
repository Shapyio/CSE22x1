import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class Homework37_2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Homework37_2() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /*
         * Initialize variables
         */
        // File input stream
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        // File output stream
        PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(args[1])));
        /*
         * Put your main program code here
         */

        // Grab each word from input and output to new file if !empty.
        String tempS = in.readLine();
        while (!tempS.isEmpty()) {
            out.print(tempS);
            tempS = in.readLine();
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
