package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class RedBlackTree {
    private Node root;
    private Node n;
    private boolean deletedNodeWasRed = false;
    private int size = 0;
    private boolean hasDeleted = true;
    private boolean hasInserted = true;

    public boolean insert(Node node) {
        if (root == null) {
            root = node;
            root.setRed(false);
            ++size;
            attatchNil(node);
            return true;
        }
        attatchNil(node);
        insertRecursive(root, node);
        if (hasInserted) {
            ++size;
            return true;
        }
        hasInserted = true;
        return false;
    }
    public boolean delete(Node node) {
        if (root == null) {
            return false;
        }

        if (node.getPlayer().getId() == root.getPlayer().getId() &&
                root.getLeft().isNil() && root.getRight().isNil()) {
            root = null;
            --size;
            return true;
        }

        root = deleteRecursive(root, node.getPlayer().getId(), node.getPlayer().getRating());

        if (hasDeleted) {
            if (deletedNodeWasRed) {

            } else if (n.isRed()) {
                n.setRed(false);
            } else {
                makeUpTreeDelete(n);
            }

            --size;
            return true;
        } else {
            hasDeleted = true;
            return false;
        }

    }
    public int getSize() {
        return size;
    }

    public Node getRootOrNull() {
        return root;
    }

    private void insertRecursive(Node parent, Node node) {
        int parentRate = parent.getPlayer().getRating();
        int nodeRate = node.getPlayer().getRating();
        int parentId = parent.getPlayer().getId();
        int nodeId = node.getPlayer().getId();

        if (parentId == nodeId) {
            hasInserted = false;
            return;
        } else {
            if (nodeRate < parentRate) {
                if (parent.getLeft().isNil()) {
                    parent.setLeft(node);
                    node.setParent(parent);
                    makeUpTreeInsert(node);
                    return;
                }
                insertRecursive(parent.getLeft(), node);
            } else {
                if (parent.getRight().isNil()) {
                    parent.setRight(node);
                    node.setParent(parent);
                    makeUpTreeInsert(node);
                    return;
                }
                insertRecursive(parent.getRight(), node);
            }
        }
    }
    private void makeUpTreeInsert(Node n) {
        assert (!n.isNil());

        Node p = n.getParent();

        if (n == root) {
            n.setRed(false);
            return;
        }
        if (!p.isRed()) {
            return;
        }
        Node g = p.getParent();
        Node u = isLeftChild(p) ? g.getRight() : g.getLeft();

        if (p.isRed() && u.isRed()) {
            p.setRed(false);
            u.setRed(false);
            g.setRed(true);
            makeUpTreeInsert(g);
            return;
        }

        // 이제 트리 회전을 해야하는 상황
        // 다음의 네가지 경우가 생김
        //   1.    g          2.    g           3.   g            4.   g
        //       p                p                    p                 p
        //     n                    n                    n             n
        //  g기준 오른쪽회전     p기준 왼쪽회전        g기준 왼쪽회전     p기준 오른쪽회전
        //  g기준 회전들은 p와 g의 색상을 swap해줘야함
        //  p기준 회전들은 회전 이후에 p기준으로 재귀해야함
        if (isLeftChild(p)) {
            if (isLeftChild(n)) {
                rotateRight(g);
                swapColor(p, g);
            } else {
                rotateLeft(p);
                makeUpTreeInsert(p);
            }
        } else {
            if (!isLeftChild(n)) {
                rotateLeft(g);
                swapColor(p, g);
            } else {
                rotateRight(p);
                makeUpTreeInsert(p);
            }
        }
    }

    private boolean isLeftChild(Node child) {
        return child == child.getParent().getLeft();
    }
    private void attatchNil(Node newNode) {
        Node nilL = new Node(null);
        Node nilR = new Node(null);
        nilL.setRed(false);
        nilR.setRed(false);
        newNode.setLeft(nilL);
        newNode.setRight(nilR);
        nilL.setParent(newNode);
        nilR.setParent(newNode);
    }

    private Node deleteRecursive(Node start, int nodeId, int nodeRating) {
        if (start.isNil()) {
            hasDeleted = false;
            return start;
        }
        int startId = start.getPlayer().getId();
        int startRating = start.getPlayer().getRating();

        if (nodeId != startId) {
            if (nodeRating < startRating) {
                start.setLeft(deleteRecursive(start.getLeft(), nodeId, nodeRating));
            } else {
                start.setRight(deleteRecursive(start.getRight(), nodeId, nodeRating));
            }
        } else {
            if (start.getLeft().isNil()) {
                deletedNodeWasRed = start.isRed();
                n = start.getRight();

                Node temp = start.getRight();
                start.setRight(null);
                temp.setParent(start.getParent());
                start.setParent(null);
                start = temp;
            } else if (start.getRight().isNil()) {
                deletedNodeWasRed = start.isRed();
                n = start.getLeft();

                Node temp = start.getLeft();
                start.setLeft(null);
                temp.setParent(start.getParent());
                start.setParent(null);
                start = temp;

            } else {
                Node temp = findLeastLargeNode(start.getRight());
                start.setPlayer(temp.getPlayer());
                start.setRight(deleteRecursive(start.getRight(), temp.getPlayer().getId(), temp.getPlayer().getRating()));
            }
        }

        return start;

    }
    private void makeUpTreeDelete(Node n) {
        // stage1
        if (n == root) {
            return;
        }
        // stage2
        Node s = getSibling(n);
        Node p = n.getParent();
        if (s.isRed()) {
            swapColor(p, s);
            if (isLeftChild(n)) {
                rotateLeft(p);
            } else {
                rotateRight(p);
            }
        }
        // stage3
        s = getSibling(n);
        p = n.getParent();

        if (!p.isRed() && !s.isRed() && !s.getLeft().isRed() && !s.getRight().isRed()) {
            s.setRed(true);
            makeUpTreeDelete(p);
        }

        // stage4
        if (p.isRed() && !s.getLeft().isRed() && !s.getRight().isRed()) {
            swapColor(p, s);
            return;
        }

        // stage5
        if (isLeftChild(n)) {
            if (s.getLeft().isRed() && !s.isRed() && !s.getRight().isRed()) {
                swapColor(s, s.getLeft());
                rotateRight(s);
            }
        } else {
            if (s.getRight().isRed() && !s.isRed() && !s.getLeft().isRed()) {
                swapColor(s, s.getRight());
                rotateLeft(s);
            }
        }

        // stage6
        s = getSibling(n);
        if (isLeftChild(n)) {
            if (s.getRight().isRed() && !s.isRed()) {
                swapColor(n.getParent(), s);
                s.getRight().setRed(false);
                rotateLeft(n.getParent());
            }
        } else {
            if (s.getLeft().isRed() && !s.isRed()) {
                swapColor(n.getParent(), s);
                s.getLeft().setRed(false);
                rotateRight(n.getParent());
            }
        }
    }
    private Node findLeastLargeNode(Node node) {
        while (!node.getLeft().isNil()) {
            node = node.getLeft();
        }
        return node;
    }
    private Node getSibling(Node n) {
        return isLeftChild(n) ? n.getParent().getRight() : n.getParent().getLeft();
    }
    private void swapColor(Node a, Node b) {
        boolean temp = a.isRed();
        a.setRed(b.isRed());
        b.setRed(temp);
    }

    private void rotateLeft(Node node) {
        boolean wasLeftChild = false;

        Node originP = node.getParent();
        if (originP != null) {
            wasLeftChild = isLeftChild(node);
        }
        Node newP = node.getRight();
        Node newRc = newP.getLeft();
        newP.setParent(originP);
        newP.setLeft(node);
        node.setParent(newP);
        node.setRight(newRc);
        newRc.setParent(node);
        if (originP != null) {
            if (wasLeftChild) {
                originP.setLeft(newP);
            } else {
                originP.setRight(newP);
            }
        } else {
            root = newP;
        }
    }
    private void rotateRight(Node node) {

        boolean wasLeftChild = false;

        Node originP = node.getParent();
        if (originP != null) {
            wasLeftChild = isLeftChild(node);
        }
        Node newP = node.getLeft();
        Node newLc = newP.getRight();
        newP.setParent(originP);
        newP.setRight(node);
        node.setParent(newP);
        node.setLeft(newLc);
        newLc.setParent(node);
        if (originP != null) {
            if (wasLeftChild) {
                originP.setLeft(newP);
            } else {
                originP.setRight(newP);
            }
        } else {
            root = newP;
        }
    }
}
