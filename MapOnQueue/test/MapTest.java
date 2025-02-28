import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Shapiy S.
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
     * Test method for constructor method.
     */
    @Test
    public final void testConstructor() {
        Map<String, String> map = this.createFromArgsTest("yes", "");
        Map<String, String> mapExpected = this.createFromArgsRef("yes", "");
        assertEquals(map, mapExpected);
    }

    /**
     * Test method for add method.
     */
    @Test
    public final void testAdd() {
        Map<String, String> map = this.createFromArgsTest("yes", "", "no", "");
        Map<String, String> mapExpected = this.createFromArgsRef("maybe", "",
                "yes", "", "no", "");
        map.add("maybe", "");
        assertEquals(map, mapExpected);
    }

    /**
     * Test method for remove method.
     */
    @Test
    public final void testRemove() {
        Map<String, String> map = this.createFromArgsTest("maybe", "", "yes",
                "", "no", "");
        Map<String, String> mapExpected = this.createFromArgsRef("yes", "",
                "no", "");
        map.remove("maybe");
        assertEquals(map, mapExpected);
    }

    /**
     * Test method for removeAny method.
     */
    @Test
    public final void testRemoveAny() {
        Map<String, String> map = this.createFromArgsTest("yes", "", "no", "");
        Map<String, String> mapExpected = this.createFromArgsRef("yes", "",
                "no", "");

        Pair<String, String> remove = map.removeAny();
        assertEquals(true, mapExpected.hasKey(remove.key()));
        mapExpected.remove(remove.key());
        assertEquals(map, mapExpected);
    }

    /**
     * Test method for hasKey method.
     */
    @Test
    public final void testHasKey() {
        Map<String, String> map = this.createFromArgsTest("yes", "", "no", "");
        boolean test = map.hasKey("yes");
        assertEquals(true, test);
    }

    /**
     * Test method for size method.
     */
    @Test
    public final void testSize() {
        Map<String, String> map = this.createFromArgsTest("yes", "", "no", "");
        int mapSize = map.size();
        assertEquals(mapSize, 2);
    }

}
