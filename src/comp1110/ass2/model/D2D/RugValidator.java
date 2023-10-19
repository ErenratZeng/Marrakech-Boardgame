package comp1110.ass2.model.D2D;

import comp1110.ass2.model.Player;
import comp1110.ass2.model.TwoRug;
import comp1110.ass2.model.State;
import javafx.scene.paint.Color;
import comp1110.ass2.model.base.Point;

import java.util.ArrayList;

/**
 * Authorship:
 * name: Ruize Luo
 * uid: u7776709
 */
public class RugValidator {

    public static boolean judgeRugValid(String gameString, String rug) {
        // The String is 7 characters long
        if (!(rug.length() == 7)) {
            return false;
        }
        try {
            TwoRug currentRug = new TwoRug(rug);
            if (currentRug.getID() < 0 || currentRug.getID() > 99) {
                return false;
            }

            // The first character in the String corresponds to the colour character of a player present in the game
            State currentState = new State(gameString);
            ArrayList<Player> playerArrays = currentState.getPlayers();

            Color[] playersColor = new Color[playerArrays.size()];

            int count = 0;
            for (Player player : playerArrays) {
                playersColor[count] = player.getColor();
                count++;
            }

            boolean hasRug = false;
            for (Color color : playersColor) {
                if (color.equals(currentRug.getColor())) {
                    hasRug = true;
                    break;
                }
            }
            if (!hasRug) {
                return false;
            }

            // The next 4 characters represent coordinates that are on the board
            TwoRug rugObj = new TwoRug(rug);
            Point[] positions = rugObj.getPoints();
            Point startPosition = positions[0];
            Point endPosition = positions[1];

            int startX = startPosition.getX();
            int startY = startPosition.getY();
            int endX = endPosition.getX();
            int endY = endPosition.getY();

            boolean isStartXInRange = startX >= 0 && startX < 7;
            boolean isStartYInRange = startY >= 0 && startY < 7;
            boolean isEndXInRange = endX >= 0 && endX < 7;
            boolean isEndYInRange = endY >= 0 && endY < 7;

            if (!(isStartXInRange && isStartYInRange && isEndXInRange && isEndYInRange)) {
                return false;
            }

            if (startX == endX) {
                int startYDiff = Math.abs(startY - endY);
                if (!(startYDiff == 1)) {
                    return false;
                }
            }

            if (startY == endY) {
                int startXDiff = Math.abs(startX - endX);
                if (!(startXDiff == 1)) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
