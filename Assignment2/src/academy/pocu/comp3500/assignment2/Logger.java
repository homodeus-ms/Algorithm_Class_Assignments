package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.LinkedList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;


import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static final String INDENT = "  ";

    private static LinkedList<Indent> logs = new LinkedList<>();
    private static Indent currIndent = new Indent();

    static {
        logs.addLast(currIndent);
    }

    public static void log(final String text) {
        currIndent.addStr(text);
    }
    public static void printTo(final BufferedWriter writer) throws IOException {

        int idx = 0;
        int indentCount = 0;
        currIndent = logs.get(idx);

        Exit:
        while (idx < logs.getSize()) {
            ArrayList<String> list = currIndent.getStrsOrNull();
            int listSize = list.getSize();

            for (int j = 0; j < listSize; ++j) {
                for (int k = 0; k < indentCount; ++k) {
                    writer.write(INDENT);
                }
                writer.write(String.format("%s%s", list.get(j), System.lineSeparator()));
            }

            if (currIndent.getChildOrNull() != null) {
                currIndent = currIndent.getChildOrNull();
                ++indentCount;
            } else {
                ++idx;
                indentCount = Math.max(0, indentCount - 1);
                if (idx >= logs.getSize()) {
                    break Exit;
                }
                currIndent = logs.get(idx);
            }
        }

        writer.flush();
    }
    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        int idx = 0;
        int indentCount = 0;
        currIndent = logs.get(idx++);
        //getFilteredStringRecursive(writer, filter, currIndent, idx, indentCount);

        while (idx <= logs.getSize() - 1) {
            ArrayList<String> currList = currIndent.getStrsOrNull();
            int listSize = currList.getSize();
            boolean hasFilter = false;

            for (int i = 0; i < listSize; ++i) {
                if (currList.get(i).contains(filter)) {
                    hasFilter = true;
                    break;
                }
            }

            if (!hasFilter) {

                currIndent = logs.get(idx++);
                continue;
            } else {
                writeList(writer, filter, currList, listSize, indentCount);
            }

            if (currIndent.getChildOrNull() != null) {
                ++indentCount;
                currIndent = currIndent.getChildOrNull();
                continue;
            } else {
                --indentCount;
                currIndent = logs.get(idx++);
            }
        }

        writer.flush();
    }
    public static void clear() {
        for (int i = 0; i < logs.getSize(); ++i) {
            Indent temp = logs.get(i);
            Indent next;

            while (temp.getChildOrNull() != null) {
                next = temp.getChildOrNull();
                temp.setParent(null);
                temp.setChild(null);
                temp = next;
            }
            temp.setParent(null);
        }
        logs.clear();
        currIndent = new Indent();
        logs.addLast(currIndent);
    }
    public static Indent indent() {
        Indent newIndent = new Indent();

        while (currIndent.getChildOrNull() != null) {
            currIndent = currIndent.getChildOrNull();
        }
        currIndent.setChild(newIndent);
        newIndent.setParent(currIndent);
        currIndent = newIndent;

        return currIndent;
    }
    public static void unindent() {
        Indent newIndent = new Indent();
        logs.addLast(newIndent);
        currIndent = newIndent;
    }
    public static void deleteIndent(Indent indent) {
        logs.remove(indent);
    }

    private static void getFilteredStringRecursive(BufferedWriter writer,
                                                   String filter, Indent currIndent,
                                                   int idx, int indentCount) throws IOException {

        if (idx == logs.getSize() - 1) {
            return;
        }

        ArrayList<String> currList = currIndent.getStrsOrNull();
        boolean hasFilter = false;
        int listSize = currList.getSize();

        for (int i = 0; i < listSize; ++i) {
            if (currList.get(i).contains(filter)) {
                hasFilter = true;
                break;
            }
        }

        if (!hasFilter) {
            return;
        } else {
            writeList(writer, filter, currList, currList.getSize(), indentCount);
        }

        if (currIndent.getChildOrNull() != null) {
            currIndent = currIndent.getChildOrNull();
            getFilteredStringRecursive(writer, filter, currIndent, idx, indentCount + 1);
        } else {
            --indentCount;
        }

        currIndent = logs.get(idx + 1);
        getFilteredStringRecursive(writer, filter, currIndent, idx + 1, indentCount + 1);
    }
    private static void writeList(BufferedWriter writer, String filter, ArrayList<String> list,
                                  int listSize, int indentCount) throws IOException {
        for (int i = 0; i < listSize; ++i) {
            if (!list.get(i).contains(filter)) {
                continue;
            }
            for (int j = 0; j < indentCount; ++j) {
                writer.write(INDENT);
            }
            writer.write(String.format("%s%s", list.get(i), System.lineSeparator()));
        }
    }
}
