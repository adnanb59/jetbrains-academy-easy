import java.util.HashSet;
import java.util.Set;

public class CinemaRoom {
    private final int rows, cols;
    private int income;
    private Set<Integer> booked;
    public static final int SMALL_SIZE_MAX = 60, REGULAR_PRICE = 10, DISCOUNTED_PRICE = 8;

    /** CinemaRoom constructor */
    public CinemaRoom(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.income = 0;
        this.booked = new HashSet<>();
    }

    /**
     * Attempt to book a seat at indicated position in the cinema room.
     *
     * @param row - row of seat
     * @param col - column of seat
     * @return - ticket price if successful, 0 if invalid coordinate, -1 if seat already booked
     */
    public int book_seat(int row, int col) {
        if (row < 1 || col < 1 || row > rows || col > cols) return 0;
        else if (booked.contains((row-1)* rows + col)) return -1;
        else {
            int cost = rows*cols <= SMALL_SIZE_MAX ? REGULAR_PRICE : (row > rows/2 ? DISCOUNTED_PRICE : REGULAR_PRICE);
            income += cost;
            booked.add((row-1)* rows + col);
            return cost;
        }
    }

    /**
     * Get number of sold seats.
     *
     * @return number of booked seats (size of booked collection)
     */
    public int getNumberOfBookedSeats() {
        return this.booked.size();
    }

    /**
     * Get number of rows in cinema room.
     *
     * @return number of rows in cinema room
     */
    public int getNumRows() {
        return this.rows;
    }

    /**
     * Get number of seats per row in cinema room.
     *
     * @return number of cols in cinema room
     */
    public int getNumCols() {
        return this.cols;
    }

    /**
     * Get total number of seats in cinema room.
     *
     * @return rows*cols (total number of seats)
     */
    public int getSizeOfRoom() {
        return this.rows*this.cols;
    }

    /**
     * Get the current income made in the cinema room.
     *
     * @return current income
     */
    public int getIncome() {
        return this.income;
    }

    /**
     * Get the maximum amount of income that the current cinema room can make (when it's fully booked).
     *
     * @return max possible income
     */
    public int getMaxPossibleIncome() {
        return rows*cols <= SMALL_SIZE_MAX ? rows*cols*REGULAR_PRICE : (rows - (rows/2))*cols*DISCOUNTED_PRICE + (rows/2)*cols*REGULAR_PRICE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cinema:\n");
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                if (i == 0) sb.append(j == 0 ? " " : j);
                else sb.append(j == 0 ? i : (booked.contains((i-1)*rows + j) ? "B" : "S"));
                sb.append(j == cols ? "\n" : " ");
            }
        }
        return sb.toString();
    }
}