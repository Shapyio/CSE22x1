import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Daniel B. and Shapiy S.
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";
        // IF
        Reporter.assertElseFatalError(tokens.dequeue().equals("IF"),
                "Violation of: <\"IF\"> is proper prefix of tokens");
        // Condition
        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Violation of: c is a condition string");
        String con = tokens.dequeue();
        Condition c = parseCondition(con);
        // THEN
        Reporter.assertElseFatalError(tokens.dequeue().equals("THEN"),
                "Violation of: <\"THEN\"> is expected");
        // BLOCK
        Statement sIf = s.newInstance();
        sIf.parseBlock(tokens);
        // ELSE or END
        Reporter.assertElseFatalError(
                tokens.front().equals("ELSE") || tokens.front().equals("END"),
                "Violation of: <\"ELSE\"> or <\"END\"> is expected");
        if (tokens.front().equals("ELSE")) { // ELSE block
            tokens.dequeue();
            Statement sElse = s.newInstance();
            sElse.parseBlock(tokens);
            s.assembleIfElse(c, sIf, sElse);
        } else { // END
            s.assembleIf(c, sIf);
        }
        // END
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Violation of: <\"END\"> is expected");
        // IF
        Reporter.assertElseFatalError(tokens.dequeue().equals("IF"),
                "Violation of: <\"IF\"> is expected");
    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";
        // WHILE
        Reporter.assertElseFatalError(tokens.dequeue().equals("WHILE"),
                "Violation of: <\"WHILE\"> is proper prefix of tokens");
        // Condition
        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Violation of: c is a condition string");
        String con = tokens.dequeue();
        Condition c = parseCondition(con);
        // Do
        Reporter.assertElseFatalError(tokens.dequeue().equals("DO"),
                "Violation of: <\"DO\"> is expected");
        // BLOCK
        Statement sWhile = s.newInstance();
        sWhile.parseBlock(tokens);
        s.assembleWhile(c, sWhile);
        // END
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Violation of: <\"END\"> is expected");
        // WHILE
        Reporter.assertElseFatalError(tokens.dequeue().equals("WHILE"),
                "Violation of: <\"WHILE\"> is expected");
    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";

        String str = tokens.dequeue();
        s.assembleCall(str);
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        Reporter.assertElseFatalError(
                tokens.front().equals("IF") || tokens.front().equals("WHILE")
                        || Tokenizer.isIdentifier(tokens.front()),
                "Violation of: <\"IF\"> or <\"WHILE\"> or a valid Identifier "
                        + "is proper prefix of tokens");
        if (tokens.front().equals("IF")) {
            parseIf(tokens, this);
        } else if (tokens.front().equals("WHILE")) {
            parseWhile(tokens, this);
        } else {
            parseCall(tokens, this);
        }
    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Statement s = this.newInstance();
        int pos = 0;
        while (!tokens.front().equals("ELSE") && !tokens.front().equals("END")
                && !tokens.front().equals(Tokenizer.END_OF_INPUT)) {
            s.clear();
            s.parse(tokens);
            this.addToBlock(pos, s);
            pos++;
        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
