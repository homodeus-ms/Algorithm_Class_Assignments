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
        if (index < 0 || rootOrNull == null) {
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
        if (index < 0 || rootOrNull == null) {
            return rootOrNull;
        }
        if (index == 0) {
            Node temp = rootOrNull.getNextOrNull();
            rootOrNull.setNext(null);
            return temp;
        }
        Node p = rootOrNull;
        Node target;

        for (int i = 0; i < index - 1; ++i) {
            p = p.getNextOrNull();
            if (p == null) {
                return rootOrNull;
            }
        }

        target = p.getNextOrNull();
        if (target == null) {
            return rootOrNull;
        }

        p.setNext(target.getNextOrNull());
        target.setNext(null);

        return rootOrNull;
    }

    public static int getIndexOf(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return -1;
        }

        Node p = rootOrNull;
        int index = 0;

        while (p != null) {
            if (p.getData() == data) {
                return index;
            }
            ++index;
            p = p.getNextOrNull();
        }

        return -1;
    }

    public static Node getOrNull(final Node rootOrNull, final int index) {
        if (index < 0) {
            return null;
        }
        Node p = rootOrNull;
        int count = index;

        while (p != null && count-- != 0) {
            p = p.getNextOrNull();
        }

        return p;
    }

    public static Node reverse(final Node rootOrNull) {
        if (rootOrNull == null) {
            return null;
        } else if (rootOrNull.getNextOrNull() == null) {
            return rootOrNull;
        }

        Node p = rootOrNull;
        Node next;
        Node keep = p.getNextOrNull();

        while (true) {
            next = keep;
            if (next == null) {
                break;
            }
            keep = next.getNextOrNull();

            next.setNext(p);
            p = next;
        }
        rootOrNull.setNext(null);

        return p;
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

        while (start.getNextOrNull() != null) {
            temp = start.getNextOrNull();
            start.setNext(next);
            start = next;
            next = temp;
        }
        start.setNext(next);

        return root0OrNull;
    }
}
