## cinema-room-manager

This program creates a manager tool for a cinema room, allowing the user to book seats, view the seats in the room as well as statistics about the income made.

Specific stats are:
- seats booked (and the percentage booked in the room)
- income made as well as max income possible

In this program, a regular sized room is at most 60 seats. Tickets for these rooms are always $10. For rooms larger than that, the first half of the rows (rounding down in the case of an odd number of rows) will pay the regular price while the back half pays a discounted price of $8.

> These values can be changed [here](src/CinemaRoom.java#L8)

#### Running Program
After compiling program using `javac src/Runner.java`, run the program using `java Runner`.

You will be prompted to create the dimensions of the room:
- Number of rows: `Enter the number of rows:`
- Number of seats per row: `Enter the number of seats in each row:`

You are required to enter one number per prompt.

After successfully entering the dimensions, a cinema room will be created and the manager program will start.

The menu for the manager is as follows:
```
1. Show the seats
2. Buy a ticket
0. Exit
```

If you choose to view the cinema, the output will look something like this (where B is booked and S is a seat):

```
Cinema:
  1 2 3 ....
1 B S B
....
```

If you choose to buy a ticket, you'll be asked to pick a seat in a similar fashion to creating the dimensions of the room (row, then seat).
If the seat is booked or an invalid position is entered, then you will be asked to pick again.

If choosing to look at the statistics, you will see this:
```
Number of purchased tickets: ...
Percentage: ...%
Current income: $...
Total income: $...
```

##### URL: https://hyperskill.org/projects/133
