import components.binarytree.BinaryTree;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class HW14 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW14() {
    }

    /**
     * Returns whether {@code x} is in {@code t}.  
     *
     * @param <T>
     *            type of {@code BinaryTree} labels  
     * @param t
     *            the {@code BinaryTree} to be searched  
     * @param x
     *            the label to be searched for  
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)  
     * @ensures isInTree = (x is in labels(t))  
     */
    public static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        boolean inTree = false;
        BinaryTree<T> lt = t.newInstance();
        BinaryTree<T> rt = t.newInstance();

        T root = t.disassemble(lt, rt);
        if (root.compareTo(x) > 0) {
            inTree = isInTree(rt, x);
        } else if (root.compareTo(x) < 0) {
            inTree = isInTree(lt, x);
        } else if (root.compareTo(x) == 0) {
            inTree = true;
        }
        t.assemble(root, lt, rt);

        return inTree;
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
