package comp1110.ass2.gui;

import comp1110.ass2.model.*;
import comp1110.ass2.model.base.Point;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;



import java.awt.*;
import java.util.ArrayList;

import static comp1110.ass2.model.Board.BOARD_HEIGHT;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    // Constants
    public static final int TILE_SIZE = 71;
    public static final int BOARD_SIZE = 7;
    public static final int BOARD_PIXEL_SIZE = TILE_SIZE * BOARD_SIZE;
    public static final int offsetX = (1200 - BOARD_PIXEL_SIZE) / 2;
    public static final int offsetY = (700 - BOARD_PIXEL_SIZE) / 2;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;

    private Label errorLabel = new Label();

    private String recentState;

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }



    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        // FIXME Task 5: implement the simple state viewer
        this.recentState = state;
        int scoreLabelY = 10;

        root.getChildren().clear();
        errorLabel.setText("");

        // Validate the state string
        if (state == null || state.length() < 144) {
            errorLabel.setText("Invalid game state provided: " + state);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setLayoutX(500);  // Set the x-coordinate for the error label
            errorLabel.setLayoutY(300);  // Set the y-coordinate for the error label
            root.getChildren().add(errorLabel);
            return;
        }

        // Use the State class to parse the game state
        State gameState = new State(state);

        // Draw the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                Rectangle square = new Rectangle(i * TILE_SIZE + offsetX, j * TILE_SIZE + offsetY, TILE_SIZE, TILE_SIZE);
                square.setFill((i + j) % 2 == 0 ? Color.DARKGRAY : Color.DARKGRAY);
                square.setStroke(Color.BLACK);
                square.setStrokeWidth(2);
                root.getChildren().add(square);
            }
        }

        // Get the list of players
        ArrayList<Player> players = gameState.getPlayers();
        int playerNumber = 1;

        // Get the coins of each player
        for (Player player : players) {
            if (!player.getAlive()) continue;
            Color playerColor = player.getColor();
            int playerMoney = player.getCoins();
            int remainingRugs = player.getRugNum();

            // Show the player coins & remain rugs
            Label scoreLabel = new Label("Player " + playerNumber + " Coins: " + playerMoney + "     Remain Rugs " + remainingRugs);
            scoreLabel.setLayoutX(50);
            scoreLabel.setLayoutY(scoreLabelY);
            root.getChildren().add(scoreLabel);

            // Display the player's rug color
            Rectangle colorSample = new Rectangle(10, scoreLabelY, 20, 20);
            colorSample.setFill(playerColor);
            colorSample.setStroke(Color.BLACK);
            colorSample.setStrokeWidth(2);
            root.getChildren().add(colorSample);

            scoreLabelY += 30;
            playerNumber++;

        }



        // Get the rug string from board string
        Board board = gameState.getBoard();
        String boardInfo = board.getString();
        for (int i = 1; i < boardInfo.length(); i += 3) {
            String abbreviatedRugString = boardInfo.substring(i, i + 3);
            AbbreviatedRug rug = new AbbreviatedRug(abbreviatedRugString);
            Color carpetColor = rug.getColor();

            int col = i / 3 / 7;
            int row = i / 3 % 7;

//            if (carpetColor != null) {
//                Rectangle carpet = new Rectangle((col * TILE_SIZE) + offsetX, (row * TILE_SIZE) + offsetY, TILE_SIZE, TILE_SIZE);
//                carpet.setFill(carpetColor);
//
//                carpet.setStroke(Color.BLACK);
//                carpet.setStrokeWidth(2);
//
//                root.getChildren().add(carpet);
//            }
            if (carpetColor != null) {
                // Replace rug with image by ImageView
                Image image = new Image(getImagePathBasedOnColor(carpetColor));
                ImageView carpet = new ImageView(image);
                carpet.setX((col * TILE_SIZE) + offsetX);
                carpet.setY((row * TILE_SIZE) + offsetY);
                carpet.setFitWidth(TILE_SIZE);
                carpet.setFitHeight(TILE_SIZE);
                root.getChildren().add(carpet);
            }

        }

        // Get Assam's information
        Assam assam = gameState.getAssam();
        Point assamPoint = assam.getPoint();
        Assam.Orientation assamOrientation = assam.getOrientation();

        // Draw Assam on the viewer
