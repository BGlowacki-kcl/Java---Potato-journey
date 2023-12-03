# Java---Potato-journey
Text-based game created in Java

Potato journey
Bartosz Glowacki 
 

Name and Description

The game is called ‘Potato journey’. It describes a story of a potato (player), 
which needs to become a pack of chips and be eaten by a group of boys in a 
canteen. This is assumed to be the end of the game. A player by collecting items 
and moving characters is able to do tasks that are required to win the game. To 
turn on the game you should open the jar file and create an object of class 
Game. After executing void play() method, the terminal window will pop up 
automatically.

Functionality

A player is supposed to finish all the tasks in the right order: wash, repair the 
road, peel, cut, fry. After completing all of them, getting into the canteen 
automatically shows the winning message and quits the game. To complete each 
task exactly one special item in backpack and one character in the room is 
needed. Player can use these commands to complete all stages of the game:
• go ‘direction’ -> moves player to the room that is connected with the 
current room in the giver direction (left, right, up, down).
• quit -> quits the game.
• help -> shows a short prompt, available commands and task that the 
player is supposed to do right now. Type help ‘command’ to get to know 
more about each command.
• pick ‘item’ -> puts the given item into the backpack, on condition that it is 
located in the same room as player and the backpack isn’t overloaded 
(maximum capacity of 5).
• put ‘item’ -> takes item out of the backpack and puts it in the currently 
visited room by the player.
• show ‘something’ -> shows a feature specified in the command:
o backpack -> shows items that are currently located in the backpack 
and their weight.
o items -> shows items that are located in the same room as player is 
currently in.
o characters -> shows characters that are located in the same room as 
player is currently in.
o weight -> shows the current weight of all the items in the backpack
o exits -> shows all possible exits with the name of the rooms hidden 
behind these exits.
o ‘character’s name’ -> shows the current room of the specified 
character and the next room the character is heading to.
• do ‘something’ -> allows the player to complete special tasks or teleport:
o wash, repair, peel, cut, fry -> checks if all conditions like being in the 
proper room, having a necessary item in backpack, having a 
necessary character in the room are met and completes the task.
o teleport -> checks if the player is in the teleport room and moves 
him/her to a different random room.
• move ‘character’ ‘room’ -> moves a given character into the room 
specified in the third word of the command. It also forces the character to 
stay in this place through the rest of the game.
• back -> moves the player to the previously visited room. Executed 
multiple times takes the player to the beginning of the game (farm). 

Code quality

• Coupling – Each class has a unique task and changes in one class do not 
affect multiple other classes. E.g. change of features describing items or 
characters would only affect main Game class and require further changes 
there.
• Cohesion – Similar methods in main method are separated from each 
other and improve the readability of the file. Chunks of codes mostly do 
not depend on methods they do not use.
• Responsibility-driven design – Classes that store the actual data, 
manipulate it. E.g. Even though I have access to characters and rooms in 
the game class and it manages when to change this, I implemented the 
changeLocation() method in the Character class as it is responsible storing 
(manipulating) this data.
• Maintainability – the program can be easily modified, adding rooms, 
character or items takes usually two lines of code (creating, adding to the 
ArrayList). E.g. if a developer wanted to add additional task to the game it 
would take one line providing necessary item, character and some strings 
to display to the player and one field in TO_DO array. All the conditions are 
checked in a separate method.

A walk-through

Commands necessary to win the game:
• Move cleaner cleaningArea, pick water, go up, do wash, go down, move
worker road, go left, go up, put water, pick asphalt, go down, go right, go 
right, do repair, put asphalt, go left, go left, go up, pick blade, go down, go 
right, go right, go up, pick knife, move kitchenPorter peelingRoom, go up, 
do peel, move kitchenPorter cutter, put knife, go down, pick oil, move chef 
cooker, go left, do cut, go left, do fry, go up
Violet are all the necessary tasks completed to win the game. Each move 
command moves the character to the unique room to complete the task. Pick 
commands pick necessary items and put commands make more free space in 
a backpack.
