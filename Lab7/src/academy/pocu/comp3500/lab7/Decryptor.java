package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private final String[] codewords;
    private final int[] strLengths;
    //private final ArrayList<char[]> sortedCodewords;
    private final ArrayList<Node> roots = new ArrayList<>();


    public Decryptor(final String[] codewords) {
        int length = codewords.length;
        strLengths = new int[length];
        this.codewords = new String[length];
        //sortedCodewords = new ArrayList<>(length);

        for (int i = 0; i < length; ++i) {
            String str = codewords[i];

            this.codewords[i] = str;
            this.strLengths[i] = str.length();
            //char[] newArr = str.toCharArray();
            //sortLexicographical(newArr);
            //sortedCodewords.add(newArr);
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

            //char[] charArr = sortedCodewords.get(i);

            for (int j = 0; j < strLengths[i]; ++j) {
                Node newNode = new Node(str.charAt(j));
                start = start.insert(start, newNode);
            }
            start.setWord(str);
        }
    }
    public String[] findCandidates(final String word) {
        if (this.codewords.length == 0) {
            return new String[]{};
        }
        int wordLength = word.length();
        int specialCharCount = 0;

        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c != '?') {
                c |= 0x20;
            }

            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        ArrayList<String> result = new ArrayList<>(wordLength);
        ArrayList<Node> list = new ArrayList<>(wordLength);


        for (Node n : roots) {
            if (n.getLength() == wordLength) {
                list.addAll(n.getNodes());
                break;
            }
        }

        if (list.isEmpty()) {
            return new String[]{};
        }


        specialCharCount = map.getOrDefault('?', 0);
        searchTrie(list, specialCharCount, map, result);

        int resultSize = result.size();
        String[] res = new String[resultSize];
        for (int i = 0; i < resultSize; ++i) {
            res[i] = result.get(i);
        }

        return res;
    }

    private void searchTrie(ArrayList<Node> list, int specialCharCount,
                                       HashMap<Character, Integer> map, ArrayList<String> result) {
        char c = 0;
        for (Node n : list) {
            c = n.getValue();
            if (map.containsKey(c) && map.get(c) > 0) {
                if (n.getNodes().isEmpty()) {
                    result.add(n.getWord());
                    return;
                }
                map.put(c, map.get(c) - 1);
                searchTrie(n.getNodes(), specialCharCount, map, result);
            } else if (specialCharCount > 0) {
                c = '?';
                if (n.getNodes().isEmpty()) {
                    result.add(n.getWord());
                    continue;
                }
                map.put(c, map.get(c) - 1);
                searchTrie(n.getNodes(), specialCharCount - 1, map, result);
            } else {
                continue;
            }
            map.put(c, map.get(c) + 1);
        }

    }

}
