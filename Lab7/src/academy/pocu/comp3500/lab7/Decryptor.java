package academy.pocu.comp3500.lab7;


import java.util.ArrayList;

public class Decryptor {
    private final String[] codewords;
    private int[] strLengths;

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

        int[] counts = new int[26];
        int[] keep = new int[26];
        ArrayList<String> result = new ArrayList<>(codewords.length);

        int wordLength = word.length();
        int specialCharCount = 0;

        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
            } else {
                c |= 0x20;
                c -= 'a';
                ++counts[c];
                ++keep[c];
            }
        }


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
                char c = str.charAt(j);
                c |= 0x20;
                c -= 'a';
                if (counts[c] != 0) {
                    --counts[c];
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

            specialCharCount = keepSpecialCharCount;
            copyArr(counts, keep);
        }

        return result.toArray(new String[0]);
    }
    private void copyArr(int[] dst, int[] src) {
        assert (dst.length == src.length);
        for (int i = 0; i < dst.length; ++i) {
            dst[i] = src[i];
        }
    }
}
