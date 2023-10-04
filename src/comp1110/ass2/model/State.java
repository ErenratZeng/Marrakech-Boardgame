package comp1110.ass2.model;

import java.util.ArrayList;

public class State {
    private final Assam assam;
    private final Board board;

    // a minimum of 2 and a maximum of 4 players

    private final ArrayList<Player> players;

    // A Game string is the concatenation of one player string for each player, followed by one Assam string, followed by one board string.
    /**
     * @param players: An ArrayList of Player objects. The size should be between 2 and 4.
     * @param assam: An Assam object representing the Assam state.
     * @param board: A Board object representing the game board state.
     * @description  Initializes a new State instance with the given players, assam, and board.
     * An exception is thrown if the size of the players list is not between 2 and 4.
     */
    public State(ArrayList<Player> players, Assam assam, Board board) {
        this.players = players;
        this.assam = assam;
        this.board = board;
        if (players.size() > 4 || players.size() < 2)
            throw new RuntimeException("players.size() should be 2~4, but now is: " + players.size());
    }

    /**
     * @param string: A string representation of the game state.
     * @description Initializes a new State instance using the provided string.
     * Extracts players, assam, and board details from the string.
     */
    public State(String string) {
        players = new ArrayList<>();
        int i = 0;
        while (string.charAt(i) == 'P'){
            players.add(new Player(string.substring(i, i + 8)));
            i += 8;
        }
        if (players.size() > 4 || players.size() < 2)
            throw new RuntimeException("players.size() should be 2~4, but now is: " + players.size());
        if (string.charAt(i) == 'A'){
            assam = new Assam(string.substring(i, i + 4));
            i += 4;
        } else throw new RuntimeException("AssamString is wrong, String: " + string + ", at: " + i);
        if (string.charAt(i) == 'B'){
            board = new Board(string.substring(i));
        } else throw new RuntimeException("BoardString is wrong, String: " + string + ", at: " + i);
    }

    /**
     * @return Returns the Assam state of the game.
     * @description Retrieves the Assam state from the current game state.
     */
    public Assam getAssam() {
        return assam;
    }

    /**
     * @return Returns the game's Board state.
     * @description Retrieves the board state from the current game state.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return Returns an ArrayList of Player objects, representing the players in the game.
     * @description Retrieves the list of players from the current game state.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return Returns a string representation of the current game state.
     * @description Converts the current game state,
     * including all players, assam, and board, into a string format.
     */
    public String getString() {
        StringBuilder sb = new StringBuilder();

        for (Player player : players) {
            sb.append(player.getString());
        }
        sb.append(assam.getString());
        sb.append(board.getString());

        return sb.toString();
    }
}