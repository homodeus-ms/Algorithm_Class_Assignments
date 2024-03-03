package academy.pocu.comp3500.lab7;

import java.util.ArrayList;

public class Node {
    private char value;
    private int length;
    private ArrayList<Node> nodes = new ArrayList<>();
    //private ArrayList<String> words = new ArrayList<>();
    private String word = null;

    public Node() {
    }
    public Node(char c) {
        value = c;
    }
    public char getValue() {
        return value;
    }
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    /*public ArrayList<String> getWord() {
        return this.words;
    }*/
    public String getWord() {
        return this.word;
    }
    public Node insert(Node root, Node n) {
        for (Node node : root.nodes) {
            if (node.value == n.value) {
                return node;
            }
        }
        root.nodes.add(n);

        return n;
    }
    /*public void insertStr(String str) {
        words.add(str);
    }*/
    public void setWord(String str) {
        word = str;
    }

    public void setLength(int length) {
        this.length = length;
    }
    public int getLength() {
        return this.length;
    }
}
