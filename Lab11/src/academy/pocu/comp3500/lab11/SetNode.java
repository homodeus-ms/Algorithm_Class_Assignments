package academy.pocu.comp3500.lab11;

public class SetNode {
    private String parent;
    private int size;

    public SetNode(final String parent, final int size) {
        this.parent = parent;
        this.size = size;
    }

    public String getParent() {
        return parent;
    }
    public void setParent(String newP) {
        this.parent = newP;
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
}
