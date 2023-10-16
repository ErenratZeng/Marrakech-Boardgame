package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import comp1110.ass2.model.base.Point;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.Marrakech.makePlacement;
import static comp1110.ass2.model.Board.BOARD_HEIGHT;
import static comp1110.ass2.model.Board.BOARD_WIDTH;
import static comp1110.ass2.model.base.utils.subStr2int;

public class Player implements IBean {

    // number of coins, start with 30.
    private int coins = 30;

    // number of remaining rugNum, start with 15.
    private int rugNum = 15;

    // player's color
    private final Color color;

    // whether player is out of the game, start with alive.
    private Boolean alive = true;

    /**
     * @param color: A Color object indicating the player's color.
     * @description Initializes a new Player instance with the specified color.
     * The player starts with 30 coins, 15 rugs, and is alive.
     */
    public Player(Color color) {
        this.color = color;
    }

    /**
     * @param string: A string representing the player's information including
     *              color, coins, rug number, and alive status.
     * @description Initializes a new Player instance using the given string.
     */
    public Player(String string) {
        switch (string.charAt(1)) {
            case 'c' -> color = Color.CYAN;
            case 'y' -> color = Color.YELLOW;
            case 'r' -> color = Color.RED;
            case 'p' -> color = Color.PURPLE;
            default -> throw new RuntimeException(
                    "Color? string is: " +
                            string +
                            ", case is " +
                            string.charAt(1)
            );
        }
        coins = subStr2int(string, 2, 5);
        rugNum = subStr2int(string, 5, 7);
        switch (string.charAt(7)) {
            case 'i' -> alive = true;
            case 'o' -> alive = false;
            default -> throw new RuntimeException(
                    "Alive? string is: " +
                            string +
                            ", case is " +
                            string.charAt(7)
            );
        }
    }

    /**
     * @return  Returns the player's Color.
     * @description Retrieves the player's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param
     * @return Returns the number of coins the player has.
     * @description Retrieves the player's coin count.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * @param coins: An integer indicating the number of coins to set for the player.
     * @return  Returns the current instance of the Player object (useful for method chaining).
     * @description Updates the player's coin count.
     */
    public Player setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    public int payCoins(int coins) {
        if (this.coins < coins) {
            alive = false;
            return this.coins;
        }
        this.coins -= coins;
        return coins;
    }

    public Player gainCoins(int coins) {
        this.coins += coins;
        return this;
    }

    /**
     * @return  Returns the number of rugs the player has.
     * @description Retrieves the player's rug count.
     */
    public int getRugNum() {
        return rugNum;
    }

    /**
     * @param rugNum: An integer indicating the number of rugs to set for the player.
     * @return Returns the current instance of the Player object (useful for method chaining).
     * @description Updates the player's rug count.
     */
    public Player setRugNum(int rugNum) {
        this.rugNum = rugNum;
        return this;
    }

    /**
     * @return Returns a boolean indicating if the player is alive or not.
     * @description Checks if the player is currently alive.
     */
    public boolean getAlive() {
        return alive;
    }

    /**
     * @param alive: A boolean value indicating whether the player is alive (true) or not (false).
     * @return Returns the current instance of the Player object (useful for method chaining).
     * @description Updates the player's alive status.
     */
    public Player setAlive(Boolean alive) {
        this.alive = alive;
        return this;
    }

    /**
     * @param
     * @return Returns a string representing the player's current state.
     * @description  Converts the player's state into a string format,
     * including color, coins, rug number, and alive status.
     */
    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();

        // kind: Player
        sb.append("P");
        if (color == Color.CYAN) sb.append("c");
        else if (color == Color.YELLOW) sb.append("y");
        else if (color == Color.RED) sb.append("r");
        else if (color == Color.PURPLE) sb.append("p");
        sb.append(String.format("%03d", coins));
        sb.append(String.format("%02d", rugNum));
        if (alive) sb.append("i");
        else sb.append("o");

        return sb.toString();
    }

    public enum Level {
        easy, // random action
        normal, // greedy algorithm
        hard // plus against the first
    }

    public State actionAssam(State state, Level level) {
        switch (level) {
            case easy -> {
                state.getAssam().setOrientationRandomly();
                return state;
            }
            case normal -> {
                //TODO: normal
                return null;
            }
            case hard -> {
                //TODO: hard
                return null;
            }
            default -> throw new RuntimeException(
                    "level? " + level
            );
        }
    }

    public State actionRug(State state, Level level) {
        switch (level) {
            case easy -> {
                Point point = state.getAssam().getPoint();
                List<Point[]> pointsList = getAllPoints(point);
                TwoRug twoRug;
                for (Point[] twoPoints : pointsList) {
                    twoRug = new TwoRug(getColor(), getCoins(), twoPoints);
                    String newGameState = makePlacement(state.getString(), twoRug.getString());
                    if (newGameState != null && !newGameState.equals(state.getString())) {
                        return new State(newGameState);
                    }
                }
                throw new RuntimeException(
                        "Can not put rug."+state.getString()
                );
            }
            case normal -> {
                //TODO: normal
                return null;
            }
            case hard -> {
                //TODO: hard
                return null;
            }
            default -> throw new RuntimeException(
                    "level? " + level
            );
        }
    }

    private List<Point[]> getAllPoints(Point point) {
        int x = point.getX();
        int y = point.getY();
        List<Point[]> pointsList = new ArrayList<>();
        if (x + 1 < BOARD_WIDTH && y + 1 < BOARD_HEIGHT)
            pointsList.add(new Point[]{new Point(x + 1, y), new Point(x + 1, y + 1)});
        if (x + 1 < BOARD_WIDTH && y - 1 >= 0)
            pointsList.add(new Point[]{new Point(x + 1, y), new Point(x + 1, y - 1)});
        if (x + 2 < BOARD_WIDTH)
            pointsList.add(new Point[]{new Point(x + 1, y), new Point(x + 2, y)});
        if (x - 1 >= 0 && y + 1 < BOARD_HEIGHT)
            pointsList.add(new Point[]{new Point(x - 1, y), new Point(x - 1, y + 1)});
        if (x - 1 >= 0 && y - 1 >= 0)
            pointsList.add(new Point[]{new Point(x - 1, y), new Point(x - 1, y - 1)});
        if (x - 2 >= 0)
            pointsList.add(new Point[]{new Point(x - 1, y), new Point(x - 2, y)});

        if (y + 1 < BOARD_WIDTH && x + 1 < BOARD_HEIGHT)
            pointsList.add(new Point[]{new Point(x, y + 1), new Point(x + 1, y + 1)});
        if (y + 1 < BOARD_WIDTH && x - 1 >= 0)
            pointsList.add(new Point[]{new Point(x, y + 1), new Point(x - 1, y + 1)});
        if (y + 2 < BOARD_WIDTH)
            pointsList.add(new Point[]{new Point(x, y + 1), new Point(x, y + 2)});
        if (y - 1 >= 0 && x + 1 < BOARD_HEIGHT)
            pointsList.add(new Point[]{new Point(x, y - 1), new Point(x + 1, y - 1)});
        if (y - 1 >= 0 && x - 1 >= 0)
            pointsList.add(new Point[]{new Point(x, y - 1), new Point(x - 1, y - 1)});
        if (y - 2 >= 0)
            pointsList.add(new Point[]{new Point(x, y - 1), new Point(x, y - 2)});

        return pointsList;
    }

}
