package academy.pocu.comp3500.lab9;

import java.util.ArrayList;

public class Node {
    private int difficulty;
    private int maxProfit;
    private Node left;
    private Node right;

    public Node(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMaxProfits() {
        return maxProfit;
    }
    public Node getLeft() {
        return left;
    }
    public Node getRight() {
        return right;
    }
    public void setLeft(Node node) {
        this.left = node;
    }
    public void setRight(Node node) {
        this.right = node;
    }
    public void updateMaxProfit(int profit) {
        this.maxProfit = Math.max(maxProfit, profit);
    }
}
