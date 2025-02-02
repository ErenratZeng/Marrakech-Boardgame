package comp1110.ass2.model.base;

import java.util.Random;

/**
 * Authorship:
 * name: Ruize Luo
 * uid: u7776709
 */
public class Dice {
    private final int[] sides;
    private final Random random;

    /**
     * @description The constructor initializes a Dice object.
     * In order to simulate special dice,
     * It sets the sides of the dice to an integer array {1, 2, 2, 3, 3, 4}
     * and creates a new Random object for generating random numbers.
     */
    public Dice() {
        this.sides = new int[]{1, 2, 2, 3, 3, 4};
        this.random = new Random();
    }

    public int[] getSides() {
        return sides;
    }

    /**
     * @return Returns an integer representing the result of rolling the dice.
     * @description This method simulates rolling the dice.
     * It randomly selects a value from the sides array,
     * using the random object, and returns that value.
     * Determine the number of moving steps by generating random numbers.
     */
    public int roll() {
        return sides[random.nextInt(sides.length)];
    }

}
