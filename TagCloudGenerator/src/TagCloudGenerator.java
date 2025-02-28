import java.util.Comparator;
import java.util.Iterator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Reads any text file and outputs a html file that is a TagCloud which means
 * more frequent occurring words appear bigger.
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
     *
     * @param SEPARATORS
     *            these are the characters that I use for my separator method to
     *            find the amount of occurrences of each word.
     *
     * @param maxCount
     *            most amount of times a word occurs.
     *
     * @param minCount
     *            least amount of times a word occurs.
     */
    private static final String SEPARATORS = "\t \n \r , - . ! ? [] ' ; : / () *";

    /**
     * This method sets up and prints out each of the header for the html page.
     *
     * @param output
     *            SimpleWriter of the user output file
     * @param inputFile
     *            String of the name of the user input file
     * @param numWords
     *            the number of words in the input file.
     */
    public static void printHeader(SimpleWriter output, String inputFile,
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
    public static void generateSet(String s, Set<Character> cSet) {
        //For loop that iterates over the string and checks if the set does not
        //Contain the character at the position in String s then adds the char
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
    public static int fontSize(int count, int max, int min) {
        int size;
        int defaultSize = 11;

        if (max != min) {
            size = (count - min) * 37 / (max - min) + defaultSize;
        } else {
            size = defaultSize;
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
    public static String nextWordOrSeparator(String text, int position) {
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
    private static SortingMachine<String> alphabetical(
            Map<String, Integer> wordCounts) {
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

        SortingMachine<String> sm = new SortingMachine1L<>(order);
        Iterator<Pair<String, Integer>> iterator = wordCounts.iterator();
        //Creates a pair of the type Iterator so that we can remove keys

        //While loops that continues while the iterator has more elements
        while (iterator.hasNext()) {
            Pair<String, Integer> tempK = iterator.next();
            //Creates a temp Map to hold the keys and values from the wordCounts Map
            sm.add(tempK.key());
            //Sorts the list in alphabetical order so it can be returned
        }
        //Returns the newly alphabetized list
        sm.changeToExtractionMode();
        return sm;
    }

    /**
     * Filters the queue to the specific N amount of largest words in queue.
     *
     * @param words
     * @param n
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
     * @return the maximum count from words
     */
    private static String findMax(Map<String, Integer> words) {
        Pair<String, Integer> max = words.removeAny();
        words.add(max.key(), max.value());
        for (Pair<String, Integer> x : words) {
            if (x.value() > words.value(max.key())) {
                max = x;
            }
        }
        return max.key();
    }

    /**
     * Finds the minimum count from words.
     *
     * @param words
     * @return the minimum count from words
     */
    private static String findMin(Map<String, Integer> words) {
        Pair<String, Integer> min = words.removeAny();
        words.add(min.key(), min.value());
        for (Pair<String, Integer> x : words) {
            if (x.value() < words.value(min.key())) {
                min = x;
            }
        }
        return min.key();
    }

    /**
     * This method organizes the words and their occurrences then prints each
     * word out and scales text size based on how many times they occur into the
     * user output html page.
     *
     * @param output
     *            SimpleWriter to store the output
     * @param inputLines
     *            String of the words that we are printing the page for
     * @param n
     *            number of words to print from map
     */
    public static void printTagCloud(SimpleWriter output,
            Queue<String> inputLines, int n) {
        //Set of sepChars that will be used with the nextWord method
        Set<Character> sepChars = new Set1L<>();
        generateSet(SEPARATORS, sepChars);

        //New Queue that will be used to store each of the words from the input
        Queue<String> words = new Queue1L<>();

        //While we have inputLines from the queue separate the words into a new Queue
        while (inputLines.length() > 0) {
            String line = inputLines.dequeue();
            int positionLine = 0;
            //While loop that goes over the entire lines length
            while (positionLine < line.length()) {
                String temp = nextWordOrSeparator(line, positionLine);
                positionLine = positionLine + temp.length();
                if (temp.length() > 0 && !sepChars.contains(temp.charAt(0))) {
                    words.enqueue(temp);
                }
            }
        }
        // Generate map with words as keys and occurrences as values
        Map<String, Integer> wordsCounts = new Map1L<>();
        while (words.length() > 0) {
            String key = words.dequeue();

            //If statement that checks if the word is not in the Map
            if (!wordsCounts.hasKey(key)) {
                wordsCounts.add(key, 1);
            } else {
                //Creates a temp Map to store the amount of occurrences of each word
                Map.Pair<String, Integer> temp = wordsCounts.remove(key);
                int value = temp.value();
                value++;
                //Adds the key to the map and its new value that has been incremented
                wordsCounts.add(key, value);
            }
        }

        wordsCounts = shirnkMapToSizeN(wordsCounts, n);
        //Creates a Queue that stores the keys to be alphabetized
        SortingMachine<String> alphaWords = alphabetical(wordsCounts);

        // Max and min for font size
        String max = findMax(wordsCounts);
        String min = findMin(wordsCounts);

        //While loop that continues for each string in the Queue
        while (alphaWords.size() > 0) {
            //Creates a string that dequeues the word from the Queue
            String word = alphaWords.removeFirst();
            //Prints out the word into the table as red and all lowercase
            output.println();
            output.print("<span style=\"cursor:default\" ");
            output.print("class=\"f" + fontSize(wordsCounts.value(word),
                    wordsCounts.value(max), wordsCounts.value(min)) + "\" ");
            output.print("title=\"count: " + wordsCounts.value(word) + "\">");
            output.print(word + "</span>");
        }
    }

    /**
     * This method prints out the bottom of the html page.
     *
     * @param output
     *            SimpleWriter of the user output file
     */
    public static void printFooter(SimpleWriter output) {
        //Prints out the bottom html lines
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
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Asks the user for the exact location Example:"data/importance.txt"
        out.print("Enter the exact location of the input file: ");
        String inputFile = in.nextLine();
        SimpleReader input = new SimpleReader1L(inputFile);

        //Creates a Queue of the lines from the input file
        Queue<String> inputLines = new Queue1L<>();
        //While the input file has a stream of text it continues to add to the Queue
        while (!input.atEOS()) {
            String tempS = input.nextLine();
            inputLines.enqueue(tempS);
        }

        //Asks the user for output file location Example:"data/importance.html"
        out.print("Enter the location you would like the output file in: ");
        String outputFile = in.nextLine();
        SimpleWriter output = new SimpleWriter1L(outputFile);

        //Asks the user how many words will be in in the TagCloud
        out.print("Enter the number of words to be included in the TagCloud: ");
        int numWords = in.nextInteger();

        //Prints out the header of the html document
        printHeader(output, inputFile, numWords);

        //Print out each of the rows in the table
        printTagCloud(output, inputLines, numWords);

        //Prints out the footer of the html document
        printFooter(output);

        input.close();
        output.close();
        in.close();
        out.close();
    }

}
