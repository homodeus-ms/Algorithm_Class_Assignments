package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

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
    public static Indent getCurrentIndent() {
        return currIndent;
    }
    public static void printTo(final BufferedWriter writer) throws IOException {
        int logsSize = logs.getSize();

        for (int i = 0; i < logsSize; ++i) {
            Indent temp = logs.get(i);

            while (temp != null) {
                ArrayList<String> list = temp.getStrsOrNull();
                int listSize = list.getSize();
                int indentCount = temp.getIndentCount();

                for (int j = 0; j < listSize; ++j) {

                    for (int k = 0; k < indentCount; ++k) {
                        writer.write(INDENT);
                    }

                    writer.write(String.format("%s%s", list.get(j), System.lineSeparator()));
                }

                temp = temp.getChildOrNull();
            }
        }

        writer.flush();

    }
    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {

        int logsSize = logs.getSize();

        for (int i = 0; i < logsSize; ++i) {
            Indent temp = logs.get(i);

            Exit:
            while (temp != null) {
                ArrayList<String> list = temp.getStrsOrNull();
                int listSize = list.getSize();
                int indentCount = temp.getIndentCount();

                while (temp != null) {
                    if (!hasFilter(list, filter)) {
                        temp = temp.getChildOrNull();
                        if (temp != null) {
                            list = temp.getStrsOrNull();
                            listSize = list.getSize();
                            indentCount = temp.getIndentCount();
                        }
                    } else {
                        break;
                    }
                }

                for (int j = 0; j < listSize; ++j) {
                    if (!list.get(j).contains(filter)) {
                        continue;
                    }
                    for (int k = 0; k < indentCount; ++k) {
                        writer.write(INDENT);
                    }

                    writer.write(String.format("%s%s", list.get(j), System.lineSeparator()));
                }

                if (temp != null) {
                    temp = temp.getChildOrNull();
                }
            }
        }
        writer.flush();
    }
    public static void clear() {
        logs.clear();
        currIndent = new Indent();
        logs.addLast(currIndent);
    }
    public static Indent indent() {
        Indent newIndent = new Indent();

        while (currIndent.getChildOrNull() != null) {
            currIndent = currIndent.getChildOrNull();
        }
        int parentIndentCount = currIndent.getIndentCount();
        newIndent.setIndentCount(parentIndentCount + 1);
        currIndent.setChild(newIndent);
        newIndent.setParent(currIndent);
        currIndent = newIndent;

        return currIndent;
    }
    public static void unindent() {
        Indent newIndent = new Indent();

        if (currIndent.getIndentCount() > 1) {
            newIndent.setIndentCount(currIndent.getIndentCount() - 1);
            currIndent.setChild(newIndent);
            newIndent.setParent(currIndent);
        } else {
            logs.addLast(newIndent);
        }

        currIndent = newIndent;
    }
    public static boolean hasFilter(ArrayList<String> list, String filter) {
        int size = list.getSize();
        for (int i = 0; i < size; ++i) {
            if (list.get(i).contains(filter)) {
                return true;
            }
        }
        return false;
    }


    /*public static void deleteIndent(Indent indent) {
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
    }*/
    /*private static void writeList(BufferedWriter writer, String filter, ArrayList<String> list,
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
    }*/
}
