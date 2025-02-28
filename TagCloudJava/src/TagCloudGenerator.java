import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

/**
 * Reads any text file and outputs a tag cloud composed of the top N amount of
 * words.
 *
 * @author Daniel Belyea & Shapiy S.
 *
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * @param SEPARATORS
     *            these are the characters that I use for my separator method to
     */
    private static final String SEPARATORS = " ,    .!--()[]?";

    /**
     * @param DEFUALT_SIZE
     *            the default size of the words in the tag cloud
     */
    private static final int DEFAULT_SIZE = 11;

    /**
     * @param MULTI
     *            what we multiply by in the font size method
     */
    private static final int MULTI = 37;

    /**
     * This method sets up and prints out each of the header for the html page.
     *
     * @param output
     *            PrintWriter to write/display text to output file
     * @param inputFile
     *            String of the name of the user input file
     * @param numWords
     *            the number of words in the input file
     */
    private static void printHeader(PrintWriter output, String inputFile,
            int numWords) {
        //Prints the header of the document and sets up the html page
        output.println("<html>");
        output.println("<head>");
        output.println("<title>" + "Top " + numWords + " words in " + inputFile
                + "</title>");
        output.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/"
                        + "web-sw2/assignments/projects/tag-cloud-generator/data/"
                        + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println(
                "<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println("</head>");
        output.println("<body>");
        output.println("<h2>" + "Top " + numWords + " words in " + inputFile
                + "</h2>");
        output.println("<hr>");
        output.println("<div class=\"cdiv\">");
        output.println("<p class=\"cbox\">");
    }

    /**
     * Generates a set of characters from a String to a set of Characters that
     * are all different.
     *
     * @param s
     *            given String from method using this method
     * @param cSet
     *            set of Strings given from the method that uses this method
     */
    private static void generateTerms(String s, Set<Character> cSet) {
        /*
         * For loop that iterates over the string and checks if the set does not
         * Contain the character at the position in String s then adds the char
         */
        for (int i = 0; i < s.length(); i++) {
            if (!cSet.contains(s.charAt(i))) {
                cSet.add(s.charAt(i));
            }
        }
    }

    /**
     * Generates font size.
     *
     * @param count
     *            given String from method using this method
     * @param max
     *            the maximum count of words
     * @param min
     *            the minimum count of words
     * @return size the font size
     */
    private static int fontSize(int count, int max, int min) {
        int size;

        if (max != min) {
            size = (count - min) * MULTI / (max - min) + DEFAULT_SIZE;
        } else {
            size = DEFAULT_SIZE;
        }
        return size;
    }

    /**
     * This method identifies spaces or characters that separate the terms.
     *
     * @param text
     *            String that contains the definition of a term
     * @param position
     *            int value of the position of the WordOrSeparator
     * @return returnText
     */
    private static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: definition is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text
                .length() : "Violation of: position < |definition|";
        //nextWordOrSeparator from Tokenizer lab
        int end = position + 1;

        while (end < text.length() && (((SEPARATORS
                .indexOf(text.charAt(end)) >= 0
                && SEPARATORS.indexOf(text.charAt(position)) >= 0))
                || ((SEPARATORS.indexOf(text.charAt(end)) <= -1
                        && SEPARATORS.indexOf(text.charAt(position)) <= -1)))) {
            end++;
        }
        return text.substring(position, end).toLowerCase();
    }

    /**
     * This method alphabetizes a map that has the words as keys and occurrences
     * as values.
     *
     * @param wordCounts
     *            map that contains keys as the strings of words and values as
     *            the integers that say how often the words occur.
     * @return a newly created Queue that has alphabetized words from the input
     *
     */
    private static List<String> alphabetical(Map<String, Integer> wordCounts) {
        /**
         * Comparator<String> implementation to be used in all test cases.
         * Compare {@code String}s in lexicographic order.
         */
        class StringLT implements Comparator<String> {

            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }

        }

        /**
         * Comparator instance to be used in all test cases.
         */
        final StringLT order = new StringLT();

        Set<Entry<String, Integer>> set = wordCounts.entrySet();
        // Creates a pair of the type Iterator so that we can remove keys
        Iterator<Entry<String, Integer>> iterator = set.iterator();
        // Creates a list to put words into and sort words alphabetically
        List<String> list = new ArrayList<>();
        //While loops that continues while the iterator has more elements
        while (iterator.hasNext()) {
            Entry<String, Integer> tempK = iterator.next();
            //Creates a temp Map to hold the keys and values from the wordCounts Map
            list.add(tempK.getKey());
            //Sorts the list in alphabetical order so it can be returned
        }
        //Returns the newly alphabetized list
        list.sort(order);
        return list;
    }

    /**
     * Filters the queue to the specific N amount of largest words in queue.
     *
     * @param words
     *            the HashMap where the words and their counts are stored.
     * @param n
     *            the size to shrink the map to
     * @return the filtered queue of size N, in alphabetical order
     */
    private static Map<String, Integer> shirnkMapToSizeN(
            Map<String, Integer> words, int n) {
        String min = "";
        // If n > keys length, then all keys would be included and we can just return keys
        if (n < words.size()) {
            // While words size != n, keep finding and removing minimum count
            while (words.size() != n) {
                min = findMin(words);
                words.remove(min);
            }
        }
        return words;
    }

    /**
     * Finds the maximum count from words.
     *
     * @param words
     *            the map of words to sift thru for max value
     * @return the maximum count from words
     */
    private static String findMax(Map<String, Integer> words) {
        Set<Entry<String, Integer>> set = words.entrySet();
        Iterator<Entry<String, Integer>> it = set.iterator();
        Entry<String, Integer> min = it.next();
        while (it.hasNext()) {
            Entry<String, Integer> x = it.next();
            if (x.getValue() > min.getValue()) {
                min = x;
            }
        }
        return min.getKey();
    }

    /**
     * Finds the minimum count from words.
     *
     * @param words
     *            the map of words to sift thru for max value
     * @return the minimum count from words
     */
    private static String findMin(Map<String, Integer> words) {
        Set<Entry<String, Integer>> set = words.entrySet();
        Iterator<Entry<String, Integer>> it = set.iterator();
        Entry<String, Integer> min = it.next();
        while (it.hasNext()) {
            Entry<String, Integer> x = it.next();
            if (x.getValue() < min.getValue()) {
                min = x;
            }
        }
        return min.getKey();
    }

    /**
     * This method organizes the words and their occurrences then prints each
     * word out and scales text size based on how many times they occur into the
     * user output html page.
     *
     * @param output
     *            PrintWriter to write/display text to output file
     * @param inputLines
     *            String of the words that we are printing the page for
     * @param n
     *            number of words to print from map
     */
    private static void printTagCloud(PrintWriter output,
            Queue<String> inputLines, int n) {
        //Set of sepChars that will be used with the nextWord method
        Set<Character> sepChars = new HashSet<>();
        generateTerms(SEPARATORS, sepChars);

        //New Queue that will be used to store each of the words from the input
        Queue<String> words = new LinkedList<>();

        //While we have inputLines from the queue separate the words into a new Queue
        while (!inputLines.isEmpty()) {
            String line = inputLines.remove();
            int positionLine = 0;
            //While loop that goes over the entire lines length
            while (positionLine < line.length()) {
                String temp = nextWordOrSeparator(line, positionLine);
                positionLine = positionLine + temp.length();
                if (temp.length() > 0 && !sepChars.contains(temp.charAt(0))) {
                    words.add(temp);
                }
            }
        }
        // Generate map with words as keys and occurrences as values
        Map<String, Integer> wordsCounts = new HashMap<>();
        while (!words.isEmpty()) {
            String key = words.remove();

            //If statement that checks if the word is not in the Map
            if (!wordsCounts.containsKey(key)) {
                wordsCounts.put(key, 1);
            } else {
                //Creates a temp Map to store the amount of occurrences of each word
                int value = wordsCounts.remove(key);
                value++;
                //Adds the key to the map and its new value that has been incremented
                wordsCounts.put(key, value);
            }
        }

        wordsCounts = shirnkMapToSizeN(wordsCounts, n);
        //Creates a Queue that stores the keys to be alphabetized
        List<String> alphaWords = alphabetical(wordsCounts);

        // Max and min for font size
        String max = findMax(wordsCounts);
        String min = findMin(wordsCounts);

        //While loop that continues for each string in the Queue
        while (alphaWords.size() > 0) {
            //Creates a string that dequeues the word from the Queue
            String word = alphaWords.remove(0);
            //Prints out the word into the table as red and all lowercase
            output.println();
            output.print("<span style=\"cursor:default\" ");
            output.print(
                    "class=\"f"
                            + fontSize(wordsCounts.get(word),
                                    wordsCounts.get(max), wordsCounts.get(min))
                            + "\" ");
            output.print("title=\"count: " + wordsCounts.get(word) + "\">");
            output.print(word + "</span>");
        }
    }

    /**
     * This method prints out the bottom of the html page.
     *
     * @param output
     *            PrintWriter to write/display text to output file
     */
    private static void printFooter(PrintWriter output) {
        output.println("</p>");
        output.println("</div>");
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * File IO stream.
         */
        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in));
        // Entering input file
        System.out.print("Enter the exact location of the input file: ");
        String inPath = "";
        try {
            inPath = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error entering input file path");
        }
        // Entering output file
        System.out.print(
                "Enter the location you would like the output file in: ");
        String outPath = "";
        try {
            outPath = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error entering output file path");
        }
        /*
         * Creating files.
         */
        BufferedReader in;
        PrintWriter out;
        try {
            in = new BufferedReader(new FileReader(inPath));
            out = new PrintWriter(new BufferedWriter(new FileWriter(outPath)));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(
                    "Error opening input file or creating output file");
            return;
        }
        /*
         * Main body of program
         */
        //Creates a Queue of the lines from the input file
        Queue<String> inputLines = new LinkedList<>();
        //While the input file has a stream of text it continues to add to the Queue
        try {
            while (in.ready()) {
                String tempS = in.readLine();
                inputLines.add(tempS);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error transfering input text lines to queue");
        }
        //Asks the user how many words will be in in the TagCloud
        System.out.print(
                "Enter the number of words to be included in the TagCloud: ");
        int numWords = 0;
        try {
            numWords = Integer.parseInt(input.readLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Error formatting string to integer");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading input stream");
        }
        //Prints out the header of the html document
        printHeader(out, inPath, numWords);

        //Print out each of the rows in the table
        printTagCloud(out, inputLines, numWords);

        //Prints out the footer of the html document
        printFooter(out);
        /*
         * Close input and output streams
         */
        try {
            in.close();
            out.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error closing IO stream");
        }
    }
}
