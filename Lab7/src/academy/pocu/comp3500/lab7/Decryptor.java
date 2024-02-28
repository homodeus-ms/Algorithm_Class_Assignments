package academy.pocu.comp3500.lab7;


import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Random;


public class Decryptor {
    private final String[] codewords;
    //private final ArrayList<char[]> codewordsToLexicographical;
    private final ArrayList<ArrayList<Integer>> eachCharCounts;
    private final int[] strLengths;

    //private Random random = new Random();


    public Decryptor(final String[] codewords) {

        int length = codewords.length;
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
        }
        //sortbyStrLength(this.codewords, strLengths);
    }
    public String[] findCandidates(final String word) {

        if (codewords.length == 0) {
            return new String[]{};
        }

        int[] wordCharCount = new int[27];
        int wordLength = word.length();
        int specialCharCount = 0;

        for (int i = 0; i < wordLength; ++i) {
            int c = word.charAt(i);
            if (c == '?') {
                ++specialCharCount;
                ++wordCharCount[26];
            } else {
                c = (c | 0x20) - 'a';
                ++wordCharCount[c];
            }
        }

        ArrayList<String> result = new ArrayList<>();
        boolean isCodeWord;

        for (int i = 0; i < codewords.length; ++i) {
            if (strLengths[i] != wordLength) {
                continue;
            }
            isCodeWord = true;
            String str = codewords[i];
            ArrayList<Integer> list = eachCharCounts.get(i);
            int listSize = list.size();

            for (int j = 0; j < listSize; ++j) {
                int idx = list.get(j++);
                int count = list.get(j);
                int actualCount = wordCharCount[idx];
                if (actualCount == count) {
                    continue;
                } else if (wordCharCount[26] != 0) {
                    wordCharCount[26] -= (count - actualCount);
                    if (wordCharCount[26] < 0) {
                        isCodeWord = false;
                        break;
                    }
                } else {
                    isCodeWord = false;
                    break;
                }
            }

            if (isCodeWord) {
                result.add(str);
            }
            wordCharCount[26] = specialCharCount;
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
    public ArrayList<Integer> getEachCharCountList(char[] chars) {
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

    /*private void swap(String[] strs, int[] lengths, int i, int j) {
        String tempStr = strs[i];
        int tempInt = lengths[i];
        strs[i] = strs[j];
        lengths[i] = lengths[j];
        strs[j] = tempStr;
        lengths[j] = tempInt;
    }*/


}
