import components.sequence.Sequence;
import components.tree.Tree;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class HW21 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW21() {
    }

    /**
     * Returns the size of the given {@code Tree<T>}.  
     *
     * @param <T>
     *                        the type of the {@code Tree} node labels  
     * @param t
     *                        the {@code Tree} whose size to return  
     * @return the size of the given {@code Tree}  
     * @ensures size = |t|  
     */
    public static <T> int sizeRecurse(Tree<T> t) {
        int size = 0;
        Sequence<Tree<T>> s = t.newSequenceOfTree();
        Sequence<Tree<T>> sCopy = t.newSequenceOfTree();
        T root = t.disassemble(s);

        //While subtree sequence not empty, count size of subtrees
        while (s.length() > 0) {
            //Add up sizes of all subtrees
            size += sizeRecurse(s.entry(0));
            //Add entry to new sequence
            sCopy.add(0, s.remove(0));
        }

        size++; //Always add one for root

        //Flip sequence since entries added backwards and reassemble
        sCopy.flip();
        t.assemble(root, sCopy);

        return size;
    }

    /**
     * Returns the size of the given {@code Tree<T>}.  
     *
     * @param <T>
     *                        the type of the {@code Tree} node labels  
     * @param t
     *                        the {@code Tree} whose size to return  
     * @return the size of the given {@code Tree}  
     * @ensures size = |t|  
     */
    public static <T> int sizeIterate(Tree<T> t) {
        int size = 0;
        Sequence<Tree<T>> s = t.newSequenceOfTree();
        T root = t.disassemble(s);

        for (int i = 0; i < s.length(); i++) {
            size += sizeIterate(s.entry(i));
        }

        size++; //Always add one for root

        //Reassemble
        t.assemble(root, s);

        return size;
    }

    /**
     * Returns the height of the given {@code Tree<T>}.  
     *
     * @param <T>
     *                        the type of the {@code Tree} node labels  
     * @param t
     *                        the {@code Tree} whose height to return  
     * @return the height of the given {@code Tree}  
     * @ensures height = ht(t)  
     */
    public static <T> int height(Tree<T> t) {
        int height = 0;
        Sequence<Tree<T>> s = t.newSequenceOfTree();
        Sequence<Tree<T>> sCopy = t.newSequenceOfTree();
        T root = t.disassemble(s);

        //If root not empty, tree has height of atleast 1
        if (!root.toString().isEmpty()) {
            //While subtree sequence not empty, count size of subtrees
            while (s.length() > 0) {
                //If subtree height greater than current height,
                //that is max height of tree
                if (height(s.entry(0)) > height) {
                    height = height(s.entry(0));
                }
                //Add entry to new sequence
                sCopy.add(0, s.remove(0));
            }
            height++;
        }

        //Flip sequence since entries added backwards and reassemble
        sCopy.flip();
        t.assemble(root, sCopy);

        return height;
    }

    /**
     * Returns the largest integer in the given {@code Tree<Integer>}.
     *
     * @param t
     *                          the {@code Tree<Integer>} whose largest integer
     *            to return  
     * @return the largest integer in the given {@code Tree<Integer>}  
     * @requires |t| > 0
     * @ensures <pre>
     * max is in labels(t)  and
     * for all i: integer where (i is in labels(t)) (i <= max)
     * </pre>  
     */
    public static int max(Tree<Integer> t) {
        Sequence<Tree<Integer>> s = t.newSequenceOfTree();
        Sequence<Tree<Integer>> sCopy = t.newSequenceOfTree();
        Integer root = t.disassemble(s);
        int max = root;

        //While subtree sequence not empty, count size of subtrees
        while (s.length() > 0) {
            //If subtree height greater than current height,
            //that is max height of tree
            if (max(s.entry(0)) > max) {
                max = max(s.entry(0));
            }
            //Add entry to new sequence
            sCopy.add(0, s.remove(0));
        }

        //Flip sequence since entries added backwards and reassemble
        sCopy.flip();
        t.assemble(root, sCopy);

        return max;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

    }

}
