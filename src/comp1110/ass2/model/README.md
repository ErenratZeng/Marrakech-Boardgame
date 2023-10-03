# **AbbreviatedRug**
### Description
AbbreviatedRug is a Java class used to represent objects with a specific color and unique ID. This class provides several construction methods and methods for obtaining object properties.
### Attributes
- Color color: The color of the object.
- int ID: Unique identifier of the object.
### Constructors
- AbbreviatedRug(): Create an object with color null and ID 0.
- AbbreviatedRug(Color color, int ID): Creates an object with the specified color and ID.
- AbbreviatedRug(String string): Initialize the object using a string. The first character of the string represents the color, and the next two characters represent the ID.
### Methods
- Color getColor(): Returns the color of the object.
- int getID(): Returns the ID of the object.
- String getString(): Returns the string representation of the object. The first character of the string represents the color, and the next two characters represent the ID.
---

# **Assam**
### Description
Assam is a Java class that represents an entity on a two-dimensional board with a specific position and orientation. This class implements the IBean interface and provides methods to get and set the entity's position and orientation.
### Attributes
- Point point: The current position of the entity.
- Orientation orientation: The current orientation of the entity.
### Enumerations
- Orientation: Indicates the possible directions of the entity (north, east, south, west).
### Constructors
- Assam(): Creates an entity with default position and orientation.
- Assam(String string): Use a string to initialize the position and orientation of the entity.
### Methods
- nPoint getPoint(): Returns the current position of the entity.
- Assam setPoint(Point point): Set the position of the entity.
- Orientation getOrientation(): Returns the current orientation of the entity.
- Assam setOrientation(Orientation orientation): Set the orientation of the entity.
- Assam setOrientationRight90(): Rotates the entity's orientation 90 degrees to the right.
- Assam setOrientationLeft90(): Rotates the entity's orientation 90 degrees to the left. 
- Assam setOrientationBack(): Rotates the entity's orientation 180 degrees. 
- String getString(): Returns the string representation of the entity.
---

# **Board**
### Description
Board is a Java class that represents a two-dimensional game board on which AbbreviatedRug objects are placed. This class implements the IBean interface and provides methods to get and set AbbreviatedRug objects on the board.
### Attributes
- BOARD_WIDTH: The width of the board.
- BOARD_HEIGHT: The height of the board.
- AbbreviatedRug[][] rugs: A two-dimensional array that stores the AbbreviatedRug objects on the board.
### Constructors
- Board(): Creates a new board and places a default AbbreviatedRug object at each position.
- Board(String string): Uses a string to initialize the board and the AbbreviatedRug objects on the board.
### Methods
- AbbreviatedRug getRug(int x, int y): Returns the AbbreviatedRug object at the specified position.
- Board setRug(AbbreviatedRug rug, int x, int y): Places an AbbreviatedRug object at the specified position.
- String getString(): Returns a string representation of the board and the AbbreviatedRug object on the board.
---

# **Dice**
### Description
Dice is a Java class used to simulate a die with a specific face value. This die is not a typical six-sided die. Each side of it has a specific value, and the roll() method can be used to simulate the action of rolling a die.
### Attributes
- int[] sides: An integer array representing the value of each side of the dice.
- Random random: A Random object used to generate random numbers to simulate rolling dice.
### Constructors
- Dice(): Creates a new Dice object whose face values are preset to {1, 2, 2, 3, 3, 4}.
### Methods
- int roll(): simulates the action of rolling a dice and returns a random face value of the dice.
---

# **Marrakech**
- TBD
---

# **Player**
### Description
Player is a Java class that represents the player in the game. Each player has a specific color, number of coins, number of rugs remaining, and survival status. This class implements the IBean interface and provides methods to get and set the player's properties.
### Attributes
- int coins: The number of coins of the player, the initial value is 30.
- int rugNum: The number of rugs left by the player, the initial value is 15.
- Color color: The player's color.
- Boolean alive: The player's survival status, the initial value is true.
### Constructors
- Player(Color color): Creates a new player object using the specified color.
- Player(String string): Use a string to initialize the player's properties.
### Methods
- Color getColor(): Returns the player's color.
- int getCoins(): Returns the player's number of coins.
- Player setCoins(int coins): Set the player's number of coins.
- int getrugNum(): Returns the number of carpets left by the player.
- Player setrugNum(int rugNum): Set the number of remaining rugs for the player.
- boolean getAlive(): Returns the player's survival status.
- Player setAlive(Boolean alive): Set the player's survival status.
- String getString(): Returns the string representation of the player.
---

# **Point**
### Description
The Point class is a fundamental component in the comp1110.ass2.model.base package, designed to represent a point in a two-dimensional space. Each Point object encapsulates the x and y coordinates, providing a straightforward way to manage and manipulate points in various applications, particularly in graphical and geometrical contexts.
### Constructor
- The class contains a constructor that allows the creation of a Point object with specified x and y coordinates.
### Methods
- setY(int y): This method sets the y coordinate of the point to the specified value and returns the updated Point object.
- Parameters_y: The new y-coordinate value.
- Return: The updated Point object.
- getY(): This method retrieves the y coordinate of the point.
- Return: The current y-coordinate value.
- setX(int x): This method sets the x coordinate of the point to the specified value and returns the updated Point object.
- Parameters_x: The new x-coordinate value.
- Return: The updated Point object.
- getX(): This method retrieves the x coordinate of the point.
- Return: The current x-coordinate value.
- toString(): Overrides the toString() method to provide a string representation of the Point object, displaying the x and y coordinates.
- Return: A string representation of the Point object.
---

# **State**
### Description
The State class is a core component that encapsulates the entire state of a game, including the players, the Assam, and the board. It provides a comprehensive view of the game's current status, offering methods to access and manipulate the game state effectively.
### Constructor
- The class contains two constructors: Accepts an ArrayList of players, an Assam object, and a Board object to initialize the game state.
Accepts a String that represents the game state and parses it to initialize the players, Assam, and board.
### Methods
- getAssam(): Retrieves the Assam object representing the current state of Assam in the game.
- Return: The Assam object.
- getBoard(): Retrieves the Board object representing the current state of the game board.
- Return: The Board object.
- getPlayers(): Retrieves an ArrayList of Player objects representing the current players in the game.
- Return: An ArrayList of Player objects.
- getString(): Generates a String representation of the current game state, concatenating the strings of all players, Assam, and the board.
- Return: A String representation of the game state.
---

# **TwoRug**
### Description
- TwoRug is a Java class that inherits from the AbbreviatedRug class and represents a carpet covering two squares. Each TwoRug object has a color, an ID, and two points that represent the location of the square covered by the rug.
### Attributes
- Point[] points: An array containing two points, representing the positions of the squares covered by the carpet.
### Constructors
- TwoRug(Color color, int ID, Point[] points): Creates a new TwoRug object with the specified color, ID, and points.
- TwoRug(String string): Use a string to initialize the properties of the TwoRug object.
### Methods
- Point[] getPoints(): Returns an array of points representing the locations of the squares covered by the carpet.
- String getString(): Returns a string representation of the TwoRug object, including its color, ID, and position of the covered square.
---

# **utils**
### Description
The utils class is a utility class in the base package, providing helper methods to facilitate common operations throughout the application. Currently, it includes a method for converting a substring of a given string into an integer.

### Method
- subStr2int(String string, int begin, int end): This method is used to extract a substring from the given string, starting from the index begin up to the index end, and then converts this substring into an integer.
- ### Parameters:
- string: The original string from which the substring will be extracted.
- begin: The starting index for the substring.
- end: The ending index for the substring.
### Return
- The integer value converted from the extracted substring.

