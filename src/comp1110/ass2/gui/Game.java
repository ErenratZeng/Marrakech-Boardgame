package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import comp1110.ass2.model.*;
import comp1110.ass2.model.base.Dice;
import comp1110.ass2.model.base.Point;
import comp1110.ass2.model.base.Tuple;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import static comp1110.ass2.Marrakech.*;
import static comp1110.ass2.gui.Viewer.*;

/**
 * Authorship:
 * name: Zhuiqi Lin
 * uid: u7733924
 * name: Qiutong Zeng
 * uid: u7724723
 */
public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private final Dice dice = new Dice();
    private Viewer viewer;
    private final Text diceFace = new Text("0");
    private State gameState;
    private int currentPlayer = 0;  // track current player
    private int currentPlayerNo = 1;  // track current player display
    private int totalPlayer = 0;
    private final ArrayList<Boolean> AIList = new ArrayList<>();
    private final ArrayList<Player.Level> levelList = new ArrayList<>();
    private final Text currentPlayerLabel = new Text();
    private Button eastButton;
    private Button westButton;
    private Button southButton;
    private Button northButton;
    private Button rollButton;
    private final Point[] selectedRugPoints = new Point[2];
    private final Rectangle currentPlayerColorRectangle = new Rectangle(0, 0, 40, 40);
    private final ArrayList<Rectangle> hintSquares = new ArrayList<>();
    private int putTwoRugCounter = 0;

    /**
     * Initializes a new game, setting up the player selection and starting state of the game board.
     */
    private void newGame() {
        // Creating player texts and buttons for selecting player types (human or AI)
        Text[] playerTexts = new Text[4];
        Button[] playerButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            playerTexts[i] = new Text("Player " + (i + 1));
            Text playerText = playerTexts[i];
            playerText.setLayoutX(200 * i + 200);
            playerText.setLayoutY(300);
            playerText.getStyleClass().add("playerText");
            root.getChildren().add(playerText);

            playerButtons[i] = new Button("Human");
            Button playerButton = playerButtons[i];
            playerButton.setLayoutX(200 * i + 200 + 20);
            playerButton.setLayoutY(400);
            playerButton.getStyleClass().add("playerButton");
            Tooltip tooltip = new Tooltip("Click it to change to AI or Human");
            tooltip.setShowDelay(Duration.seconds(0.01));
            Tooltip.install(playerButton, tooltip);
            playerButton.setOnAction(e -> {
                switch (playerButton.getText()) {
                    case "Disable" -> playerButton.setText("Human");
                    case "Human" -> playerButton.setText("Easy");
                    case "Easy" -> playerButton.setText("Normal");
                    case "Normal" -> playerButton.setText("Hard");
                    case "Hard" -> playerButton.setText("Disable");
                }
            });
            root.getChildren().add(playerButton);
        }

        Stack<Color> colors = new Stack<>();
        colors.push(Color.CYAN);
        colors.push(Color.YELLOW);
        colors.push(Color.PURPLE);
        colors.push(Color.RED);

        // Creating images to represent different players
        Image player1_image = new Image("comp1110/ass2/gui/assets/player_red.png");  // Update with the actual path
        ImageView player1_imageview = new ImageView(player1_image);
        player1_imageview.setX(170);
        player1_imageview.setY(150);
        player1_imageview.setFitWidth(150);
        player1_imageview.setFitHeight(150);
        root.getChildren().add(player1_imageview);

        Image player2_image = new Image("comp1110/ass2/gui/assets/player_purple.png");  // Update with the actual path
        ImageView player2_imageview = new ImageView(player2_image);
        player2_imageview.setX(370);
        player2_imageview.setY(150);
        player2_imageview.setFitWidth(150);
        player2_imageview.setFitHeight(150);
        root.getChildren().add(player2_imageview);

        Image player3_image = new Image("comp1110/ass2/gui/assets/player_yellow.png");  // Update with the actual path
        ImageView player3_imageview = new ImageView(player3_image);
        player3_imageview.setX(570);
        player3_imageview.setY(150);
        player3_imageview.setFitWidth(150);
        player3_imageview.setFitHeight(150);
        root.getChildren().add(player3_imageview);

        Image player4_image = new Image("comp1110/ass2/gui/assets/player_cyan.png");  // Update with the actual path
        ImageView player4_imageview = new ImageView(player4_image);
        player4_imageview.setX(770);
        player4_imageview.setY(150);
        player4_imageview.setFitWidth(150);
        player4_imageview.setFitHeight(150);
        root.getChildren().add(player4_imageview);

        // Create a start button
        Button start = new Button("Start");
        start.setLayoutX(520);
        start.setLayoutY(500);
        start.getStyleClass().add("start");
        Tooltip tooltip = new Tooltip("Are u sure u wanna start the game?");
        tooltip.setShowDelay(Duration.seconds(0.01));
        Tooltip.install(start, tooltip);
        start.setOnAction(event -> {
            ArrayList<Player> playersList = new ArrayList<>();
            for (Button playerButton : playerButtons) {
                switch (playerButton.getText()) {
                    case "Human", "Easy", "Normal", "Hard" -> totalPlayer++;
                }
            }

            // Setting up the initial game state and player configurations based on selections
            if (totalPlayer > 1) {
                while (Objects.equals(playerButtons[currentPlayer].getText(), "Disable")) currentPlayer++;
                for (Button playerButton : playerButtons) {
                    switch (playerButton.getText()) {
                        case "Disable" -> {
                            playersList.add(new Player(colors.pop()).setAlive(false));
                            AIList.add(false);
                            levelList.add(Player.Level.easy);
                        }
                        case "Human" -> {
                            playersList.add(new Player(colors.pop()));
                            AIList.add(false);
                            levelList.add(Player.Level.easy);
                        }
                        case "Easy" -> {
                            playersList.add(new Player(colors.pop()));
                            AIList.add(true);
                            levelList.add(Player.Level.easy);
                        }
                        case "Normal" -> {
                            playersList.add(new Player(colors.pop()));
                            AIList.add(true);
                            levelList.add(Player.Level.normal);
                        }
                        case "Hard" -> {
                            playersList.add(new Player(colors.pop()));
                            AIList.add(true);
                            levelList.add(Player.Level.hard);
                        }
                    }
                }
                for (Button playerButton : playerButtons) {
                    root.getChildren().remove(playerButton);
                }
                for (Text playerText : playerTexts) {
                    root.getChildren().remove(playerText);
                }
                root.getChildren().remove(start);
                root.getChildren().remove(player1_imageview);
                root.getChildren().remove(player2_imageview);
                root.getChildren().remove(player3_imageview);
                root.getChildren().remove(player4_imageview);

                gameState = new State(playersList);
                makeControls();
            }
        });
        root.getChildren().add(start);


    }

    /**
     * Update the game view based on the current game state.
     *
     * @param gameState The current game state.
     */
    private void refreshGameView(State gameState) {
        // Use the getString() method of the State class to get the string representation of the current game state
        String currentState = gameState.getString();
        // Use the displayState() method of the Viewer class to display the current game state
        if (currentState == null || currentState.isEmpty()) {
            System.err.println("Error: Game state string is empty or null.");
            return;
        }
        viewer.displayState(currentState);
    }

    /**
     * Sets up the game controls and initializes the game board view.
     */
    public void makeControls() {
        // Initializing the game viewer
        viewer = new Viewer();
        root.getChildren().add(viewer.getViewerRoot()); //Add root to viewer
        refreshGameView(gameState);
        Rectangle square = new Rectangle(66, 123, 110, 110);
        square.setArcWidth(60);
        square.setArcHeight(60);
        square.setFill(Color.GREY);

        // setting up the game board background
        Image boardBackgroundImage = new Image(getClass().getResource("/comp1110/ass2/gui/assets/BoardImage.png").toString());
        ImageView boardBackgroundView = new ImageView(boardBackgroundImage);

        // Set board background size
        boardBackgroundView.setFitWidth(Board.BOARD_WIDTH * TILE_SIZE);
        boardBackgroundView.setFitHeight(Board.BOARD_HEIGHT * TILE_SIZE);

        // Set the board background
        boardBackgroundView.setX(offsetX-100);
        boardBackgroundView.setY(offsetY-103);
        boardBackgroundView.setFitWidth(690);
        boardBackgroundView.setFitHeight(690);
        root.getChildren().add(boardBackgroundView);

        // Setting up the label to display the current player's turn
        currentPlayerLabel.setX(10);
        currentPlayerLabel.setY(300);
        currentPlayerLabel.getStyleClass().add("currentPlayerLabel");
        currentPlayerLabel.setText("Player " + 1 + "'s turn");

        // Create a button to roll the dice and set its position
        rollButton = new Button("Roll dice");
        rollButton.getStyleClass().add("rollButton");
        Tooltip tooltip = new Tooltip("Click it");
        Tooltip.install(rollButton, tooltip);
        rollButton.setLayoutX(230);
        rollButton.setLayoutY(392);
        rollButton.setOnAction(e -> rollDice());

        // Set up the direction buttons for assam
        eastButton = new Button("→");
        westButton = new Button("←");
        southButton = new Button("↓");
        northButton = new Button("↑");
        eastButton.getStyleClass().add("eastButton");
        westButton.getStyleClass().add("westButton");
        southButton.getStyleClass().add("southButton");
        northButton.getStyleClass().add("northButton");

        eastButton.setLayoutX(136);
        eastButton.setLayoutY(390);
        eastButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "E");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        westButton.setLayoutX(55);
        westButton.setLayoutY(390);
        westButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "W");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        southButton.setLayoutX(95);
        southButton.setLayoutY(430);
        southButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "S");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        northButton.setLayoutX(95);
        northButton.setLayoutY(350);
        northButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "N");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        root.getChildren().addAll(square, diceFace, rollButton, eastButton, westButton, southButton, northButton, currentPlayerLabel);

        // Handling AI player's turn
        if (AIList.get(currentPlayer)) {
            rollDice();
        }

        // Adding the return button to allow resetting the game
        Button backButton = new Button("Back to Player Selection");
        backButton.setLayoutX(50);
        backButton.setLayoutY(600);
        backButton.getStyleClass().add("backButton");
        Tooltip tooltip1 = new Tooltip("Are u sure u wanna leave the game?");
        tooltip1.setShowDelay(Duration.seconds(0.01));
        Tooltip.install(backButton, tooltip1);
        backButton.setOnAction(e -> resetGame());
        root.getChildren().add(backButton);

        // Displaying the current player's color
        Color currentPlayerColor = gameState.getPlayer(currentPlayer).getColor();
        // update player color
        currentPlayerColorRectangle.setFill(currentPlayerColor);
        currentPlayerColorRectangle.setX(360);
        currentPlayerColorRectangle.setY(265);
        currentPlayerColorRectangle.setStroke(Color.BLACK);
        currentPlayerColorRectangle.setStrokeWidth(2);
        root.getChildren().add(currentPlayerColorRectangle);
    }

    private void rollDice() {
        rollButton.setDisable(true);
        // Disabling the roll button and direction buttons during the dice roll
        disableDirectionButtons();
        Timeline timeline = new Timeline();
        diceFace.setX(100);
        diceFace.setY(200);
        diceFace.setStyle("-fx-font-size: 72;");

        // Create a series of keyframes that change the face of the dice
        for (int i = 0; i < 10; i++) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), e -> {
                int face = dice.roll();
                diceFace.setText(String.valueOf(face));
            }));
        }

        // Creating a timeline for animating the dice roll
        timeline.setOnFinished(e -> {
            disableDirectionButtons();
            // Get the die result from the diceFace Text node
            int dieResult = Integer.parseInt(diceFace.getText());
            // Move Assam based on the die result
            moveAssamAfterRoll(dieResult);
            Player current = gameState.getPlayer(currentPlayer);
            Color color = gameState.getBoard().getRug(gameState.getAssam().getPoint().getX(), gameState.getAssam().getPoint().getY()).getColor();
            for (Player player : gameState.getPlayers()) {
                if (player.getColor() == color) {
                    player.gainCoins(current.payCoins(getPaymentAmount(gameState.getString())));
                    refreshGameView(gameState);
                }
            }
            if (current.getAlive()) {
                if (AIList.get(currentPlayer)) {
                    Tuple<State, TwoRug> tuple = current.actionRug(gameState, levelList.get(currentPlayer));
                    gameState = tuple.x;
                    selectedRugPoints[0] = tuple.y.getPoints()[0];
                    selectedRugPoints[1] = tuple.y.getPoints()[1];
                    refreshGameView(gameState);
                    updateCurrentPlayerLabel();
                } else {
                    int x = gameState.getAssam().getPoint().getX();
                    int y = gameState.getAssam().getPoint().getY();
                    if (x + 1 < BOARD_SIZE)
                        hintSquares.add(new Rectangle((x + 1) * TILE_SIZE + offsetX, y * TILE_SIZE + offsetY, TILE_SIZE, TILE_SIZE));
                    if (x - 1 >= 0)
                        hintSquares.add(new Rectangle((x - 1) * TILE_SIZE + offsetX, y * TILE_SIZE + offsetY, TILE_SIZE, TILE_SIZE));
                    if (y + 1 < BOARD_SIZE)
                        hintSquares.add(new Rectangle(x * TILE_SIZE + offsetX, (y + 1) * TILE_SIZE + offsetY, TILE_SIZE, TILE_SIZE));
                    if (y - 1 >= 0)
                        hintSquares.add(new Rectangle(x * TILE_SIZE + offsetX, (y - 1) * TILE_SIZE + offsetY, TILE_SIZE, TILE_SIZE));
                    for (Rectangle hintSquare : hintSquares) {
                        hintSquare.setFill(gameState.getPlayer(currentPlayer).getColor());
                        hintSquare.setOpacity(0.3);
                        root.getChildren().add(hintSquare);
                    }
                    root.addEventFilter(MouseEvent.MOUSE_CLICKED, handleMouseClick);
                }
            } else {
                updateCurrentPlayerLabel();
            }
        });
        timeline.play(); //Play the dice
    }

    /**
     * Checks if the selected rug placement is valid and provides hints for the second rug placement.
     */
    private void checkHint() {
        String rugString = new TwoRug(gameState.getPlayer(currentPlayer).getColor(), gameState.getPlayer(currentPlayer).getRugNum(), selectedRugPoints).getString();
        String newGameState = makePlacement(gameState.getString(), rugString);
        if (newGameState != null && !newGameState.equals(gameState.getString())){
            hintSquares.add(new Rectangle(selectedRugPoints[1].getX() * TILE_SIZE + offsetX, selectedRugPoints[1].getY() * TILE_SIZE + offsetY, TILE_SIZE, TILE_SIZE));
        }
    }

    /**
     * Updates the label displaying the current player's turn and handles game end conditions.
     */
    private void updateCurrentPlayerLabel() {
        // Cycling through players and handling the game end conditions
        updateDirectionButtons();
        rollButton.setDisable(false);
        //totalPlayer = 4
        currentPlayer = (currentPlayer + 1) % 4;

        // Determine the winner of a game of Marrakech.
        switch (getWinner(gameState.getString())) {
            case 'n' -> {
                while (!gameState.getPlayer(currentPlayer).getAlive()) {
                    currentPlayer = (currentPlayer + 1) % 4;
                }
                currentPlayerLabel.setText("Player " + (currentPlayerNo++ % totalPlayer + 1) + "'s turn");
                // Get current player color
                Color currentPlayerColor = gameState.getPlayer(currentPlayer).getColor();

                // update square color
                currentPlayerColorRectangle.setFill(currentPlayerColor);
                // update square position
                currentPlayerColorRectangle.setX(360);
                currentPlayerColorRectangle.setY(265);
                currentPlayerColorRectangle.setStroke(Color.BLACK);
                currentPlayerColorRectangle.setStrokeWidth(2);

                if (AIList.get(currentPlayer)) {
                    gameState = gameState.getPlayer(currentPlayer).actionAssam(gameState, levelList.get(currentPlayer));
                    rollDice();
                }
            }
            case 't' -> {
                currentPlayerLabel.setText("Game is a tie");
                rollButton.setDisable(true);
                root.getChildren().remove(currentPlayerColorRectangle);
            }
            case 'c' -> {
                currentPlayerLabel.setText("Winner is Cyan");
                rollButton.setDisable(true);
                root.getChildren().remove(currentPlayerColorRectangle);
            }
            case 'y' -> {
                currentPlayerLabel.setText("Winner is Yellow");
                rollButton.setDisable(true);
                root.getChildren().remove(currentPlayerColorRectangle);
            }
            case 'r' -> {
                currentPlayerLabel.setText("Winner is Red");
                rollButton.setDisable(true);
                root.getChildren().remove(currentPlayerColorRectangle);
            }
            case 'p' -> {
                currentPlayerLabel.setText("Winner is Purple");
                rollButton.setDisable(true);
                root.getChildren().remove(currentPlayerColorRectangle);
            }
        }
    }

    /**
     * Disables the direction buttons after Assam's direction has been selected.
     */
    private void disableDirectionButtons() {
        eastButton.setDisable(true);
        westButton.setDisable(true);
        southButton.setDisable(true);
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
        }
    }


    /**
     * Rotate Assam to face the specified direction.
     *
     * @param currentAssam    The current state of Assam.
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
        return switch (orientation) {
            case N -> 0;
            case E -> 90;
            case S -> 180;
            case W -> 270;
        };
    }

    /**
     * Update the state of the direction buttons based on Assam's current orientation and the game state.
     */
    private void updateDirectionButtons() {
        // Get Assam's current orientation from the game state
        Assam.Orientation orientation = gameState.getAssam().getOrientation();
        // Enable or disable buttons based on Assam's orientation to indicate valid directions for movement
        switch (orientation) {
            case N, S -> {
                westButton.setDisable(false);
                eastButton.setDisable(false);
            }
            case E, W -> {

                southButton.setDisable(false);
                northButton.setDisable(false);
            }
        }
    }

    /**
     * Handles mouse clicks for selecting rug placements.
     */
    EventHandler<MouseEvent> handleMouseClick = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            int colAssam = gameState.getAssam().getPoint().getX();
            int rowAssam = gameState.getAssam().getPoint().getY();

            // Check if the game has started and a direction has been selected
            double x = e.getX();
            double y = e.getY();
            int colRug = -1;
            int rowRug = -1;

            // Check if the mouse click is within the board boundaries
            if (x >= offsetX && x <= offsetX + Board.BOARD_WIDTH * TILE_SIZE &&
                    y >= offsetY && y <= offsetY + Board.BOARD_HEIGHT * TILE_SIZE) {

                // Convert the mouse click coordinates to board column and row indices
                colRug = (int) ((x - offsetX) / TILE_SIZE);
                rowRug = (int) ((y - offsetY) / TILE_SIZE);
            }
            if (putTwoRugCounter == 0) {
                // Check if the converted column and row indices are valid
                if ((colRug == colAssam + 1 && rowRug == rowAssam) ||
                        (colRug == colAssam - 1 && rowRug == rowAssam) ||
                        (colRug == colAssam && rowRug == rowAssam + 1) ||
                        (colRug == colAssam && rowRug == rowAssam - 1)
                ) {
                    selectedRugPoints[0] = new Point(colRug, rowRug);
                    for (Rectangle hintSquare : hintSquares) {
                        root.getChildren().remove(hintSquare);
                    }
                    hintSquares.clear();
                    viewer.putRug(colRug, rowRug, gameState.getPlayer(currentPlayer).getColor());
                    putTwoRugCounter++;
                    selectedRugPoints[1] = new Point(selectedRugPoints[0].getX() + 1, selectedRugPoints[0].getY());
                    checkHint();
                    selectedRugPoints[1] = new Point(selectedRugPoints[0].getX() - 1, selectedRugPoints[0].getY());
                    checkHint();
                    selectedRugPoints[1] = new Point(selectedRugPoints[0].getX(), selectedRugPoints[0].getY() + 1);
                    checkHint();
                    selectedRugPoints[1] = new Point(selectedRugPoints[0].getX(), selectedRugPoints[0].getY() - 1);
                    checkHint();
                    for (Rectangle hintSquare : hintSquares) {
                        hintSquare.setFill(gameState.getPlayer(currentPlayer).getColor());
                        hintSquare.setOpacity(0.3);
                        root.getChildren().add(hintSquare);
                    }
                }


            } else if (putTwoRugCounter == 1) {
                selectedRugPoints[1] = new Point(colRug, rowRug);
                // Get the current player and their color
                Player current = gameState.getPlayer(currentPlayer);
                Color currentPlayerColor = current.getColor();

                // Create a string representation of the rug placement
                String rugString = new TwoRug(currentPlayerColor, current.getRugNum(), selectedRugPoints).getString();
                // Check if the rug and its placement are valid
                if (isRugValid(gameState.getString(), rugString) &&
                        isPlacementValid(gameState.getString(), rugString)) {

                    // Make the rug placement and get the new game state
                    String newGameState = makePlacement(gameState.getString(), rugString);

                    // Update the game state and refresh the game view if the game state has changed
                    if (newGameState != null && !newGameState.equals(gameState.getString())) {
                        for (Rectangle hintSquare : hintSquares) {
                            root.getChildren().remove(hintSquare);
                        }
                        hintSquares.clear();
                        gameState = new State(newGameState);
                        refreshGameView(gameState);

                        updateCurrentPlayerLabel();
                        root.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
                        putTwoRugCounter = 0;
                    }
                }

            }
        }
    };

    /**
     * Resets the game to the player selection screen.
     */
    private void resetGame() {
        // Clearing the game board and resetting to the player selection screen
        root.getChildren().clear();
        currentPlayer = 0;
        totalPlayer = 0;
        AIList.clear();
        levelList.clear();
        putTwoRugCounter = 0;

        Image backgroundImage = new Image(getClass().getResource("/comp1110/ass2/gui/assets/Background.png").toString());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(WINDOW_WIDTH);
        backgroundView.setFitHeight(WINDOW_HEIGHT);
        backgroundView.setOpacity(0.5);
        root.getChildren().add(backgroundView);
        newGame();
    }


    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        stage.setTitle("Marrakech Game");
        Image backgroundImage = new Image(getClass().getResource("/comp1110/ass2/gui/assets/Background.png").toString());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(WINDOW_WIDTH);
        backgroundView.setFitHeight(WINDOW_HEIGHT);
        backgroundView.setOpacity(0.5);
        root.getChildren().add(backgroundView);
        newGame();
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/comp1110/ass2/gui/marrakechStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}

