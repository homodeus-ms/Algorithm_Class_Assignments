package academy.pocu.comp3500.lab7;


import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private final String[] codewords;
    private final int[] strLengths;
    private final ArrayList<char[]> sortedCodewords;

    private final Node root = new Node();
    private final ArrayList<Node> roots = new ArrayList<>();


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

            int idx = 0;
            Node start = null;

            for (Node n : roots) {
                if (n.getLength() == strLengths[i]) {
                    start = n;
                    break;
                }
            }
            if (start == null) {
                start = new Node();
                start.setLength(strLengths[i]);
                roots.add(start);
            }

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

        HashMap<Character, Integer> map = new HashMap<>();

        //int[] charCounts = new int[26];
        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
            } else {
                c |= 0x20;
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
        }

        ArrayList<String> result = new ArrayList<>();
        ArrayList<Node> list = new ArrayList<>();
        for (Node n : roots) {
            if (n.getLength() == wordLength) {
                list.addAll(n.getNodes());
                break;
            }
        }

        if (list.isEmpty()) {
            return new String[]{};
        }

        findRecursive2(list, map, list.get(0), specialCharCount, 0, result);
        //findRecursive(list, chars, 0, list.get(0), 0, specialCharCount, result);

        //findStr(list, chars, 0,0, specialCharCount, result);

        /*Stack<Node> stack = new Stack<>();
        Stack<Integer> specialCounts = new Stack<>();
        Stack<Integer> depths = new Stack<>();
        Stack<Integer> indexes = new Stack<>();
        int listSize = list.size();

        for (int i = 0; i < listSize; ++i) {
            Node n = list.get(i);
            if (n.getValue() == chars[idx]) {
                stack.push(list.get(i));
                specialCounts.push(specialCharCount);
                depths.push(startDepth);
                indexes.push(idx);
            } else if (specialCharCount > 0) {
                ArrayList<Node> temp = n.getNodes();

                if (1 == chars.length) {
                    if (!n.getWords().isEmpty()) {
                        result.addAll(n.getWords());
                    }
                    continue;
                }

                for (Node tempNode : temp) {
                    stack.push(tempNode);
                    specialCounts.push(specialCharCount - 1);
                    depths.push(startDepth + 1);
                    indexes.push(idx);
                }
            }
        }

        int index = 0;

        while (!stack.isEmpty() && index < chars.length) {
            Node node = stack.pop();
            int specialCount = specialCounts.pop();
            int depth = depths.pop();
            index = indexes.pop();
            char c = chars[index];


            if (node.getValue() == c) {
                ArrayList<Node> children = node.getNodes();
                int size = children.size();

                if (depth == chars.length - 1) {
                    if (!node.getWords().isEmpty()) {
                        result.addAll(node.getWords());
                    }
                    continue;
                }

                for (int i = 0; i < size; ++i) {
                    stack.push(children.get(i));
                    depths.push(depth + 1);
                    specialCounts.push(specialCount);
                    indexes.push(index + 1);
                }
            } else if (specialCount > 0) {
                --specialCount;
                ArrayList<Node> children = node.getNodes();
                int size = children.size();

                if (depth == chars.length - 1) {
                    if (!node.getWords().isEmpty()) {
                        result.addAll(node.getWords());
                    }
                    continue;
                }

                for (int i = 0; i < size; ++i) {
                    if (chars[index] == children.get(i).getValue() || specialCount > 0) {
                        stack.push(children.get(i));
                        depths.push(depth + 1);
                        specialCounts.push(specialCount);
                        indexes.push(index);
                    }
                }
            }
        }*/

        return result.toArray(new String[0]);

        //findRecursive(list, chars, 0, nodes.get(0), 0, specialCharCount, result);



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

    }
    /*private void findStr(ArrayList<Node> list, char[] chars, int idx, int startDepth,
                         int specialCharCount, ArrayList<String> result) {
        Stack<Node> stack = new Stack<>();
        Stack<Integer> specialCounts = new Stack<>();
        Stack<Integer> depths = new Stack<>();
        Stack<Integer> indexes = new Stack<>();
        int listSize = list.size();

        for (int i = 0; i < listSize; ++i) {
            stack.push(list.get(i));
            specialCounts.push(specialCharCount);
            depths.push(startDepth);
            indexes.push(idx);
        }

        int index = 0;

        while (!stack.isEmpty() && index < chars.length) {
            Node node = stack.pop();
            int specialCount = specialCounts.pop();
            int depth = depths.pop();
            index = indexes.pop();
            char c = chars[index];


            if (node.getValue() == c) {
                ArrayList<Node> children = node.getNodes();
                int size = children.size();

                if (depth == chars.length - 1) {
                    if (!node.getWords().isEmpty()) {
                        result.addAll(node.getWords());
                    }
                    continue;
                }

                for (int i = 0; i < size; ++i) {
                    stack.push(children.get(i));
                    depths.push(depth + 1);
                    specialCounts.push(specialCount);
                    indexes.push(index + 1);
                }
            } else if (specialCount > 0) {
                --specialCount;
                ArrayList<Node> children = node.getNodes();
                int size = children.size();

                if (depth == chars.length - 1) {
                    if (!node.getWords().isEmpty()) {
                        result.addAll(node.getWords());
                    }
                    continue;
                }

                for (int i = 0; i < size; ++i) {
                    stack.push(children.get(i));
                    depths.push(depth + 1);
                    specialCounts.push(specialCount);
                    indexes.push(index);
                }
            }
        }
    }*/

    private void findRecursive2(ArrayList<Node> list, HashMap<Character, Integer> map, Node n,
                                int specialCharCount, int depth, ArrayList<String> result) {
        if (list.isEmpty()) {
            if (!n.getWords().isEmpty()) {
                result.addAll(n.getWords());
            }
            return;
        }
        int listSize = list.size();
        for (int i = 0; i < listSize; ++i) {
            Node node = list.get(i);
            char key = node.getValue();

            if (map.containsKey(key)) {
                map.put(key, map.get(key) - 1);
                findRecursive2(node.getNodes(), map, node, specialCharCount, depth + 1, result);
                map.put(key, map.get(key) + 1);
            } else if (specialCharCount > 0) {
                findRecursive2(node.getNodes(), map, node, specialCharCount - 1, depth + 1,
                        result);
            }
        }

    }

    private void findRecursive(ArrayList<Node> list, char[] chars, int idx, Node n, int depth,
                               int specialCharCount, ArrayList<String> result) {
        if (depth == chars.length) {
            if (!n.getWords().isEmpty()) {
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
