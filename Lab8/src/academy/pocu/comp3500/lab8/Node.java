package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Node {
    private Point pos;
    //private int distToEnd;
    private int visitedCount;
    //private ArrayList<Node> children = new ArrayList<>(4);
    //private PriorityQueue<Node> children = new PriorityQueue<>(4);
    private Queue<Node> children = new LinkedList<>();

    public Node(Point pos) {
        this.pos = pos;
        //this.distToEnd = distToEnd;
    }
    public Point getPos() {
        return this.pos;
    }
    /*public PriorityQueue<Node> getChildren() {
        return this.children;
    }*/
    public Queue<Node> getChildren() {
        return this.children;
    }
    public void insertChild(Node n) {
        this.children.add(n);
    }
    public void checkVisited() {
        ++visitedCount;
    }
    public int getVisitedCount() {
        return this.visitedCount;
    }

    /*@Override
    public int compareTo(Node other) {
        if (this.visitedCount != other.visitedCount) {
            return this.visitedCount > other.visitedCount ? 1 : -1;
        }
        return Integer.compare(this.distToEnd, other.distToEnd);
    }*/

}
