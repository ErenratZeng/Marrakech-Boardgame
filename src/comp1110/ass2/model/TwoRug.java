package comp1110.ass2.model;

import comp1110.ass2.model.base.Point;
import javafx.scene.paint.Color;

import static comp1110.ass2.model.base.utils.subStr2int;

public class TwoRug extends AbbreviatedRug {

    // (x,y) with 2 squares covered by the rug.

    private Point[] points = new Point[2];

    /**
     * @param color: A Color object that specifies the color of the rug.
     * @param ID: An integer representing the identification of the rug.
     * @param points: An array of Point objects with a length of 2, indicating the coordinates covered by the rug.
     * @description Initializes a new TwoRug instance with the specified color, ID, and points.
     */
    public TwoRug(Color color, int ID, Point[] points) {
        super(color, ID);
        this.points = points;
    }

    /**
     * @param string A string representation of the TwoRug instance.
     *              It should have a length of 7, where the first 3 characters define the abbreviated rug
     *              and the next 4 characters define the points covered by the rug.
     * @description  Initializes a new TwoRug instance using the provided string representation.
     */
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

    /**
     * @return  Returns an array of Point objects with a length of 2,
     * indicating the coordinates covered by the rug.
     * @description Retrieves the points covered by the rug.
     */
    public Point[] getPoints() {
        return points;
    }

    /**
     * @return Returns a string representation of the TwoRug instance.
     * @description Converts the rug's details (color, ID, and covered points) into a string format.
     */
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
