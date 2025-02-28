import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Shapiy S.
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     * Test to see if constructor method works.
     */
    @Test
    public void seqConstructorTest() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq1 = this.createFromArgsTest("car", "far", "tar");
        Sequence<String> expectedSeq1 = this.createFromArgsTest("car", "far",
                "tar");
        Sequence<String> seq2 = this.createFromArgsTest("sit", "", "bit");
        Sequence<String> expectedSeq2 = this.createFromArgsTest("sit", "",
                "bit");
        Sequence<String> seq3 = this.createFromArgsTest();
        Sequence<String> expectedSeq3 = this.createFromArgsTest();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
        assertEquals(expectedSeq3, seq3);
    }

    /**
     * Test to see if add method works.
     */
    @Test
    public void kerAddTest() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq1 = this.createFromArgsTest("car", "far");
        seq1.add(2, "tar");
        Sequence<String> expectedSeq1 = this.createFromArgsTest("car", "far",
                "tar");
        Sequence<String> seq2 = this.createFromArgsTest("sit", "bit");
        seq2.add(1, "");
        Sequence<String> expectedSeq2 = this.createFromArgsTest("sit", "",
                "bit");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test to see if remove method works.
     */
    @Test
    public void kerRemoveTest() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq1 = this.createFromArgsTest("car", "far", "tar");
        seq1.remove(2);
        Sequence<String> expectedSeq1 = this.createFromArgsTest("car", "far");
        Sequence<String> seq2 = this.createFromArgsTest("sit", "nope", "bit");
        seq2.remove(1);
        Sequence<String> expectedSeq2 = this.createFromArgsTest("sit", "",
                "bit");
        Sequence<String> seq3 = this.createFromArgsTest("remove me");
        seq3.remove(0);
        Sequence<String> expectedSeq3 = this.createFromArgsTest();
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
    public void kerLengthTest() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq1 = this.createFromArgsTest("car", "far", "tar");
        Integer expectedLen1 = 1 + 2;
        Sequence<String> seq2 = this.createFromArgsTest("sit", "", "bit");
        Integer expectedLen2 = 1 + 2;
        Sequence<String> seq3 = this.createFromArgsTest();
        Integer expectedLen3 = 0;
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(expectedLen1.equals(seq1.length()));
        assertTrue(expectedLen2.equals(seq2.length()));
        assertTrue(expectedLen3.equals(seq3.length()));
    }
}
