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
        if (root == null) {
            root = LinkedList.append(root, data);
        } else {
            root = LinkedList.prepend(root, data);
        }

        ++size;
    }

    public int peek() {
        return root.getData();
    }

    public int pop() {
        int ret;

        if (size == 1) {
            ret = root.getData();
            root = null;

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
