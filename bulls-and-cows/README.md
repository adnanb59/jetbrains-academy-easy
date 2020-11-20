## bulls-and-cows

This program is the game Bulls and Cows, where there is a secret code generated and the user has to attempt to guess it.
Each guess is graded by a score of bulls and cows. The game continues for as many turns until the code is solved.

### Code Creation

The method to create the code takes in it's length and the number of possible symbols. There is an assumption made in my algorithm, that is all the numbers are available in all cases. Meaning that, let's say the number of symbols is less than 10 (digits 0-9), there is no cut-off to the numbers available (e.g. `symbols=4`, the possible symbols aren't only 0-3 but all the numbers. This contrasts with the use of letters, where letters are cut off based on the number of symbols available (e.g. `symbosl=36, available={0-9, a-z}` and `symbols=13, available={0-9, a-c}`.

With this in mind, while building the code a number is generated randomly (between 0 and `symbols`). Assuming the number hasn't been used yet then an ASCII value is calculated. If the number correpsonds to a numeral character (between 0-9) then a numeral character is used. Likewise, if it corresponds to a letter then a letter is calculated. The numbers used are stored in a set, so they can be compared against when generating new numbers.

### Grading

- A bull is given when there exists a position where the guess and the code have the same character.
- A cow is given when there exists a character in the guess that exists in the code but at a different position.
- If a user gets no cows or bulls, the Grade will be `None`.

To calculate the grade, characters of the code are placed in a set which will be used to compare characters of the guess later. Because the code contains unique characters, the set works well to accurately describe the code, if the code contained non-unique characters, a map would be the right data structure to use. Afterwards, each character of the guess is traversed over and checked, first against the code's character at that position (for a bull) or at any other position (using the set - for a cow). Scores are tallied anf returned.

The use of a set is crucial because it allows for a faster lookup of characters (in the case of a cow), with the set's O(1) lookup vs. the O(n) search if you were to check the entire code. Additionally, it covers the case where if you were looking at each position with no info on future characters, you wouldn't know if the character is potentially a bull. Yoo could alleviate this by using data structures for both guess and code's characters (keeping track of visited). However, in that case it would be better (space-wise & time-wise) to use the method that is being used currently.

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
