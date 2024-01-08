package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node root;
    private Node lastNode;
    private int size;

    public Queue() {
        root = null;
        lastNode = null;
        size = 0;
    }
    public void enqueue(final int data) {
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
        return root.getData();
    }

    public int dequeue() {
        int ret;
        if (size == 1) {
            assert (root == lastNode);
            assert (root.getNextOrNull() == null);
            ret = root.getData();
            root = null;
            lastNode = null;

        } else {
            ret = root.getData();
            Node temp = root.getNextOrNull();
            root.setNext(null);
            root = null;
            root = temp;
        }

        --size;
        return ret;
    }

    public int getSize() {
        return size;
    }
}
