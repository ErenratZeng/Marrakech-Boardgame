package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import comp1110.ass2.model.*;
import comp1110.ass2.model.base.Dice;
import comp1110.ass2.model.base.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

import static comp1110.ass2.Marrakech.*;
import static comp1110.ass2.gui.Viewer.*;

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
    private ArrayList<Boolean> AIList = new ArrayList<>();
    private ArrayList<Player.Level> levelList = new ArrayList<>();
    private final Text currentPlayerLabel = new Text();
    private Button eastButton;
    private Button westButton;
    private Button southButton;
    private Button northButton;
    private Button rollButton;
    private final Point[] selectedRugPoints = new Point[2];

    private int putTwoRugCounter = 0;


    private void newGame() {
        Text[] playerTexts = new Text[4];
        Button[] playerButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            playerTexts[i] = new Text("Player " + i);
            Text playerText = playerTexts[i];
            playerText.setLayoutX(200 * i + 200);
            playerText.setLayoutY(300);
            root.getChildren().add(playerText);

            playerButtons[i] = new Button("Human");
            Button playerButton = playerButtons[i];
            playerButton.setLayoutX(200 * i + 200);
            playerButton.setLayoutY(400);
            playerButton.setOnAction(e -> {
                switch (playerButton.getText()) {
                    case "Disable" -> playerButton.setText("Human");
                    case "Human" -> playerButton.setText("AI");
                    case "AI" -> playerButton.setText("Disable");
                }
            });
            root.getChildren().add(playerButton);
        }

        Stack<Color> colors = new Stack<>();
        colors.push(Color.CYAN);
        colors.push(Color.YELLOW);
        colors.push(Color.PURPLE);
        colors.push(Color.RED);

        Button start = new Button("start");
        start.setLayoutX(1000);
        start.setLayoutY(500);
        start.setOnAction(event -> {
            ArrayList<Player> playersList = new ArrayList<>();
            for (Button playerButton : playerButtons) {
                switch (playerButton.getText()) {
                    case "Human", "AI" -> totalPlayer++;
                }
            }
            if (totalPlayer > 1) {
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
                        case "AI" -> {
                            playersList.add(new Player(colors.pop()));
                            AIList.add(true);
                            levelList.add(Player.Level.easy);
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
     * Method that creates elements and game controls buttons for the game.
     */

    public void makeControls() {
        viewer = new Viewer();
        root.getChildren().add(viewer.getViewerRoot()); //Add root to viewer
        refreshGameView(gameState);
        Rectangle square = new Rectangle(965, 25, 110, 110);
        square.setFill(Color.GREY);

        currentPlayerLabel.setX(365);
        currentPlayerLabel.setY(60);
        currentPlayerLabel.setStyle("-fx-font-size: 72;");
        currentPlayerLabel.setText("Player " + 1 + "'s turn");

        // Create a button to roll the dice and set its position
        rollButton = new Button("Roll Dice");
        rollButton.setLayoutX(720);
        rollButton.setLayoutY(650);
        rollButton.setOnAction(e -> rollDice());

        eastButton = new Button("→");
        westButton = new Button("←");
        southButton = new Button("↓");
        northButton = new Button("↑");

        // Rotate Assam 90 degrees to the Right when clicked
        eastButton.setLayoutX(1000);
        eastButton.setLayoutY(600);
        eastButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "E");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        westButton.setLayoutX(900);
        westButton.setLayoutY(600);
        westButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "W");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        southButton.setLayoutX(950);
        southButton.setLayoutY(650);
        southButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "S");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        northButton.setLayoutX(950);
        northButton.setLayoutY(550);
        northButton.setOnAction(e -> {
            String newAssamState = rotateAssamToDirection(gameState.getAssam().getString(), "N");
            if (!newAssamState.equals(gameState.getAssam().getString())) {
                gameState.getAssam().setOrientation(Assam.Orientation.valueOf(newAssamState.substring(3, 4)));
                refreshGameView(gameState);
                disableDirectionButtons();
            }
        });

        root.getChildren().addAll(square, diceFace, rollButton, eastButton, westButton, southButton, northButton, currentPlayerLabel);

        if (AIList.get(currentPlayer)) {
            rollDice();
        }

        Button backButton = new Button("Back to Player Selection");
        backButton.setLayoutX(50);
        backButton.setLayoutY(650);
        backButton.setOnAction(e -> resetGame());
        root.getChildren().add(backButton);
    }

    private void rollDice() {
        rollButton.setDisable(true);
        disableDirectionButtons();
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
            disableDirectionButtons();
            int dieResult = Integer.parseInt(diceFace.getText());  // Get the die result from the diceFace Text node
            moveAssamAfterRoll(dieResult);  // Move Assam based on the die result
            Player current = gameState.getPlayer(currentPlayer);
            Color color = gameState.getBoard().getRug(gameState.getAssam().getPoint().getX(), gameState.getAssam().getPoint().getY()).getColor();
            for (Player player : gameState.getPlayers()) {
                if (player.getColor() == color) {
                    player.gainCoins(current.payCoins(getPaymentAmount(gameState.getString())));
                    refreshGameView(gameState);
                }
            }

            if (AIList.get(currentPlayer)) {
                gameState = current.actionRug(gameState, levelList.get(currentPlayer));
                refreshGameView(gameState);
                addConnectingLine(selectedRugPoints[0], selectedRugPoints[1], current.getColor());
                updateCurrentPlayerLabel();
            } else {
                // TODO：增加放地毯的提示
                root.addEventFilter(MouseEvent.MOUSE_CLICKED, handleMouseClick);
            }
        });
        timeline.play(); //Play the dice
    }

    /**
     * Update the current player's turn label.
     */
    private void updateCurrentPlayerLabel() {
        // Cycle through players after each turn
        updateDirectionButtons();
        rollButton.setDisable(false);
        currentPlayer = (currentPlayer + 1) % 4; //totalPlayer = 4
        // Determine the winner of a game of Marrakech.
        switch (getWinner(gameState.getString())) {
            case 'n' -> {
                while (!gameState.getPlayer(currentPlayer).getAlive()) {
                    currentPlayer = (currentPlayer + 1) % 4; //totalPlayer = 4
                }
                currentPlayerLabel.setText("Player " + (currentPlayerNo++ % totalPlayer + 1) + "'s turn");
                if (AIList.get(currentPlayer)) {
                    gameState = gameState.getPlayer(currentPlayer).actionAssam(gameState, levelList.get(currentPlayer));
                    rollDice();
                }
            }
            case 't' -> {
                currentPlayerLabel.setText("Game is a tie");
                rollButton.setDisable(true);
            }
            case 'c' -> {
                currentPlayerLabel.setText("Winner is Cyan");
                rollButton.setDisable(true);
            }
            case 'y' -> {
                currentPlayerLabel.setText("Winner is Yellow");
                rollButton.setDisable(true);
            }
            case 'r' -> {
                currentPlayerLabel.setText("Winner is Red");
                rollButton.setDisable(true);
            }
            case 'p' -> {
                currentPlayerLabel.setText("Winner is Purple");
                rollButton.setDisable(true);
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
                    putTwoRugCounter++;
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
                        gameState = new State(newGameState);
                        refreshGameView(gameState);
                        addConnectingLine(selectedRugPoints[0], selectedRugPoints[1], current.getColor());

                        updateCurrentPlayerLabel();
                        root.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
                        putTwoRugCounter = 0;
                    }
                }

            }
        }
    };

    private void addConnectingLine(Point p1, Point p2, Color color) {
        // TODO
        double x1 = viewer.getOffsetX() + p1.getX() * TILE_SIZE + TILE_SIZE / 2;
        double y1 = viewer.getOffsetY() + p1.getY() * TILE_SIZE + TILE_SIZE / 2;
        double x2 = viewer.getOffsetX() + p2.getX() * TILE_SIZE + TILE_SIZE / 2;
        double y2 = viewer.getOffsetY() + p2.getY() * TILE_SIZE + TILE_SIZE / 2;


        Line line;
        if (y1 == y2) { // If the rugs are placed horizontally
            double midX = (x1 + x2) / 2;
            line = new Line(midX, y1 - TILE_SIZE / 2, midX, y2 + TILE_SIZE / 2); // Adjusted the y coordinates
        } else if (x1 == x2) { // If the rugs are placed vertically
            double midY = (y1 + y2) / 2;
            line = new Line(x1 - TILE_SIZE / 2, midY, x2 + TILE_SIZE / 2, midY); // Adjusted the x coordinates
        } else {
            // In case the rugs are not aligned horizontally or vertically
            return;
        }

//        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(color);
        line.setStrokeWidth(4);
        root.getChildren().add(line);
    }



    private void resetGame() {

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
        stage.setScene(scene);
        stage.show();
    }
}
// TODO套转向边框 and 地毯连接到一起 提醒玩家放地毯的提示（显示可放范围） label显示玩家颜色
/**
 * 逻辑：
 * 选择Player数量(2~4),以及是否加入AI
 * 若加入AI，选择ai难度
 * 游戏开始，第一个移动的玩家可以选择assam的任何方向，后续的玩家只能向左或者向右旋转90度（Finished）
 * 先选择Assam旋转的(Task 9)方向（Finished）
 * 然后投骰子(Task 6)（Finished）
 * 然后Assam移动(Task 13)（Finished）
 * 完成移动后判断玩家是否需要支付coins给其他玩家(Task 11)
 * 然后玩家选择放置一张地毯在Assam的W,E,N,S方向(Task 14)
 * 一张rug占据的格子数量是两格,判断放置是否合法(Task 10)
 * 当合法后地毯被放置，其他玩家依次移动assam放rug
 * 玩家当前回合结束后，判断玩家是否over(Task 8)
 * 以及每回合都判断游戏是否结束，以及胜者是谁(Task 12)
 */

