package academy.pocu.comp3500.lab7;


import java.util.ArrayList;
import java.util.Random;


public class Decryptor {
    private final String[] codewords;
    private final ArrayList<char[]> codewordsToLexicographical;
    private final int[] strLengths;
    private Random random = new Random();


    public Decryptor(final String[] codewords) {

        int length = codewords.length;
        strLengths = new int[length];
        this.codewords = new String[length];
        this.codewordsToLexicographical = new ArrayList<>(length);

        for (int i = 0; i < length; ++i) {
            String str = codewords[i];
            str = str.toLowerCase();

            this.codewords[i] = str;
            this.strLengths[i] = str.length();
            char[] newArr = str.toCharArray();
            sortLexicographical(newArr);
            codewordsToLexicographical.add(newArr);
        }
        //sortbyStrLength(this.codewords, strLengths);
    }
    public String[] findCandidates(final String word) {

        if (codewords.length == 0) {
            return new String[]{};
        }

        int wordLength = word.length();
        ArrayList<String> result = new ArrayList<>(codewords.length);

        ArrayList<Character> charList = new ArrayList<>(wordLength);
        int specialCharCount = 0;
        for (int i = 0; i < wordLength; ++i) {
            char c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
                continue;
            } else {
                c |= 0x20;
                charList.add(c);
            }
        }
        int keepSpecialCharCount = specialCharCount;

        int listSize = charList.size();
        char[] wordToCharArr = new char[listSize];
        for (int i = 0; i < listSize; ++i) {
            wordToCharArr[i] = charList.get(i);
        }


        sortLexicographical(wordToCharArr);

        boolean isRightCode;

        for (int i = 0; i < codewords.length; ++i) {

            if (strLengths[i] != wordLength) {
                continue;
            }

            specialCharCount = keepSpecialCharCount;

            isRightCode = true;

            int wordIdx = 0;
            char[] code = codewordsToLexicographical.get(i);

            for (int j = 0; j < strLengths[i]; ++j) {
                char strC = code[j];
                char wordC;
                if (wordIdx == wordToCharArr.length) {
                    continue;
                } else {
                    wordC = wordToCharArr[wordIdx];
                }

                if (strC != wordC) {
                    if (specialCharCount != 0) {
                        --specialCharCount;
                    } else {
                        isRightCode = false;
                        break;
                    }
                } else {
                    ++wordIdx;
                }
            }

            if (isRightCode) {
                result.add(codewords[i]);
            }
        }

        return result.toArray(new String[0]);


        /*int[] charCountsInWord = new int[27];
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

        return result.toArray(new String[0]);*/
    }

    private void sortLexicographical(char[] chars) {
        quickSortRecursive(chars, 0, chars.length - 1);
    }
    private void quickSortRecursive(char[] chars, int left, int right) {
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

        quickSortRecursive(chars, originLeft, left - 1);
        quickSortRecursive(chars, left + 1, right);

    }
    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    /*private void swap(String[] strs, int[] lengths, int i, int j) {
        String tempStr = strs[i];
        int tempInt = lengths[i];
        strs[i] = strs[j];
        lengths[i] = lengths[j];
        strs[j] = tempStr;
        lengths[j] = tempInt;
    }*/


}
