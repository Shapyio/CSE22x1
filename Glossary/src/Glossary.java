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
 * Program takes a input file of words and definitions and then outputs a html
 * file to navigate like a dictionary. All methods set to public for testing
 * purposes.
 *
 * @author Shapiy S.
 *
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
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
     * Uses input {@code file} and separates words from definitions and then
     * initializes the dictionary {@code Map}.
     *
     * @param file
     *            input file used to get words and definitions
     * @param dictionary
     *            map that is initialized to take in the words and definitions
     *            from file
     * @param sepSet
     *            set of all separators to look out for
     * @updates dictionary
     * @ensures dictionary = entries(file)
     */
    public static void initializeDictionary(SimpleReader file,
            Map<String, String> dictionary, Set<Character> sepSet) {
        String term = "";
        String definition = "";

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
             * the end of a definition, add the terms so far to the dictionary
             */
            if (temp.length() > 0) {
                /*
                 * If it is word it is returned as true. If not, it is sentence.
                 */
                if (wordOrSentence(temp, sepSet)) {
                    term = temp;
                } else {
                    definition = temp;
                }
            } else {
                /*
                 * Add term and definition to dictionary, reset both strings
                 */
                dictionary.add(term, definition);
                term = "";
                definition = "";
            }
        }
        /*
         * Does not output last term for some reason, so it is done manually
         * here
         */
        if (term.length() > 0 && definition.length() > 0) {
            dictionary.add(term, definition);
        }
    }

    /**
     * Prints the index or homepage, based off of original example provided.
     *
     * @param path
     *            the file path
     * @param wordQ
     *            Queue of all terms
     */
    public static void printIndex(String path, Queue<String> wordQ) {
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
        index.print("<html>\n<head>\n<title>Glossary Index</title>\n</head>\n");
        /*
         * Output header
         */
        index.print(
                "<body>\n<h2>Glossary Index</h2>\n<hr />\n<h3>Index</h3>\n<ul>\n");
        /*
         * Loop thru all entries to list words
         */
        for (String term : wordQ) {
            index.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }
        /*
         * Output footer
         */
        index.print("</ul>\n</body>\n</html>\n");
        /*
         * Close output stream
         */
        index.close();
    }

    /**
     * Prints individual terms from dictionary {@code Map}.
     *
     * @param dictionary
     *            a Map<String, String> that stores all terms with definitions
     * @param path
     *            where to generate files
     */
    public static void printTerms(Map<String, String> dictionary, String path) {
        /*
         * Loop thru all pairs in dictionary (map)
         */
        for (Map.Pair<String, String> term : dictionary) {
            /*
             * find all strings equal to the selected term
             */
            Set<String> terms = findTerms(term.value(), dictionary);
            /*
             * Print out html page for term
             */
            SimpleWriter out = new SimpleWriter1L(
                    path + "/" + term.key() + ".html");
            /*
             * Set up html page with title and header
             */
            out.print("<html>\n<head>\n<title>" + term.key()
                    + "</title>\n</head>\n");
            out.print("<body>\n<h2>\n<b>\n<i>\n<font color=\"red\">"
                    + term.key() + "</font>\n</i>\n</b>\n</h2>\n");
            /*
             * Get definition of term
             */
            String definition = term.value();
            /*
             * Loop thru words of definition, if term found in it, make link to
             * definition
             */
            for (String words : terms) {
                definition = definition.substring(0, definition.indexOf(words))
                        + "\n<a href=\"" + words + ".html\">" + words + "</a>\n"
                        + definition.substring(
                                definition.indexOf(words) + words.length());
            }
            /*
             * Format definition text
             */
            out.print("<blockquote>" + definition + "</blockquote>");
            /*
             * Output footer
             */
            out.println("<hr />");
            out.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
            out.print("</body>\n</html>");
            /*
             * Close output stream
             */
            out.close();
        }
    }

    /**
     * Checks if the given {@code String} is a word or is a sentence. This is
     * used to determine whether the {@code String} should be a word or
     * definition.
     *
     * @param str
     *            the string to check
     * @param sepSet
     *            the Set contain all possible separators
     * @return true if str is one continuous word, if not then str is a sentence
     *         containing spaces
     * @requires 0 < str.length
     */
    public static boolean wordOrSentence(String str, Set<Character> sepSet) {
        /*
         * Asserting str.length() > 0 to fulfill requires clause
         */
        assert str.length() > 0 : "Violation of: str.length > 0";
        /*
         * Initializing boolean if it is word (isWord = true)
         */
        boolean isWord = true;
        /*
         * Loop thru entire string, if sepSet contains a character from it, it
         * has separators meaning it is a sentence (isWord = false)
         */
        for (int i = 0; i < str.length(); i++) {
            if (sepSet.contains(str.charAt(i))) {
                isWord = false;
            }
        }
        /*
         * Returning final results
         */
        return isWord;
    }

    /**
     * Gets the map terms into a queue.
     *
     * @param dictionary
     *            the given Map that stores all terms with definition
     * @return A sorted Queue<String> that stores all terms
     */
    public static Queue<String> mapToQueue(Map<String, String> dictionary) {
        /*
         * Initialize queue
         */
        Queue<String> entries = new Queue1L<String>();
        /*
         * Loop thru dictionary and enqueue terms
         */
        for (Map.Pair<String, String> terms : dictionary) {
            entries.enqueue(terms.key());
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
     * Searches for dictionary terms in a given sentence.
     *
     * @param str
     *            the given sentence
     * @param dictionary
     *            a Map<String, String> that stores all terms
     * @return a Set<String> that stores all terms exist in the given definition
     */
    public static Set<String> findTerms(String str,
            Map<String, String> dictionary) {
        Set<String> terms = new Set1L<String>();
        for (Map.Pair<String, String> term : dictionary) {
            if (str.contains(term.key()) && !terms.contains(term.key())) {
                terms.add(term.key());
            }
        }
        return terms;
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
        final String separatorStr = " \t, ";
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
        Map<String, String> dictionary = new Map1L<String, String>();
        /*
         * Get input file for dictionary. And ask for output file destination.
         */
        out.print("Enter input file: ");
        String file = in.nextLine();
        SimpleReader fileReader = new SimpleReader1L(file);
        initializeDictionary(fileReader, dictionary, separatorSet);
        out.print("Enter output file path: ");
        String path = in.nextLine();
        /*
         * Set up a queue of all terms
         */
        Queue<String> wordQ = mapToQueue(dictionary);
        /*
         * Print index/homepage
         */
        printIndex(path, wordQ);
        /*
         * print the terms
         */
        printTerms(dictionary, path);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
