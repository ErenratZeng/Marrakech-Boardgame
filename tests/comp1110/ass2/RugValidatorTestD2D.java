package comp1110.ass2;

import comp1110.ass2.model.D2D.RugValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class RugValidatorTestD2D {

    @Test
    // This test ensures that rugs with strings other than a length of 7 are considered invalid.
    public void checkInvalidLength() {
        // Tests a rug string that does not have a length of 7.
        String gameString = "SampleGameString";
        String rug = "ABCD";  // invalid length
        Assertions.assertFalse(RugValidator.judgeRugValid(gameString, rug));
    }

    @Test
    // This test checks if rugs with invalid IDs (either too high or too low) are marked as invalid.
    public void checkInvalidID() {
        // Tests rug strings with invalid IDs.
        String gameString = "SampleGameString";
        String rug = "1234567";  // invalid ID (assuming this format is invalid)
        Assertions.assertFalse(RugValidator.judgeRugValid(gameString, rug));
    }

    @Test
    // we assume a certain game state where only specific rug colors are allowed, then test with a invalid rug color
    public void checkInvalidRugColor() {
        // Assuming a game state where only a few colors are allowed, test a rug with an invalid color.
        String gameString = "SampleGameString";  // Make sure this gameString doesn't allow certain colors
        String rug = "INVALID";  // Make sure this rug has an invalid color for the gameString
        Assertions.assertFalse(RugValidator.judgeRugValid(gameString, rug));
    }


    @Test
    // A more comprehensive test that should result in an invalid rug.
    public void checkGeneralInvalidity() {
        // A general test case that should be invalid.
        String gameString = "SampleGameString";
        String rug = "GENINVD";  // Make sure this rug is generally invalid
        Assertions.assertFalse(RugValidator.judgeRugValid(gameString, rug));
    }

}
