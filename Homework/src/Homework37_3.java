import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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
public final class Homework37_3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Homework37_3() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) {
        /*
         * Initialize variables
         */
        // File input stream
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error opening input file");
            return;
        }
        // File output stream
        PrintWriter out;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error creating output file");
            return;
        }
        /*
         * Put your main program code here
         */

        // Grab each word from input and output to new file if !empty.
        String tempS;
        try {
            tempS = in.readLine();
            while (!tempS.isEmpty()) {
                out.print(tempS);
                tempS = in.readLine();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error reading or writing");
        }

        /*
         * Close input and output streams
         */
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error closing IO stream");
        }
    }

}
