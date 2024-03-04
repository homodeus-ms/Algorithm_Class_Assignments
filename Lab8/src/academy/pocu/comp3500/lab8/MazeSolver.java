package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public final class MazeSolver {
    private static final char WALL = 'x';
    private static final char EXIT = 'E';

    //private static Point exitPos;
    private static int width;
    private static int height;
    private static List<Point> result;
    private static int resultSize;

    private static char[][] map = null;
    private static HashMap<Integer, Node> visitedNodes;

    public static List<Point> findPath(final char[][] maze, final Point start) {
        map = maze;
        width = maze[0].length;
        height = maze.length;
        visitedNodes = new HashMap<>(width * height);
        result = new LinkedList<>();
        resultSize = 0;


        //findExitPos(maze);
        /*if (exitPos == null) {
            return new LinkedList<>();
        }*/
        Node root = new Node(start);

        boolean found = getPath(root, root.getPos());


        return result;
    }
    private static boolean getPath(Node start, Point startPos) {


        result.add(startPos);
        visitedNodes.put(getVisitedMapKey(startPos.getX(), startPos.getY()), start);
        start.checkVisited();
        ++resultSize;

        int x = startPos.getX();
        int y = startPos.getY();

        if (map[y][x] == EXIT) {
            return true;
        }

        getNextAvailablePos(start, startPos);

        //PriorityQueue<Node> queue = start.getChildren();
        Queue<Node> queue = start.getChildren();

        if (queue.isEmpty()) {
            result.remove(resultSize - 1);
            --resultSize;
        } else {
            boolean res;
            while (!queue.isEmpty()) {
                Node nextNode = queue.poll();
                Point nextPos = nextNode.getPos();
                res = getPath(nextNode, nextPos);
                if (res) {
                    return res;
                }
            }
            result.remove(resultSize - 1);
            --resultSize;
            return false;
        }

        return false;
    }

    /*private static void findExitPos(char[][] maze) {
        for (int y = 0; y < maze.length; ++y) {
            for (int x = 0; x < maze[0].length; ++x) {
                if (maze[y][x] == 'E') {
                    exitPos = new Point(x, y);
                    break;
                }
            }
        }
    }*/
    /*private static int getDistanceToExit(Point from) {
        return Math.abs(exitPos.getX() - from.getX()) + Math.abs(exitPos.getY() - from.getY());
    }*/
    private static void getNextAvailablePos(Node n, Point p) {
        int xPos = p.getX();
        int yPos = p.getY();

        if (map[yPos][xPos] == EXIT) {
            return;
        }

        // north
        yPos -= 1;
        if (isInBoundary(xPos, yPos) && map[yPos][xPos] != WALL) {
            int key = getVisitedMapKey(xPos, yPos);
            if (!visitedNodes.containsKey(key)) {
                Point newP = new Point(xPos, yPos);
                //n.insertChild(new Node(new Point(xPos, yPos), getDistanceToExit(newP)));
                n.insertChild(new Node(newP));
            }
        }
        // south
        yPos = p.getY() + 1;
        if (isInBoundary(xPos, yPos) && map[yPos][xPos] != WALL) {
            int key = getVisitedMapKey(xPos, yPos);
            if (!visitedNodes.containsKey(key)) {
                Point newP = new Point(xPos, yPos);
                //n.insertChild(new Node(new Point(xPos, yPos), getDistanceToExit(newP)));
                n.insertChild(new Node(newP));
            }
        }
        // east
        xPos = p.getX() + 1;
        yPos = p.getY();
        if (isInBoundary(xPos, yPos) && map[yPos][xPos] != WALL) {
            int key = getVisitedMapKey(xPos, yPos);
            if (!visitedNodes.containsKey(key)) {
                Point newP = new Point(xPos, yPos);
                //n.insertChild(new Node(new Point(xPos, yPos), getDistanceToExit(newP)));
                n.insertChild(new Node(newP));
            }
        }
        // west
        xPos = p.getX() - 1;
        if (isInBoundary(xPos, yPos) && map[yPos][xPos] != WALL) {
            int key = getVisitedMapKey(xPos, yPos);
            if (!visitedNodes.containsKey(key)) {
                Point newP = new Point(xPos, yPos);
                //n.insertChild(new Node(new Point(xPos, yPos), getDistanceToExit(newP)));
                n.insertChild(new Node(newP));
            }
        }
    }


    private static boolean isInBoundary(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }
    private static int getVisitedMapKey(int x, int y) {
        return (x * 10007) + (y * 10009);
    }

}
