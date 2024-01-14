package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node front;
    private Node back;
    private int size;

    public Queue() {

    }
    public void enqueue(final int data) {
        if (size == 0) {
            front = new Node(data);
            back = front;
        } else {
            Node newNode = new Node(data);
            back.setNext(newNode);
            back = newNode;
        }
        ++size;
    }

    public int peek() {
        return front.getData();
    }

    public int dequeue() {
        int res = front.getData();
        Node temp = front.getNextOrNull();
        front.setNext(null);
        front = temp;
        --size;
        if (size == 0) {
            back = null;
        }
        return res;
    }

    public int getSize() {
        return size;
    }
}
