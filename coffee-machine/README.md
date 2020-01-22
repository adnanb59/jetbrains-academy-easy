## coffee-machine

This program emulates a coffee machine that allows users to make drinks (at a cost of course).  
Additionally, it allows for the owner of the machine to restock resources and collecting money made from the machine.  

The user is able to do a variety of actions as prompted by the program as follows:  
`Write action (buy, fill, take, status, amounts, available, exit): `

- *buy*: Allows a user to purchase a specific drink (will be prompted later for choice). If the resources are available
then the drink will be made, otherwise the user will be notified. 
- *fill*: Allows a user to fill coffee machine with coffee beans, water, milk & coffee cups.
- *take*: Allows a user to collect money from the coffee machine (resetting stored amount to $0)
- *status*: Allows a user to be notified of the amount of resources available in the current machine
- *amounts*: Notifies a user of the amounts of resources (beans, milk, water) required to make a specified number
of drinks desired by user (both will be prompted for later)
- *available*: Notifies the user if the machine make the specified number of cups of a specified drink (both will be
prompted for). If the machine cannot make the specified amount of drink, let the user know how much cups the
machine can make. Likewise, if the machine can make more than the specified amount, then let the user know how many
more cups. 
- *exit*: With this prompt, the user can shut down the coffee machine (exit the program)

#### _Running Program_
After compiling the files, you can run the program with:  
`java Runner`

##### URL: https://hyperskill.org/projects/33
