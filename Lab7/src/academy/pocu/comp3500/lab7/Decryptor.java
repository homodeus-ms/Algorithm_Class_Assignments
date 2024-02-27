package academy.pocu.comp3500.lab7;


import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private final String[] codewords;
    private final int[] strLengths;

    public Decryptor(final String[] codewords) {
        int length = codewords.length;
        strLengths = new int[length];

        this.codewords = new String[length];
        for (int i = 0; i < length; ++i) {
            String str = codewords[i];
            str = str.toLowerCase();
            this.codewords[i] = str;
            this.strLengths[i] = str.length();
        }
    }
    public String[] findCandidates(final String word) {

        if (codewords.length == 0) {
            return new String[]{};
        }

        int wordLength = word.length();

        HashMap<Integer, Integer> minusCount = new HashMap<>();

        int[] counts = new int[26];
        ArrayList<String> result = new ArrayList<>(codewords.length);
        int specialCharCount = 0;


        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
            } else {
                c |= 0x20;
                c -= 'a';
                ++counts[c];
            }
        }


        int keepSpecialCharCount = specialCharCount;

        boolean found;
        
        for (int i = 0; i < codewords.length; ++i) {

            minusCount.clear();

            String str = codewords[i];
            int strLength = strLengths[i];

            if (strLength != wordLength) {
                continue;
            }

            found = true;

            for (int j = 0; j < strLength; ++j) {
                int c = str.charAt(j) - 'a';
                if (counts[c] != 0) {
                    --counts[c];
                    minusCount.compute(c, (k, v) -> (v == null) ? 1 : v + 1);
                    /*if (minusCount.containsKey(c)) {
                        int get = minusCount.get(c) + 1;
                        minusCount.put(c, get);
                    } else {
                        minusCount.put(c, 1);
                    }*/

                } else if (specialCharCount != 0) {
                    --specialCharCount;
                } else {
                    found = false;
                    break;
                }
            }

            if (found) {
                result.add(str);
            }

            for (int n : minusCount.keySet()) {
                counts[n] += minusCount.get(n);
            }

            specialCharCount = keepSpecialCharCount;
        }

        return result.toArray(new String[0]);
    }

}
