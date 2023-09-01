package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;

public class Board implements IBean {
    public final static int BOARD_WIDTH = 7;

    public final static int BOARD_HEIGHT = 7;

    // each rug on the board.
    private final Rug[][] rugs = new Rug[BOARD_HEIGHT][BOARD_WIDTH];

    /**
     * Record the existing Rugs on the Board
     */
    public Board() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                rugs[i][j] = new Rug();
            }
        }
    }

    public Rug getRug(int x, int y) {
        return rugs[x][y];
    }

    public Board setRug(Rug rug, int x, int y) {
        rugs[x][y] = rug;
        return this;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                sb.append(rugs[i][j].getAbbreviatedString());
            }
        }

        return sb.toString();
    }
}
