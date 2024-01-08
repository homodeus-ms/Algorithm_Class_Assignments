package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {

    private Node root;
    private Node lastNode;
    private int size;

    public Stack() {
        root = null;
        lastNode = null;
        size = 0;
    }

    public void push(final int data) {
        if (root == null) {
            root = LinkedList.append(root, data);
            lastNode = root;
        } else {
            lastNode = LinkedList.append(lastNode, data);
            lastNode = lastNode.getNextOrNull();
        }

        ++size;
    }

    public int peek() {
        return lastNode.getData();
    }

    public int pop() {
        int ret;

        if (size == 1) {
            ret = root.getData();
            root = null;
            lastNode = null;

        } else {
            ret = lastNode.getData();
            lastNode = LinkedList.getOrNull(root, size - 2);
            lastNode.setNext(null);
        }
        --size;
        return ret;
    }

    public int getSize() {
        return size;
    }
}
