package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import javafx.scene.paint.Color;

import static comp1110.ass2.model.base.utils.subStr2int;

public class Player implements IBean {

    // number of coins, start with 30.
    private int coins = 30;

    // number of remaining rugs, start with 15.
    private int rugs = 15;

    // player's color
    private final Color color;

    // whether player is out of the game, start with alive.
    private Boolean alive = true;

    public Player(Color color) {
        this.color = color;
    }

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
        rugs = subStr2int(string, 5, 7);
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

    public Color getColor() {
        return color;
    }

    public int getCoins() {
        return coins;
    }

    public Player setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    /**
     * Get the number of remaining rugs for players.
     */
    public int getRugs() {
        return rugs;
    }

    public Player setRugs(int rugs) {
        this.rugs = rugs;
        return this;
    }

    public boolean getAlive() {
        return alive;
    }

    public Player setAlive(Boolean alive) {
        this.alive = alive;
        return this;
    }

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
        sb.append(String.format("%02d", rugs));
        if (alive) sb.append("i");
        else sb.append("o");

        return sb.toString();
    }

}
