package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    private void newGame() {

    }

    /**
     * Creates the board for the application.
     */
    private void makeBoard() {

    }

    /**
     * Method that creates elements for the game.
     */
    public void makeControls() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
