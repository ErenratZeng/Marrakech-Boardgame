package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import comp1110.ass2.model.base.Point;

import java.util.Random;

import static comp1110.ass2.model.Board.BOARD_HEIGHT;
import static comp1110.ass2.model.Board.BOARD_WIDTH;
import static comp1110.ass2.model.base.utils.subStr2int;

public class Assam implements IBean {

    // Assam starts the game in the centre of the board (at position (3,3))
    private Point point = new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2);

    public enum Orientation {
        N, // facing towards the top of the board
        E, // facing towards the right of the board
        S, // facing towards the bottom of the board
        W // facing towards the left of the board
    }

    // Assam may be facing in any direction. Suppose N.
    private Orientation orientation = Orientation.N;


    public Assam() {
    }

    /**
     * @param string: A string where the first character is 'A' (indicating Assam),
     *                followed by two characters representing the x and y positions,
     *                and the last character representing the orientation (N, E, S, or W).
     * @description The constructor parses the input string to initialize the position and orientation of Assam.
     * If the orientation character isn't valid, a RuntimeException is thrown.
     */
    public Assam(String string) {
        point.setX(subStr2int(string, 1, 2))
                .setY(subStr2int(string, 2, 3));
        switch (string.charAt(3)) {
            case 'N' -> orientation = Orientation.N;
            case 'E' -> orientation = Orientation.E;
            case 'S' -> orientation = Orientation.S;
            case 'W' -> orientation = Orientation.W;
            default -> throw new RuntimeException(
                    "Orientation? string is: " +
                            string +
                            ", case is " +
                            string.charAt(3)
            );
        }
    }

    /**
     * @return Returns an instance of Point which represents Assam's position.
     * @description Retrieves the current position of Assam.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @param point: An instance of Point representing Assam's desired position.
     * @return Returns the current instance of the Assam object (useful for method chaining).
     * @description Updates the position of Assam.
     */
    public Assam setPoint(Point point) {
        this.point = point;
        return this;
    }

    /**
     * @return Returns the current orientation of Assam
     * which can be one of the values in the Orientation enum.
     * @description Retrieves Assam's facing direction.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * @param orientation: A value from the Orientation enum indicating Assam's desired direction.
     * @return Returns the current instance of the Assam object (useful for method chaining).
     * @description Updates the direction Assam is facing.
     */
    public Assam setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    /**
     * @return Returns the current instance of the Assam object (useful for method chaining).
     * @description Rotates Assam's orientation 90 degrees to the right.
     */
    public Assam setOrientationRight90() {
        switch (orientation) {
            case N -> orientation = Orientation.E;
            case E -> orientation = Orientation.S;
            case S -> orientation = Orientation.W;
            case W -> orientation = Orientation.N;
        }
        return this;
    }

    /**
     * @return Returns the current instance of the Assam object (useful for method chaining).
     * @description Rotates Assam's orientation 90 degrees to the left.
     */
    public Assam setOrientationLeft90() {
        switch (orientation) {
            case N -> orientation = Orientation.W;
            case E -> orientation = Orientation.S;
            case S -> orientation = Orientation.E;
            case W -> orientation = Orientation.N;
        }
        return this;
    }

    /**
     * @return Returns the current instance of the Assam object (useful for method chaining).
     * @description Rotates Assam's orientation 180 degrees (makes him face the opposite direction).
     */
    public Assam setOrientationBack() {
        switch (orientation) {
            case N -> orientation = Orientation.S;
            case E -> orientation = Orientation.W;
            case S -> orientation = Orientation.N;
            case W -> orientation = Orientation.E;
        }
        return this;
    }

    public Orientation getOrientationRandomly() {
        switch (orientation) {
            case N, S -> {
                return new Orientation[]{orientation, Orientation.E, Orientation.W}[new Random().nextInt(3)];
            }
            case E, W -> {
                return new Orientation[]{orientation, Orientation.N, Orientation.S}[new Random().nextInt(3)];
            }
            default -> throw new RuntimeException(
                    "Orientation? "+ orientation
            );
        }
    }

    /**
     * @param
     * @return Returns a string representation of the Assam object, starting with an 'A',
     * followed by x and y position, and the orientation.
     * @description Converts Assam's position and orientation to a string format.
     */
    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();

        // kind: Assam
        sb.append("A");
        sb.append(point.getX());
        sb.append(point.getY());
        sb.append(orientation);

        return sb.toString();
    }
}
