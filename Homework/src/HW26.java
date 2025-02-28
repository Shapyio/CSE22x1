import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;

/**
 * Homework 26 - Tokenizer.
 *
 * @author Shapiy S.
 *
 */
public final class HW26 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW26() {
    }

    /**
     * Definition of whitespace separators.
     */
    private static final String SEPARATORS = " \t\n\r";

    /**
     * Token to mark the end of the input. This token cannot come from the input
     * stream because it contains whitespace.
     */
    public static final String END_OF_INPUT = "### END OF INPUT ###";

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of  
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.  
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string  
     * @param position
     *            the starting index  
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}  
     * @requires 0 <= position < |text|  
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     * </pre>  
     */
    private static String nextWordOrSeparator(String text, int position) {
        // Initialize variables
        String str = "";
        // Convert SEPARATORS (String) to a character set
        Set<Character> sepSet = generateSetFromString(SEPARATORS);

        // If position is less than text length
        if (position < text.length()) {
            // Assign c to be first letter at position
            char c = text.charAt(position);
            // While separator set does not contain character c and position is
            // still less than text length
            while (!sepSet.contains(c) && position < text.length()) {
                // Grab character at position (first iteration is repetitive)
                c = text.charAt(position);
                // If character is not in separator set, add character to string
                if (!sepSet.contains(c)) {
                    str += c;
                }

                position++;
            }
        }

        return str;
    }

    /**
     * Takes a string and returns it as a set of characters.
     *
     * @param sepString
     *            the {@code String} from which to convert to set
     *
     * @return {@Code Set<Character>}
     */
    private static Set<Character> generateSetFromString(String sepString) {
        // Initialize variables
        Set<Character> sepSet = new Set1L<>();

        // Loop thru separator string, adding the characters to character set
        for (int i = 0; i < sepString.length(); i++) {
            char c = SEPARATORS.charAt(i);
            if (!sepSet.contains(c)) {
                sepSet.add(c);
            }
        }
        // Return newly made Set<Character> which are all separators
        return sepSet;
    }

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}.
     *
     * @param in
     *            the input stream  
     * @return the queue of tokens  
     * @requires in.is_open  
     * @ensures <pre>
     * tokens =
     *   [the non-whitespace tokens in #in.content] * <END_OF_INPUT>  and
     * in.content = <>
     * </pre>  
     */
    public static Queue<String> tokens(SimpleReader in) {
        // Initialize variables
        Queue<String> q = new Queue1L<>();
        int position = 0;
        // Loop thru entire input
        while (!in.atEOS()) {
            // Separate one word at a time
            String str = nextWordOrSeparator(SEPARATORS, position);
            // Update position for next iteration of while loop
            position = str.length() + 1;
            // Enqueue word into queue
            q.enqueue(str);
        }
        // Add END_OF_INPUT to queue
        q.enqueue(END_OF_INPUT);
        // Return q
        return q;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        String s = "";

        nextWordOrSeparator(s, 0);
    }

}
