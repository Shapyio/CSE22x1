import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class HW23 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW23() {
    }

    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,  
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent  
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs  
     * switched. Every other statement is left unmodified.    
     *
     * @param s
     *                        the {@code Statement}  
     * @updates s  
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>  
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
            case BLOCK: {

                //Leave empty because every other statement is unmodified.

                break;
            }
            case IF: {

                //Leave empty because every other statement is unmodified.

                break;
            }
            case IF_ELSE: {

                Statement temp = s.newInstance();
                Statement temper = s.newInstance();
                Statement.Condition con = s.disassembleIfElse(temp, temper);
                if (con.equals(Condition.NEXT_IS_NOT_EMPTY)) {
                    con = Condition.NEXT_IS_EMPTY;
                } else if (con.equals(Condition.NEXT_IS_NOT_ENEMY)) {
                    con = Condition.NEXT_IS_ENEMY;
                } else if (con.equals(Condition.NEXT_IS_NOT_FRIEND)) {
                    con = Condition.NEXT_IS_FRIEND;
                } else if (con.equals(Condition.NEXT_IS_NOT_WALL)) {
                    con = Condition.NEXT_IS_WALL;
                }
                s.assembleIfElse(con, temper, temp);

                break;
            }
            case WHILE: {

                //Leave empty because every other statement is unmodified.

                break;
            }
            case CALL: {
                // nothing to do here...can you explain why?
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
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
