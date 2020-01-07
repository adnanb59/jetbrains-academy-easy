package bot;

import java.util.Scanner;

public class SimpleChattyBot {
    Scanner in;
    private final String NAME = "Aid";
    private final int CREATED_YEAR = 2020;

    public SimpleChattyBot() {
        in = new Scanner(System.in);
        promptGreeting();
        promptForName();
        promptGuessAge();
    }

    private void promptGreeting() {
        System.out.println("Hello! My name is " + NAME + ".");
        System.out.println("I was created in " + CREATED_YEAR + ".");
    }

    private void promptForName() {
        System.out.println("Please, remind me of your name.");
        System.out.println("What a great name you have, " + in.nextLine() + "!");
    }

    private void promptGuessAge() {
        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7.");
        System.out.println("Your age is " + ((in.nextInt() * 70 + in.nextInt() * 21 + in.nextInt() * 15) % 105) + "; that's a good time to start programming!");
    }

    private void promptCountToNumber() {
        System.out.println("Now I will prove to you that I can count to any number you want.");
        int stop = in.nextInt();
        for (int i = 0; i <= stop; i++) {
            System.out.println(i + "!");
        }
    }

    private void promptTestKnowledge() {
        System.out.println("Let's test your programming knowledge.");
        int selection = (int) Math.round(Math.random()*5);
        int[] solns = {2, 1, 4 ,3, 3};
        
        switch (selection) {
            case 0:
                System.out.println("How is a ternary operator written?\n" + "1. A -> B | C\n2. A ? X : Y\n3. if A then B else C\n4. B if A then C");
                break;
            case 1:
                System.out.println("Which data modifier will provide access to EVERYBODY?\n1. public\n2. private\n3. no modifier\n4. private");
                break;
            case 2:
                System.out.println("Which data type is good for 32 bit decimals?\n1. short\n2. bigdecimal\n3. decimal\n4. float");
                break;
            case 3:
                System.out.println("Which data structure is good for indexed access?\n1. map\n2. set\n3. array\n4. linked list");
                break;
            case 4:
                System.out.println("What does this describe? Creating a subclass by deriving from another class, thus reusing fields and methods from parent.\n" +
                                   "1. encapsulation\n2. abstraction\n3. inheritance\n4. polymorphism");
                break;
            default:
                break;
        }

        while (in.nextInt() != solns[selection]) {
            System.out.println("Please, try again.");
        }
    }

    private void promptGoodbye() {
        System.out.println("Completed, have a nice day!");
    }

    public void interact() {
        boolean exit = false;
        while (!exit) {
            System.out.print("What would you like to do? (count, test, exit): ");
            switch (in.next()) {
                case "count":
                    promptCountToNumber();
                    break;
                case "test":
                    promptTestKnowledge();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    break;
            }
        }
        promptGoodbye();
    }
}