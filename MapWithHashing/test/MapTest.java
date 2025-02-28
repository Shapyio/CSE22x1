import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Daniel Belyea & Shapiy Sagiev
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test for constructors method one key and value.
     */
    @Test
    public final void constructorsTest1() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0");
        Map<String, String> t = this.createFromArgsTest("A", "0");
        assertEquals(tExpected, t);
    }

    /**
     * Test for constructors method empty map.
     */
    @Test
    public final void constructorsTest2() {
        Map<String, String> tExpected = this.createFromArgsRef();
        Map<String, String> t = this.createFromArgsTest();
        assertEquals(tExpected, t);
    }

    /**
     * Test for constructors method two keys and values.
     */
    @Test
    public final void constructorsTest3() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0", "B",
                "1");
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1");
        assertEquals(tExpected, t);
    }

    /**
     * Test for add method into an empty map.
     */
    @Test
    public final void addTest1() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0");
        Map<String, String> t = this.createFromArgsTest();
        t.add("A", "0");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for add method adding empty string key and value returns false.
     */
    @Test
    public final void addTest2() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0", "B",
                "1");
        Map<String, String> t = this.createFromArgsTest("A", "0");
        t.add("", "");
        assertEquals(t.equals(tExpected), false);
    }

    /**
     * Test for add method adding three keys and values.
     */
    @Test
    public final void addTest3() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0", "B",
                "1", "C", "2");
        Map<String, String> t = this.createFromArgsTest("A", "0");
        t.add("B", "1");
        t.add("C", "2");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for add method adding two keys and values (boundary), shows it has
     * boundary greater than integer because it is a string.
     */
    @Test
    public final void addTest4() {
        Map<String, String> tExpected = this.createFromArgsRef("A",
                "-2147483649", "B", "2147483648");
        Map<String, String> t = this.createFromArgsTest("A", "-2147483649");
        t.add("B", "2147483648");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for remove method on map with size 1.
     */
    @Test
    public final void removeTest1() {
        Map<String, String> tExpected = this.createFromArgsRef();
        Map<String, String> t = this.createFromArgsTest("Z", "99");
        t.remove("Z");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for remove method on map with size 2.
     */
    @Test
    public final void removeTest2() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0");
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1");
        t.remove("B");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for remove method on a map with size 3.
     */
    @Test
    public final void removeTest3() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0", "B",
                "1");
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1", "C",
                "2");
        t.remove("C");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for remove method remove all the keys and values from the map with
     * size 2.
     */
    @Test
    public final void removeTest4() {
        Map<String, String> tExpected = this.createFromArgsRef();
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1");
        t.remove("A");
        t.remove("B");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for remove method on a map with empty key and value.
     */
    @Test
    public final void removeTest5() {
        Map<String, String> tExpected = this.createFromArgsRef("A", "0");
        Map<String, String> t = this.createFromArgsTest("", "", "A", "0");
        t.remove("");
        assertEquals(t.equals(tExpected), true);
    }

    /**
     * Test for removeAny method on a map that has size one.
     */
    @Test
    public final void removeAnyTest1() {
        Map<String, String> tExpected = this.createFromArgsTest("A", "0");
        Map<String, String> t = this.createFromArgsRef("A", "0");
        Map.Pair<String, String> remove = t.removeAny();
        assertEquals(true, tExpected.hasKey(remove.key()));
        tExpected.remove(remove.key());
        assertEquals(tExpected, t);
    }

    /**
     * Test for removeAny method for a map with size two.
     */
    @Test
    public final void removeAnyTest2() {
        Map<String, String> tExpected = this.createFromArgsTest("A", "0", "B",
                "1");
        Map<String, String> t = this.createFromArgsRef("A", "0", "B", "1");
        Map.Pair<String, String> remove = t.removeAny();
        assertEquals(true, tExpected.hasKey(remove.key()));
        tExpected.remove(remove.key());
        assertEquals(tExpected, t);
    }

    /**
     * Test for removeAny method for a map with size three.
     */
    @Test
    public final void removeAnyTest3() {
        Map<String, String> tExpected = this.createFromArgsTest("A", "0", "B",
                "1", "Z", "99");
        Map<String, String> t = this.createFromArgsRef("A", "0", "B", "1", "Z",
                "99");
        Map.Pair<String, String> remove = t.removeAny();
        assertEquals(true, tExpected.hasKey(remove.key()));
        tExpected.remove(remove.key());
        assertEquals(tExpected, t);
    }

    /**
     * Test for removeAny method for a map with an empty string key and value.
     */
    @Test
    public final void removeAnyTest4() {
        Map<String, String> tExpected = this.createFromArgsTest("", "", "Z",
                "99");
        Map<String, String> t = this.createFromArgsRef("", "", "Z", "99");
        Map.Pair<String, String> remove = t.removeAny();
        assertEquals(true, tExpected.hasKey(remove.key()));
        tExpected.remove(remove.key());
        assertEquals(tExpected, t);
    }

    /**
     * Test for value method.
     */
    @Test
    public final void valueTest1() {
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1", "C",
                "2");
        assertEquals("1", t.value("B"));
    }

    /**
     * Test for value method empty string value.
     */
    @Test
    public final void valueTest2() {
        Map<String, String> t = this.createFromArgsTest("Empty", "");
        assertEquals("", t.value("Empty"));
    }

    /**
     * Test for hasKey method true.
     */
    @Test
    public final void hasKeyTest1() {
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1");
        assertEquals(true, t.hasKey("A"));
    }

    /**
     * Test for hasKey method false.
     */
    @Test
    public final void hasKeyTest2() {
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1");
        assertEquals(false, t.hasKey("C"));
    }

    /**
     * Test for hasKey method true empty string.
     */
    @Test
    public final void hasKeyTest3() {
        Map<String, String> t = this.createFromArgsTest("", "100");
        assertEquals(true, t.hasKey(""));
    }

    /**
     * Test for size method empty size of 0.
     */
    @Test
    public final void sizeTest1() {
        Map<String, String> t = this.createFromArgsTest();
        assertEquals(0, t.size());
    }

    /**
     * Test for size method with two keys and values.
     */
    @Test
    public final void sizeTest2() {
        Map<String, String> t = this.createFromArgsTest("A", "0", "B", "1");
        assertEquals(2, t.size());
    }

    /**
     * Test for size method with an empty string key and value.
     */
    @Test
    public final void sizeTest3() {
        Map<String, String> t = this.createFromArgsTest("", "");
        assertEquals(1, t.size());
    }
}
