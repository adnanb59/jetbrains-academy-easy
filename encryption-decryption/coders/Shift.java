package coders;

public class Shift extends Coder {
    private final Character LOWER_MIN = 'a';
    private final Character LOWER_MAX = 'z';
    private final Character UPPER_MIN = 'A';
    private final Character UPPER_MAX = 'Z';

    /*
     * @param c -
     * @param shift -
     * @param multiplier -
     * @return
     */
    @Override
    public Character evaluate(char c, int shift, int multiplier) {
        if (Character.isLetter(c)) {
            char local_min = (c >= 'a') ? LOWER_MIN : UPPER_MIN;
            char local_max = (c >= 'a') ? LOWER_MAX : UPPER_MAX;
            return modify(c - local_min + shift * multiplier, local_min, local_max);
        } else return c;
    }
}
