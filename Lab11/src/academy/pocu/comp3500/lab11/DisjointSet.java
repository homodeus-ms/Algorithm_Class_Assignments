package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

import java.util.HashMap;
import java.util.LinkedList;

public class DisjointSet {

    private final HashMap<String, SetNode> sets = new HashMap<>();

    public DisjointSet(final Point[] nodes) {
        for (Point point : nodes) {
            String s = point.toString();
            SetNode n = new SetNode(s, 1);
            sets.put(s, n);
        }
        String startNode = new Point(0, 0).toString();
        SetNode n = new SetNode(startNode, 1);
        sets.put(startNode, n);
    }

    public String find(final String node) {

        SetNode n = sets.get(node);
        String parent = n.getParent();

        if (parent.equals(node)) {
            return node;
        }
        n.setParent(find(n.getParent()));

        return n.getParent();
    }
    public void union(final Point p1, final Point p2) {
        String root1 = find(p1.toString());
        String root2 = find(p2.toString());

        if (root1.equals(root2)) {
            return;
        }

        SetNode parent = sets.get(root1);
        SetNode child = sets.get(root2);

        if (parent.getSize() < child.getSize()) {
            SetNode temp = parent;
            parent = child;
            child = temp;
        }
        child.setParent(parent.getParent());
        parent.setSize(child.getSize() + parent.getSize());
    }

}
