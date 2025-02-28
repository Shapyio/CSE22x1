import java.io.Serializable;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Inputs text file and records each word and how often that word appeared in
 * given text. It outputs it in html table.
 *
 * @author Shapiy Sagiev
 *
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    public static void generateElements(String str, Set<Character> charSet) {
        // Loops thru all characters of entire string
        for (int i = 0; i < str.length(); i++) {
            // If character at i position not in charSet, then add to charSet
            if (!charSet.contains(str.charAt(i))) {
                charSet.add(str.charAt(i));
            }
        }
    }

    /**
     * Uses input {@code file} and separates words and then initializes the word
     * counter {@code Map}.
     *
     * @param file
     *            input file used to get words and counts
     * @param wordMap
     *            map that is initialized to take in the words and counts from
     *            file
     * @param sepSet
     *            set of all separators to look out for
     * @updates wordCounter
     * @ensures wordCounter = entries(file)
     */
    public static void countAllWords(SimpleReader file,
            Map<String, Integer> wordMap, Set<Character> sepSet) {
        /*
         * Iterate thru entire file, line-by-line
         */
        while (!file.atEOS()) {
            /*
             * Grab line
             */
            String temp = file.nextLine();
            /*
             * Check length of temp, if it is zero, it is blank line, meaning
             * the end of a line, add the words so far to the map
             */
            if (temp.length() > 0) {
                /*
                 * If it is word it is returned as true. If not, it is sentence.
                 */
                countLine(temp, wordMap, sepSet);
            }
        }
    }

    /**
     * Uses given {@code String} to find words by looking out for separators.
     *
     * @param temp
     *            string of words
     * @param wordMap
     *            map that is initialized to take in the words and counts from
     *            file
     * @param sepSet
     *            set of all separators to look out for
     * @updates wordMap
     * @ensures wordMap = entries(file)
     */
    private static void countLine(String temp, Map<String, Integer> wordMap,
            Set<Character> sepSet) {
        Queue<Character> word = new Queue1L<Character>();
        /*
         * Loop thru entire line
         */
        for (int i = 0; i < temp.length(); i++) {
            /*
             * Keeping looping until a separator is reached, the array is a word
             */
            if (!sepSet.contains(temp.charAt(i))) {
                word.enqueue(temp.charAt(i));
            } else {
                /*
                 * Convert char Queue to string (and clear char queue)
                 */
                StringBuffer buf = new StringBuffer();
                for (int j = 0; j < word.length() + j; j++) {
                    buf.append(word.dequeue().toString().toLowerCase());
                }
                String str = buf.toString();

                /*
                 * If Map has the word already, increase the word's count
                 */
                if (wordMap.hasKey(str)) {
                    int counter = wordMap.value(str);
                    counter++;
                    wordMap.replaceValue(str, counter);
                } else {
                    /*
                     * If new word and not space, add word to map, start count
                     */
                    if (str.length() > 0) {
                        wordMap.add(str, 1);
                    }
                }
                /*
                 * Reset string
                 */
                str = "";
            }
        }
    }

    /**
     * Prints the index or homepage, based off of original example provided.
     *
     * @param path
     *            the file path
     * @param wordMap
     *            Map of all terms
     * @param wordQ
     *            Queue of all terms (in alphabetic order)
     */
    public static void printIndex(String path, Map<String, Integer> wordMap,
            Queue<String> wordQ) {
        /*
         * Initialize writer
         */
        SimpleWriter index;
        /*
         * Adjust writer output based on file path
         */
        if (path.endsWith("index.html")) {
            index = new SimpleWriter1L(path);
        } else {
            index = new SimpleWriter1L(path + "/index.html");
        }
        /*
         * Output title
         */
        index.print("<html>\n<head>\n<title>Words Counted</title>\n</head>\n");
        /*
         * Output header
         */
        index.print("<body>\n<h2>Words Counted</h2>\n<hr>\n<table "
                + "border=\"1\">\n<tbody>");
        index.print("<tr>\n<th>Words</th>\n<th>Counts</th></tr>");
        /*
         * Loop thru all entries to list words
         */
        for (String term : wordQ) {
            index.println("<tr><th>" + term + "</th>\n<th>"
                    + wordMap.value(term) + "</th>\n</tr>");
        }
        /*
         * Output footer
         */
        index.print("</tbody>\n</table>\n</body>\n</html>\n");
        /*
         * Close output stream
         */
        index.close();
    }

    /**
     * Gets the map terms into a queue.
     *
     * @param wordMap
     *            the given Map that stores all terms with definition
     * @return A sorted Queue<String> that stores all terms
     */
    public static Queue<String> mapToQueue(Map<String, Integer> wordMap) {
        /*
         * Initialize queue
         */
        Queue<String> entries = new Queue1L<String>();
        /*
         * Loop thru dictionary and enqueue terms
         */
        for (Map.Pair<String, Integer> terms : wordMap) {
            entries.enqueue(terms.key().toLowerCase());
        }
        /*
         * Initialize comparator
         */
        Comparator<String> strCompare = new Sort();
        /*
         * Sort queue
         */
        entries.sort(strCompare);
        /*
         * Return sorted queue
         */
        return entries;
    }

    /**
     * Orders alphabetically.
     */
    @SuppressWarnings("serial") //Added serializable because of spotbugs
    private static class Sort implements Comparator<String>, Serializable {
        /**
         * compares which String is bigger.
         *
         * @param str1
         *            the first String
         * @param str2
         *            the second String
         * @return str1.compareTo(str2)
         */
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Define separator characters for test
         */
        final String separatorStr = " \t,.-";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);
        /*
         * Open input and output streams
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Set up the dictionary. First String is word. Second is definition.
         */
        Map<String, Integer> wordMap = new Map1L<String, Integer>();
        /*
         * Get input file for dictionary. And ask for output file destination.
         */
        out.print("Enter input file: ");
        String file = in.nextLine();
        SimpleReader fileReader = new SimpleReader1L(file);
        countAllWords(fileReader, wordMap, separatorSet);
        out.print("Enter output file path: ");
        String path = in.nextLine();
        /*
         * Set up a queue of all terms
         */
        Queue<String> wordQ = mapToQueue(wordMap);
        /*
         * Print index/homepage
         */
        printIndex(path, wordMap, wordQ);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}
