package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import javafx.scene.paint.Color;

import static comp1110.ass2.model.base.utils.subStr2int;

/**
 * Authorship:
 * name: Zhuiqi Lin
 * uid: u7733924
 */
public class AbbreviatedRug implements IBean {

    // color matches the owner.
    private final Color color;

    // ID of each rug should be unique.
    private final int ID;

    /**
     * @description The constructor initializes an
     * AbbreviatedRug object with a null color and an ID of 0.
     */
    public AbbreviatedRug() {
        this.color = null;
        this.ID = 0;
    }

    /**
     * @param color: An instance of javafx.scene.paint.Color, representing the color of the rug.
     * @param ID: An integer representing the unique ID of the rug.
     * @return
     * @description  The constructor initializes an
     * AbbreviatedRug object with the provided color and ID.
     */
    public AbbreviatedRug(Color color, int ID) {
        this.color = color;
        this.ID = ID;
    }

    /**
     * @param string: A string of length 3, where the first character
     * indicates the color and the next two characters represent the ID of the rug.
     * @description The constructor parses the input string to initialize the color and ID of the rug.
     * If the string is not of length 3, a RuntimeException is thrown.
     */
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

    /**
     * @return  Returns an instance of javafx.scene.paint.Color indicating the color of the rug.
     * @description Retrieves the color of the AbbreviatedRug object.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return Returns an integer indicating the unique ID of the rug.
     * @description  Retrieves the ID of the AbbreviatedRug object.
     */
    public int getID() {
        return ID;
    }

    /**
     * @return  Returns a string representation of the AbbreviatedRug object,
     * where the first character indicates the color and the following two characters indicate the ID.
     * @description Converts the color and ID of the rug to a string format.
     */
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
