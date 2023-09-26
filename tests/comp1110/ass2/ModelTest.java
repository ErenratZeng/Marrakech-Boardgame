package comp1110.ass2;

import comp1110.ass2.model.Player;
import comp1110.ass2.model.base.Dice;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class ModelTest {
    @Test
    public void checkAbbreviatedRug() {
        // TODO
        Assertions.assertTrue(true);
    }

    @Test
    public void checkAssam() {
        // TODO
        Assertions.assertTrue(true);
    }

    @Test
    public void checkBoard() {
        // TODO
        Assertions.assertTrue(true);
    }

    @Test
    public void checkPlayer() {
        // Test constructor with Color
        Player playerCyan = new Player(Color.CYAN);
        Assertions.assertEquals(Color.CYAN, playerCyan.getColor(), "Expected color to be CYAN");
        Assertions.assertEquals(30, playerCyan.getCoins(), "Expected initial coins to be 30");
        Assertions.assertEquals(15, playerCyan.getrugNum(), "Expected initial rugNum to be 15");
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

        playerCyan.setrugNum(10);
        Assertions.assertEquals(10, playerCyan.getrugNum(), "Expected rugNum to be set to 10");

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
        // TODO
        Assertions.assertTrue(true);
    }

    @Test
    public void checkTwoRug() {
        // TODO
        Assertions.assertTrue(true);
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
        // TODO
        Assertions.assertTrue(true);
    }

}
