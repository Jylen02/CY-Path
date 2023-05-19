CY-Path

To compile the javadoc :
	- javadoc -d CY-Path/doc -classpath "C:/Users/jerem/Desktop/Études/Cours/2022-2023/javafx-sdk-20.0.1/lib/*" CY-Path/src/abstraction/*.java CY-Path/src/presentation/*.java

To see the Javadoc :
	- Run the file "index.html" in the "doc" folder which is present in "CY-Path" folder

To run the deliverable:
	- Make sure your Java version is up to date, otherwise download the latest version of JDK from Oracle's official website and restart the console.
	- Download the "quoridor.jar" file and place it in the folder of your choice
	- Open the terminal on Windows
	- Enter the command "cd AbsolutePath" replacing AbsolutePath with the absolute path of the quoridor.jar file
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
			-> Check that there are a maximum of 20 walls on the board
			-> DFS to be applied to all players, ensuring that all players have the possibility to win
	- If there is an error, the turn is restarted 

Unimplemented features :
	- Human-machine interface
	- Players can choose there icon for their pawn

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
		-> The number of wall remaining is displayed
		-> Choose an action between "move the pawn" or "place a wall"
		-> To move the pawn :
			-> Enter the coordinates of the pawn's destination
		-> To place a wall :
			-> Enter the coordinates of the middle of the wall, corresponding to a "+" square
			-> Enter the orientation of the wall
	- It's now the next player's turn
	- The game ends when one of the players has reached the opposite side of their starting point