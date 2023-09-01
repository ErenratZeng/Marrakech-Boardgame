package comp1110.ass2.model;

import java.util.ArrayList;

public class State {
    private final Assam assam;
    private final Board board;

    // a minimum of 2 and a maximum of 4 players
    private final ArrayList<Player> players;
    private final Rug rug;

    public State(Builder builder) {
        this.assam = builder.assam;
        this.board = builder.board;
        this.players = builder.players;
        this.rug = builder.rug;
        if (players.size() > 4 || players.size() < 2)
            throw new RuntimeException("players.size() should be 2~4, but now is: " + players.size());
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

    public Rug getRug() {
        return rug;
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

    public static class Builder {
        private Assam assam;
        private Board board;
        private final ArrayList<Player> players = new ArrayList<>();
        private Rug rug;

        public Builder() {

        }

        public Builder setAssam(Assam assam) {
            this.assam = assam;
            return this;
        }

        public Builder setBoard(Board board) {
            this.board = board;
            return this;
        }

        public Builder setPlayer(Player player) {
            players.add(player);
            return this;
        }

        public Builder setRug(Rug rug) {
            this.rug = rug;
            return this;
        }

        public State build() {
            return new State(this);
        }
    }
}