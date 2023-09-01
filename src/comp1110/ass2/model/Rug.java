package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import comp1110.ass2.model.base.Point;
import javafx.scene.paint.Color;

import static comp1110.ass2.model.base.utils.subStr2int;

public class Rug implements IBean {

    // color matches the owner.
    private final Color color;

    // ID of each rug should be unique.
    private final int ID;

    // (x,y) with 2 squares covered by the rug.
    private Point[] points = new Point[2];

    public Rug() {
        this.color = null;
        this.ID = 0;
    }

    public Rug(Color color, int ID, Point point) {
        this.color = color;
        this.ID = ID;
        points[0] = point;
    }

    public Rug(Color color, int ID, Point[] points) {
        this.color = color;
        this.ID = ID;
        this.points = points;
    }

    public Rug(String string) {
        switch (string.charAt(0)) {
            case 'c' -> color = Color.CYAN;
            case 'y' -> color = Color.YELLOW;
            case 'r' -> color = Color.RED;
            case 'p' -> color = Color.PURPLE;
            default -> throw new RuntimeException(
                    "Color? string is: " +
                            string +
                            ", case is " +
                            string.charAt(0)
            );
        }
        ID = subStr2int(string, 1, 3);

        // if not abbreviated.
        if (string.length() > 3) {
            points[0] = new Point(
                    subStr2int(string, 3, 4),
                    subStr2int(string, 4, 5)
            );
            points[1] = new Point(
                    subStr2int(string, 5, 6),
                    subStr2int(string, 6, 7)
            );
        }
    }

    public Color getColor() {
        return color;
    }

    public int getID() {
        return ID;
    }

    public Point[] getPoints() {
        return points;
    }

    public Point getPoint() {
        return points[0];
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();

        // kind: color of owner
        if (color == Color.CYAN) sb.append("c");
        else if (color == Color.YELLOW) sb.append("y");
        else if (color == Color.RED) sb.append("r");
        else if (color == Color.PURPLE) sb.append("p");
        sb.append(String.format("%02d", ID));
        for (Point point : points) {
            sb.append(point.getX());
            sb.append(point.getY());
        }

        return sb.toString();
    }

    public String getAbbreviatedString() {
        StringBuilder sb = new StringBuilder();

        // not covered by any rug
        if (color == null) return "n00";

        // kind: color of owner
        if (color == Color.CYAN) sb.append("c");
        else if (color == Color.YELLOW) sb.append("y");
        else if (color == Color.RED) sb.append("r");
        else if (color == Color.PURPLE) sb.append("p");
        sb.append(String.format("%02d", ID));

        return sb.toString();
    }
}
