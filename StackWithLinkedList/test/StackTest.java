import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    // TODO - add test cases for constructor, push, pop, and length

    /**
     * Test to see if constructor method works.
     */
    @Test
    public void constructorTest1() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> seq1 = this.createFromArgsTest("car", "far", "tar");
        Stack<String> expectedSeq1 = this.createFromArgsTest("car", "far",
                "tar");
        Stack<String> seq2 = this.createFromArgsTest("sit", "", "bit");
        Stack<String> expectedSeq2 = this.createFromArgsTest("sit", "", "bit");
        Stack<String> seq3 = this.createFromArgsTest();
        Stack<String> expectedSeq3 = this.createFromArgsTest();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
        assertEquals(expectedSeq3, seq3);
    }

    /**
     * Test to see if push method works.
     */
    @Test
    public void pushTest1() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> seq1 = this.createFromArgsTest("car", "far");
        seq1.push("tar");
        Stack<String> expectedSeq1 = this.createFromArgsTest("tar", "car",
                "far");
        Stack<String> seq2 = this.createFromArgsTest("sit", "bit");
        seq2.push("");
        Stack<String> expectedSeq2 = this.createFromArgsTest("", "sit", "bit");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test to see if pop method works.
     */
    @Test
    public void popTest1() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> seq1 = this.createFromArgsTest("car", "far", "tar");
        seq1.pop();
        Stack<String> expectedSeq1 = this.createFromArgsTest("far", "tar");
        Stack<String> seq2 = this.createFromArgsTest("sit", "nope", "bit");
        seq2.pop();
        Stack<String> expectedSeq2 = this.createFromArgsTest("nope", "bit");
        Stack<String> seq3 = this.createFromArgsTest("remove me");
        seq3.pop();
        Stack<String> expectedSeq3 = this.createFromArgsTest();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
        assertEquals(expectedSeq3, seq3);
    }

    /**
     * Test to see if length method works.
     */
    @Test
    public void lengthTest1() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> seq1 = this.createFromArgsTest("car", "far", "tar");
        Integer expectedLen1 = 1 + 2;
        Stack<String> seq2 = this.createFromArgsTest("sit", "", "bit");
        Integer expectedLen2 = 1 + 2;
        Stack<String> seq3 = this.createFromArgsTest();
        Integer expectedLen3 = 0;
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(expectedLen1.equals(seq1.length()));
        assertTrue(expectedLen2.equals(seq2.length()));
        assertTrue(expectedLen3.equals(seq3.length()));
    }
}
