import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Shapiy S.
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Test to see if constructor method works.
     */
    @Test
    public void setConstructorTest() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set1 = this.createFromArgsTest("car", "far", "tar");
        Set<String> expectedSet1 = this.createFromArgsTest("car", "far", "tar");
        Set<String> set2 = this.createFromArgsTest("sit", "", "bit");
        Set<String> expectedSet2 = this.createFromArgsTest("sit", "", "bit");
        Set<String> set3 = this.createFromArgsTest();
        Set<String> expectedSet3 = this.createFromArgsTest();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet1, set1);
        assertEquals(expectedSet2, set2);
        assertEquals(expectedSet3, set3);
    }

    /**
     * Test to see if add method works.
     */
    @Test
    public void kerAddTest() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set1 = this.createFromArgsTest("car", "far");
        set1.add("tar");
        Set<String> expectedSet1 = this.createFromArgsTest("car", "far", "tar");
        Set<String> set2 = this.createFromArgsTest("sit", "bit");
        set2.add("");
        Set<String> expectedSet2 = this.createFromArgsTest("sit", "", "bit");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet1, set1);
        assertEquals(expectedSet2, set2);
    }

    /**
     * Test to see if remove method works.
     */
    @Test
    public void kerRemoveTest() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set1 = this.createFromArgsTest("car", "far", "tar");
        set1.remove("tar");
        Set<String> expectedSet1 = this.createFromArgsTest("car", "far");
        Set<String> set2 = this.createFromArgsTest("sit", "nope", "bit");
        set2.remove("nope");
        Set<String> expectedSet2 = this.createFromArgsTest("sit", "", "bit");
        Set<String> set3 = this.createFromArgsTest("remove me");
        set3.remove("remove me");
        Set<String> expectedSet3 = this.createFromArgsTest();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet1, set1);
        assertEquals(expectedSet2, set2);
        assertEquals(expectedSet3, set3);
    }

    /**
     * Test to see if contains method works.
     */
    @Test
    public void kerContainsTest() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set1 = this.createFromArgsTest("car", "far", "tar");
        Set<String> set2 = this.createFromArgsTest("sit", "", "bit");
        Set<String> set3 = this.createFromArgsTest();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, set1.contains("far"));
        assertEquals(false, set2.contains("far"));
        assertEquals(true, set3.contains(""));
    }

    /**
     * Test to see if size method works.
     */
    @Test
    public void kerSizeTest() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set1 = this.createFromArgsTest("car", "far", "tar");
        Integer expectedSize1 = 1 + 2;
        Set<String> set2 = this.createFromArgsTest("sit", "", "bit");
        Integer expectedSize2 = 1 + 2;
        Set<String> set3 = this.createFromArgsTest();
        Integer expectedSize3 = 0;
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(expectedSize1.equals(set1.size()));
        assertTrue(expectedSize2.equals(set2.size()));
        assertTrue(expectedSize3.equals(set3.size()));
    }

    /**
     * Test to see if removeAny method works.
     */
    @Test
    public void kerRemoveAny() {
        // Setup
        Set<String> set1 = this.createFromArgsTest("car", "far", "tar");
        Set<String> expectedSet1 = set1;
        // Call
        String removedWord = set1.removeAny();
        // Evaluation
        assertEquals(true, expectedSet1.contains(removedWord));
    }
}
