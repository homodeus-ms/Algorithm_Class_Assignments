package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() {
    }

    public static Node append(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        }
        Node p = rootOrNull;

        while (p.getNextOrNull() != null) {
            p = p.getNextOrNull();
        }
        p.setNext(new Node(data));

        return rootOrNull;
    }

    public static Node prepend(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        }

        Node newNode = new Node(data);
        newNode.setNext(rootOrNull);
        return newNode;
    }

    public static Node insertAt(final Node rootOrNull, final int index, final int data) {

        if (index == 0) {
            Node newNode = new Node(data);
            newNode.setNext(rootOrNull);
            return newNode;
        }
        if (rootOrNull == null || index < 0) {
            return rootOrNull;
        }

        Node p = rootOrNull;

        for (int i = 0; i < index - 1; ++i) {
            p = p.getNextOrNull();
            if (p == null) {
                return rootOrNull;
            }
        }
        Node newNode = new Node(data);
        newNode.setNext(p.getNextOrNull());
        p.setNext(newNode);

        return rootOrNull;
    }

    public static Node removeAt(final Node rootOrNull, final int index) {
        if (rootOrNull == null || index < 0) {
            return rootOrNull;
        }
        if (index == 0) {
            Node temp = rootOrNull.getNextOrNull();
            rootOrNull.setNext(null);
            return temp;
        }

        Node p = rootOrNull;

        for (int i = 0; i < index - 1; ++i) {
            p = p.getNextOrNull();

            if (p == null) {
                return rootOrNull;
            }
        }

        Node target = p.getNextOrNull();
        if (target == null) {
            return rootOrNull;
        }
        p.setNext(target.getNextOrNull());
        target.setNext(null);

        return rootOrNull;
    }

    public static int getIndexOf(final Node rootOrNull, final int data) {

        Node p = rootOrNull;
        int index = -1;

        while (p != null) {
            ++index;
            if (p.getData() == data) {
                return index;
            }
            p = p.getNextOrNull();
        }

        return -1;
    }

    public static Node getOrNull(final Node rootOrNull, final int index) {
        if (rootOrNull == null || index < 0) {
            return null;
        }

        Node p = rootOrNull;

        for (int i = 0; i < index; ++i) {
            p = p.getNextOrNull();
            if (p == null) {
                return null;
            }
        }

        return p;
    }

    public static Node reverse(final Node rootOrNull) {

        Node p = rootOrNull;
        Node next;
        Node prev = null;

        while (p != null) {
            next = p.getNextOrNull();
            p.setNext(prev);
            prev = p;
            p = next;
        }

        return prev;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        if (root0OrNull == null) {
            return root1OrNull;
        }
        if (root1OrNull == null) {
            return root0OrNull;
        }

        Node start = root0OrNull;
        Node next = root1OrNull;
        Node temp;

        while (next != null) {
            temp = start.getNextOrNull();
            start.setNext(next);
            start = next;
            next = temp;
        }

        return root0OrNull;
    }
}

