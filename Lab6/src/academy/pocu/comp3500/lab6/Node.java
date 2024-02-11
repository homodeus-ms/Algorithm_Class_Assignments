package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class Node {
    private Player player;
    private Node parent;
    private Node left;
    private Node right;
    private boolean isRed = true;

    public Node(Player player) {
        this.player = player;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public boolean isRed() {
        return isRed;
    }
    public void setRed(boolean isRed) {
        this.isRed = isRed;
    }
    public boolean isNil() {
        return player == null;
    }
}
