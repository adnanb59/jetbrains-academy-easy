package machine;

class Drink {
    final double COST;
    final int WATER_ML, MILK_ML, COFFEE_BEANS_G;
    final String DRINK;

    public Drink(double cost, int water, int milk, int beans, String drink) {
        this.COST = cost;
        this.WATER_ML = water;
        this.MILK_ML = milk;
        this.COFFEE_BEANS_G = beans;
        this.DRINK = drink;
    }

    public String toString() {
        return this.DRINK;
    }
}

/** Static factory to make drinks based on drink name */
class DrinkFactory {
    public static Drink makeDrink(String drink) {
        Drink ret;
        switch (drink) {
            case "Cappuccino":
                ret = new Drink(6.00, 200, 100, 12, "Cappuccino");
                break;
            case "Espresso":
                ret = new Drink(4.00, 250, 0, 16, "Espresso");
                break;
            case "Latte":
                ret = new Drink(7.00, 350, 75, 20, "Latte");
                break;
            case "Coffee":
                ret = new Drink(2.00, 200, 50, 15, "Coffee");  
                break;
            default:
                ret = null;
                break;
        }
        return ret;
    }
}