import components.program.Program;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;

/**
 * Layered implementation of secondary method {@code prettyPrint} for
 * {@code Program}.
 */
public final class HW24 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constructs into the given {@code Program} the program read from the given
     * input file.
     *
     * @param fileName
     *            the name of the file containing the program
     * @param p
     *            the constructed program
     * @replaces p
     * @requires [fileName is the name of a file containing a valid BL program]
     * @ensures p = [program from file fileName]
     */
    private static void loadStatement(String fileName, Statement p) {
        SimpleReader in = new SimpleReader1L(fileName);
        Queue<String> lines = new Queue1L<String>();
        while (!in.atEOS()) {
            lines.enqueue(in.nextLine());
        }
        p.parseBlock(lines);
        in.close();
    }

    /**
     * Prints the given number of spaces to the given output stream.
     *
     * @param out
     *            the output stream
     * @param numSpaces
     *            the number of spaces to print
     * @updates out.content
     * @requires out.is_open and spaces >= 0
     * @ensures out.content = #out.content * [numSpaces spaces]
     */
    private static void printSpaces(SimpleWriter out, int numSpaces) {
        for (int i = 0; i < numSpaces; i++) {
            out.print(' ');
        }
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public HW24() {
        super();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    /**
     * Pretty prints {@code this} to the given stream {@code out} {@code offset}
     * spaces from the left margin using
     * {@link components.program.Program#INDENT_SIZE Program.INDENT_SIZE} spaces
     * for each indentation level.
     *
     * @param out
     *            the output stream
     * @param offset
     *            the number of spaces to be placed before every nonempty line
     *            of output; nonempty lines of output that are indented further
     *            will, of course, continue with even more spaces
     * @updates out.content
     * @requires out.is_open and 0 <= offset
     * @ensures <pre>
     * out.content =
     *   #out.content * [this pretty printed offset spaces from the left margin
     *                   using Program.INDENT_SIZE spaces for indentation]
     * </pre>
     */
    @Override
    public void prettyPrint(SimpleWriter out, int offset) {
        switch (this.kind()) {
            case BLOCK: {
                // Loop thru block's children, calling prettyPrint on each
                for (int i = 0; i < this.lengthOfBlock(); i++) {
                    // Remove block's child (Statement)
                    Statement temp = this.removeFromBlock(i);
                    // Recurse prettyPrint with new Statement
                    temp.prettyPrint(out, offset);
                    // At the end add Statement back to block and repeat for list
                    this.addToBlock(i, temp);
                }

                break;
            }
            case IF: {
                // Initialize variables
                Statement temp = this.newInstance();
                // Disassemble IF (with temp = IF's child and con = IF's condition)
                Statement.Condition con = this.disassembleIf(temp);
                // Begin with Statement "title"
                out.println("IF " + con + " THEN");
                // Recurse prettyPrint with extra offset (for indents)
                temp.prettyPrint(out, offset + offset);
                // End with Statement "close"
                out.println("END IF");
                // Reassemble IF to restore IF's condition and statement
                this.assembleIf(con, temp);

                break;
            }
            case IF_ELSE: {
                // Initialize variables
                Statement temp = this.newInstance();
                Statement temper = this.newInstance();
                // Disassemble IF_ELSE (with temp = IF's statements,
                // temper = ELSE's statements, and con = IF's condition)
                Statement.Condition con = this.disassembleIfElse(temp, temper);
                // Begin with IF's Statement "title"
                out.println("IF " + con + " THEN");
                // Recurse prettyPrint on IF's statement, with extra offset (for indents)
                temp.prettyPrint(out, offset + offset);
                // ELSE's Statement "title"
                out.println("ELSE");
                //Recurse prettyPrint on ELSE's statement, with extra offset (for indents)
                temper.prettyPrint(out, offset + offset);
                // End with IF's Statement "close"
                out.println("END IF");
                // Reassemble IF_ELSE to restore IF's condition and statement,
                // and ELSE's statement
                this.assembleIfElse(con, temp, temper);

                break;
            }
            case WHILE: {
                // Initialize variables
                Statement temp = this.newInstance();
                // Disassemble WHILE (temp = WHILE's child and con = WHILE's condition)
                Statement.Condition con = this.disassembleWhile(temp);
                // Begin with WHILE's Statement "title"
                out.println("WHILE " + con + " DO");
                //Recurse prettyPrint on WHILE's statement, with extra offset
                temp.prettyPrint(out, offset + offset);
                // End with IF's Statement "close"
                out.println("END WHILE");
                // Reassemble WHILE to restore WHILE's condition and statement
                this.assembleWhile(con, temp);

                break;
            }
            case CALL: {
                // Disassemble CALL into string "s"
                String s = this.disassembleCall();
                // Print string "s"
                out.println(s);
                // Reassemble string "s" back into CALL
                this.assembleCall(s);

                break;
            }
            default: {
                // this will never happen...
                break;
            }
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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        Statement p1 = new Statement1();
        loadStatement(fileName, p1);
        SimpleWriter ppOut = new SimpleWriter1L("data/expected-output.txt");
        p1.prettyPrint(ppOut, Program.INDENT_SIZE);
        ppOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        Statement p2 = new HW24();
        loadStatement(fileName, p2);
        ppOut = new SimpleWriter1L("data/actual-output.txt");
        p2.prettyPrint(ppOut, Program.INDENT_SIZE);
        ppOut.close();
        /*
         * Check that prettyPrint restored the value of the program
         */
        if (p2.equals(p1)) {
            out.println("Program value restored correctly.");
        } else {
            out.println("Error: program value was not restored.");
        }

        in.close();
        out.close();
    }

}
