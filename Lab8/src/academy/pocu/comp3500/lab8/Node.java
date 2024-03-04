package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
    private Point pos;
    //private int visitedCount;
    private Queue<Node> children = new LinkedList<>();

    public Node(Point pos) {
        this.pos = pos;
    }
    public Point getPos() {
        return this.pos;
    }
    public Queue<Node> getChildren() {
        return this.children;
    }
    public void insertChild(Node n) {
        this.children.add(n);
    }
    /*public void checkVisited() {
        ++visitedCount;
    }*/

}
