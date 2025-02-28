import components.queue.Queue;

/**
 * Homework 29 - Evaluation of Boolean Expressions.
 *
 * @author Shapiy S.
 *
 */
public final class HW29 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW29() {
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     *
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates tokens
     * @requires [a bool-expr string is a prefix of tokens]
     * @ensures
     *
     *          <pre>
     * valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens
     *          </pre>
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
            } else if (s == "AND") {
                result = result && valueOfBoolExpr(tokens);
            } else if (s == "OR") {
                result = result || valueOfBoolExpr(tokens);
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

    }

}
