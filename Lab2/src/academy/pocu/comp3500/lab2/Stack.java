package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {
    private Node top;
    private int size;

    public Stack() {
    }
    public void push(final int data) {
        if (size == 0) {
            top = new Node(data);
        } else {
            Node newNode = new Node(data);
            newNode.setNext(top);
            top = newNode;
        }
        ++size;
    }

    public int peek() {
        return top.getData();
    }

    public int pop() {
        int res = top.getData();
        Node temp = top.getNextOrNull();
        top.setNext(null);
        top = temp;
        --size;

        return res;
    }

    public int getSize() {
        return size;
    }
}