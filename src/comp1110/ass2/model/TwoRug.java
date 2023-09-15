package comp1110.ass2.model;

import comp1110.ass2.model.base.Point;
import javafx.scene.paint.Color;

import static comp1110.ass2.model.base.utils.subStr2int;

public class TwoRug extends AbbreviatedRug {

    // (x,y) with 2 squares covered by the rug.
    private Point[] points = new Point[2];

    public TwoRug(Color color, int ID, Point[] points) {
        super(color, ID);
        this.points = points;
    }

    public TwoRug(String string) {
        super(string.substring(0, 3));
        if (string.length() != 7)
            throw new RuntimeException(
                    "TwoRugString is: " +
                            string +
                            ", length != 7"
            );
        points[0] = new Point(
                subStr2int(string, 3, 4),
                subStr2int(string, 4, 5)
        );
        points[1] = new Point(
                subStr2int(string, 5, 6),
                subStr2int(string, 6, 7)
        );
    }

    public Point[] getPoints() {
        return points;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.getString());
        for (Point point : points) {
            sb.append(point.getX());
            sb.append(point.getY());
        }

        return sb.toString();
    }
}
