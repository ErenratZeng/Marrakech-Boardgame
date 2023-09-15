package comp1110.ass2.model.base;
import java.util.Random;

public class Dice {
    private final int[] sides;
    private final Random random;

    /**
     * Constructor to create an instance of Dice
     */
    public Dice() {
        this.sides = new int[]{1, 2, 2, 3, 3, 4};
        this.random = new Random();
    }

    /**
     * Determine the number of moving steps by generating random numbers
     */
    public int roll(){
        return sides[random.nextInt(sides.length)];
    }

}
