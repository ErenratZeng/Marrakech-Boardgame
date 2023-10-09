package comp1110.ass2.model;

import comp1110.ass2.model.base.Point;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.Marrakech.isPlacementValid;
import static comp1110.ass2.model.Board.BOARD_HEIGHT;
import static comp1110.ass2.model.Board.BOARD_WIDTH;

public class AI extends Player {
    public enum Level {
        easy, // random action
        normal, // greedy algorithm
        hard // plus against the first
    }

    Level level;

    public AI(Color color, Level level) {
        super(color);
        this.level = level;
    }

    public Assam.Orientation actionAssam(State state) {
        switch (level) {
            case easy -> {
                return state.getAssam().getOrientationRandomly();
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

    public TwoRug actionRug(State state) {
        switch (level) {
            case easy -> {
                Point point = state.getAssam().getPoint();
                List<Point[]> pointsList = getAllPoints(point);
                TwoRug twoRug;
                for (Point[] twoPoints : pointsList) {
                    twoRug = new TwoRug(getColor(), getCoins(), twoPoints);
                    if (isPlacementValid(state.getString(), twoRug.getString())) return twoRug;
                }
                throw new RuntimeException(
                        "Can not put rug."
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
