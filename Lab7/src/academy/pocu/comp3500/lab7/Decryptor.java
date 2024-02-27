package academy.pocu.comp3500.lab7;


import java.util.ArrayList;

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

        int[] wordToLowerCase = new int[wordLength];
        int[] wordCharCount = new int[wordLength];
        //int wordToLowerCaseLength = 0;

        int[] counts = new int[26];
        //int[] keep = new int[26];
        ArrayList<String> result = new ArrayList<>(codewords.length);
        int specialCharCount = 0;

        for (int i = 0; i < wordLength; ++i) {
            int c = (word.charAt(i) | 0x20) - 'a';
            // ('?' | 0x20) - 'a' = -34
            if (c == -34) {
                ++specialCharCount;
                wordToLowerCase[i] = -1;
                wordCharCount[i] = -1;
                continue;
            }
            wordToLowerCase[i] = c;
            ++wordCharCount[i];
            ++counts[c];
            //++wordToLowerCaseLength;
        }

        /*for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
            } else {
                c |= 0x20;
                c -= 'a';
                ++counts[c];
                ++keep[c];
            }
        }*/


        int keepSpecialCharCount = specialCharCount;

        boolean found;
        
        for (int i = 0; i < codewords.length; ++i) {

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
                } else if (specialCharCount != 0) {
                    --specialCharCount;
                } else {
                    found = false;
                    //break;
                }
            }

            if (found) {
                result.add(str);
            }

            for (int j = 0; j < wordToLowerCase.length; ++j) {
                int idx = wordToLowerCase[j];
                if (idx != -1) {
                    counts[idx] += wordCharCount[j];
                }
            }

            specialCharCount = keepSpecialCharCount;
            //copyArr(counts, keep);
        }

        return result.toArray(new String[0]);
    }
    /*private void copyArr(int[] dst, int[] src) {
        assert (dst.length == src.length);
        for (int i = 0; i < dst.length; ++i) {
            dst[i] = src[i];
        }
    }*/
}
