import java.util.Scanner;

public class Runner {
    /**
     * Run cinema room manager program until user exists
     *
     * @param in - Scanner to read user input
     * @param cr - Cinema room object
     */
    public static void run(Scanner in, CinemaRoom cr) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            try {
                int option = Integer.parseInt(in.nextLine().trim());
                switch (option) {
                    case 0:
                        isRunning = false;
                        break;
                    case 1:
                        System.out.println("\n" + cr);
                        break;
                    case 2:
                        int cost = 0;
                        // if you can book a seat, the cost would be 8 or 10, 0 & -1 indicate errors
                        while (cost == 0 || cost == -1) {
                            int r = -1, c = -1;
                            while (r < 1 || r > cr.getNumRows()) {
                                System.out.println("\nEnter a row number:");
                                try {
                                    r = Integer.parseInt(in.nextLine().trim());
                                    if (r < 1 || r > cr.getNumRows()) System.out.printf("Error: Valid row is between 1 and %d.\n", cr.getNumRows());
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a number.");
                                }
                            }
                            while (c < 1 || c > cr.getNumCols()) {
                                System.out.println("Enter a seat number in that row:");
                                try {
                                    c = Integer.parseInt(in.nextLine().trim());
                                    if (c < 1 || r > cr.getNumCols()) System.out.printf("Error: Valid seat number is between 1 and %d.\n", cr.getNumCols());
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a number.");
                                }
                            }
                            cost = cr.book_seat(r, c);
                            if (cost == 0) System.out.println("\nWrong input!");
                            else if (cost == -1) System.out.println("\nThat ticket has already been purchased!");
                            else System.out.printf("\nTicket price:\n$%d\n", cost);
                        }
                        break;
                    case 3:
                        System.out.printf("\nNumber of purchased tickets: %d\n", cr.getSizeOfRoom());
                        System.out.printf("Percentage: %.2f%%\n", 100.0*cr.getNumberOfBookedSeats()/cr.getSizeOfRoom());
                        System.out.printf("Current income: $%d\n", cr.getIncome());
                        System.out.printf("Total income: $%d\n", cr.getMaxPossibleIncome());
                        break;
                    default:
                        System.out.println("\nInvalid option.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int R = 0, C = 0;
        while (R <= 0) {
            System.out.println("Enter the number of rows:");
            try {
                R = Integer.parseInt(in.nextLine().trim());
                if (R <= 0) System.out.println("Error: Rows must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number.");
            }
        }
        while (C <= 0) {
            System.out.println("Enter the number of seats in each row:");
            try {
                C = Integer.parseInt(in.nextLine().trim());
                if (C <= 0) System.out.println("Error: Number of seats must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number.");
            }
        }
        run(in, new CinemaRoom(R, C));
        in.close();
    }
}