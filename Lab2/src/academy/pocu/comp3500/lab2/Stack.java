package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {

    private Node root;
    private int size;

    public Stack() {
        root = null;
        size = 0;
    }

    public void push(final int data) {
        root = LinkedList.append(root, data);
        ++size;
    }

    public int peek() {
        return LinkedList.getOrNull(root, size - 1).getData();
    }

    public int pop() {
        int ret;

        if (size == 1) {
            ret = root.getData();
            root = null;

        } else {
            Node preLastNode = LinkedList.getOrNull(root, size - 2);
            ret = preLastNode.getNextOrNull().getData();
            preLastNode.setNext(null);
        }
        --size;
        return ret;
    }

    public int getSize() {
        return size;
    }
}
