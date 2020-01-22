import bot.*;

import java.util.Scanner;

public class Runner {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        SimpleChattyBot scb = new SimpleChattyBot();

        // Bot's initial preamble with user
        System.out.println("Please, remind me of your name.");
        scb.promptForName(in.nextLine());

        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7.");
        try {
            int mod3 = in.nextInt();
            int mod5 = in.nextInt();
            int mod7 = in.nextInt();
            scb.promptGuessAge(mod3, mod5, mod7);
        } catch (NumberFormatException e) {
            System.out.println("Please enter numbers");
        } finally {
            in.nextLine();
        }

        // -- PROCESS USER ARGUMENTS --
        // Prompt users to do actions
        boolean exit = false;
        while (!exit) {
            System.out.print("What would you like to do? (count, test, exit): ");
            switch (in.next()) {
                case "count":
                    System.out.println("Now I will prove to you that I can count to any number you want.");
                    int count = in.nextInt();
                    while (count < 0) {
                        try {
                            count = in.nextInt();
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter numbers");
                        } finally {
                            in.nextLine();
                        }
                    }
                    scb.promptCountToNumber(count);
                    break;
                case "test":
                    System.out.println("Let's test your programming knowledge.");
                    // select a random number representing # of q's
                    int selection = (int) Math.round(Math.random()*5);
                    System.out.println(scb.promptTestKnowledge(selection)); // print question
                    int ans = in.nextInt();
                    while (ans != scb.checkAnswer(selection)) {
                        try {
                            System.out.println("Please, try again.");
                            ans = in.nextInt();
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter numbers.");
                        } finally {
                            in.nextLine();
                        }
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Error: incorrect prompt entered");
                    break;
            }
        }
        scb.promptGoodbye();
    }
}