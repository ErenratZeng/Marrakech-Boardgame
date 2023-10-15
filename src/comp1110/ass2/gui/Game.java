package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import comp1110.ass2.model.*;
import comp1110.ass2.model.base.Dice;
import comp1110.ass2.model.base.Point;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

import static comp1110.ass2.Marrakech.*;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    // Height and width of each tile
    private static final double Tile_Size = 120;

    // Pixel gap between the grey rectangles that indicates where the tiles are on the board
    private static final double BOARD_TILE_SHADOW_GAP = 20;

    // how much the blue background extends past the tiles
    private static final int BOARD_BORDER = 40;

    // The start of the board in the x-direction (ie: x = 0)
    private static final double START_X = 110.0;

    // The start of the board in the y-direction (ie: y = 0)
    private static final double START_Y = 110.0;

    /*
     The base of the filepath for all the assets in the game. This points to
     the "assets" directory within the directory containing this class.
    */
    private static final String URI_BASE = "assets/";

    // Group containing all the assets corresponding to pieces of the board.
    private final Group board = new Group();


    //private final double boardWidth = Board.BOARD_WIDTH*Tile_Size;

    //private final double boardHeight = Board.BOARD_HEIGHT*Tile_Size;

    // The margins used for all visual assets
    private static final int MARGIN_X = 100;
    private static final int MARGIN_Y = 50;

    /*
     Distance to leave from a button to the right - used for setting up
     all the buttons at the bottom of the window.
    */
    private static final double BUTTON_BUFFER = 100.0;

    /*
     Distance to leave from a slider to the right - the slider is a bit
     longer than the buttons, hence the need for differing lengths.
     */
    private static final double SLIDER_BUFFER = 200.0;

    // Group containing all the buttons, sliders and text used in this application.
    private final Group controls = new Group();

    private final static String INSTRUCTIONS = "";

    private final static String CONTROLS = " ";

    /**
     * A method that interfaces with the backend of this project to create a
     * new conceptual "game".
     */

    private final Dice dice = new Dice();

