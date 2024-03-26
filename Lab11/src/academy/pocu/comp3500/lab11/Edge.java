package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

public class Edge implements Comparable<Edge> {
    private final Point p1;
    private final Point p2;
    private float weight;

    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        int x = Math.abs(p2.getX() - p1.getX());
        int y = Math.abs(p2.getY() - p1.getY());
        weight = (float) Math.sqrt(x * x + y * y);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public float getWeight() {
        return this.weight;
    }

    @Override
    public int compareTo(Edge e) {
        return Float.compare(this.weight, e.weight);
    }
    @Override
    public String toString() {
        return String.format("%s -> %s", p1.toString(), p2.toString());
    }
}
