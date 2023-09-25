package comp1110.ass2;

import comp1110.ass2.model.base.Dice;
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
        // TODO
        Assertions.assertTrue(true);
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
