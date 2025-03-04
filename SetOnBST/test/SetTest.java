import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
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

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /**
     * Test for constructors methods.
     */
    @Test
    public void testConstructor() {
        Set<String> t1 = this.createFromArgsTest();
        Set<String> t2 = this.createFromArgsRef();

        assertEquals(t2, t1);
    }

    /**
     * Test for add method into empty set.
     */
    @Test
    public void testAdd1() {
        Set<String> t1 = this.createFromArgsTest();
        Set<String> t2 = this.createFromArgsRef("1");
        t1.add("1");

        assertEquals(t2, t1);
    }

    /**
     * Test for add method into set with 1 string.
     */
    @Test
    public void testAdd2() {
        Set<String> t1 = this.createFromArgsTest("0");
        Set<String> t2 = this.createFromArgsRef("0", "1");
        t1.add("1");

        assertEquals(t2, t1);
    }

    /**
     * Test for remove method.
     */
    @Test
    public void testRemove1() {
        Set<String> t1 = this.createFromArgsTest("1");
        Set<String> t2 = this.createFromArgsRef();
        t1.remove("1");

        assertEquals(t2, t1);
    }

    /**
     * Test for remove method (remove from right tree).
     */
    @Test
    public void testRemove2() {
        Set<String> t1 = this.createFromArgsTest("3", "1", "0", "2", "4", "6",
                "5", "7");
        Set<String> t2 = this.createFromArgsRef("3", "1", "0", "2", "5", "6",
                "7");
        t1.remove("4");

        assertEquals(t2, t1);
    }

    /**
     * Test for remove method (remove from root).
     */
    @Test
    public void testRemove3() {
        Set<String> t1 = this.createFromArgsTest("3", "1", "0", "2", "4", "6",
                "5", "7");
        Set<String> t2 = this.createFromArgsRef("4", "1", "0", "2", "6", "5",
                "7");
        t1.remove("3");

        assertEquals(t2, t1);
    }

    /**
     * Test for remove method (remove from left tree).
     */
    @Test
    public void testRemove4() {
        Set<String> t1 = this.createFromArgsTest("5", "1", "0", "2", "4", "-1");
        Set<String> t2 = this.createFromArgsRef("5", "1", "2", "4", "-1");
        t1.remove("0");

        assertEquals(t2, t1);
    }

    /**
     * Test for removeAny method.
     */
    @Test
    public void testRemoveAny1() {
        Set<String> t1 = this.createFromArgsTest("1", "2");
        Set<String> t2 = this.createFromArgsRef("1", "2");

        String remove = t1.removeAny();
        assertEquals(true, t2.contains(remove));
        t2.removeAny();
        assertEquals(t2, t1);
    }

    /**
     * Test to see if removeAny method works.
     */
    @Test
    public void testRemoveAny2() {
        // Setup
        Set<String> set1 = this.createFromArgsTest("car", "far", "tar");
        Set<String> expectedSet1 = this.createFromArgsRef("car", "far", "tar");
        // Call
        String removedWord = set1.removeAny();
        // Evaluation
        assertEquals(true, expectedSet1.contains(removedWord));
    }

    /**
     * Test for contains method true.
     */
    @Test
    public void testContains1() {
        Set<String> t1 = this.createFromArgsTest("0", "1");

        assertEquals(true, t1.contains("1"));
    }

    /**
     * Test for contains method false.
     */
    @Test
    public void testContains2() {
        Set<String> t1 = this.createFromArgsTest("0", "1");

        assertEquals(false, t1.contains("2"));
    }

    /**
     * Test for contains method false.
     */
    @Test
    public void testContains3() {
        Set<String> t1 = this.createFromArgsTest("3", "1", "0", "2", "4", "6",
                "5", "7");

        assertEquals(true, t1.contains("2"));
    }

    /**
     * Test for size method of an empty set.
     */
    @Test
    public void testSize1() {
        Set<String> t1 = this.createFromArgsTest();

        assertEquals(0, t1.size());
    }

    /**
     * Test for size method of an set with 1 string.
     */
    @Test
    public void testSize2() {
        Set<String> t1 = this.createFromArgsTest("1");

        assertEquals(1, t1.size());
    }

    /**
     * Test for size method of an set with 1 string.
     */
    @Test
    public void testSize3() {
        Set<String> t1 = this.createFromArgsTest("0", "1", "2", "3");

        assertEquals(2 * 2, t1.size());
    }
}
