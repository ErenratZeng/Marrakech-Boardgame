package comp1110.ass2.model;

import java.util.ArrayList;

public class State {
    private final Assam assam;
    private final Board board;

    // a minimum of 2 and a maximum of 4 players
    private final ArrayList<Player> players;

    // A Game string is the concatenation of one player string for each player, followed by one Assam string, followed by one board string.
    public State(ArrayList<Player> players, Assam assam, Board board) {
        this.players = players;
        this.assam = assam;
        this.board = board;
        if (players.size() > 4 || players.size() < 2)
            throw new RuntimeException("players.size() should be 2~4, but now is: " + players.size());
    }

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

    public Assam getAssam() {
        return assam;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

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