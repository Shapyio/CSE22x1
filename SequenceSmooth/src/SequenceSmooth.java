import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Shapiy S.
 *
 */
public final class SequenceSmooth {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmooth() {
    }

    /**
     * Smooths a given {@code Sequence<Integer>}.
     *
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     * @return s2 the resulting sequence
     */
    public static Sequence<Integer> smooth(Sequence<Integer> s1,
            Sequence<Integer> s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";

        s2.clear();

// Made with loop
//        for (int x = 0; x < s1.length(); x++) {
//            if (x + 1 <= s1.length()) {
//                int i = s1.entry(x);
//                int j = s1.entry(x + 1);
//
//                s2.add(x, (i + j) / 2);
//            }
//        }

// Made with recursion
        if (s1.length() > 1) {
            int i = s1.remove(0);
            int j = s1.entry(0);

            smooth(s1, s2);

            s2.add(0, (i + j) / 2);

            s1.add(0, i);
        }

        return s2;
    }
}
