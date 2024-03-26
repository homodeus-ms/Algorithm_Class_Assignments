package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

import java.util.*;

import static java.util.Collections.sort;

public class BallBoy {
    private static final Point START = new Point(0, 0);
    private static Point[] pointArr;
    private static ArrayList<DisjointSet> setArr;
    private static HashMap<Point, ArrayList<Edge>> edgeMap;
    //private static ArrayList<Edge> edges;
    //private static ArrayList<Edge> mst = new ArrayList<>();

    public static List<Point> findPath(final Point[] points) {

        if (points.length == 0) {
            ArrayList<Point> result = new ArrayList<>();
            result.add(START);
            return result;
        }

        pointArr = new Point[points.length + 1];
        edgeMap = new HashMap<>();
        DisjointSet set = new DisjointSet(points);
        ArrayList<Edge> edges = getEdges(points);
        Collections.sort(edges);
        ArrayList<Edge> mst = getMst(set, edges);
        ArrayList<Point> firstDfsResult = getFirstDfs(mst);
        ArrayList<Point> paths = getPath(firstDfsResult);
        paths.add(START);

        return paths;
    }
    private static ArrayList<Point> getPath(ArrayList<Point> firstDfs) {
        HashMap<Point, Boolean> discovered = new HashMap<>();
        int size = firstDfs.size();
        ArrayList<Point> result = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            Point p = firstDfs.get(i);
            if (!discovered.containsKey(p)) {
                discovered.put(p, true);
                result.add(p);
            }
        }
        return result;
    }
    private static ArrayList<Point> getFirstDfs(ArrayList<Edge> mst) {
        int size = mst.size();
        ArrayList<Point> result = new ArrayList<>(size * 2);
        searchDfsRecursive(START, START, result);
        result.remove(result.size() - 1);

        return result;
    }
    private static void searchDfsRecursive(Point from, Point node,
                                           ArrayList<Point> result) {
        result.add(node);
        ArrayList<Edge> edges = edgeMap.get(node);

        for (Edge edge : edges) {
            Point to = edge.getP1() == node ? edge.getP2() : edge.getP1();
            if (to != from) {
                searchDfsRecursive(node, to, result);
            }
        }
        result.add(from);

        /*result.add(start);*/

        /*if (edges.size() == 1) {
            Edge e = edges.get(0);
            Point to = e.getP1() == start ? e.getP2() : e.getP1();
            result.add(to);
        }*/
    }

    private static ArrayList<Edge> getMst(DisjointSet set, ArrayList<Edge> edges) {
        int size = edges.size();
        ArrayList<Edge> mst = new ArrayList<>(size);

        for (int i = 0; i < size; ++i) {
            Point p1 = edges.get(i).getP1();
            Point p2 = edges.get(i).getP2();
            String n1 = p1.toString();
            String n2 = p2.toString();

            String root1 = set.find(n1);
            String root2 = set.find(n2);

            if (!root1.equals(root2)) {
                Edge e = edges.get(i);

                mst.add(e);
                if (!edgeMap.containsKey(p1)) {
                    edgeMap.put(p1, new ArrayList<>());
                }
                if (!edgeMap.containsKey(p2)) {
                    edgeMap.put(p2, new ArrayList<>());
                }
                edgeMap.get(p1).add(e);
                edgeMap.get(p2).add(e);


                set.union(p1, p2);
            }
        }
        return mst;
    }
    private static ArrayList<Edge> getEdges(final Point[] points) {

        int size = points.length;

        pointArr = new Point[size + 1];
        pointArr[0] = START;
        int idx = 1;
        for (Point p : points) {
            pointArr[idx++] = p;
        }

        ArrayList<Edge> edges = new ArrayList<>((size + 1) * size / 2);

        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size + 1; ++j) {
                Edge e = new Edge(pointArr[i], pointArr[j]);
                edges.add(e);
            }
        }
        return edges;
    }

    private static void printEdges(ArrayList<Edge> edges) {
        System.out.printf("Edge Count : %d\n", edges.size());
        for (Edge edge : edges) {
            System.out.println(edge.toString());
        }
        System.out.println();
    }

}
