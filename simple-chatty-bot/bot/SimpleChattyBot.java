package bot;

public class SimpleChattyBot {
    private final String NAME = "Aid";
    private final int CREATED_YEAR = 2020;

    /** SimpleChattyBot constructor, call greeting once created */
    public SimpleChattyBot() {
        promptGreeting();
    }

    /** Print greeting to user informing them about bot */
    private void promptGreeting() {
        System.out.println("Hello! My name is " + NAME + ".");
        System.out.println("I was created in " + CREATED_YEAR + ".");
    }

    /**
    * Provide user with a personalized message
    *
    * @param name - Name of user
    */
    public void promptForName(String name) {
        System.out.println("What a great name you have, " + name + "!");
    }

    /**
    * Guess user's age given the values provided to the function
    * and send a message telling them to start programming
    *
    * @param m3 - age of user mod 3
    * @param m5 - age of user mod 5
    * @param m7 - age of user mod 7
    */
    public void promptGuessAge(int m3, int m5, int m7) {
        System.out.println("Your age is " + ((m3 * 70 + m5 * 21 + m7 * 15) % 105) +
                           "; that's a good time to start programming!");
    }

    /**
    * Count to a number and display it as the program iterates
    *
    * @param count - Number to count to
    */
    public void promptCountToNumber(int count) {
        for (int i = 0; i <= count; i++) {
            try {
                System.out.println(i + "!");
                wait(10L);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
    * Return the question representing the number passed in by user (there's a cap on the number from 0-4)
    *
    * @param q - Number representing a question
    * @return String representing the question
    */
    public String promptTestKnowledge(int q) {
        switch (q) {
            case 0:
                return "How is a ternary operator written?\n" +
                        "1. A -> B | C\n2. A ? X : Y\n3. if A then B else C\n4. B if A then C";
            case 1:
                return "Which data modifier will provide access to EVERYBODY?" +
                       "\n1. public\n2. private\n3. no modifier\n4. private";
            case 2:
                return "Which data type is good for 32 bit decimals?\n1. short\n2. bigdecimal\n3. decimal\n4. float";
            case 3:
                return "Which data structure is good for indexed access?\n1. map\n2. set\n3. array\n4. linked list";
            case 4:
                return "What does this describe? Creating a subclass by deriving from another class, " +
                       "thus reusing fields and methods from parent.\n" +
                       "1. encapsulation\n2. abstraction\n3. inheritance\n4. polymorphism";
            default:
                return "No question";
        }
    }

    /**
    * Provide answer to question specified by user
    *
    * @param question - Number representing question the user is currently answering
    * @return The number representing the answer for the specified question
    */
    public int checkAnswer(int question) {
        int[] solns = {2, 1, 4 ,3, 3};
        return solns[question];
    }

    /** Provide user with a goodbye message */
    public void promptGoodbye() {
        System.out.println("Completed, have a nice day!");
    }
}