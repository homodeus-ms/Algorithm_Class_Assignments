package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class BST {
    static int idx = 0;
    private Node root = null;
    private int size = 0;
    private boolean hasDelete = true;

    public BST() {
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public void insert(Node node) {
        if (root == null) {
            root = node;
            ++size;
            return;
        }

        insertRecursive(root, node);
    }
    public Node searchOrNull(Node node) {
        if (size == 0) {
            return null;
        }
        return findNodeOrNullRecursive(root, node);
    }
    public Node findMatchOrNull(Node node) {
        if (size == 0 || size == 1) {
            return null;
        }
        if (node.getPlayer().getId() == root.getPlayer().getId()) {
            return findMatchRecursive(root, node, 0x7FFFFFFF, root);
        }

        int diff = Math.abs(root.getPlayer().getRating() - node.getPlayer().getRating());
        return findMatchRecursive(root, node, diff, root);
    }
    public void getTopPlayers(Player[] players, int count) {
        idx = 0;
        getTopPlayersRecursive(root, players, count);
    }
    public void getBottomPlayers(Player[] players, int count) {
        idx = 0;
        getBottomPlayersRecursive(root, players, count);
    }
    public boolean insertPlayer(Node node) {
        if (size == 0) {
            ++size;
            root = node;
            return true;
        }
        Node ret = insertRecursive2(root, node);

        if (ret != null) {
            ++size;
            return true;
        }
        return false;
    }
    public boolean delete(Node node) {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            if (root.getPlayer().getId() == node.getPlayer().getId()) {
                --size;
                root = null;
                return true;
            } else {
                return false;
            }
        }

        deleteRecursive(root, node.getPlayer().getId(), node.getPlayer().getRating());

        if (hasDelete) {
            --size;
            return true;
        }
        hasDelete = true;
        return false;
    }
    public Node getClosestMinRateNode(Node node) {
        return null;
    }
    public Node getClosestMaxRateNode(Node node) {
        return null;
    }

    private Node insertRecursive(Node root, Node node) {
        if (root == null) {
            ++size;
            return node;
        }

        int rootRate = root.getPlayer().getRating();
        int nodeRate = node.getPlayer().getRating();

        if (nodeRate < rootRate) {
            Node temp = insertRecursive(root.getLeft(), node);
            root.setLeft(temp);
            temp.setParent(root);
        } else {
            Node temp = insertRecursive(root.getRight(), node);
            root.setRight(temp);
            temp.setParent(root);
        }
        return root;
    }
    private Node findNodeOrNullRecursive(Node root, Node node) {
        if (root == null) {
            return null;
        }
        int rootId = root.getPlayer().getId();
        int nodeId = node.getPlayer().getId();
        if (nodeId == rootId) {
            return root;
        }
        int roodRating = root.getPlayer().getRating();
        int nodeRating = node.getPlayer().getRating();

        if (nodeRating < roodRating) {
            return findNodeOrNullRecursive(root.getLeft(), node);
        } else {
            return findNodeOrNullRecursive(root.getRight(), node);
        }
    }
    private Node findMatchRecursive(Node root, Node node, int minDiffParentValue, Node minDiffParent) {
        // 트리에 노드가 존재하지 않는 경우
        if (root == null) {
            return null;
        }
        int rootId = root.getPlayer().getId();
        int nodeId = node.getPlayer().getId();
        int rootRating = root.getPlayer().getRating();
        int nodeRating = node.getPlayer().getRating();

        // 트리에서 노드를 찾은 경우 -> 최소차이를 내는 작은값, 큰값, 부모값을 비교해서 조건에 맞는 것을 리턴
        if (nodeId == rootId) {
            if (root.getLeft() == null && root.getRight() == null) {
                return minDiffParent;

            } else {
                Node smaller = root.getLeft() == null ? null : getFarRightRecursive(root.getLeft());
                Node greater = root.getRight() == null ? null : getFarLeftRecursive(root.getRight());

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
            if (root.getLeft() == null) {
                return null;
            }

            int childDiff = Math.abs(root.getLeft().getPlayer().getRating() - node.getPlayer().getRating());
            if (childDiff != 0 && isNewMinDiffNode(root.getLeft(), minDiffParent, childDiff, minDiffParentValue)) {
                minDiffParentValue = childDiff;
                minDiffParent = root.getLeft();
            }
            return findMatchRecursive(root.getLeft(), node, minDiffParentValue, minDiffParent);
        } else {
            if (root.getRight() == null) {
                return null;
            }

            int childDiff = Math.abs(root.getRight().getPlayer().getRating() - node.getPlayer().getRating());
            if (childDiff != 0 && isNewMinDiffNode(root.getRight(), minDiffParent, childDiff, minDiffParentValue)) {
                minDiffParentValue = childDiff;
                minDiffParent = root.getRight();
            }
            return findMatchRecursive(root.getRight(), node, minDiffParentValue, minDiffParent);
        }
    }

    private void getTopPlayersRecursive(Node start, Player[] players, int count) {
        if (start.getRight() == null) {
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

        if (start.getLeft() != null) {
            getTopPlayersRecursive(start.getLeft(), players, count);
        }
    }
    private void getBottomPlayersRecursive(Node start, Player[] players, int count) {
        if (start.getLeft() == null) {
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

        if (start.getRight() != null) {
            getBottomPlayersRecursive(start.getRight(), players, count);
        }
    }
    private Node insertRecursive2(Node root, Node node) {

        int rootRate = root.getPlayer().getRating();
        int nodeRate = node.getPlayer().getRating();
        int rootId = root.getPlayer().getId();
        int nodeId = node.getPlayer().getId();

        if (rootId == nodeId) {
            return null;
        }

        Node temp;

        if (nodeRate < rootRate) {
            if (root.getLeft() == null) {
                root.setLeft(node);
                node.setParent(root);
                return node;
            }
            temp = insertRecursive2(root.getLeft(), node);

        } else {
            if (root.getRight() == null) {
                root.setRight(node);
                node.setParent(root);
                return node;
            }
            temp = insertRecursive2(root.getRight(), node);
        }
        return temp;
    }

    private Node deleteRecursive(Node start, int nodeId, int nodeRating) {
        if (start == null) {
            hasDelete = false;
            return null;
        }
        int startId = start.getPlayer().getId();
        int startRating = start.getPlayer().getRating();

        if (nodeId != startId) {
            if (nodeRating < startRating) {
                Node temp = deleteRecursive(start.getLeft(), nodeId, nodeRating);
                start.setLeft(temp);
            } else {
                Node temp = deleteRecursive(start.getRight(), nodeId, nodeRating);
                start.setRight(temp);
            }
        } else {
            if (start.getLeft() == null) {
                //start.setParent(null);
                Node temp = start.getRight();

                if (temp != null) {
                    start.setLeft(temp.getLeft());
                    start.setRight(temp.getRight());
                    start.setPlayer(temp.getPlayer());
                    start.setParent(null);
                } else {
                    start = temp;
                }

            } else if (start.getRight() == null) {
                Node temp = start.getLeft();
                if (temp != null) {
                    start.setLeft(temp.getLeft());
                    start.setRight(temp.getRight());
                    start.setPlayer(temp.getPlayer());
                    start.setParent(null);
                } else {
                    start = temp;
                }


            } else {
                Node temp = getSuccessor(start.getLeft());
                start.setPlayer(temp.getPlayer());
                start.setLeft(deleteRecursive(start.getLeft(), temp.getPlayer().getId(), temp.getPlayer().getRating()));
            }
        }

        return start;
    }

    private void disconnectNode(Node parent, Node child) {
        if (parent.getRight() == child) {
            parent.setRight(null);
        } else {
            parent.setLeft(null);
        }
        child.setParent(null);
    }

    private Node getFarLeftRecursive(Node start) {
        if (start.getLeft() == null) {
            return start;
        }
        return getFarLeftRecursive(start.getLeft());
    }
    private Node getFarRightRecursive(Node start) {
        if (start.getRight() == null) {
            return start;
        }
        return getFarRightRecursive(start.getRight());
    }
    private Node getGreaterParentOrNullRecursive(Node parent, int rating) {
        if (parent == null || parent.getPlayer().getRating() > rating) {
            return parent;
        }
        return getGreaterParentOrNullRecursive(parent.getParent(), rating);
    }
    private Node getSmallerParentOrNullRecursive(Node parent, int rating) {
        if (parent == null || parent.getPlayer().getRating() < rating) {
            return parent;
        }
        return getSmallerParentOrNullRecursive(parent.getParent(), rating);
    }
    private Node getSuccessor(Node start) {
        if (start.getRight() == null) {
            return start;
        }
        return getSuccessor(start.getRight());
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

    private Node getPredecessor(Node root) {
        if (root.getRight() == null) {
            return root;
        }
        return getPredecessor(root.getRight());
    }
    public void printNode(Node root) {
        if (root == null) {
            return;
        }

        System.out.printf("(%d %s %d) ", root.getPlayer().getId(), root.getPlayer().getName(), root.getPlayer().getRating());

        printNode(root.getLeft());
        printNode(root.getRight());
    }

}
