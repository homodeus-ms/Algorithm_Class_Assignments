package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private final String[] codewords;
    private final int[] strLengths;
    private final ArrayList<char[]> sortedCodewords;
    private final ArrayList<Node> roots = new ArrayList<>();


    public Decryptor(final String[] codewords) {
        int length = codewords.length;
        strLengths = new int[length];
        this.codewords = new String[length];
        sortedCodewords = new ArrayList<>(length);

        for (int i = 0; i < length; ++i) {
            String str = codewords[i];

            this.codewords[i] = str;
            this.strLengths[i] = str.length();
            char[] newArr = str.toCharArray();
            sortLexicographical(newArr);
            sortedCodewords.add(newArr);
        }

        for (int i = 0; i < this.codewords.length; ++i) {
            String str = this.codewords[i];

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

            char[] charArr = sortedCodewords.get(i);

            for (int j = 0; j < charArr.length; ++j) {
                Node newNode = new Node(charArr[j]);
                start = start.insert(start, newNode);
            }
            start.insertStr(str);
        }
    }
    public String[] findCandidates(final String word) {
        if (this.codewords.length == 0) {
            return new String[]{};
        }
        int wordLength = word.length();
        int specialCharCount = 0;

        HashMap<Character, Integer> map = new HashMap<>(wordLength);

        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            } else {
                c |= 0x20;
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
        }

        ArrayList<String> result = new ArrayList<>(wordLength);
        ArrayList<Node> list = new ArrayList<>(wordLength);

        //Node start = null;

        for (Node n : roots) {
            if (n.getLength() == wordLength) {
                list.addAll(n.getNodes());
                break;
            }
        }

        if (list.isEmpty()) {
            return new String[]{};
        }
        int listSize = list.size();

        for (int i = 0; i < listSize; ++i) {
            Node start = list.get(i);
            findRecursive2(start, map, specialCharCount, result);
        }

        //findRecursive(list, map, list.get(0), specialCharCount, 0, result);

        int resultSize = result.size();
        String[] res = new String[resultSize];
        for (int i = 0; i < resultSize; ++i) {
            res[i] = result.get(i);
        }

        return res;
    }
    private void findRecursive2(Node node, HashMap<Character, Integer> map,
                                int specialCharCount, ArrayList<String> result) {
        if (!node.getWord().isEmpty()) {
            char c = node.getValue();
            if (map.containsKey(c) && map.get(c) > 0) {
                result.addAll(node.getWord());
            } else if (specialCharCount > 0) {
                result.addAll(node.getWord());
            }
            return;
        }

        char key = node.getValue();

        if (map.containsKey(key) && map.get(key) > 0) {
            //int value = map.get(key);
            map.put(key, map.get(key) - 1);
            ArrayList<Node> list = node.getNodes();
            for (Node n : list) {
                findRecursive2(n, map, specialCharCount, result);
                //map.put(key, map.get(key) + 1);
            }
        } else if (specialCharCount > 0) {
            ArrayList<Node> list = node.getNodes();
            key = '?';
            map.put(key, map.get(key) - 1);
            for (Node n : list) {
                findRecursive2(n, map, specialCharCount - 1, result);
            }
        } else {
            return;
        }
        map.put(key, map.get(key) + 1);
    }

    private void findRecursive(ArrayList<Node> list, HashMap<Character, Integer> map, Node n,
                               int specialCharCount, int depth, ArrayList<String> result) {
        if (list.isEmpty()) {
            result.addAll(n.getWord());
            return;
        }
        int listSize = list.size();
        for (int i = 0; i < listSize; ++i) {
            Node node = list.get(i);
            char key = node.getValue();

            if (map.containsKey(key) && map.get(key) > 0) {
                map.put(key, map.get(key) - 1);
                findRecursive(node.getNodes(), map, node, specialCharCount, depth + 1, result);
                map.put(key, map.get(key) + 1);
            } else if (specialCharCount > 0) {
                findRecursive(node.getNodes(), map, node, specialCharCount - 1, depth + 1,
                        result);
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
