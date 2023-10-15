package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import javafx.scene.paint.Color;

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
    public int getrugNum() {
        return rugNum;
    }

    /**
     * @param rugNum: An integer indicating the number of rugs to set for the player.
     * @return Returns the current instance of the Player object (useful for method chaining).
     * @description Updates the player's rug count.
     */
    public Player setrugNum(int rugNum) {
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

}
