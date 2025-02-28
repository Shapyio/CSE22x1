/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class PhoneNumber {

    /**
     * The phone number representation.
     */
    private String rep;

    /**
     * Constructor. {@code pNum} must be in the form "XXX-XXXX" where each "X"
     * is a digit '0'-'9'.
     */
    public PhoneNumber(String pNum) {
        this.rep = pNum;
    }

    /**
     * Hash function for phone numbers. Totals number for unique hash.
     */
    @Override
    public int hashCode() {
        int total = 0;
        for (int i = 0; i < this.rep.length(); i++) {
            if (this.rep.charAt(i) != '-') {
                total += Character.digit(this.rep.charAt(i), 9);
            }
        }
        return total;
    }
}
