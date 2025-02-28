import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Daniel B. and Shapiy S.
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";
        // INSTRUCTION
        Reporter.assertElseFatalError(tokens.dequeue().equals("INSTRUCTION"),
                "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens");
        // Instruction name
        String inst = tokens.dequeue();
        // IS
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Violation of: <\"IS\"> is expected");
        // BLOCK
        body.parseBlock(tokens);
        // END
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Violation of: <\"END\"> is expected");
        // Instruction name
        Reporter.assertElseFatalError(tokens.dequeue().equals(inst),
                "Violation of: <\"" + inst + "\"> is expected");
        return inst;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        // PROGRAM
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Violation of: <\"PROGRAM\"> is proper prefix of tokens");
        // Program name
        String name = tokens.dequeue();
        this.setName(name); // Set name
        // IS
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Violation of: <\"IS\"> is expected");
        // Context-------------------------
        Map<String, Statement> context = this.newContext();
        // INSTRUCTION
        while (tokens.front().equals("INSTRUCTION")) {
            Statement s = this.newBody();
            String instName = parseInstruction(tokens, s);
            context.add(instName, s);
        }
        this.swapContext(context);
        // Body----------------------------
        Statement body = this.newBody();
        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Violation of: <\"BEGIN\"> is expected");
        body.parseBlock(tokens);
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Violation of: <\"END\"> is expected");
        Reporter.assertElseFatalError(tokens.dequeue().equals(name),
                "Violation of: <\"" + name + "\"> is expected");
        Reporter.assertElseFatalError(
                tokens.dequeue().equals("### END OF INPUT ###"),
                "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens");
        Reporter.assertElseFatalError(tokens.length() == 0,
                "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens");
        this.swapBody(body);
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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
