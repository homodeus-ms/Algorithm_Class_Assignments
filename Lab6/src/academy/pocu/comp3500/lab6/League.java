package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.Random;

public class League {

    //private final BST bst = new BST();
    private final RedBlackTree tree = new RedBlackTree();
    private static int idx = 0;

    public League() {
    }
    public League(Player[] players) {
        /*Random random = new Random();
        boolean[] hasSelected = new boolean[players.length];

        for (int i = 0; i < players.length; ++i) {
            int randIdx = random.nextInt(players.length);

            while (hasSelected[randIdx]) {
                randIdx = random.nextInt(players.length);
            }

            Node node = new Node(players[randIdx]);
            bst.insert(node);
            hasSelected[randIdx] = true;
        }*/
        for (Player p : players) {
            Node node = new Node(p);
            tree.insert(node);
        }

    }
    public Player findMatchOrNull(final Player player) {
        Node root = tree.getRootOrNull();
        int treeSize = tree.getSize();
        if (treeSize == 0 || treeSize == 1) {
            return null;
        }

        Node node = new Node(player);

        if (node.getPlayer().getId() == root.getPlayer().getId()) {
            Node ret = findMatchOrNullRecursive(root, node, 0x7FFFFFFF, root);
            return ret == null ? null : ret.getPlayer();
        }

        int diff = Math.abs(root.getPlayer().getRating() - node.getPlayer().getRating());
        Node ret = findMatchOrNullRecursive(root, node, diff, root);
        return ret == null ? null : ret.getPlayer();
    }
    private Node findMatchOrNullRecursive(Node root, Node node, int minDiffParentValue, Node minDiffParent) {
        // 트리에 노드가 존재하지 않는 경우
        if (root.isNil()) {
            return null;
        }
        int rootId = root.getPlayer().getId();
        int nodeId = node.getPlayer().getId();
        int rootRating = root.getPlayer().getRating();
        int nodeRating = node.getPlayer().getRating();

        // 트리에서 노드를 찾은 경우 -> 최소차이를 내는 작은값, 큰값, 부모값을 비교해서 조건에 맞는 것을 리턴
        if (nodeId == rootId) {
            if (root.getLeft().isNil() && root.getRight().isNil()) {
                return minDiffParent;

            } else {
                Node smaller = root.getLeft().isNil() ? null : getFarRightRecursive(root.getLeft());
                Node greater = root.getRight().isNil() ? null : getFarLeftRecursive(root.getRight());

                int smallerDiff = smaller == null ? 0x7FFFFFFF : Math.abs(smaller.getPlayer().getRating() - rootRating);
                int greaterDiff = greater == null ? 0x7FFFFFFF : Math.abs(greater.getPlayer().getRating() - rootRating);

                int diffSmallest = Math.min(Math.min(smallerDiff, greaterDiff), minDiffParentValue);

                if (diffSmallest == greaterDiff) {
                    return greater;
                } else if (diffSmallest == minDiffParentValue) {
                    return minDiffParent;
                }
                return smaller;
            }
        }


        if (nodeRating < rootRating) {
            if (root.getLeft().isNil()) {
                return null;
            }

            int childDiff = Math.abs(root.getLeft().getPlayer().getRating() - node.getPlayer().getRating());
            if (childDiff != 0 && isNewMinDiffNode(root.getLeft(), minDiffParent, childDiff, minDiffParentValue)) {
                minDiffParentValue = childDiff;
                minDiffParent = root.getLeft();
            }
            return findMatchOrNullRecursive(root.getLeft(), node, minDiffParentValue, minDiffParent);
        } else {
            if (root.getRight().isNil()) {
                return null;
            }

            int childDiff = Math.abs(root.getRight().getPlayer().getRating() - node.getPlayer().getRating());
            if (childDiff != 0 && isNewMinDiffNode(root.getRight(), minDiffParent, childDiff, minDiffParentValue)) {
                minDiffParentValue = childDiff;
                minDiffParent = root.getRight();
            }
            return findMatchOrNullRecursive(root.getRight(), node, minDiffParentValue, minDiffParent);
        }
    }
    public Player[] getTop(final int count) {
        if (count <= 0 || tree.getRootOrNull() == null) {
            return new Player[0];
        }
        int currTreeSize = tree.getSize();
        int arrSize = Math.min(currTreeSize, count);
        Player[] players = new Player[arrSize];

        idx = 0;
        getTopPlayersRecursive(tree.getRootOrNull(), players, arrSize);
        return players;

        /*if (count <= 0 || bst.getSize() == 0) {
            return new Player[0];
        }
        int currBstSize = bst.getSize();
        int arrSize = Math.min(currBstSize, count);
        Player[] players = new Player[arrSize];

        bst.getTopPlayers(players, arrSize);
        return players;*/
    }
    public Player[] getBottom(final int count) {
        if (count <= 0 || tree.getRootOrNull() == null) {
            return new Player[0];
        }
        int currTreeSize = tree.getSize();
        int arrSize = Math.min(currTreeSize, count);
        Player[] players = new Player[arrSize];

        idx = 0;
        getBottomPlayersRecursive(tree.getRootOrNull(), players, arrSize);
        return players;
        /*if (count <= 0 || bst.getSize() == 0) {
            return new Player[0];
        }

        int currBstSize = bst.getSize();
        int arrSize = Math.min(currBstSize, count);
        Player[] players = new Player[arrSize];

        bst.getBottomPlayers(players, arrSize);
        return players;*/
    }
    public boolean join(final Player player) {
        return tree.insert(new Node(player));
    }
    public boolean leave(final Player player) {
        int treeSize = tree.getSize();
        if (treeSize == 0 || (treeSize == 1 && tree.getRootOrNull().getPlayer().getId() != player.getId())) {
            return false;
        }
        return tree.delete(new Node(player));
    }

    private Node getFarLeftRecursive(Node start) {
        if (start.getLeft().isNil()) {
            return start;
        }
        return getFarLeftRecursive(start.getLeft());
    }
    private Node getFarRightRecursive(Node start) {
        if (start.getRight().isNil()) {
            return start;
        }
        return getFarRightRecursive(start.getRight());
    }
    private boolean isNewMinDiffNode(Node newNode, Node origin, int newRating, int originRating) {
        if (newRating < originRating) {
            return true;
        } else if (newRating == originRating) {
            if (newNode.getPlayer().getRating() > origin.getPlayer().getRating()) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
    private void getTopPlayersRecursive(Node start, Player[] players, int count) {
        if (start.getRight().isNil()) {
            players[idx++] = start.getPlayer();
            if (count == idx) {
                return;
            }
        } else {
            getTopPlayersRecursive(start.getRight(), players, count);

            if (count == idx) {
                return;
            }
            players[idx++] = start.getPlayer();
        }

        if (count == idx) {
            return;
        }

        if (!start.getLeft().isNil()) {
            getTopPlayersRecursive(start.getLeft(), players, count);
        }
    }

    private void getBottomPlayersRecursive(Node start, Player[] players, int count) {
        if (start.getLeft().isNil()) {
            players[idx++] = start.getPlayer();
            if (count == idx) {
                return;
            }
        } else {
            getBottomPlayersRecursive(start.getLeft(), players, count);

            if (count == idx) {
                return;
            }
            players[idx++] = start.getPlayer();
        }

        if (count == idx) {
            return;
        }

        if (!start.getRight().isNil()) {
            getBottomPlayersRecursive(start.getRight(), players, count);
        }
    }

    public void print() {
        tree.print();
    }

    public int getSize() {
        return tree.getSize();
    }

}
