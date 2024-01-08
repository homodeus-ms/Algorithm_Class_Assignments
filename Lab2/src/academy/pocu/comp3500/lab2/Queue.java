package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node mRoot;
    private int mSize;

    public Queue() {
        mRoot = null;
        mSize = 0;
    }
    public void enqueue(final int data) {
        mRoot = LinkedList.append(mRoot, data);
        ++mSize;
    }

    public int peek() {
        return mRoot.getData();
    }

    public int dequeue() {
        Node temp = mRoot.getNextOrNull();
        int ret;
        if (temp == null) {
            ret = mRoot.getData();
            mRoot = null;
        } else {
            mRoot.setNext(null);
            mRoot = temp;
            ret = mRoot.getData();
        }
        return ret;
    }

    public int getSize() {
        return mSize;
    }
}
