package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() {
    }

    public static Node append(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        } else {
            Node p = rootOrNull;

            while (p.getNextOrNull() != null) {
                p = p.getNextOrNull();
            }
            p.setNext(new Node(data));

            return rootOrNull;
        }
    }

    public static Node prepend(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        } else {
            Node newNode = new Node(data);
            newNode.setNext(rootOrNull);
            return newNode;
        }
    }

    public static Node insertAt(final Node rootOrNull, final int index, final int data) {
        if (rootOrNull == null) {
            if (index == 0) {
                return new Node(data);
            } else {
                return null;
            }

        } else if (index == 0) {
            Node newNode = new Node(data);
            newNode.setNext(rootOrNull);
            return newNode;

        } else if (index < 0) {
            return rootOrNull;

        } else {
            Node p = rootOrNull;

            for (int i = 0; i < index - 1; ++i) {
                p = p.getNextOrNull();
                if (p == null) {
                    return rootOrNull;
                }
            }
            Node preNode = p;
            p = preNode.getNextOrNull();
            if (p == null) {
                preNode.setNext(new Node(data));
                return rootOrNull;
            }

            Node newNode = new Node(data);
            preNode.setNext(newNode);
            newNode.setNext(p);

            return rootOrNull;
        }
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
        Node preNode;
        for (int i = 0; i < index - 1; ++i) {
            p = p.getNextOrNull();
            if (p == null) {
                return rootOrNull;
            }
        }

        preNode = p;
        p = preNode.getNextOrNull();
        if (p == null) {
            return rootOrNull;
        }
        preNode.setNext(p.getNextOrNull());

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

        if (rootOrNull == null || rootOrNull.getNextOrNull() == null) {
            return rootOrNull;
        }

        Node p = rootOrNull.getNextOrNull();
        Node prev = rootOrNull;
        prev.setNext(null);
        Node next;

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

        Node p = root0OrNull;
        Node q = root1OrNull;
        Node keep0;
        Node keep1;

        while (p != null && q != null) {
            keep0 = p.getNextOrNull();
            keep1 = q.getNextOrNull();

            p.setNext(q);

            if (keep0 != null) {
                q.setNext(keep0);
            }

            p = keep0;
            q = keep1;
        }

        return root0OrNull;
    }

    private static Node reverseRecursive(final Node p, final Node pNext) {
        if (pNext.getNextOrNull() == null) {
            pNext.setNext(p);
            return pNext;
        }

        Node newRoot = reverseRecursive(pNext, pNext.getNextOrNull());
        pNext.setNext(p);
        return newRoot;
    }
}

