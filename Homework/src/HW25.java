import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.statement.Statement;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class HW25 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW25() {
    }

    /**
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.  
     *
     * @param s
     *                          the {@code Statement}
     * @param oldName
     *                          the name of the instruction to be renamed
     * @param newName
     *                          the new name of the renamed instruction
     * @updates s  
     * @requires [newName is a valid IDENTIFIER]  
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of instruction oldName
     *   is replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
        switch (s.kind()) {
            case BLOCK: {
                // Loop thru block's children, calling prettyPrint on each
                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    // Remove block's child (Statement)
                    Statement temp = s.removeFromBlock(i);
                    // Recurse prettyPrint with new Statement
                    renameInstruction(temp, oldName, newName);
                    // At the end add Statement back to block and repeat for list
                    s.addToBlock(i, temp);
                }

                break;
            }
            case IF: {
                // Initialize variables
                Statement temp = s.newInstance();
                // Disassemble IF (with temp = IF's child and con = IF's condition)
                Statement.Condition con = s.disassembleIf(temp);
                // Recurse prettyPrint with extra offset (for indents)
                renameInstruction(temp, oldName, newName);
                // Reassemble IF to restore IF's condition and statement
                s.assembleIf(con, temp);

                break;
            }
            case IF_ELSE: {
                // Initialize variables
                Statement tempIf = s.newInstance();
                Statement tempElse = s.newInstance();
                // Disassemble IF_ELSE (with temp = IF's statements,
                // temper = ELSE's statements, and con = IF's condition)
                Statement.Condition con = s.disassembleIfElse(tempIf, tempElse);
                // Recurse prettyPrint on IF's statement, with extra offset (for indents)
                renameInstruction(tempIf, oldName, newName);
                renameInstruction(tempElse, oldName, newName);
                // Reassemble IF_ELSE to restore IF's condition and statement,
                // and ELSE's statement
                s.assembleIfElse(con, tempIf, tempElse);

                break;
            }
            case WHILE: {
                // Initialize variables
                Statement temp = s.newInstance();
                // Disassemble WHILE (temp = WHILE's child and con = WHILE's condition)
                Statement.Condition con = s.disassembleWhile(temp);
                //Recurse prettyPrint on WHILE's statement, with extra offset
                renameInstruction(temp, oldName, newName);
                // Reassemble WHILE to restore WHILE's condition and statement
                s.assembleWhile(con, temp);

                break;
            }
            case CALL: {
                // Disassemble CALL into string "s"
                String str = s.disassembleCall();
                if (str.equals(oldName)) {
                    str = newName;
                }
                // Reassemble string "s" back into CALL
                s.assembleCall(str);

                break;
            }
            default: {
                // this will never happen...
                break;
            }
        }
    }

    /**
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.  
     *
     * @param p
     *                          the {@code Statement}
     * @param oldName
     *                          the name of the instruction to be renamed
     * @param newName
     *                          the new name of the renamed instruction
     * @updates s  
     * @requires [newName is a valid IDENTIFIER]  
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of instruction oldName
     *   is replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Program p, String oldName,
            String newName) {
        /*
         * ======= Program's Name =======
         */
        // Check program name. If name is equal to old name reassign name to new name
        if (p.name().equals(oldName)) {
            p.setName(newName);
        }
        /*
         * ======= Program's Context =======
         */
        Map<String, Statement> tempContext = p.newContext();
        Map<String, Statement> finalContext = tempContext.newInstance();
        // Swap p.context with tempContext to be able to work context
        p.swapContext(tempContext);

        // Loop thru entire tempContext
        while (tempContext.size() > 0) {
            // Grab any pair
            Pair<String, Statement> x = tempContext.removeAny();
            // If pair name = old name, add to finalContext with new name but same value
            if (x.key().equals(oldName)) {
                finalContext.add(newName, x.value());
            } else {
                // Else just recurse thru the pair's value (statement)
                renameInstruction(x.value(), oldName, newName);
                // Finally add pair into finalContext
                finalContext.add(oldName, x.value());
            }
        }
        // At the end, swap context back into the program
        p.swapContext(finalContext);
        /*
         * ======= Program's Body =======
         */
        Statement tempBody = p.newBody();
        // Swap p.body with tempBody to be able to edit body using tempBody
        p.swapBody(tempBody);
        // Recurse thru tempBody
        renameInstruction(tempBody, oldName, newName);
        // Swap body back into program
        p.swapBody(tempBody);
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
