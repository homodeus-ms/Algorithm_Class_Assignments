package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {

    private Node mRoot;
    private int mSize;

    public Stack() {
        mRoot = null;
        mSize = 0;
    }

    public void push(final int data) {
        mRoot = LinkedList.append(mRoot, data);
        ++mSize;
    }

    public int peek() {
        return LinkedList.getIndexOf(mRoot, mSize - 1);
    }

    public int pop() {
        int ret;

        if (mSize == 1) {
            ret = mRoot.getData();
            mRoot = null;
            return ret;
        } else {
            Node preLastNode = LinkedList.getOrNull(mRoot, mSize - 2);
            ret = preLastNode.getNextOrNull().getData();
            preLastNode.setNext(null);
        }
        --mSize;
        return ret;
    }

    public int getSize() {
        return mSize;
    }
}
