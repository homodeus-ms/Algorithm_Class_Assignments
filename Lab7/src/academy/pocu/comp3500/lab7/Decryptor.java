package academy.pocu.comp3500.lab7;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Decryptor {
    private final String[] codewords;
    private final int[] strLengths;
    private final ArrayList<char[]> sortedCodewords;

    private final Node root = new Node();


    public Decryptor(final String[] codewords) {
        int length = codewords.length;
        strLengths = new int[length];
        this.codewords = new String[length];
        sortedCodewords = new ArrayList<>(length);

        for (int i = 0; i < length; ++i) {
            String str = codewords[i];
            str = str.toLowerCase();

            this.codewords[i] = str;
            this.strLengths[i] = str.length();
            char[] newArr = str.toCharArray();
            sortLexicographical(newArr);
            sortedCodewords.add(newArr);
        }

        for (int i = 0; i < this.codewords.length; ++i) {
            String str = codewords[i];
            char[] chars = sortedCodewords.get(i);
            Node start = root;
            for (int j = 0; j < chars.length; ++j) {
                Node newNode = new Node(chars[j]);
                start = root.insert(start, newNode);
            }
            start.insertStr(str.toLowerCase());
        }

        /*int length = codewords.length;
        strLengths = new int[length];
        this.codewords = new String[length];
        //this.codewordsToLexicographical = new ArrayList<>(length);
        eachCharCounts = new ArrayList<>(length);


        for (int i = 0; i < length; ++i) {
            String str = codewords[i];
            str = str.toLowerCase();

            this.codewords[i] = str;
            this.strLengths[i] = str.length();
            char[] newArr = str.toCharArray();
            sortLexicographical(newArr);
            ArrayList<Integer> list = getEachCharCountList(newArr);
            eachCharCounts.add(list);
            //codewordsToLexicographical.add(newArr);
        }*/
    }
    public String[] findCandidates(final String word) {
        if (this.codewords.length == 0) {
            return new String[]{};
        }
        int wordLength = word.length();
        int specialCharCount = 0;
        int keepSpecialCharCount = 0;
        char[] chars = new char[wordLength];

        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
                c = '{';
                chars[i] = c;
            } else {
                chars[i] = (char) (c | 0x20);
            }
        }
        sortLexicographical(chars);

        ArrayList<String> result = new ArrayList<>();
        Node start = root;
        ArrayList<Node> nodes = root.getNodes();
        int listSize = nodes.size();

        findRecursive(nodes, chars, 0, nodes.get(0), 0, specialCharCount, result);



        //findRecursive(chars, 0, start, specialCharCount, result);

        /*for (int i = 0; i < chars.length; ++i) {
            ArrayList<Node> list = start.getNodes();
            int listSize = list.size();
            char wordChar = chars[i];

            for (int j = 0; j < listSize; ++j) {
                Node node = list.get(j);
                if (node.getValue() == wordChar) {
                    start = node;
                    break;
                }
            }
            if (specialCharCount == 0) {
                return new String[]{};
            } else {

            }

        }
        result = start.getWords();
*/

        return result.toArray(new String[0]);

    }
    private void findRecursive(ArrayList<Node> list, char[] chars, int idx, Node n, int depth,
                               int specialCharCount, ArrayList<String> result) {
        if (depth == chars.length) {
            if (list.isEmpty()) {
                result.addAll(n.getWords());
            }
            return;
        }

        int listSize = list.size();
        for (int i = 0; i < listSize; ++i) {
            Node node = list.get(i);
            char c = chars[idx];
            if (node.getValue() == c) {
                findRecursive(node.getNodes(), chars, idx + 1, node, depth + 1, specialCharCount, result);
            } else if (specialCharCount > 0) {
                findRecursive(node.getNodes(), chars, idx, node, depth + 1,
                        specialCharCount - 1, result);
            }
        }
    }

    private void sortLexicographical(char[] chars) {
        sortRecursive(chars, 0, chars.length - 1);
    }
    private void sortRecursive(char[] chars, int left, int right) {
        if (left > right) {
            return;
        }

        int originLeft = left;

        for (int i = left; i < right; ++i) {
            if (chars[i] < chars[right]) {
                swap(chars, i, left);
                ++left;
            }
        }
        swap(chars, left, right);

        sortRecursive(chars, originLeft, left - 1);
        sortRecursive(chars, left + 1, right);

    }
    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
    private ArrayList<Integer> getEachCharCountList(char[] chars) {
        ArrayList<Integer> result = new ArrayList<>(chars.length * 2);
        for (int i = 0; i < chars.length; ++i) {
            int idx = i;

            if (idx == chars.length - 1) {
                result.add(chars[chars.length - 1] - 'a');
                result.add(1);
                break;
            }

            while (chars[idx] == chars[idx + 1]) {
                ++idx;
                if (idx == chars.length - 1) {
                    break;
                }
            }
            result.add((int) chars[i] - 'a');
            result.add(idx - i + 1);
            i = idx;
        }

        return result;
    }

}
