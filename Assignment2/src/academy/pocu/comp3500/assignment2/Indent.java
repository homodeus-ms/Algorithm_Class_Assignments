package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

import java.util.Iterator;

public final class Indent {

    private ArrayList<String> strs;
    private Indent parent = null;
    private Indent child = null;

    public Indent() {
        strs = new ArrayList<>();
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
        this.strs.clear();
        Indent temp = this;
        while (temp.getChildOrNull() != null) {
            temp = temp.getChildOrNull();
            temp.getStrsOrNull().clear();
        }

        //Logger.deleteIndent(this);
    }
}
