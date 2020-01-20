package coders;

interface Evaluator {
    Character evaluate(char c, int shift, int multiplier);
}

public abstract class Coder implements Evaluator {
    protected final Integer ENCRYPT_CONST = 1;
    protected final Integer DECRYPT_CONST = -1;

    /*
    * @param character -
    * @param min -
    * @param max -
    * @return
    */
    public Character modify(int character, Character min, Character max) {
        int tmp = character;
        if (tmp < 0) tmp += (max - min + 1);
        tmp = (tmp % (max - min + 1)) + min;
        return (char) tmp;
    }

    /*
    * @param ciphertext -
    * @param shift -
    * @param multiplier -
    * @return
    */
    protected String action(String ciphertext, int shift, int multiplier) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            sb.append(evaluate(ciphertext.charAt(i), shift, multiplier));
        }
        return sb.toString();
    }

    /*
    * @param ciphertext -
    * @param shift -
    * @return
    */
    public String encrypt(String ciphertext, int shift) {
        return action(ciphertext, shift, ENCRYPT_CONST);
    }

    /*
    * @param ciphertext -
    * @param shift -
    * @return
    */
    public String decrypt(String ciphertext, int shift) {
        return action(ciphertext, shift, DECRYPT_CONST);
    }
}