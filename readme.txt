CY-Path

To compile the Javadoc :
	- Make sure you have downloaded the JavaFX library 
	- Download the project
	- Open the terminal on Windows
	- Enter the command "cd AbsolutePath" replacing AbsolutePath with the absolute path of the project
	- Enter the following command : 
			javadoc -d CY-Path/doc -classpath "AbsolutePath/lib/*" CY-Path/src/abstraction/*.java CY-Path/src/presentation/*.java
	  replacing AbsolutePath with the absolute path of the "javafx-sdk-20.0.1" folder

To see the Javadoc :
	- Run the file "index.html" in the "doc" folder which is present in "CY-Path" folder

Terminal version :

To run the deliverable:
	- Make sure your Java version is up to date, otherwise download the latest version of JDK from Oracle's official website and restart the console.
	- Download the "quoridor.jar" file and place it in the folder of your choice
	- Open the terminal on Windows
	- Enter the command "cd AbsolutePath" replacing AbsolutePath with the absolute path of the folder where the quoridor.jar file is located
	- Then enter the command "java -jar quoridor.jar"

Implemented features:
	- Choice of the number of players (2 or 4 players)
	- Choice of players' names
	- Initialization and display of the board
	- Players play one at a time and the turn skip automatically
	- Player's possible move are displayed.
	- Player's remaining wall are displayed.
	- Choice of action
		-> Move the pawn
			-> Choose the movement to make
			-> Check possible movements (normal, jump, and diagonal)
			-> Check if the pawn has crossed the board (thus winning)
		-> Place a wall
			-> Choose its orientation and position
			-> Check that the wall does not extend beyond the board
			-> Check that there is no overlap with another wall
			-> Cannot place a wall on the edge of the board
			-> Check that the player still have remaining walls to place
			-> DFS to be applied to all players, ensuring that all players have the possibility to win
	- If there is an error, the turn is restarted 

Board display description :
	- Players are indicated by their number
	- Vertical ("|") and horizontal ("-") bars represent the board grid
	- Plus signs ("+") represent the intersection between the rows and columns of the grid
	- Walls are represented by slashes ("/")
	- Coordinates are indicated at the top and left of the board

How to play ? :
	- Enter the number of players (2 or 4)
	- Enter the names of each players
	- Player's turn :
		-> A list of possible moves for the active player is provided
		-> The number of remaining walls is displayed
		-> Choose an action between "move the pawn" or "place a wall"
		-> To move the pawn :
			-> Enter the coordinates of the pawn's destination
		-> To place a wall :
			-> Enter the coordinates of the middle of the wall, corresponding to a "+" square
			-> Enter the orientation of the wall
	- The updated board is displayed
	- A confirmation is needed to make the action
	- The player can also cancel its action and make another action
	- It's now the next player's turn
	- The game ends when one of the players has reached the opposite side of their starting point

Human-machine interface version :

To run the IHM version of the deliverable :
	- Same as previously, but make sure to have download JavaFX 
	- Open the terminal on Windows
	- Enter the command "cd AbsolutePath" replacing AbsolutePath with the absolute path of the folder where the quoridorHMI.jar file is located
	- Then enter the command "java --module-path "...\javafx-sdk-20\lib" --add-modules=javafx.controls,javafx.fxml,javafx.swing,javafx.base,javafx.graphics,javafx.media,javafx.web -jar quoridorHMI.jar"
	-> /!\ Be careful to replace "..." by the absolute path of JavaFX's folder path.

Human-machine interface only features :
	- Same features as terminal's ones
	- See the rules of the game
	- There is an icon for the application
	- Players have icon on their pawn
	- Exit the game (there is no backup of the current game, all progress will be lose)
	- Restart the game
	- Background music & sound when we do an action (Moving a pawn)
	- The sound's volume can be controled 

Not implemented features:
	- Sound when we do an action (Placing a wall)
	- Can't move the pawn when placing wall
	- Accurate rules
	- Backup of the game

How to play ? :
	- Press the "Play" button
	- Select the number of players (2 or 4)
	- Enter the names of each players then press the "Start" button
	- Player's turn :
		-> Possible moves are displayed on the board for the active player
		-> The number of remaining walls is displayed on the Wall's button
		-> To move the pawn :
			-> Click on the case of the pawn's destination
		-> To place a wall :
			-> Press the "Wall" button
			-> A wall will be displayed and you have to click on the intersection between four cases,
			   the wall will be colored in black if you can place it there.
			-> Right click to change the orientation of the wall
			-> Left click to place the wall
	- The updated board is displayed
	- The player can cancel its action and make another action (Press "Cancel" button)
	- A confirmation is needed to make the action (Press "Confirm" button)
	- It's now the next player's turn
	- The game ends when one of the players has reached the opposite side of their starting point
	- You can exit or restart the game at any moment but the current game will not be saved.
