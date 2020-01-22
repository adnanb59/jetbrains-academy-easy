import tictactoe.*;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        // -- INITIALIZE VARIABLES --
        TicTacToe ttt = new TicTacToe();
        Scanner in = new Scanner(System.in);
        boolean areCoordinatesValid;
        int promptX, promptY;

        // -- PROCESS USER INPUT --
        // Play Tic-Tac-Toe
        while (!ttt.isCompleted()) {
            ttt.printBoard();
            areCoordinatesValid = false; // set to false during each iteration
            // Prompt user to enter coordinates (and keep doing so until valid ones are entered)
            while (!areCoordinatesValid) {
               /* BOARD COORDINATES:
                * (1,3) (2,3) (3,3)
                * (1,2) (2,2) (2,3)
                * (1,1) (2,1) (3,1) */
                System.out.print("(" + ttt.getNextMover() + ") Enter the coordinates: ");
                // If coordinates are valid (& unoccupied), then the move will be made
                // Otherwise, provide an error message
                try {
                    promptX = in.nextInt();
                    promptY = in.nextInt();
                    if (promptX >= 1 && promptX <= 3 && promptY >= 1 && promptY <= 3) {
                        areCoordinatesValid = ttt.makeMove(promptX, promptY);
                        if (!areCoordinatesValid) System.err.println("This cell is occupied! Choose another one!");
                    } else {
                        System.err.println("Coordinates should be from 1 to 3!");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("You should enter numbers!");
                } finally {
                    in.nextLine();
                }
            }
        }

        // Once game is finished, print the result
        System.out.println(ttt.getState());
    }
}