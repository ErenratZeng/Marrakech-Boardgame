package comp1110.ass2.model;

import comp1110.ass2.model.base.IBean;
import comp1110.ass2.model.base.Point;

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

    public Point getPoint() {
        return point;
    }

    public Assam setPoint(Point point) {
        this.point = point;
        return this;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Assam setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public Assam setOrientationRight90() {
        switch (orientation) {
            case N -> orientation = Orientation.E;
            case E -> orientation = Orientation.S;
            case S -> orientation = Orientation.W;
            case W -> orientation = Orientation.N;
        }
        return this;
    }

    public Assam setOrientationLeft90() {
        switch (orientation) {
            case N -> orientation = Orientation.W;
            case E -> orientation = Orientation.S;
            case S -> orientation = Orientation.E;
            case W -> orientation = Orientation.N;
        }
        return this;
    }

    public Assam setOrientationBack() {
        switch (orientation) {
            case N -> orientation = Orientation.S;
            case E -> orientation = Orientation.W;
            case S -> orientation = Orientation.N;
            case W -> orientation = Orientation.E;
        }
        return this;
    }

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
