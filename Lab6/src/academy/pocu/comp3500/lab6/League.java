package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.Random;

public class League {

    private final BST bst = new BST();

    public League() {
    }
    public League(Player[] players) {
        Random random = new Random();
        boolean[] hasSelected = new boolean[players.length];

        for (int i = 0; i < players.length; ++i) {
            int randIdx = random.nextInt(players.length);

            while (hasSelected[randIdx]) {
                randIdx = random.nextInt(players.length);
            }

            Node node = new Node(players[randIdx]);
            bst.insert(node);
            hasSelected[randIdx] = true;
        }

        /*for (Player p : players) {
            Node node = new Node(p);
            bst.insert(node);
        }*/

    }
    public Player findMatchOrNull(final Player player) {
        Node node = new Node(player);
        Node ret = bst.findMatchOrNull(node);
        return ret == null ? null : ret.getPlayer();
    }
    public Player[] getTop(final int count) {
        if (count <= 0 || bst.getSize() == 0) {
            return new Player[0];
        }
        int currBstSize = bst.getSize();
        int arrSize = Math.min(currBstSize, count);
        Player[] players = new Player[arrSize];

        bst.getTopPlayers(players, arrSize);
        return players;
    }
    public Player[] getBottom(final int count) {
        if (count <= 0 || bst.getSize() == 0) {
            return new Player[0];
        }

        int currBstSize = bst.getSize();
        int arrSize = Math.min(currBstSize, count);
        Player[] players = new Player[arrSize];

        bst.getBottomPlayers(players, arrSize);
        return players;
    }
    public boolean join(final Player player) {
        return bst.insertPlayer(new Node(player));
    }
    public boolean leave(final Player player) {
        return bst.delete(new Node(player));
    }
}
