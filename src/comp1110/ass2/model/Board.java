package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;

public class Board implements IBean {
    public final static int BOARD_WIDTH = 7;

    public final static int BOARD_HEIGHT = 7;

    // each rug on the board.
    private final AbbreviatedRug[][] rugs = new AbbreviatedRug[BOARD_HEIGHT][BOARD_WIDTH];

    /**
     * @description The constructor initializes an empty board,
     * where each position is initialized with a default AbbreviatedRug object.
     */
    public Board() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                rugs[i][j] = new AbbreviatedRug();
            }
        }
    }

    /**
     * @param string: A string representing the AbbreviatedRug information for each position on the board,
     *              where every 3 characters correspond to one AbbreviatedRug.
     * @description Initializes the AbbreviatedRug objects on the board using the given string.
     */
    public Board(String string) {
        int k = 1;
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                rugs[i][j] = new AbbreviatedRug(string.substring(k, k + 3));
                k += 3;
            }
        }
    }

    /**
     * @param x: The x-coordinate of the rug
     * @param y: The y-coordinate of the rug
     * @return Returns the AbbreviatedRug object at position (x, y).
     * @description Retrieves the rug at the specified position.
     */
    public AbbreviatedRug getRug(int x, int y) {
        return rugs[x][y];
    }

    /**
     * @param rug: An AbbreviatedRug object
     * @param x: The x-coordinate of the rug
     * @param y: The y-coordinate of the rug
     * @return Returns the current instance of the Board object (useful for method chaining).
     * @description Sets the rug at the specified position.
     */
    public Board setRug(AbbreviatedRug rug, int x, int y) {
        rugs[x][y] = rug;
        return this;
    }

    /**
     * @return Returns a string representing the current board state.
     * @description Converts the entire board state into a string format.
     */
    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();

        sb.append("B");
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                sb.append(rugs[i][j].getString());
            }
        }

        return sb.toString();
    }
}
