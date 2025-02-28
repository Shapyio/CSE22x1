import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class HashingMod {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HashingMod() {
    }

    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     *
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    public static int mod(int a, int b) {
        assert b > 0 : "Violation of: i >= 0";

        int r = a;
        if (a > 0) {
            while (r > b) {
                r = r - b;
            }
        } else if (a < 0) {
            while (r < 0) {
                r = r + b;
            }
        }

        return r;
    }

    /**
     * Hash code implementation.
     *
     * @param a
     *            the number being sorted into bucket
     * @param b
     *            the number of buckets
     * @return the bucket which a would be in
     */
    public static int hashCode(Integer a, int b) {
        return mod(a, b);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        int exit = 0;
        while (exit == 0) {
            out.print("Enter a 'a' value: ");
            int a = in.nextInteger();
            out.print("Enter a 'b' value: ");
            int b = in.nextInteger();
            out.print("a: " + a);
            out.print(", b: " + b);
            out.println("\na % b: " + mod(a, b));
            out.print("1 to exit, 0 to input again: ");
            exit = in.nextInteger();
            out.print("\n");
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
