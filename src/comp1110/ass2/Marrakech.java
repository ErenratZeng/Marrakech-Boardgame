package comp1110.ass2;
import comp1110.ass2.model.AbbreviatedRug;
import comp1110.ass2.model.Assam;
import comp1110.ass2.model.Board;
import comp1110.ass2.model.Player;
import comp1110.ass2.model.TwoRug;
import comp1110.ass2.model.State;
import javafx.scene.paint.Color;
import comp1110.ass2.model.base.Point;
import comp1110.ass2.model.base.Dice;

import java.util.*;


public class Marrakech {

    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     *  - The String is 7 characters long
     *  - The first character in the String corresponds to the colour character of a player present in the game
     *  - The next two characters represent a 2-digit ID number
     *  - The next 4 characters represent coordinates that are on the board
     *  - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     *  - c023343 (Shares the colour but not the ID)
     *  - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     * @param gameString A String representing the current state of the game as per the README
     * @param rug A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rug) {
        // FIXME: Task 4
        // Check if the rug color are valid
        char rugColor = rug.charAt(0);
        if (!(rugColor == 'c' || rugColor == 'y' || rugColor == 'r' || rugColor == 'p')) {
            return false;
        }
        TwoRug twoRug = new TwoRug(rug);
        Point[] currentRug = twoRug.getPoints();

        // Check if the coordinates are valid
        for (Point point : currentRug) {
            if (point.getX() < 0 || point.getX() > 6 || point.getY() < 0 || point.getY() > 6) {
                return false;
            }
        }

        // Using a HashSet to store rugID for the same color
        HashSet<String> rugIDs = new HashSet<>();
        for (int i = 37; i < 37 + 3 * 49; i += 3) {
            char color = gameString.charAt(i);
            if (color == rugColor) {
                String currentID = gameString.substring(i+1, i+3); // getting the rug ID
                rugIDs.add(currentID);
            }
        }

        // Check if the rug IDs are unique
        String rugID = rug.substring(1, 3);
        return !rugIDs.contains(rugID); // Rug with the same ID exists
    }


    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     *  - One shows 1
     *  - Two show 2
     *  - Two show 3
     *  - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     * @return The result of the roll of the die meeting the criteria above
     */
    public static int rollDie() {
        // FIXME: Task 6
        Dice playerDice = new Dice();
        return playerDice.roll();
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        // FIXME: Task 8
        System.out.println("currentGame="+ currentGame);
        for (int i = 0; i < 4; i++) {
            int startIndex = i * 8;
            if (currentGame.charAt(startIndex + 7) == 'i' &&
                    (currentGame.charAt(startIndex + 5) != '0' ||
                            currentGame.charAt(startIndex + 6) != '0')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        // FIXME: Task 9
        if (rotation % 90 != 0 || rotation % 180 == 0) {
            return currentAssam;
        }

        Assam assam = new Assam(currentAssam);

        if (rotation == 90) { // rotate right
            assam.setOrientationRight90();
        } else if (rotation == 270) { // rotate left
            assam.setOrientationLeft90();
        }

        return assam.getString();
    }



    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     *   1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     *   2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     *      the new rug must not cover the entirety of another rug that's already on the board.
     * //@param gameState A game string representing the current state of the game
     * //@param rug A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */


    public static boolean isPlacementValid(String gameState, String rug) {
        // FIXME: Task 10
        // Get Assam's position from gameState
        State state = new State(gameState);
        Point assamPosition = state.getAssam().getPoint();
        int assamX = assamPosition.getX();
        int assamY = assamPosition.getY();

        TwoRug twoRug = new TwoRug(rug);
        Point[] currentRug = twoRug.getPoints();
        int rugSide1X = currentRug[0].getX();
        int rugSide1Y = currentRug[0].getY();
        int rugSide2X = currentRug[1].getX();
        int rugSide2Y = currentRug[1].getY();

        Board board = state.getBoard();

        AbbreviatedRug rugSide1Rug = board.getRug(rugSide1X, rugSide1Y);
        Color rugSide1Color = rugSide1Rug.getColor();
        int rugSide1ID = rugSide1Rug.getID();

        AbbreviatedRug rugSide2Rug = board.getRug(rugSide2X, rugSide2Y);
        Color rugSide2Color = rugSide2Rug.getColor();
        int rugSide2ID = rugSide2Rug.getID();

        // Check if rug is adjacent to Assam
        boolean isAdjacent = (Math.abs(rugSide1X - assamX) <= 1 && Math.abs(rugSide1Y - assamY) <= 1) ||
                (Math.abs(rugSide2X - assamX) <= 1 && Math.abs(rugSide2Y - assamY) <= 1);

        if (!isAdjacent) {
            return false;
        }

        // Check if the rug is placed on the Assam position
        if ((rugSide1X == assamX &&  rugSide1Y == assamY) || (rugSide2X == assamX && rugSide2Y == assamY)) {
            return false;
        }

        // Check if the rug cover the other rug though check color
        if (rugSide2Color == null || rugSide1Color != rugSide2Color) {
            return true;
        }

        // Check if the rug cover the other rug though check ID
        return rugSide1ID != rugSide2ID;
    }


    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        // FIXME: Task 11
        State state = new State(gameString);
        Point assam = state.getAssam().getPoint();
        Board board = state.getBoard();
        int assamX = assam.getX();
        int assamY = assam.getY();

        // Wrap the x and y coordinates into a list for easier manipulation.
        List<Integer> assamPosition = Arrays.asList(assamX, assamY);

        // Extract the color of the rug Assam has landed on.
        AbbreviatedRug currentAssamRug = board.getRug(assamX, assamY);
        Color currentAssamRugColor = currentAssamRug.getColor();

        // If there's no rug under Assam, return 0 payment.
        if (currentAssamRugColor == null) {
            return 0;
        }

        // Use BFS to explore all connected rugs of the same color as Assam landed on.
        Set<List<Integer>> visited = new HashSet<>(); // Keeps track of visited squares.
        Queue<List<Integer>> queue = new LinkedList<>(); // BFS queue.
        queue.add(assamPosition);
        visited.add(assamPosition);

        while (!queue.isEmpty()) {
            List<Integer> currentRugPosition = queue.poll();
            int x = currentRugPosition.get(0);
            int y = currentRugPosition.get(1);

            // Generate the adjacent positions.
            List<List<Integer>> adjacent = new ArrayList<>();
            if (x > 0) adjacent.add(Arrays.asList(x - 1, y));
            if (x < 6) adjacent.add(Arrays.asList(x + 1, y));
            if (y > 0) adjacent.add(Arrays.asList(x, y - 1));
            if (y < 6) adjacent.add(Arrays.asList(x, y + 1));

            // If the adjacentRug is of the same color and hasn't been visited, add it to the queue.
            for (List<Integer> adjacentRugs : adjacent) {
                AbbreviatedRug adjacentRug = board.getRug(adjacentRugs.get(0), adjacentRugs.get(1));
                Color adjacentRugColor = adjacentRug.getColor();

                if (!visited.contains(adjacentRugs) && adjacentRugColor == currentAssamRugColor) {
                    queue.add(adjacentRugs);
                    visited.add(adjacentRugs);
                }
            }
        }
        return visited.size();
    }


    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     * //@param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        // FIXME: Task 12
        // calculate each score of player(coins + color on board)
        State state = new State(gameState);
        Board board = state.getBoard();
        ArrayList<Player> playerArray = state.getPlayers();

        // Create a map that stores player scores for each color (coins + color on board)
        Map<Color, Integer> scores = new HashMap<>();
        scores.put(Color.CYAN, 0);
        scores.put(Color.YELLOW, 0);
        scores.put(Color.PURPLE, 0);
        scores.put(Color.RED, 0);

        // Traverse the player list and store each player's coins (i.e. dirhams) into the mapping
        for (Player player : playerArray) {
            scores.put(player.getColor(), player.getCoins());
        }

        // Create a new mapping to store only the coins of each player
        // to determine the winner when the total score is the same
        Map<Color, Integer> originalScores = new HashMap<>(scores);

        // Traverse the entire board, count the number of blocks of each color,
        // and add that value to the score of the player of that color
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                AbbreviatedRug rug = board.getRug(x, y);
                Color color = rug.getColor();
                if (color != null) {
                    scores.put(color, scores.get(color) + 1);
                }
            }
        }

        // Convert the map that stores the total score into a list
        // and sort it in descending order by the player's total score
        List<Map.Entry<Color, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort(Map.Entry.<Color, Integer>comparingByValue().reversed());

        // Define a color variable to store the color of the winner
        Color WinnerColor;

        // If the two players with the highest scores have the same score,
        // the winner is determined based on their coins
        if (Objects.equals(sortedScores.get(0).getValue(), sortedScores.get(1).getValue())) {
            WinnerColor = Collections.max(originalScores.entrySet(), Map.Entry.comparingByValue()).getKey();
        } else {
            // Otherwise the player with the highest score is the winner
            WinnerColor = sortedScores.get(0).getKey();
        }

        // Check if the game has ended, return 'n' if not
        if (!isGameOver(gameState)) return 'n';

        // Return the corresponding character based on the color of the winner
        if (WinnerColor.equals(Color.CYAN)) return 'c';
        if (WinnerColor.equals(Color.YELLOW)) return 'y';
        if (WinnerColor.equals(Color.PURPLE)) return 'p';
        if (WinnerColor.equals(Color.RED)) return 'r';

        // The remaining situation is a tie, return 't'
        return 't';
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult){
        // FIXME: Task 13
        Assam assam = new Assam(currentAssam);
        Point assamPosition = assam.getPoint();
        Assam.Orientation currentOrientation = assam.getOrientation();
        Assam.Orientation targetOrientation = assam.getOrientation();
        int currentX = assamPosition.getX();
        int currentY = assamPosition.getY();
        int targetX = 0;
        int targetY = 0;


        //原本朝向北方向的情况
        if (currentOrientation == Assam.Orientation.N) {
            //未出界
            if (currentY >= dieResult) {
                targetOrientation = currentOrientation;
                targetX = currentX;
                targetY = currentY - dieResult;
            }

            else {
                //出界转西
                if (currentX == 6) {
                    targetOrientation = Assam.Orientation.W;
                    targetX = currentY - dieResult + 7;
                    targetY = 0;
                }

                else {
                    //出界转南
                    targetOrientation = Assam.Orientation.S;
                    targetX = currentX == 1 || currentX == 3 || currentX == 5 ? currentX - 1 : currentX + 1;
                    targetY = - currentY + dieResult - 1;
                }
            }
        }

        //原本朝向南方向的情况
        else if (currentOrientation == Assam.Orientation.S) {
            if (currentY  < 7 - dieResult) {
                //未出界
                targetOrientation = currentOrientation;
                targetX = currentX;
                targetY = currentY + dieResult;
            }

            else {
                //出界转东
                targetOrientation = Assam.Orientation.E;
                if (currentX == 0) {
                    targetX = currentY + dieResult - 7;
                    targetY = 6;
                }

                else {
                    //出界转北
                    targetOrientation = Assam.Orientation.N;
                    targetX = currentX == 1 || currentX == 3 || currentX == 5 ? currentX + 1 : currentX - 1;
                    targetY =  -currentY - dieResult + 13;
                }
            }
        }

        //原本朝向东方向的情况
        else if (currentOrientation == Assam.Orientation.E) {
            if (currentX  < 7 - dieResult) {
                targetOrientation = currentOrientation;
                targetX = currentX + dieResult;
                targetY = currentY;
            }

            else {
                if (currentY == 0) {
                    //出界转南
                    targetOrientation = Assam.Orientation.S;
                    targetX = 6;
                    targetY = currentX + dieResult - 7;
                }

                else {
                    //出界转西
                    targetOrientation = Assam.Orientation.W;
                    targetX = -currentX - dieResult + 13;
                    targetY = currentY == 1 || currentY == 3 || currentY == 5 ? currentY + 1 : currentY - 1;
                }
            }
        }

        //原本朝向西方向的情况
        else if (currentOrientation == Assam.Orientation.W) {
            // 未出界情况
            if (currentX >= dieResult) {
                targetOrientation = currentOrientation;
                targetX = currentX - dieResult;
                targetY = currentY;
            }

            else {
                if (currentY == 6) {
                    //出界转北
                    targetOrientation = Assam.Orientation.N;
                    targetX = 0;
                    targetY = currentX - dieResult + 7;
                }

                else {
                    // 出界转东
                    targetOrientation = Assam.Orientation.E;
                    targetX = -currentX + dieResult - 1;
                    targetY = currentY == 1 || currentY == 3 || currentY == 5 ? currentY - 1 : currentY + 1;
                }
            }
        }

        return "A" + targetX + targetY + targetOrientation ;

    }


    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     * @param currentGame A String representation of the current state of the game.
     * @param rug A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {
        // FIXME: Task 14
        return "";
    }

}
