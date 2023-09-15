package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import javafx.scene.paint.Color;

import static comp1110.ass2.model.base.utils.subStr2int;

public class AbbreviatedRug implements IBean {

    // color matches the owner.
    private final Color color;

    // ID of each rug should be unique.
    private final int ID;

    public AbbreviatedRug() {
        this.color = null;
        this.ID = 0;
    }

    public AbbreviatedRug(Color color, int ID) {
        this.color = color;
        this.ID = ID;
    }

    public AbbreviatedRug(String string) {
        if (string.length() != 3)
            throw new RuntimeException(
                    "AbbreviatedRugString is: " +
                            string +
                            ", length != 3"
            );
        switch (string.charAt(0)) {
            case 'c' -> color = Color.CYAN;
            case 'y' -> color = Color.YELLOW;
            case 'r' -> color = Color.RED;
            case 'p' -> color = Color.PURPLE;
            case 'n' -> color = null;
            default -> throw new RuntimeException(
                    "Color? string is: " +
                            string +
                            ", case is " +
                            string.charAt(0)
            );
        }
        ID = subStr2int(string, 1, 3);
    }

    public Color getColor() {
        return color;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();

        // kind: color of owner
        if (color == Color.CYAN) sb.append("c");
        else if (color == Color.YELLOW) sb.append("y");
        else if (color == Color.RED) sb.append("r");
        else if (color == Color.PURPLE) sb.append("p");
        else if (color == null) sb.append("n");
        sb.append(String.format("%02d", ID));

        return sb.toString();
    }
}
