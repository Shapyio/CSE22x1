import java.util.Comparator;

import components.queue.Queue;

/**
 * Put a short phrase describing the program here.
 *
 * @author Shapiy S.
 *
 */
public final class HW15 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HW15() {
    }

    /**
     * Inserts the given {@code T} in the {@code Queue<T>} sorted according to
     * the given {@code Comparator<T>} and maintains the {@code Queue<T>}
     * sorted.  
     *
     * @param <T>
     *               type of {@code Queue} entries
     * @param q
     *               the {@code Queue} to insert into  
     * @param x
     *               the {@code T} to insert  
     * @param order
     *               the {@code Comparator} defining the order for {@code T}  
     * @updates q
     * @requires <pre>
     * IS_TOTAL_PREORDER([relation computed by order.compare method])  and
     * IS_SORTED(q, [relation computed by order.compare method])
     * </pre>  
     * @ensures <pre>
     * perms(q, #q * <x>)  and
     * IS_SORTED(q, [relation computed by order.compare method])
     * </pre>  
     */
    private static <T> void insertInOrder(Queue<T> q, T x,
            Comparator<T> order) {
        Queue<T> qCopy = q.newInstance();

        if (q.length() != 0) {
            T front = q.dequeue();

            while (order.compare(x, front) < 0) {
                qCopy.enqueue(front);
                front = q.dequeue();
            }
            q.enqueue(front);
            q.enqueue(x);
            qCopy.append(q);
            q.append(qCopy);
        } else {
            q.enqueue(x);
        }
    }

    /**
     * Sorts {@code this} according to the ordering provided by the
     * {@code compare} method from {@code order}.
     *
     * @param order
     *            ordering by which to sort
     * @updates this
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * perms(this, #this)  and
     * IS_SORTED(this, [relation computed by order.compare method])
     * </pre>
     */
    public void sort(Comparator<T> order) {
        Queue<T> qCopy = q.newInstance();

        while (this.)
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Queue<int> q = new Queue1L<int>();
    }

}