//        Circle assamCircle = new Circle((assamPoint.getX() * TILE_SIZE + TILE_SIZE / 2) + offsetX, (assamPoint.getY() * TILE_SIZE + TILE_SIZE / 2) + offsetY, TILE_SIZE * 0.4);
//        assamCircle.setFill(Color.ORANGE);
//        root.getChildren().add(assamCircle);
        Image assamImage = new Image("comp1110/ass2/gui/assets/Assam.png");  // Update with the actual path
        ImageView assamImageView = new ImageView(assamImage);
        assamImageView.setX((assamPoint.getX() * TILE_SIZE) + offsetX);
        assamImageView.setY((assamPoint.getY() * TILE_SIZE) + offsetY);
        assamImageView.setFitWidth(TILE_SIZE);
        assamImageView.setFitHeight(TILE_SIZE);
        root.getChildren().add(assamImageView);

        // Draw an arrow for the direction of Assam
//        Polygon arrow = new Polygon();
//        arrow.setFill(Color.BLACK);
//        double arrowLength = TILE_SIZE * 0.5;
//        double arrowWidth = TILE_SIZE * 0.2;
//        double centerX = (assamPoint.getX() * TILE_SIZE + TILE_SIZE / 2) + offsetX;
//        double centerY = (assamPoint.getY() * TILE_SIZE + TILE_SIZE / 2) + offsetY;
        Image arrowImage = new Image("comp1110/ass2/gui/assets/Assam.png");  // Update with the actual path
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setX((assamPoint.getX() * TILE_SIZE) + offsetX);
        arrowImageView.setY((assamPoint.getY() * TILE_SIZE) + offsetY);
        arrowImageView.setFitWidth(TILE_SIZE);
        arrowImageView.setFitHeight(TILE_SIZE);

//        switch (assamOrientation) {
//            case N:
//                arrow.getPoints().addAll(new Double[]{
//                        centerX - arrowWidth, centerY + arrowLength / 2,
//                        centerX, centerY - arrowLength / 2,
//                        centerX + arrowWidth, centerY + arrowLength / 2
//                });
//                break;
//            case E:
//                arrow.getPoints().addAll(new Double[]{
//                        centerX - arrowLength / 2, centerY - arrowWidth,
//                        centerX + arrowLength / 2, centerY,
//                        centerX - arrowLength / 2, centerY + arrowWidth
//                });
//                break;
//            case S:
//                arrow.getPoints().addAll(new Double[]{
//                        centerX - arrowWidth, centerY - arrowLength / 2,
//                        centerX, centerY + arrowLength / 2,
//                        centerX + arrowWidth, centerY - arrowLength / 2
//                });
//                break;
//            case W:
//                arrow.getPoints().addAll(new Double[]{
//                        centerX + arrowLength / 2, centerY - arrowWidth,
//                        centerX - arrowLength / 2, centerY,
//                        centerX + arrowLength / 2, centerY + arrowWidth
//                });
//                break;
//        }
//        root.getChildren().add(arrow);
        switch (assamOrientation) {
            case N:
                arrowImageView.setRotate(0);
                break;
            case E:
                arrowImageView.setRotate(90);
                break;
            case S:
                arrowImageView.setRotate(180);
                break;
            case W:
                arrowImageView.setRotate(270);
                break;
        }

        root.getChildren().add(arrowImageView);
    }

    private String getImagePathBasedOnColor(Color color) {
        if (color == Color.RED) {
            return "/comp1110/ass2/gui/assets/red_Rug.png";
        } else if (color == Color.YELLOW) {
            return "/comp1110/ass2/gui/assets/yellow_Rug.png";
        } else if (color == Color.CYAN) {
            return "/comp1110/ass2/gui/assets/cyan_Rug.png";
        } else if (color == Color.PURPLE) {
            return "/comp1110/ass2/gui/assets/purple_Rug.png";
        } else {
            throw new IllegalArgumentException("Unsupported color");
        }
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    public Group getViewerRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
