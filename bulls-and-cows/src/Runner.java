import java.util.*;

public class Runner {
    /**
     * Play game of Bulls and Cows until user wins
     *
     * @param code - secret code
     * @param in - Scanner object for user input
     */
    private static void play(String code, Scanner in) {
        int turn = 1;
        boolean found = false;
        while (!found) {
            System.out.printf("Turn %d:\n", turn++);
            String guess = in.nextLine().trim();
            if (guess.length() != code.length()) System.out.println("Error: Incorrect code length.");
            else {
                try {
                    Map<String, Integer> grade = grade(code, guess);
                    System.out.print("Grade: ");
                    if (grade.get("bulls") + grade.get("cows") == 0) System.out.print("None");
                    else {
                        if (grade.get("bulls") != 0) System.out.printf("%d bull(s)", grade.get("bulls"));
                        if (grade.get("bulls") != 0 && grade.get("cows") != 0) System.out.print(" and ");
                        if (grade.get("cows") != 0) System.out.printf("%d cow(s)", grade.get("cows"));
                    }
                    System.out.println();
                    found = grade.get("bulls") == code.length();
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter numbers.");
                }
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    /**
     * Grade the user guess against the secret code and return a map containing the bulls and cows scores.
     *
     * @param c - code
     * @param g - user attempted guess at code
     * @return Grade of user guess against code (in bulls and cows)
     */
    private static Map<String, Integer> grade(String c, String g) {
        Set<Character> visited = new HashSet<>();
        // Add characters of code to set to compare guess to later
        // Helps when the guess has characters that show up later in the code
        for (int i = 0; i < c.length(); i++) {
            visited.add(c.charAt(i));
        }

        int cows = 0, bulls = 0;
        // Go through characters in the guess
        // - if they match with the code at that point => bull
        // - if they match with the code at some other point => cow
        for (int i = 0; i < c.length(); i++) {
            if (c.charAt(i) == g.charAt(i)) bulls++;
            else if (visited.contains(g.charAt(i))) {
                cows++;
                // Remove character as it's been accounted for
                visited.remove(g.charAt(i));
            }
        }
        return Map.of("bulls", bulls, "cows", cows);
    }

    /**
     * Create (pseudo-randomly) the secret code of _length_ length with _symbols_ characters available.
     *
     * @param length - length of access codes
     * @param symbols - size of domain of characters available for code
     * @return Secret code for Bulls & Cows game
     */
    private static String create(int length, int symbols) {
        Random r = new Random(System.currentTimeMillis());
        StringBuilder result = new StringBuilder();
        Set<Integer> unavailable = new HashSet<>();

        // Use set to maintain characters added to code (since unique, no repeats)
        while (length > 0) {
            // All the numbers will be used regardless of # of symbols
            // Range of letters is determined by symbols though
            int number = r.nextInt(Math.max(symbols, 10));
            if (!unavailable.contains(number)) {
                length--;
                if (number < 10) result.append(Character.toString('0' + number));
                else result.append(Character.toString('a' + (number - 10)));
                unavailable.add(number);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int digits = -1, symbols = -1;

        while (digits <= 0 || digits > 36) {
            try {
                System.out.println("Input the length of the secret code:");
                digits = Integer.parseInt(in.nextLine().trim());
                if (digits <= 0) System.out.println("Error: can't generate a secret number with a length of " + digits + " because it's an invalid size.");
                else if (digits > 36) System.out.println("Error: can't generate a secret number with a length of " + digits + " because there aren't enough unique digits.");
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.\n", e.getCause());
            }
        }

        while (symbols <= 0 || symbols > 36 || symbols < digits) {
            try {
                System.out.println("Input the number of possible symbols in the code:");
                symbols = Integer.parseInt(in.nextLine().trim());
                if (symbols <= 0) System.out.println("Error: can't generate a secret number with " + symbols + " symbols because it's an invalid value.");
                else if (symbols > 36) System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                else if (symbols < digits) System.out.printf("Error: it's not possible to generate a code of length %d with %d unique symbols.\n", digits, symbols);
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.\n", e.getCause());
            }
        }

        String secret_code = create(digits, symbols);

        System.out.print("The secret is prepared: ");
        for (int i = 0; i < digits; i++) System.out.print("*");
        System.out.print(" (0-9");
        if (symbols > 10) System.out.print(", a");
        if (symbols > 11) System.out.printf("-%c", 'a' + (symbols-11));
        System.out.println(")");
        System.out.println("Okay, let's start a game!");

        play(secret_code, in);
        in.close();
    }
}
