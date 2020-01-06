package machine;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner; 

class CoffeeMachine {
    private double money;
    private int water_ml, milk_ml, coffeeBeans_g, cups;
    private List<Drink> drinks;
    private String prompt = "";
    private Scanner in;

    public CoffeeMachine() {
        this.in = new Scanner(System.in);
        this.water_ml = 0;
        this.milk_ml = 0;
        this.coffeeBeans_g = 0;
        this.cups = 0;
        this.money = 0;

        this.drinks = new ArrayList<Drink>();
        drinks.add(DrinkFactory.makeDrink("Espresso"));
        drinks.add(DrinkFactory.makeDrink("Latte"));
        drinks.add(DrinkFactory.makeDrink("Cappuccino"));
        drinks.add(DrinkFactory.makeDrink("Coffee"));
        
        for (int i = 0; i < drinks.size(); i++) {
            prompt += String.format("%d", i+1) + " - " + drinks.get(i).toString();
            if (i != drinks.size() - 1) {
                prompt += ", ";
            }
        }
    }

    private boolean checkStock(Drink curr) {
        boolean ret = false;
        if (curr.WATER_ML > this.water_ml) {
            System.out.println("Insufficient amount of water for drink.");
        } else if (curr.MILK_ML > this.milk_ml) {
            System.out.println("Insufficient amount of milk for drink.");
        } else if (curr.COFFEE_BEANS_G > this.coffeeBeans_g) {
            System.out.println("Insufficient amount of coffee beans for drink.");
        } else if (cups == 0) {
            System.out.println("Need to restock cups.");
        } else {
            ret = true;
        }
        
        return ret;
    }

    private void makeDrink() {
        System.out.println("\nStarting to make the drink...");
        System.out.println("Grinding coffee beans...");
        System.out.println("Boiling water...");
        System.out.println("Mixing boiled water with crushed coffee beans...");
        System.out.println("Pouring coffee into the cup...");
        System.out.println("Pouring some milk into the cup...");
        System.out.println("Drink is ready!\n");
    }

    private Drink getDrink(String initialPortion) {
        int selection;
        
        do {
            System.out.print(initialPortion + this.prompt + ": ");
            selection = this.in.nextInt();
            if (selection <= 0 && selection >= drinks.size()) {
                System.out.println("Invalid selection, try again.");
            }
        } while (selection <= 0 && selection >= drinks.size());

        return drinks.get(selection - 1);
    }

    private void sellDrink() {
        Drink curr = getDrink("What do you want to buy? ");
        if (checkStock(curr)) {
            updateAmounts(-curr.WATER_ML, -curr.MILK_ML, -curr.COFFEE_BEANS_G, -1);
            makeDrink();
        }
    }

    private void fillCoffeeMachine() {
        int water, milk, beans, cups;
        do {
            System.out.print("Write how many ml of water do you want to add: ");
            water = this.in.nextInt();
        } while (water < 0);

        do {
            System.out.print("Write how many ml of milk do you want to add: ");
            milk = this.in.nextInt();
        } while (milk < 0);

        do {
            System.out.print("Write how many grams of coffee beans do you want to add: ");
            beans = this.in.nextInt();
        } while (beans < 0);

        do {
            System.out.print("Write how many disposable cups of coffee do you want to add: ");
            cups = this.in.nextInt();
        } while (cups < 0);

        updateAmounts(water, milk, beans, cups);
    }

    private void updateAmounts(int water, int milk, int beans, int cups) {
        this.water_ml += water;
        this.milk_ml += milk;
        this.coffeeBeans_g += beans;
        this.cups += cups;
    }

    private void takeMoneyFromMachine() {
        System.out.printf("I gave you $%.2f\n\n", this.money);
        this.money = 0;
    }

    public void operate() {
        boolean exit = false;
        while (!exit) {
            System.out.print("Write action (buy, fill, take, status, amounts, available, exit): ");
            switch (this.in.nextLine()) {
                case "buy":
                    System.out.println();
                    sellDrink();
                    break;
                case "fill":
                    System.out.println();
                    fillCoffeeMachine();
                    break;
                case "take":
                    System.out.println();
                    takeMoneyFromMachine();
                    break;
                case "status":
                    System.out.println();
                    System.out.println(this.toString());
                    break;
                case "amounts":
                    System.out.println();
                    displayAmountsForCups();
                    break;
                case "available":
                    System.out.println();
                    displayAvailableCupsPerSupply();
                    break;
                case "exit":
                    System.out.println();
                    System.out.println("Closing system down...");
                    exit = true;
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid command, try again.");
                    break;                
            }
            System.out.println();
        }
    }

    public void displayAvailableCupsPerSupply() {
        int cups, minimum;
        Drink curr = getDrink("What drink do you want to make? ");

        do {
            System.out.print("Write how many cups of " + curr.toString() + " you will need: ");
            cups = this.in.nextInt();
        } while (cups < 0);

        minimum = Math.min(this.milk_ml/curr.MILK_ML, this.water_ml/curr.MILK_ML);
        minimum = Math.min(minimum, this.coffeeBeans_g/curr.MILK_ML);
        minimum = Math.min(minimum, this.cups);

        if (minimum - cups >= 0) {
            System.out.print("\nYes, I can make that amount of " + curr.toString());
            if (minimum - cups > 0) System.out.print(String.format(" (and even %d cups more than that)", minimum - cups));
            System.out.println();
        } else {
            System.out.printf("\nNo, I can only make %d cup(s) of %s\n", minimum, curr.toString());
        }
    }

    public void displayAmountsForCups() {
        int cups;
        Drink curr = getDrink("What drink do you want to make? ");

        do {
            System.out.print("Write how many cups of " + curr.toString() + " you will need: ");
            cups = this.in.nextInt();
        } while (cups < 0);

        System.out.printf("\nFor %d cups of " + curr.toString() + " you will need:\n", cups);
        System.out.printf("%d ml of water\n", curr.WATER_ML*cups);
        System.out.printf("%d ml of milk\n", curr.MILK_ML*cups);
        System.out.printf("%d g of coffee beans\n\n", curr.COFFEE_BEANS_G*cups);
    }

    public String toString() {
        return String.format("\nThe coffee machine has:\n%d ml of water\n%d ml of milk\n" +
                             "%d g of coffee beans\n%d disposable cups\n$%.2f stored\n", this.water_ml, this.milk_ml, this.coffeeBeans_g, this.cups, this.money);
    }
}