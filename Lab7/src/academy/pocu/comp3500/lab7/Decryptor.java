package academy.pocu.comp3500.lab7;


public class Decryptor {
    private final String[] codewords;

    public Decryptor(final String[] codewords) {
        int length = codewords.length;
        this.codewords = new String[length];
        for (int i = 0; i < length; ++i) {
            String str = codewords[i];
            str = str.toLowerCase();
            this.codewords[i] = str;
        }
        //this.codewords = codewords;
    }
    public String[] findCandidates(final String word) {

        if (codewords.length == 0) {
            return new String[]{};
        }

        int[] counts = new int[27];
        byte[] indexes = new byte[codewords.length];

        int wordLength = word.length();
        int specialCharCount = 0;

        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++counts[26];
                ++specialCharCount;
            } else {
                c |= 0x20;
                c -= 'a';
                ++counts[c];
            }
        }

        int[] keep = new int[27];
        int keepSpecialCharCount = specialCharCount;
        copyArr(keep, counts);
        boolean found = true;
        int foundCount = 0;


        for (int i = 0; i < codewords.length; ++i) {
            if (codewords[i].length() != wordLength) {
                continue;
            }

            found = true;

            String str = codewords[i];

            int strLength = str.length();

            for (int j = 0; j < strLength; ++j) {
                char c = str.charAt(j);
                c |= 0x20;
                c -= 'a';
                if (counts[c] != 0) {
                    --counts[c];
                } else if (specialCharCount != 0 && counts[26] != 0) {
                    --counts[26];
                } else {
                    found = false;
                    break;
                }
            }

            if (found) {
                indexes[foundCount++] = (byte) i;
            }
            specialCharCount = keepSpecialCharCount;

            copyArr(counts, keep);
        }

        String[] result = new String[foundCount];

        for (int i = 0; i < foundCount; ++i) {
            result[i] = codewords[indexes[i]];
        }


        return result;
    }
    private void copyArr(int[] dst, int[] src) {
        assert (dst.length == src.length);
        for (int i = 0; i < dst.length; ++i) {
            dst[i] = src[i];
        }
    }
}
