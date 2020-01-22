package machine;

import java.util.*;

public class CoffeeMachine {
    private double money;
    private int water_ml, milk_ml, coffeeBeans_g, cups;
    private List<Drink> drinks;
    private String prompt;

    /** CoffeeMachine constructor*/
    public CoffeeMachine() {
        this.water_ml = 0;
        this.milk_ml = 0;
        this.coffeeBeans_g = 0;
        this.cups = 0;
        this.money = 0;

        // Pre-populate machine with drinks
        this.drinks = new ArrayList<Drink>();
        drinks.add(DrinkFactory.makeDrink("Espresso"));
        drinks.add(DrinkFactory.makeDrink("Latte"));
        drinks.add(DrinkFactory.makeDrink("Cappuccino"));
        drinks.add(DrinkFactory.makeDrink("Coffee"));
        prompt = "1 - Espresso, 2 - Latte, 3 - Cappuccino, 4 - Coffee";
    }

    /** Simulate coffee machine process of making a drink */
    private void makeDrink() {
        try {
            System.out.println("\nStarting to make the drink...");
            System.out.println("Grinding coffee beans...");
            wait(10L);
            System.out.println("Boiling water...");
            wait(100L);
            System.out.println("Mixing boiled water with crushed coffee beans...");
            wait(100L);
            System.out.println("Pouring coffee into the cup...");
            wait(10L);
            System.out.println("Pouring some milk into the cup...");
            wait(10L);
            System.out.println("Drink is ready!\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
    * Complete purchase of drink in coffee machine (if possible) and make it
    *
    * @param drink - Number representing drink (from prompt string)
    */
    public void sellDrink(int drink) {
        Drink curr = drinks.get(drink-1);
        if (curr.WATER_ML > this.water_ml) {
            System.out.println("Insufficient amount of water for drink.");
        } else if (curr.MILK_ML > this.milk_ml) {
            System.out.println("Insufficient amount of milk for drink.");
        } else if (curr.COFFEE_BEANS_G > this.coffeeBeans_g) {
            System.out.println("Insufficient amount of coffee beans for drink.");
        } else if (cups == 0) {
            System.out.println("Need to restock cups.");
        } else {
            updateAmounts(-curr.WATER_ML, -curr.MILK_ML, -curr.COFFEE_BEANS_G, -1);
            this.money += curr.COST;
            makeDrink();
        }
    }

    /**
    * Replenish coffee machine with water, milk, coffee beans & coffee cups.
    *
    * @param water - Amount of water (in mL) to fill machine with
    * @param milk - Amount of milk (in mL) to fill machine with
    * @param beans - Amount of coffee beans (in g) to fill machine with
    * @param cups - Amount of cups to add
    */
    public void fillCoffeeMachine(int water, int milk, int beans, int cups) {
        updateAmounts(water, milk, beans, cups);
    }

    /**
    * Update coffee machine resources after modifying amounts using values passed in
    *
    * @param water - Amount of water (in mL) to fill machine with
    * @param milk - Amount of milk (in mL) to fill machine with
    * @param beans - Amount of coffee beans (in g) to fill machine with
    * @param cups - Amount of cups to add
    */
    private void updateAmounts(int water, int milk, int beans, int cups) {
        this.water_ml += water;
        this.milk_ml += milk;
        this.coffeeBeans_g += beans;
        this.cups += cups;
    }

    /** Collect money from the coffee machine */
    public void takeMoneyFromMachine() {
        System.out.printf("I gave you $%.2f\n\n", this.money);
        this.money = 0;
    }

    /**
    * Inform the user if they can make the desired cups of the drink (and if not, let them know how much they can make)
    *
    * @param drink - Number representing drink the user wants to make
    * @param cups - Number of cups the user wants to make
    * @return A string describing amount of cups that the machine can make
    */
    public String displayAvailableCupsPerSupply(int drink, int cups) {
        Drink curr = drinks.get(drink-1);
        int minimum;

        minimum = Math.min(this.milk_ml/curr.MILK_ML, this.water_ml/curr.MILK_ML);
        minimum = Math.min(minimum, this.coffeeBeans_g/curr.MILK_ML);
        minimum = Math.min(minimum, this.cups);

        String ret;
        if (minimum - cups >= 0) {
            ret = "\nYes, I can make that amount of " + curr.toString();
            if (minimum - cups > 0) ret += String.format(" (and even %d cups more than that)", minimum - cups);
        } else {
            ret = String.format("\nNo, I can only make %d cup(s) of %s\n", minimum, curr.toString());
        }
        return ret;
    }

    /**
    * Inform the user of the amount of resources required to make the amount of cups of drink that the user wanted
    *
    * @param drink - Number representing drink the user wants to make
    * @param cups - Number of cups the user wants to make
    * @return A string representing how much resources is required that many cups of the drink
    */
    public String displayAmountsForCups(int drink, int cups) {
        Drink curr = drinks.get(drink-1);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\nFor %d cups of " + curr.toString() + " you will need:\n", cups));
        sb.append(String.format("%d ml of water\n", curr.WATER_ML*cups));
        sb.append(String.format("%d ml of milk\n", curr.MILK_ML*cups));
        sb.append(String.format("%d g of coffee beans\n\n", curr.COFFEE_BEANS_G*cups));
        return sb.toString();
    }

    /** Get the updated amounts of the resources available in the coffee machine
     *
     * @return String representation for the resources in the machine
     * */
    public String toString() {
        return String.format("\nThe coffee machine has:\n%d ml of water\n%d ml of milk\n" +
                             "%d g of coffee beans\n%d disposable cups\n$%.2f stored\n",
                             this.water_ml, this.milk_ml, this.coffeeBeans_g, this.cups, this.money);
    }

    /**
     * Get string representing drink options available
     *
     * @return String representing all the drinks available (and the input to select them)
     * */
    public String getPrompt() {
        return this.prompt;
    }

    /**
     * Get the number of drinks available in the machine
     *
     * @return number of drinks in the machine
     * */
    public int getDrinksCount() {
        return this.drinks.size();
    }

    /**
     * Get the drink name of a drink represented by number inputted by user (from prompt)
     *
     * @return Drink name (i.e. Coffee, Espresso, etc.)
     * */
    public String getDrink(Integer drink) {
        return drinks.get(drink-1).toString();
    }
}