## simple-chatty-bot

The program simulates a "Simple Chatty Bot", which will interact with the user 
and allow them to do a couple of actions when prompted.  

Once the program is run, the bot introduces itself to the user and asks for their name.  
To further introductions, the bot plays a game where they will guess their age.  
This is done by asking the user to provide the following values:  
`age mod 3`, `age mod 5`, `age mod 7`.

After this little introduction phase is complete, the user will be prompted to do some actions as follows:  
`What would you like to do? (count, test, exit): `

- *count*: Shows the user the robot's "skill" by prompting the user to enter a number and counting from 1 to that number
- *test*: Allows the user to test their programming knowledge by answering a random question
    - The questions & answers are hardcoded to the class just because of how few questions there are. If there were
    more (and if there are plans to expand) then the best practice would be to save to a text file and read from there. 
- *exit*: Allows the user to exit the program  and cease communicates with the robot (a bit intense, I know)

##### Running Program
After compiling the files, can run program with:  
`java Runner`

##### URL: https://hyperskill.org/projects/48
