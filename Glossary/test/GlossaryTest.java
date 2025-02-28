import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * Different test for various methods of Glossary.
 *
 * @author Safi
 *
 */
public class GlossaryTest {
    /**
     * Test for the generateElements method.
     */
    @Test
    public void generateElementsTest() {
        final String setStr = " a, c, b, d";
        Set<Character> actualSet = new Set1L<>();
        Glossary.generateElements(setStr, actualSet);

        Set<Character> expectedSet = new Set1L<>();
        expectedSet.add('a');
        expectedSet.add('c');
        expectedSet.add('b');
        expectedSet.add('d');
    }

    /**
     * Test for the initializeDictionary method.
     */
    @Test
    public void initializeDictionaryTest() {
        Set<Character> separatorSet = new Set1L<>();
        separatorSet.add('\t');
        separatorSet.add(' ');

        String file = "data/test.txt";
        SimpleReader fileReader = new SimpleReader1L(file);

        Map<String, String> actualMap = new Map1L<String, String>();
        Glossary.initializeDictionary(fileReader, actualMap, separatorSet);

        Map<String, String> expectedMap = new Map1L<String, String>();
        expectedMap.add("a", "for apple");
        expectedMap.add("b", "for banana");
        expectedMap.add("c", "for carrot");
        expectedMap.add("d", "for dill");

        assertEquals(expectedMap, actualMap);
    }

    /**
     * Test for the printIndex method.
     */
    @Test
    public void printIndexTest() {
        System.out.println(
                "Cannot test printIndex. It is print function, nothing to test.");
    }

    /**
     * Test for the printTerms method.
     */
    @Test
    public void printTermsTest() {
        System.out.println(
                "Cannot test printTerms. It is print function, nothing to test.");
    }

    /**
     * Test for the wordOrSentenceTest method.
     */
    @Test
    public void wordOrSentenceTest1() {
        Set<Character> separatorSet = new Set1L<>();
        separatorSet.add('\t');
        separatorSet.add(' ');

        String str = "hello";

        boolean actualBool = Glossary.wordOrSentence(str, separatorSet);

        assertTrue(actualBool);
    }

    /**
     * Test for the wordOrSentenceTest method.
     */
    @Test
    public void wordOrSentenceTest2() {
        Set<Character> separatorSet = new Set1L<>();
        separatorSet.add('\t');
        separatorSet.add(' ');

        String str = "hello world how are you";

        boolean actualBool = Glossary.wordOrSentence(str, separatorSet);

        assertFalse(actualBool);
    }

    /**
     * Test for the generateElements method.
     */
    @Test
    public void mapToQueueTest() {
        Map<String, String> map = new Map1L<String, String>();
        map.add("a", " for apple");
        map.add("c", " for carrot");
        map.add("b", " for banana");

        Queue<String> actualQ = Glossary.mapToQueue(map);

        Queue<String> expectedQ = new Queue1L<String>();
        expectedQ.enqueue("a");
        expectedQ.enqueue("b");
        expectedQ.enqueue("c");

        assertEquals(expectedQ, actualQ);
    }

    /**
     * Test for the findTerms method.
     */
    @Test
    public void findTermsTest() {
        String alphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z";

        Map<String, String> map = new Map1L<String, String>();
        map.add("x", "for xylophone");
        map.add("k", "for kangaroo");
        map.add("r", "for rabbit");
        map.add("a", "for aligator");
        map.add("fail", "it did not");

        Set<String> expectedSet = new Set1L<String>();
        expectedSet.add("x");
        expectedSet.add("k");
        expectedSet.add("r");
        expectedSet.add("a");

        Set<String> actualSet = Glossary.findTerms(alphabet, map);

        assertEquals(expectedSet, actualSet);
    }
}
