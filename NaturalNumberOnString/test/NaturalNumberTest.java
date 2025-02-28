import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Daniel Belyea & Shapiy Sagiev
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /**
     * Test for constructors methods.
     */
    @Test
    public final void constructorsTest() {
        NaturalNumber t = this.constructorRef();
        NaturalNumber tExpected = this.constructorTest();
        assertEquals(tExpected, t);
    }

    /**
     * Test for Integer constructors methods.
     */
    @Test
    public final void constructorsInteger() {
        NaturalNumber t = this.constructorRef(0);
        NaturalNumber tExpected = this.constructorTest(0);
        assertEquals(tExpected, t);
    }

    /**
     * Test for String constructors methods.
     */
    @Test
    public final void constructorsString() {
        NaturalNumber t = this.constructorRef("0");
        NaturalNumber tExpected = this.constructorTest("0");
        assertEquals(tExpected, t);
    }

    /**
     * Test for Natural Number constructors methods.
     */
    @Test
    public final void constructorsNaturalNumber() {
        NaturalNumber t = this.constructorRef(123456789);
        NaturalNumber tExpected = this.constructorTest(123456789);
        assertEquals(tExpected, t);
    }

    // TODO - 3 test cases of each kernel method (Routine, Challenge, Boundary).

    /**
     * Test for multiplyBy10 method (Routine).
     */
    @Test
    public final void multiplyBy10Test1() {
        NaturalNumber n = this.constructorRef("2");
        NaturalNumber nExpected = this.constructorTest("21");

        n.multiplyBy10(1);

        assertEquals(nExpected, n);
    }

    /**
     * Test for multiplyBy10 method (Boundary).
     */
    @Test
    public final void multiplyBy10Test2() {
        NaturalNumber n1 = this.constructorRef("1");
        NaturalNumber n1Expected = this.constructorTest("10");

        NaturalNumber n2 = this.constructorRef("1");
        NaturalNumber n2Expected = this.constructorTest("19");

        n1.multiplyBy10(0); // Test lowest accepted value in contract
        n2.multiplyBy10(9); // Test highest accepted value in contract

        assertEquals(n1Expected, n1);
        assertEquals(n2Expected, n2);
    }

    /**
     * Test for divideBy10 method (Routine).
     */
    @Test
    public final void divideBy10Test1() {
        NaturalNumber n1 = this.constructorRef("21");
        int n1Expected = 1;
        NaturalNumber n2 = this.constructorRef("32");
        int n2Expected = 2;

        int n1Result = n1.divideBy10();
        int n2Result = n2.divideBy10();

        assertEquals(n1Expected, n1Result);
        assertEquals(n2Expected, n2Result);
    }

    /**
     * Test for divideBy10 method (Boundary).
     */
    @Test
    public final void divideBy10Test2() {
        NaturalNumber n = this.constructorRef("10");
        int nExpected = 0;

        int nResult = n.divideBy10();

        assertEquals(nExpected, nResult);
    }

    /**
     * Test for isZero method (Routine).
     */
    @Test
    public final void isZeroTest1() {
        NaturalNumber n = this.constructorRef("2");

        assertEquals(false, n.isZero());
    }
}
