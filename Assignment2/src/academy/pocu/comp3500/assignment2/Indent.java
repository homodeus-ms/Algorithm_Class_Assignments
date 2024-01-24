package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

import java.util.Iterator;

public final class Indent {

    private ArrayList<String> strs;
    private Indent parent = null;
    private Indent child = null;
    private int indentCount = 0;

    public Indent() {
        strs = new ArrayList<>();
    }
    public int getIndentCount() {
        return this.indentCount;
    }
    public void setIndentCount(int count) {
        this.indentCount += count;
    }
    public void increaseIndentCount() {
        ++indentCount;
    }
    public void decreaseIndentCount() {
        indentCount = Math.max(0, indentCount - 1);
    }
    public void setParent(final Indent indent) {
        this.parent = indent;
    }
    public Indent getParentOrNull() {
        return this.parent;
    }
    public void setChild(final Indent indent) {
        this.child = indent;
    }
    public Indent getChildOrNull() {
        return this.child;
    }
    public ArrayList<String> getStrsOrNull() {
        return this.strs;
    }
    public void addStr(final String str) {
        strs.add(str);
    }

    public void discard() {

        Indent parent = this.parent;

        if (this.parent != null) {
            this.parent.child = Logger.getCurrentIndent();
            this.parent = null;
        }
        this.strs.clear();
        Indent temp = this.child;
        Indent tempChild;
        this.child = null;

        while (temp != null) {
            temp.strs.clear();
            tempChild = temp.getChildOrNull();
            temp.parent = null;
            temp.child = null;
            temp = tempChild;
        }

        Logger.getCurrentIndent().setParent(parent);

        /*this.strs = null;

        if (this.parent == null && this.child == null) {

        } else if (this.parent != null && this.child != null) {
            this.parent.child = this.child;
            this.child.parent = this.parent;
            this.parent = null;
            this.child = null;
        } else if (this.parent != null) {
            this.parent.child = null;
            this.parent = null;
        } else {

        }


        Logger.deleteIndent(this);*/
    }
}
