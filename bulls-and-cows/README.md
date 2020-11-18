## bulls-and-cows

This program is the game Bulls and Cows, where there is a secret code generated and the user has to attempt to guess it.
Each guess is graded by a score of bulls and cows. The game continues for as many turns until the code is solved.

### Code Creation

### Grading

- A bull is given when there exists a position where the guess and the code have the same character.
- A cow is given when there exists a character in the guess that exists in the code but at a different position.
- If a user gets no cows or bulls, the Grade will be `None`.

#### Running Program
After compiling program with `javac src/Runner.java`, run program with `java src/Runner`.

The user will then be prompted to enter details about the secret code, starting with the length of the code.
Since the code consists of numbers and lowercase letters, the maximum length of the code is 36 (10 for the numbers, 26 for the alphabet) while maintaining no repeated characters.
Additionally, the length must be greater than zero.

After successful length entry, the user will be asked for how many symbols that the code can be made from. It's value restraints are the same as the length (valid values are in range [1, 36]). However, there is an additional constraint that the length of the code can't be bigger than the number of symbols used (because the uniqueness factor won't hold true - pigeonhole principle on symbols in positions).

Once both values are given, the secret code will be generated (as described above) and a prompt will be given to the user letting them know such. Additionally, it will let the user know the characters used.

After that the game begins.

A typical round display looks like this:

```
Turn ...
<user guess>
Grade: ...
```

if the user manages to guess the code eventually, they will be prompted with the success message `Congratulations! You guessed the secret code.` followed by the program's exit.


##### URL: https://hyperskill.org/projects/53
