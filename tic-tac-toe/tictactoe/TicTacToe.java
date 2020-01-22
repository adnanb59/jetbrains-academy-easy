package tictactoe;

import java.util.Arrays;

/** State of Tic-Tac-Toe game */
enum State {
    DRAW("Draw"),
    NOT_FINISHED_X("Game not finished"),
    NOT_FINISHED_O("Game not finished"),
    O_WINS("O wins"),
    X_WINS("X wins"),
    IMPOSSIBLE("Impossible");

    private final String statement;

    /** Constructor for State Enum */
    State(String st) {
        this.statement = st;
    }

    /**
    * String representation of State Enum
    * @return statement associated with given state
    */
    @Override
    public String toString() {
        return this.statement;
    }
}

public class TicTacToe {
    private State state;
    private char[][] board;
    private int x_moves, o_moves;

    /** TicTacToe constructor */
    public TicTacToe() {
        this.board = new char[3][3];
        cleanBoard();
    }

    /** Initialize variables of class symbolizing a new game. */
    private void cleanBoard() {
        this.state = State.NOT_FINISHED_X;
        this.x_moves = 0;
        this.o_moves = 0;
        for (char[] row : this.board) {
            Arrays.fill(row, '_');
        }
    }

    /**
    * Check that the move placed has lead to winning scenarios (and count them)
    * First check vertically, then horizontally,
    * then diagonally (if it applies - last move was along either diagonal).
    *
    * @param r - Specific row in grid
    * @param c - Specific column in grid
    * @return Number of winning positions that exists from specific position in grid
    */
    private int processBoard(int r, int c) {
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

    /**
    * After a move has been processed, update the state of the game to see if it has reached a conclusion.
    *
    * @param xW - number of wins X gets from last move
    * @param oW - number of wins O gets from last move
    */
    private void updateState(int xW, int oW) {
        if (Math.abs(this.x_moves - this.o_moves) > 1 || (xW > 0 && oW > 0)) {
            this.state = State.IMPOSSIBLE;
        } else if (xW == 0 && oW == 0 && this.x_moves + this.o_moves == 9) {
            this.state = State.DRAW;
        } else if (xW == 1) {
            this.state = State.X_WINS;
        } else if (oW == 1) {
            this.state = State.O_WINS;
        } else {
            this.state = this.state == State.NOT_FINISHED_X ? State.NOT_FINISHED_O : State.NOT_FINISHED_X;
        }
    }

    /**
    * Provide the user the next character to move.
    *
    * @return The character representing the next character to move
    */
    public Character getNextMover() {
        return this.state == State.NOT_FINISHED_X ? 'X' : 'O';
    }

    /**
    * Take coordinates from user and make move, process it and update the state of the game.
    *
    * @param r - User inputted row in the grid
    * @param c - User inputted column in the grid
    * @return Whether or not the specified position in the grid is already occupied
    */
    public boolean makeMove(int r, int c) {
        int tmp = r;
        r = 2-(c-1);
        c = tmp-1;
        boolean isNotOccupied = this.board[r][c] == '_';
        if (isNotOccupied) {
            // Update the board and check if the game has reached a conclusion
            this.board[r][c] = this.state == State.NOT_FINISHED_X ? 'O' : 'X';
            this.x_moves += this.board[r][c] == 'X' ? 1 : 0;
            this.o_moves += this.board[r][c] == 'O' ? 1 : 0;
            tmp = processBoard(r, c);
            updateState(this.board[r][c] == 'X' ? tmp : 0, this.board[r][c] == 'O' ? tmp : 0);
        }
        return isNotOccupied;
    }
    
    /**
    * Provide the current Tic-Tac-Toe board to the user.
    *
    * @return String representation of the board
    */
    public String printBoard() {
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
        return s.toString();
    }

    /**
    * Provide the current state of the game to the user (in a friendlier representation).
    *
    * @return The string representation of the current state
    */
    public String getState() {
        return this.state.toString();
    }

    /**
    * Check whether the game has been completed.
    *
    * @return The result of the current state of the game equalling one of the completed states
    */
    public boolean isCompleted() {
        return !(this.state == State.NOT_FINISHED_X || this.state == State.NOT_FINISHED_O);
    }
}