import machine.*;

import java.util.Scanner;

public class Runner {
    /**
     * Based on user option chosen, prompt user for choice of drink (and amount of cups if applicable)
     *
     * @param option - Action user is trying to do
     * @param c - Coffee Machine instance
     * @param in - Scanner for user input
     * */
    public static void handleUserInput(String option, CoffeeMachine c, Scanner in) {
        boolean isDrinkInvalid = true;

        while (isDrinkInvalid) {
            String preamble = option.equals("buy") ? "What do you want to buy? " : "What drink do you want to make?";
            System.out.print(preamble + c.getPrompt() + ": ");
            try {
                // Prompt user for drink
                Integer drink = in.nextInt();
                isDrinkInvalid = drink <= 0 || drink > c.getDrinksCount();
                if (!isDrinkInvalid) {
                    if (option.equals("buy")) {
                        c.sellDrink(drink);
                    } else if (option.equals("amounts") || option.equals("available")) {
                        // Prompt user for cups desired
                        int amountOfCups = -1;
                        while (amountOfCups < 0) {
                            System.out.print("Write how many cups of " + c.getDrink(drink) +
                                    " you will need: ");
                            amountOfCups = in.nextInt();
                        }
                        System.out.println(option.equals("amounts") ? c.displayAmountsForCups(drink, amountOfCups) :
                                           c.displayAvailableCupsPerSupply(drink, amountOfCups));
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter numbers");
            } finally {
                in.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        CoffeeMachine c = new CoffeeMachine();
        boolean exit = false;
        while (!exit) {
            System.out.print("Write action (buy, fill, take, status, amounts, available, exit): ");
            switch (in.nextLine()) {
                case "buy":
                    handleUserInput("buy", c, in);
                    break;
                case "fill":
                    int water, milk, beans, cups;
                    do {
                        System.out.print("Write how many ml of water do you want to add: ");
                        water = in.nextInt();
                    } while (water < 0);

                    do {
                        System.out.print("Write how many ml of milk do you want to add: ");
                        milk = in.nextInt();
                    } while (milk < 0);

                    do {
                        System.out.print("Write how many grams of coffee beans do you want to add: ");
                        beans = in.nextInt();
                    } while (beans < 0);

                    do {
                        System.out.print("Write how many disposable cups of coffee do you want to add: ");
                        cups = in.nextInt();
                    } while (cups < 0);
                    in.nextLine();
                    c.fillCoffeeMachine(water, milk, beans, cups);
                    break;
                case "take":
                    c.takeMoneyFromMachine();
                    break;
                case "status":
                    System.out.println(c.toString());
                    break;
                case "amounts":
                    handleUserInput("amounts", c, in);
                    break;
                case "available":
                    handleUserInput("available", c, in);
                    break;
                case "exit":
                    System.out.println("Closing system down...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command, try again.");
                    break;
            }
            System.out.println();
        }
    }
}