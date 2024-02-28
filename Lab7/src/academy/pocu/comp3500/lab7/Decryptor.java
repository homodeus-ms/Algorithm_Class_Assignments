package academy.pocu.comp3500.lab7;


import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private final String[] codewords;
    private final int[] strLengths;

    //private HashMap<Integer, Integer> minusCount = new HashMap<>();

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
        //minusCount.clear();

        int wordLength = word.length();

        int[] charCountsInWord = new int[27];
        ArrayList<String> result = new ArrayList<>(codewords.length);
        int specialCharCount = 0;


        for (int i = 0; i < wordLength; ++i) {
            int c = word.charAt(i);
            if (c == '?') {
                ++charCountsInWord[26];
                ++specialCharCount;
            } else {
                c |= 0x20;
                c -= 'a';
                ++charCountsInWord[c];
                //minusCount.compute(c, (k, v) -> (v == null) ? 1 : v + 1);
            }
        }


        //int keepSpecialCharCount = specialCharCount;

        boolean found;
        
        for (int i = 0; i < codewords.length; ++i) {

            String str = codewords[i];
            int strLength = strLengths[i];

            if (strLength != wordLength) {
                continue;
            }

            found = true;
            int j = 0;
            for (j = 0; j < strLength; ++j) {
                int c = str.charAt(j) - 'a';
                if (charCountsInWord[c] > 0) {
                    --charCountsInWord[c];

                } else if (charCountsInWord[26] != 0) {
                    --charCountsInWord[c];
                    --charCountsInWord[26];
                } else {
                    found = false;
                    break;
                }
            }

            if (found) {
                result.add(str);
            }
            for (int k = 0; k < j; ++k) {
                int c = str.charAt(k) - 'a';
                ++charCountsInWord[c];
            }
            charCountsInWord[26] = specialCharCount;

            /*for (int n : minusCount.keySet()) {
                charCountsInWord[n] = minusCount.get(n);
            }*/

            //specialCharCount = keepSpecialCharCount;
        }

        return result.toArray(new String[0]);
    }


}
