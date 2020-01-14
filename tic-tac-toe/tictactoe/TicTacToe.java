package tictactoe;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

// State of Tic-Tac-Toe game
enum State {
    READY, READING, PROCESSING, PROCESSED
}

// Result of Tic-Tac-Toe game after a move
enum ProcessedState {
    DRAW("Draw"),
    NOT_FINISHED_X("Game not finished"),
    NOT_FINISHED_O("Game not finished"),
    O_WINS("O wins"),
    X_WINS("X wins"),
    IMPOSSIBLE("Impossible");

    private final String statement;
    ProcessedState(String st) {
        this.statement = st;
    }

    @Override
    public String toString() {
        return this.statement;
    }
}

public class TicTacToe {
    private State state;
    private ProcessedState pState;
    private char[][] board;
    private int x, o;
    private char last;
    Scanner in;
    
    public TicTacToe() {
        this.in = new Scanner(System.in);
        this.board = new char[3][3];
        cleanBoard();
    }

    // Initialize board and variables to be ready for a new game
    private void cleanBoard() {
        this.state = State.READY;
        this.x = 0;
        this.o = 0;
        this.last = 'O';
        for (char[] row : this.board) {
            Arrays.fill(row, '_');
        }
    }

    // Check that the move placed has lead to winning scenarios (and count them)
    // First check vertically, then horizontally,
    // then diagonally (if it applies - last move was along either diagonal)
    private int processBoard(int r, int c) {
        this.state = State.PROCESSING;
        int t = 0;
        if (this.board[r][c] != '_') {
            t += (this.board[0][c] == this.board[1][c] && this.board[1][c] == this.board[2][c]) ? 1 : 0;
            t += (this.board[r][0] == this.board[r][1] && this.board[r][1] == this.board[r][2]) ? 1 : 0;
            // L to R diagonal is along spots where row and column index are the same
            if (r == c) t += (this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2]) ? 1 : 0;
            // R to L diagonal is opposite
            if (r+c == 2) t += (this.board[0][2] == this.board[1][1] && this.board[1][1] == this.board[2][0]) ? 1 : 0;
        }
        return t;
    }

    // After a move has been processed, update the state of
    // the game to see if it has reached a conclusion
    // `xW` & `oW` represent X and O wins
    private void updateState(int xW, int oW) {
        this.state = State.PROCESSED;
        if (Math.abs(this.x - this.o) > 1 || (xW > 0 && oW > 0)) {
            this.pState = ProcessedState.IMPOSSIBLE;
        } else if (xW == 0 && oW == 0 && this.x + this.o == 9) {
            this.pState = ProcessedState.DRAW;
        } else if (xW == 1) {
            this.pState = ProcessedState.X_WINS;
        } else if (oW == 1) {
            this.pState = ProcessedState.O_WINS;
        } else {
            this.pState = this.last == 'X' ? ProcessedState.NOT_FINISHED_X : ProcessedState.NOT_FINISHED_O;
            this.state = State.READY;
        }
    }

    // Prompt user to make a move on the board, then process it and update the state of the game
    private void makeMove() {
        int userX = -1, userY = -1;
        boolean success = false;
        this.state = State.READING;

        // Loop until user has entered valid input
        while (!success) {
            System.out.print("(" + (this.last == 'X' ? 'O' : 'X') + ") Enter the coordinates: ");
            /**
             * MOVE SET:
             * (1,3) (2,3) (3,3)
             * (1,2) (2,2) (2,3)
             * (1,1) (2,1) (3,1) 
             */
            try {
                userX = in.nextInt();
                userY = in.nextInt();
                if (userX > 3 || userY > 3 || userX < 1 || userX < 1) {
                    System.err.println("Coordinates should be from 1 to 3!");
                } else if (this.board[2-(userY-1)][userX-1] == '_') { // User found unoccupied space
                    success = true;
                } else {
                    System.err.println("This cell is occupied! Choose another one!");
                }
            } catch (InputMismatchException e) { // User tried to enter something other than a number
                System.err.println("You should enter numbers!");
                in.nextLine();
            }
        }

        // Update the board and check if the game has reached a conclusion
        this.board[2-(userY-1)][userX-1] = this.last == 'X' ? 'O' : 'X';
        this.last = this.board[2-(userY-1)][userX-1];
        x += this.last == 'X' ? 1 : 0;
        o += this.last == 'O' ? 1 : 0;
        int xWins = this.last == 'X' ? processBoard(2-(userY-1), userX-1) : 0;
        int oWins = this.last == 'O' ? processBoard(2-(userY-1), userX-1) : 0;
        updateState(xWins, oWins);
    }

    // Entry point for user to play game
	public void play() {
        if (this.state != State.READY) cleanBoard(); // Clean board if already used
        // Loop until state is PROCESSED & the processed state is WIN, DRAW or IMPOSSIBLE
        // This means the moves have been processed and it has reached a conclusive state
        while (this.state != State.PROCESSED && (this.pState != ProcessedState.NOT_FINISHED_O || this.pState != ProcessedState.NOT_FINISHED_X)) {
            System.out.println(printBoard(false));
            makeMove();
        }
        System.out.println(printBoard(true));
    }
    
    // Return string representation of the Tic-Tac-Toe board
    // `state`: indicator to print state of game (state printed at end)
    private String printBoard(boolean state) {
        StringBuilder s = new StringBuilder();
        s.append("---------\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    s.append("| ");
                }
                s.append(this.board[i][j] + " ");
                if (j == 2) {
                    s.append("|\n");
                }
            }
        }
        s.append("---------");
        if (state) s.append("\n" + this.pState);
        return s.toString();
    }
}