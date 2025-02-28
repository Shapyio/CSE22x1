import components.statement.Statement;
import components.statement.StatementKernel.Kind;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class HW22 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW22() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.  
     *
     * @param s
     *                        the {@code Statement}  
     * @return the number of calls to primitive instructions in {@code s}  
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>  
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */
                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement temp = s.removeFromBlock(i);
                    if (temp.kind() == Kind.CALL) {
                        count++;
                    }
                    s.addToBlock(i, temp);
                }

                break;
            }
            case IF: {
                /*
                 *  * Add up the number of calls to primitive instructions in
                 * the "then" and "else" bodies of the IF_ELSE.  
                 */

                Statement temp = s.newInstance();
                Statement.Condition con = s.disassembleIf(temp);
                count = countOfPrimitiveCalls(temp);
                s.assembleIf(con, temp);

                break;
            }
            case IF_ELSE: {
                /*
                 *          * Add up the number of calls to primitive
                 * instructions in          * the "then" and "else" bodies of
                 * the IF_ELSE.          
                 */

                Statement temp = s.newInstance();
                Statement temper = s.newInstance();
                Statement.Condition con = s.disassembleIfElse(temp, temper);
                count = countOfPrimitiveCalls(temp);
                count += countOfPrimitiveCalls(temper);
                s.assembleIfElse(con, temp, temper);

                break;
            }
            case WHILE: {
                /*
                 *  * Find the number of calls to primitive instructions in  *
                 * the body of the WHILE.
                 */

                Statement temp = s.newInstance();
                Statement.Condition con = s.disassembleWhile(temp);
                count = countOfPrimitiveCalls(temp);
                s.assembleWhile(con, temp);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String str = s.disassembleCall();
                if (str.equals("move") || str.equals("infect")
                        || str.equals("turnright") || str.equals("turnleft")
                        || str.equals("skip")) {
                    count++;
                }
                s.assembleCall(str);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
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
