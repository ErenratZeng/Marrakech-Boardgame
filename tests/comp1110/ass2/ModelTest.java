package comp1110.ass2;

import comp1110.ass2.model.*;
import comp1110.ass2.model.base.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Authorship:
 * name: Ruize Luo
 * uid: u7776709
 */

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class ModelTest {
    @Test
    public void checkAbbreviatedRug() {
        AbbreviatedRug defaultRug = new AbbreviatedRug();
        Assertions.assertNull(defaultRug.getColor(), "Default constructor should initialize color as null");
        Assertions.assertEquals(0, defaultRug.getID(), "Default constructor should initialize ID as 0");

        // Test constructor with color and ID
        AbbreviatedRug specificRug = new AbbreviatedRug(Color.CYAN, 12);
        Assertions.assertEquals(Color.CYAN, specificRug.getColor());
        Assertions.assertEquals(12, specificRug.getID());

        // Test constructor with string representation
        AbbreviatedRug stringRug = new AbbreviatedRug("c12");
        Assertions.assertEquals(Color.CYAN, stringRug.getColor());
        Assertions.assertEquals(12, stringRug.getID());

        // Test getString() method
        String stringRepresentation = stringRug.getString();
        Assertions.assertEquals("c12", stringRepresentation, "getString should return the string representation of the rug");
    }

    @Test
    public void checkAssam() {
        // Test default constructor
        Assam assam = new Assam();
        Assertions.assertEquals(3, assam.getPoint().getX(), "Default position x should be 3");
        Assertions.assertEquals(3, assam.getPoint().getY(), "Default position y should be 3");
        Assertions.assertEquals(Assam.Orientation.N, assam.getOrientation(), "Default orientation should be N");

        // Test string constructor
        assam = new Assam("A32E");
        Assertions.assertEquals(3, assam.getPoint().getX());
        Assertions.assertEquals(2, assam.getPoint().getY());
        Assertions.assertEquals(Assam.Orientation.E, assam.getOrientation());

        // Test set point
        assam.setPoint(new Point(4, 4));
        Assertions.assertEquals(4, assam.getPoint().getX());
        Assertions.assertEquals(4, assam.getPoint().getY());

        // Test set orientation
        assam.setOrientation(Assam.Orientation.S);
        Assertions.assertEquals(Assam.Orientation.S, assam.getOrientation());

        // Test orientation rotation to right
        assam.setOrientationRight90();
        Assertions.assertEquals(Assam.Orientation.W, assam.getOrientation(), "Rotation to right from S should give W");

        // Test orientation rotation to left
        assam.setOrientationLeft90();
        Assertions.assertEquals(Assam.Orientation.S, assam.getOrientation(), "Rotation to left from W should give S");

        // Test orientation rotation to back
        assam.setOrientationBack();
        Assertions.assertEquals(Assam.Orientation.N, assam.getOrientation(), "180 degrees rotation from S should give N");

        // Test string representation of Assam
        String assamStr = assam.getString();
        Assertions.assertTrue(assamStr.startsWith("A"), "String representation should start with 'A'");
        Assertions.assertTrue(assamStr.contains("4"), "String representation should contain x and y coordinates of Assam");
        Assertions.assertTrue(assamStr.endsWith("N"), "String representation should end with orientation of Assam");
    }

    @Test
    public void checkBoard() {
        // Test default constructor
        Board board = new Board();
        for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
            for (int j = 0; j < Board.BOARD_WIDTH; j++) {
                Assertions.assertNotNull(board.getRug(i, j), "Each position should have a rug initialized");
            }
        }

        // Test string constructor with given boardString
        String boardString = "By05r09r09n00n00n00n00y09p09r06n00y10n00n00y09p09r06n00y10r05r01c01p06p06p08p08n00c03c01c04p00n00p10n00r04n00r07p00n00p10n00r04c05r07p03y01n00n00n00";
        board = new Board(boardString);
        Assertions.assertEquals("y05", board.getRug(0, 0).getString(), "Rug at position (0,0) should have value 'y05'");

        // Test set rug
        AbbreviatedRug rug = new AbbreviatedRug("r01");
        board.setRug(rug, 0, 0);
        Assertions.assertEquals("r01", board.getRug(0, 0).getString(), "Rug at position (0,0) should have value 'xr01'");

        // Test string representation of board
        String boardStr = board.getString();
        Assertions.assertTrue(boardStr.startsWith("B"), "String representation should start with 'B'");
        Assertions.assertTrue(boardStr.contains("r01"), "String representation should contain rug values");
        Assertions.assertEquals(Board.BOARD_WIDTH * Board.BOARD_HEIGHT * 3 + 1, boardStr.length(), "Length of string representation should be BOARD_WIDTH * BOARD_HEIGHT * 3 + 1");
    }

    @Test
    public void checkPlayer() {
        // Test constructor with Color
        Player playerCyan = new Player(Color.CYAN);
        Assertions.assertEquals(Color.CYAN, playerCyan.getColor(), "Expected color to be CYAN");
        Assertions.assertEquals(30, playerCyan.getCoins(), "Expected initial coins to be 30");
        Assertions.assertEquals(15, playerCyan.getRugNum(), "Expected initial rugNum to be 15");
        Assertions.assertTrue(playerCyan.getAlive(), "Expected player to be alive initially");

        Player playerYellow = new Player(Color.YELLOW);
        Assertions.assertEquals(Color.YELLOW, playerYellow.getColor(), "Expected color to be YELLOW");

        Player playerRed = new Player(Color.RED);
        Assertions.assertEquals(Color.RED, playerRed.getColor(), "Expected color to be RED");

        Player playerPurple = new Player(Color.PURPLE);
        Assertions.assertEquals(Color.PURPLE, playerPurple.getColor(), "Expected color to be PURPLE");


        // Test constructor with String for CYAN
        Player playerCyanFromString = new Player("Pc03015i");
        Assertions.assertEquals(Color.CYAN, playerCyanFromString.getColor(), "Expected color to be CYAN from string constructor");

        // Test constructor with String for YELLOW
        Player playerYellowFromString = new Player("Py03015i");
        Assertions.assertEquals(Color.YELLOW, playerYellowFromString.getColor(), "Expected color to be YELLOW from string constructor");

        // Test constructor with String for RED
        Player playerRedFromString = new Player("Pr03015i");
        Assertions.assertEquals(Color.RED, playerRedFromString.getColor(), "Expected color to be RED from string constructor");

        // Test constructor with String for PURPLE
        Player playerPurpleFromString = new Player("Pp03015i");
        Assertions.assertEquals(Color.PURPLE, playerPurpleFromString.getColor(), "Expected color to be PURPLE from string constructor");


        // Test set and get methods
        playerCyan.setCoins(25);
        Assertions.assertEquals(25, playerCyan.getCoins(), "Expected coins to be set to 25");

        playerCyan.setRugNum(10);
        Assertions.assertEquals(10, playerCyan.getRugNum(), "Expected rugNum to be set to 10");

        playerCyan.setAlive(false);
        Assertions.assertFalse(playerCyan.getAlive(), "Expected player to be set to not alive");


        // Test getString method
        String expectedString = "Pc02510o";
        Assertions.assertEquals(expectedString, playerCyan.getString(), "Expected string representation to match");

        // Test Player constructor with invalid string input
        Assertions.assertThrows(RuntimeException.class, () -> new Player("Px03015i"), "Expected RuntimeException for invalid color character");
        Assertions.assertThrows(RuntimeException.class, () -> new Player("Pc03015x"), "Expected RuntimeException for invalid alive character");

        playerCyan = new Player(Color.CYAN);
        playerYellow = new Player(Color.YELLOW);
        playerRed = new Player(Color.RED);
        playerPurple = new Player(Color.PURPLE);

        // Test getString method for all colors
        Assertions.assertEquals("Pc03015i", playerCyan.getString(), "Expected string representation for CYAN to match");
        Assertions.assertEquals("Py03015i", playerYellow.getString(), "Expected string representation for YELLOW to match");
        Assertions.assertEquals("Pr03015i", playerRed.getString(), "Expected string representation for RED to match");
        Assertions.assertEquals("Pp03015i", playerPurple.getString(), "Expected string representation for PURPLE to match");
    }

    @Test
    public void checkState() {
        String givenStateString = "Pc08906iPy01606iPp01506iPr00006oA23SBc18c07r07r07y08p15c09c18p14p02p17p17p15c09c14y18c06c11c11c01n00c19y18c06p11c16c16c15c19y05n00p11y14p09c15r05r18n00c05n00n00n00n00r18n00n00y11y11n00";

        // Test string-based constructor
        State state = new State(givenStateString);
        Assertions.assertNotNull(state.getBoard(), "Board should not be null");
        Assertions.assertNotNull(state.getAssam(), "Assam should not be null");
        Assertions.assertEquals(4, state.getPlayers().size(), "Should have four players");

        // Test getString method
        Assertions.assertEquals(givenStateString, state.getString(), "String representation should match expected");

        // Additional assertions can be added to verify other properties of the state
        Assertions.assertEquals(2, state.getAssam().getPoint().getX(), "Assam X coordinate should match expected");
        Assertions.assertEquals(3, state.getAssam().getPoint().getY(), "Assam Y coordinate should match expected");
    }

    @Test
    public void checkTwoRug() {
        // Test for string-based constructor with given TwoRugString
        String givenTwoRugString = "y011023";
        TwoRug rugFromStr = new TwoRug(givenTwoRugString);

        // Assertions for properties initialized from the string
        Assertions.assertEquals(Color.YELLOW, rugFromStr.getColor(), "Rug color should match expected");
        Assertions.assertEquals(1, rugFromStr.getID(), "Rug ID should match expected");

        Point[] pointsFromStr = rugFromStr.getPoints();
        Assertions.assertNotNull(pointsFromStr, "Points should not be null");
        Assertions.assertEquals(2, pointsFromStr.length, "Should have two points");
        Assertions.assertEquals(1, pointsFromStr[0].getX(), "First point's X coordinate should match expected");
        Assertions.assertEquals(0, pointsFromStr[0].getY(), "First point's Y coordinate should match expected");
        Assertions.assertEquals(2, pointsFromStr[1].getX(), "Second point's X coordinate should match expected");
        Assertions.assertEquals(3, pointsFromStr[1].getY(), "Second point's Y coordinate should match expected");

        // Test for getString method
        Point[] givenPoints = {new Point(1, 0), new Point(2, 3)};
        TwoRug createdRug = new TwoRug(Color.YELLOW, 1, givenPoints);
        String rugString = createdRug.getString();
        Assertions.assertEquals(givenTwoRugString, rugString, "String representation should match expected");
    }

    @Test
    // In this test, the dice is rolled a large number of times (100,000 times in this example).
    // We then check if the results are as expected given the sides of the dice. Since the dice has non-uniform sides ({1, 2, 2, 3, 3, 4}),
    // we expect the frequency of 2 and 3 to be roughly double that of 1 and 4.
    // We use a tolerance of 5% (0.05 * numberOfRolls) to allow for some statistical variability. You can adjust this tolerance as needed.
    public void checkDice() {
        Dice dice = new Dice();
        int numberOfRolls = 100000; // large number for statistical accuracy
        Map<Integer, Integer> frequencyMap = new HashMap<>(); // maps roll result to its frequency

        // Roll the dice a large number of times and record the results.
        for (int i = 0; i < numberOfRolls; i++) {
            int roll = dice.roll();
            Assertions.assertTrue(roll >= 1 && roll <= 4);
            frequencyMap.put(roll, frequencyMap.getOrDefault(roll, 0) + 1);
        }

        // Ensure each result appears within a reasonable range given the sides of the dice.
        // Note: Since the dice doesn't have uniform probabilities, this check ensures
        // that the observed frequencies match the expected frequencies.
        Assertions.assertTrue(Math.abs(frequencyMap.getOrDefault(1, 0) - (numberOfRolls / 6.0)) < 0.05 * numberOfRolls);
        Assertions.assertTrue(Math.abs(frequencyMap.getOrDefault(2, 0) - (numberOfRolls / 3.0)) < 0.05 * numberOfRolls);
        Assertions.assertTrue(Math.abs(frequencyMap.getOrDefault(3, 0) - (numberOfRolls / 3.0)) < 0.05 * numberOfRolls);
        Assertions.assertTrue(Math.abs(frequencyMap.getOrDefault(4, 0) - (numberOfRolls / 6.0)) < 0.05 * numberOfRolls);
    }

    @Test
    public void checkPoint() {
        // Testing constructor and getters
        int givenX = 5;
        int givenY = 10;
        Point point = new Point(givenX, givenY);

        Assertions.assertEquals(givenX, point.getX(), "X coordinate should match the given value");
        Assertions.assertEquals(givenY, point.getY(), "Y coordinate should match the given value");

        // Testing setters
        int newX = 8;
        int newY = 12;
        point.setX(newX);
        point.setY(newY);

        Assertions.assertEquals(newX, point.getX(), "X coordinate after setting should match the new value");
        Assertions.assertEquals(newY, point.getY(), "Y coordinate after setting should match the new value");

        // Testing chaining of setters
        int chainX = 15;
        int chainY = 20;
        point.setX(chainX).setY(chainY);

        Assertions.assertEquals(chainX, point.getX(), "X coordinate after chaining setters should match the chained value");
        Assertions.assertEquals(chainY, point.getY(), "Y coordinate after chaining setters should match the chained value");

        // Testing toString method
        String expectedString = "Point{x=" + chainX + ", y=" + chainY + '}';
        Assertions.assertEquals(expectedString, point.toString(), "String representation should match expected format");
    }
}
