package academy.pocu.comp3500.lab7;


import java.util.ArrayList;
import java.util.Random;


public class Decryptor {
    private final String[] codewords;
    private final int[] strLengths;
    private Random random = new Random();


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
        sortbyStrLength(this.codewords, strLengths);
    }
    public String[] findCandidates(final String word) {

        if (codewords.length == 0) {
            return new String[]{};
        }

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
            }
        }

        boolean found;
        
        for (int i = 0; i < codewords.length; ++i) {

            String str = codewords[i];
            int strLength = strLengths[i];

            if (strLength > wordLength) {
                break;
            }

            if (strLength != wordLength) {
                continue;
            }

            found = true;
            int j;
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

        }

        return result.toArray(new String[0]);
    }

    private void sortbyStrLength(String[] strs, int[] lengths) {
        quickSortRecursive(strs, lengths, 0, lengths.length - 1);
    }
    private void quickSortRecursive(String[] strs, int[] lengths, int left, int right) {
        if (left > right) {
            return;
        }

        //int pivot = random.nextInt(right - left) + left;
        //swap(strs, lengths, pivot, right);
        int originLeft = left;

        for (int i = left; i < right; ++i) {
            if (lengths[i] < lengths[right]) {
                swap(strs, lengths, i, left);
                ++left;
            }
        }
        swap(strs, lengths, left, right);

        quickSortRecursive(strs, lengths, originLeft, left - 1);
        quickSortRecursive(strs, lengths, left + 1, right);

    }
    private void swap(String[] strs, int[] lengths, int i, int j) {
        String tempStr = strs[i];
        int tempInt = lengths[i];
        strs[i] = strs[j];
        lengths[i] = lengths[j];
        strs[j] = tempStr;
        lengths[j] = tempInt;
    }


}