//    private Assam assam;
    private Player[] players;
    private Viewer viewer;
    private ArrayList<Player> playersList;
    private Text diceFace = new Text("0");
    private State gameState;
    private Board gameBoard;
    private boolean directionSelected = false;
    private int currentPlayer = 0;  // track current player
    private Text currentPlayerLabel = new Text();
    private int TILE_SIZE = 10;
    private Button turnRightButton;
    private Button turnLeftButton;
    private Button turn180DegreeButton;
    private Button northButton;
    // A flag to indicate if the game has started
    private boolean gameStarted = false;
    private ArrayList<Point> selectedTiles = new ArrayList<>();
    private int nextRugID = 1;  // 用于分配新的地毯ID

    Assam newAssam = new Assam();

    private int putTwoRugCounter = 0;

    private void newGame() {
        this.playersList = new ArrayList<>();
        playersList.add(new Player(Color.RED));
        playersList.add(new Player(Color.YELLOW));
        playersList.add(new Player(Color.CYAN));
        playersList.add(new Player(Color.PURPLE));
        this.players = playersList.toArray(new Player[0]);

        Board board = new Board();
        gameState = new State(playersList, newAssam, board);
        refreshGameView(gameState);
        // Reset the game started flag
        gameStarted = false;
    }

    /**
     * Update the game view based on the current game state.
     *
     * @param gameState The current game state.
     */
    private void refreshGameView(State gameState) {
        // Use the getString() method of the State class to get the string representation of the current game state
        String currentState = gameState.getString();
//        System.out.println(currentState);
        // Use the displayState() method of the Viewer class to display the current game state
        if (currentState == null || currentState.isEmpty()) {
            System.err.println("Error: Game state string is empty or null.");
            return;
        }
        viewer = new Viewer();
        viewer.displayState(currentState);
        root.getChildren().add(viewer.getViewerRoot()); //Add root to viewer
    }

    /**
     * Method that creates elements and game controls buttons for the game.
     */

    public void makeControls() {
//        System.out.println("makeControls called");
        Rectangle square = new Rectangle(965, 25, TILE_SIZE + 100, TILE_SIZE + 100);
        square.setFill(Color.GREY);

        currentPlayerLabel.setX(365);
        currentPlayerLabel.setY(60);
        currentPlayerLabel.setStyle("-fx-font-size: 72;");
        currentPlayerLabel.setText("Player " + (currentPlayer + 1) + "'s turn");

        // Create a button to roll the dice and set its position
        Button rollButton = new Button("Roll Dice");
        rollButton.setLayoutX(720);
        rollButton.setLayoutY(650);
        rollButton.setOnAction(e -> rollDice());

        turnRightButton = new Button("→");
        turnLeftButton = new Button("←");
        turn180DegreeButton = new Button("↓");
        northButton = new Button("↑");

        // Rotate Assam 90 degrees to the Right when clicked
        turnRightButton.setLayoutX(1000);
        turnRightButton.setLayoutY(600);
        turnRightButton.setOnAction(e -> {
            if (!directionSelected) {
                String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "E");
                if (!newAssamState.equals(gameState.getAssam().getString())) {
                    gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                    refreshGameView(gameState);
                    if (!directionSelected) {
                        directionSelected = true;
                        disableDirectionButtons(turnRightButton, turnLeftButton, turn180DegreeButton, northButton);
                        updateDirectionButtons();
                    }
                }
            }
        });

        turnLeftButton.setLayoutX(900);
        turnLeftButton.setLayoutY(600);
        turnLeftButton.setOnAction(e -> {
            if (!directionSelected) {
                String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "W");
                if(!newAssamState.equals(gameState.getAssam().getString())) {
                    gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                    refreshGameView(gameState);
                    if (!directionSelected) {
                        directionSelected = true;
                        disableDirectionButtons(turnRightButton, turnLeftButton, turn180DegreeButton, northButton);
                        updateDirectionButtons();
                    }
                }
            }
        });

        turn180DegreeButton.setLayoutX(950);
        turn180DegreeButton.setLayoutY(650);
        turn180DegreeButton.setOnAction(e -> {
            if (!directionSelected) {
                String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "S");
                if(!newAssamState.equals(gameState.getAssam().getString())) {
                    gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                    refreshGameView(gameState);
                    if (!directionSelected) {
                        directionSelected = true;
                        disableDirectionButtons(turnRightButton, turnLeftButton, turn180DegreeButton, northButton);
                        updateDirectionButtons();
                    }
                }
            }
        });

        northButton.setLayoutX(950);
        northButton.setLayoutY(550);
        northButton.setOnAction(e ->{
            if(!directionSelected){
                String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(),"N");
                if(!newAssamState.equals(gameState.getAssam().getString())) {
                    gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                    refreshGameView(gameState);
                    if (!directionSelected) {
                        directionSelected = true;
                        disableDirectionButtons(turnRightButton, turnLeftButton, turn180DegreeButton, northButton);
                        updateDirectionButtons();
                    }
                }
            }
        });

        root.getChildren().addAll(square, diceFace, rollButton, turnRightButton, turnLeftButton, turn180DegreeButton, northButton,currentPlayerLabel);
        updateDirectionButtons();
    }

    private void rollDice() {
        Timeline timeline = new Timeline();
        diceFace.setX(1000);
        diceFace.setY(100);
        diceFace.setStyle("-fx-font-size: 72;");

        // Create a series of keyframes that change the face of the dice
        for (int i = 0; i < 10; i++) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), e -> {
                int face = dice.roll();
                diceFace.setText(String.valueOf(face));
            }));
        }

        timeline.setOnFinished(e -> {
            gameStarted = true;
            // Enable all direction buttons after rolling the dice
            turnRightButton.setDisable(false);
            turnLeftButton.setDisable(false);
            turn180DegreeButton.setDisable(false);
            northButton.setDisable(false);
            directionSelected = false;

            //updateCurrentPlayerLabel();  // Update player label
            int dieResult = Integer.parseInt(diceFace.getText());  // Get the die result from the diceFace Text node
            moveAssamAfterRoll(dieResult);  // Move Assam based on the die result
            putTwoRug();
        });
        timeline.play(); //Play the dice
        updateDirectionButtons();
    }

    /**
     * Update the current player's turn label.
     */
    private void updateCurrentPlayerLabel() {
        // Cycle through players after each turn
        directionSelected = false;
        turnRightButton.setDisable(false);  // Enable the direction buttons
        turnLeftButton.setDisable(false);
        turn180DegreeButton.setDisable(false);
        northButton.setDisable(false);
        currentPlayer = (currentPlayer + 1) % players.length;
        currentPlayerLabel.setText("Player " + (currentPlayer + 1) + "'s turn");
    }

    /**
     * Disables the direction buttons after Assam's direction has been selected.
     */
    private void disableDirectionButtons(Button turnRightButton, Button turnLeftButton, Button turn180DegreeButton, Button northButton) {
        turnRightButton.setDisable(true);
        turnLeftButton.setDisable(true);
        turn180DegreeButton.setDisable(true);
        northButton.setDisable(true);
    }

    /**
     * Move Assam based on the roll of the dice.
     *
     * @param dieResult The result of the dice roll.
     */
    private void moveAssamAfterRoll(int dieResult) {
        String newAssamState = Marrakech.moveAssam(gameState.getAssam().getString(), dieResult);
        if (!newAssamState.equals(gameState.getAssam().getString())) {
            // Update Assam's position and orientation based on the new state
            Assam newAssam = new Assam(newAssamState);
            gameState = new State(gameState.getPlayers(), newAssam, gameState.getBoard());
            refreshGameView(gameState);  // Refresh the game view to reflect Assam's new position
            updateDirectionButtons();
        }
    }



    /**
     * Rotate Assam to face the specified direction.
     *
     * @param currentAssam The current state of Assam.
     * @param targetDirection The direction to rotate Assam to.
     * @return The new state of Assam after rotation.
     */
    public static String rotateAssamToDirection(String currentAssam, String targetDirection) {
        Assam assam = new Assam(currentAssam);
        // Determine the current and target angles based on Assam's orientation
        int currentAngle = getAngleFromOrientation(assam.getOrientation());
        int targetAngle = getAngleFromOrientation(Assam.Orientation.valueOf(targetDirection));

        // Calculate the angle difference to determine the direction and magnitude of rotation
        int angleDifference = targetAngle - currentAngle;
        if (angleDifference < 0) {
            angleDifference += 360;
        }

        // Rotate Assam based on the calculated angle difference
        if (angleDifference == 90) {
            assam.setOrientationRight90();
        } else if (angleDifference == 270) {
            assam.setOrientationLeft90();
        } else if (angleDifference == 180) {
            assam.setOrientationBack();
        }

        return assam.getString();
    }

    /**
     * Converts Assam's orientation into an angle.
     * the angle difference when rotating Assam to a specific orientation.
     *
     * @param orientation The current orientation of Assam.
     * @return The angle corresponding to Assam's orientation, facilitating calculations for rotations.
     */
    private static int getAngleFromOrientation(Assam.Orientation orientation) {
        // Convert Assam's orientation into an angle
        switch (orientation) {
            case N: return 0;
            case E: return 90;
            case S: return 180;
            case W: return 270;
            default: throw new IllegalArgumentException("Illegal orientation");
        }
    }

    /**
     * Update the state of the direction buttons based on Assam's current orientation and the game state.
     */
    private void updateDirectionButtons() {
        // Enable all direction buttons if the game has not started yet
        if (!gameStarted) {
            turnRightButton.setDisable(false);
            turnLeftButton.setDisable(false);
            turn180DegreeButton.setDisable(false);
            northButton.setDisable(false);
            return;
        }
        // Get Assam's current orientation from the game state
        Assam.Orientation orientation = gameState.getAssam().getOrientation();
        // Enable or disable buttons based on Assam's orientation to indicate valid directions for movement
        switch (orientation) {
            case N:
            case S:
                turn180DegreeButton.setDisable(true);
                northButton.setDisable(true);
                break;
            case E:
            case W:
                turnLeftButton.setDisable(true);
                turnRightButton.setDisable(true);
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation");
        }
    }

    private void putTwoRug() {
        turnRightButton.setDisable(false);  // Enable the direction buttons
        turnLeftButton.setDisable(false);
        turn180DegreeButton.setDisable(false);
        northButton.setDisable(false);
        // TODO：提示放地毯
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, handleMouseClick);
    }

    EventHandler<MouseEvent> handleMouseClick = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            // TODO：放地毯的逻辑有问题，设置flag记录第一次点击和第二次点击，点击完以后跳出

            System.out.println("Mouse Clicked at: X = " + e.getX() + ", Y = " + e.getY());
            System.out.println("START_X: " + START_X + ", START_Y: " + START_Y);

            // Check if the game has started and a direction has been selected
            double x = e.getX();
            double y = e.getY();

            // Check if the mouse click is within the board boundaries
            if (x >= START_X && x <= START_X + Board.BOARD_WIDTH * Tile_Size &&
                    y >= START_Y && y <= START_Y + Board.BOARD_HEIGHT * Tile_Size) {

                // Convert the mouse click coordinates to board column and row indices
                int col = (int) ((x - START_X) / Tile_Size);
                int row = (int) ((y - START_Y) / Tile_Size);
                System.out.println("11111");

                // Check if the converted column and row indices are valid
                if (col >= 0 && row >= 0 && col < Board.BOARD_WIDTH && row < Board.BOARD_HEIGHT) {
                    // Add the selected tile to the list of selected tiles
                    selectedTiles.add(new Point(col, row));
                    System.out.println("Selected Tiles: " + selectedTiles);

                    // Check if two tiles have been selected to place a rug
                    if (selectedTiles.size() == 2) {
                        // Get the current player and their color
                        Player current = players[currentPlayer];
                        Color currentPlayerColor = current.getColor();

                        // Generate a new rug ID and create a new rug with the current player's color
                        int newRugID = nextRugID++;
                        AbbreviatedRug newRug = new AbbreviatedRug(currentPlayerColor, newRugID);

                        // Create a string representation of the rug placement
                        String rugString = newRug.getString() +
                                selectedTiles.get(0).getX() +
                                selectedTiles.get(0).getY() +
                                selectedTiles.get(1).getX() +
                                selectedTiles.get(1).getY();
                        System.out.println("Rug String Before Placement: " + rugString);

                        // Check if the rug and its placement are valid
                        if (isRugValid(gameState.getString(), rugString) &&
                                isPlacementValid(gameState.getString(), rugString)) {

                            // Make the rug placement and get the new game state
                            String newGameState = makePlacement(gameState.getString(), rugString);
                            System.out.println("Old Game State: " + gameState.getString());
                            System.out.println("New Game State: " + newGameState);
                            System.out.println("New Game State After Placement: " + newGameState);

                            // Update the game state and refresh the game view if the game state has changed
                            if (newGameState != null && !newGameState.equals(gameState.getString())) {
                                gameState = new State(newGameState);
                                refreshGameView(gameState);
                            }
                        } else {
                            // Print an error message if the rug or its placement is invalid
                            System.out.println("Invalid rug or placement");
                            System.out.println("isRugValid: " + isRugValid(gameState.getString(), rugString));
                            System.out.println("isPlacementValid: " + isPlacementValid(gameState.getString(), rugString));
                        }

                        // Clear the selected tiles after attempting to place a rug
                        selectedTiles.clear();
                    }
                }
            }
            if (putTwoRugCounter++ == 1) {
                updateCurrentPlayerLabel();
                root.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
                putTwoRugCounter = 0;
            }
        }
    };

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        newGame();
        makeControls();
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Print number of root
//        System.out.println("Number of children in root: " + root.getChildren().size());
//        root.getChildren().forEach(child -> System.out.println(child.toString()));
        stage.setScene(scene);
        stage.show();
    }
}
/**
 *逻辑：
 * 游戏开始，第一个移动的玩家可以选择assam的任何方向，后续的玩家只能向左或者向右旋转90度
 * 先选择Assam旋转的(Task 9)方向，然后投骰子，然后Assam移动(Task 13),在完成移动后，使用Task 11判断玩家是否需要支付coins给其他玩家
 * 然后玩家选择放置一张地毯在Assam的W,E,N,S方向(Task 14)
 *一张rug占据的格子数量是两格,判断放置是否合法(Task 10)，当合法后，其他玩家依次移动assam放rug，当当前回合结束后，判断玩家是否over(Task 8)
 * 以及每回合都判断游戏是否结束，以及胜者是谁(Task 12)
 */
// Assam本地和gamestate冲突了

