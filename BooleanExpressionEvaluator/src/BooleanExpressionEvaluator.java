import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the value of a Boolean expression consisting of
 * Boolean operators NOT, AND, and OR, operands T and F, and parentheses.
 *
 * @author Shapiy S.
 *
 */
public final class BooleanExpressionEvaluator {

    /**
     * Private no-argument constructor so this utility class cannot be
     * instantiated.
     */
    private BooleanExpressionEvaluator() {
    }

    /**
     * The length of the two longest tokens in the bool-exp language.
     */
    private static final int LENGTH_OF_LONGEST_TOKENS = 3;

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}. Note the
     * requires clause that the input be well-formed. This implementation
     * assumes the input does not contain any invalid characters or tokens and
     * does not do any error checking.
     *
     * @param source
     *            the input
     * @return the queue of tokens
     * @requires <pre>
     * [source only contains valid tokens for the Boolean expression
     *  grammar and white space to separate them]
     * </pre>
     * @ensures tokens = [the non-whitespace tokens in source]
     */
    private static Queue<String> tokens(String source) {
        Queue<String> tokens = new Queue1L<String>();
        int pos = 0;
        while (pos < source.length()) {
            switch (source.charAt(pos)) {
                case 'T': {
                    tokens.enqueue("T");
                    pos++;
                    break;
                }
                case 'F': {
                    tokens.enqueue("F");
                    pos++;
                    break;
                }
                case '(': {
                    tokens.enqueue("(");
                    pos++;
                    break;
                }
                case ')': {
                    tokens.enqueue(")");
                    pos++;
                    break;
                }
                case 'N': {
                    tokens.enqueue("NOT");
                    pos += LENGTH_OF_LONGEST_TOKENS;
                    break;
                }
                case 'A': {
                    tokens.enqueue("AND");
                    pos += LENGTH_OF_LONGEST_TOKENS;
                    break;
                }
                case 'O': {
                    tokens.enqueue("OR");
                    pos += 2;
                    break;
                }
                default: {
                    pos++;
                    break;
                }
            }
        }
        return tokens;
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     *
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates tokens
     * @requires [a bool-expr string is a prefix of tokens]
     * @ensures <pre>
     * valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens
     * </pre>
     */
    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        boolean result = false;

        while (tokens.length() > 0) {
            String s = tokens.dequeue();

            if (s == "T") {
                result = true;
                valueOfBoolExpr(tokens);
            } else if (s == "F") {
                result = false;
                valueOfBoolExpr(tokens);
            } else if (s == "NOT") {
                result = !valueOfBoolExpr(tokens);
            } else if (s == "(") {
                result = valueOfBoolExpr(tokens);
            } else if (s == "AND") {
                result = result && valueOfBoolExpr(tokens);
            } else if (s == "OR") {
                result = result || valueOfBoolExpr(tokens);
            } else {
                // Do nothing, in cases of s == ")"
            }
        }
        return result;
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
        out.print("Enter a valid Boolean expression: ");
        String source = in.nextLine();
        while (source.length() > 0) {
            boolean value = valueOfBoolExpr(tokens(source));
            out.println(source + " = " + value);
            out.print("Enter a valid Boolean expression: ");
            source = in.nextLine();
        }
        in.close();
        out.close();
    }

}
