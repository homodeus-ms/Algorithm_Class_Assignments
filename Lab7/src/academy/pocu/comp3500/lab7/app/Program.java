package academy.pocu.comp3500.lab7.app;

import academy.pocu.comp3500.lab7.Decryptor;

import java.util.ArrayList;


public class Program {

    public static void main(String[] args) {


        String[] codewords = new String[]{"cat", "cats", "acts", "scan", "acre", "ants"};
        //String[] codewords = new String[]{"aa", "a", "abc", "abcd", "a", "a", "abcdef", "bb", "a"};

        Decryptor decryptor = new Decryptor(codewords);

        String[] candidates = decryptor.findCandidates("cat");

        assert (candidates.length == 1);
        assert (candidates[0].equals("cat"));

        candidates = decryptor.findCandidates("rAce");

        assert (candidates.length == 1);
        assert (candidates[0].equals("acre"));

        candidates = decryptor.findCandidates("ca");

        assert (candidates.length == 0);

        candidates = decryptor.findCandidates("span");

        assert (candidates.length == 0);

        candidates = decryptor.findCandidates("act");

        assert (candidates.length == 1);
        assert (candidates[0].equals("cat"));

        candidates = decryptor.findCandidates("cA?s");

        assert (candidates.length == 3);
        assert (candidates[0].equals("cats") || candidates[0].equals("acts") || candidates[0].equals("scan"));
        assert (candidates[1].equals("cats") || candidates[1].equals("acts") || candidates[1].equals("scan"));

        candidates = decryptor.findCandidates("SCAt");

        assert (candidates.length == 2);
        assert (candidates[0].equals("cats") || candidates[0].equals("acts"));
        assert (candidates[1].equals("cats") || candidates[1].equals("acts"));

        candidates = decryptor.findCandidates("c?s?");

        assert (candidates.length == 3);
        assert (candidates[0].equals("cats") || candidates[0].equals("acts") || candidates[0].equals("scan"));
        assert (candidates[1].equals("cats") || candidates[1].equals("acts") || candidates[1].equals("scan"));

        test1();
        test2();
        test3();
        System.out.println("No Assert!");
    }
    public static void test1() {
        String[] codeWords = new String[] {
                "Hello",
                "WORLD",
                "Java",
                "pRoGrAm",
                "TeSt",
                "cOdE",
                "HELLO",
                "world"
        };
        Decryptor decryptor = new Decryptor(codeWords);
        String[] candidates = decryptor.findCandidates("HELLO");
        assert(candidates.length == 2);
        assert(candidates[0].equals("hello") || candidates[0].equals("hello"));
        assert(candidates[1].equals("hello") || candidates[1].equals("hello"));
        candidates = decryptor.findCandidates("world");
        assert(candidates.length == 2);
        assert(candidates[0].equals("world") || candidates[0].equals("world"));
        assert(candidates[1].equals("world") || candidates[1].equals("world"));
        // Additional test cases
        candidates = decryptor.findCandidates("hElLo");
        assert(candidates.length == 2);
        assert(candidates[0].equals("hello") || candidates[0].equals("hello"));
        assert(candidates[1].equals("hello") || candidates[1].equals("hello"));
        candidates = decryptor.findCandidates("WoRlD");
        assert(candidates.length == 2);
        assert(candidates[0].equals("world") || candidates[0].equals("world"));
        assert(candidates[1].equals("world") || candidates[1].equals("world"));

        System.out.println("Test1 No Assert!");
    }
    public static void test2() {
        String[] codeWords = new String[] {
                "aaabcbcbaaabbbcccc",
                "aaabbbbbcccaaabccc",
                "aaabbbcccaaabbbccc",
                "aaaabbbcccbbbcaacc",
                "aaabbbbccbcaaabccc",
                "aacabbbcccaacabbcb",
                "aaccabbbcbccbaacab",
                "aacaccbcaacbabbcbb",
                "aabcccQWERTccaaaa",
                "aaacbOIUKJHbbabcca",
                "aacabHJKBNMbbcaaab",
                "aaabbJKLIOPaabbbccc",
        };
        Decryptor decryptor = new Decryptor(codeWords);
        String[] candidates = decryptor.findCandidates("aaabbbcccaaabbbccc");
        assert(candidates.length == 8);

        System.out.println("Test2 No Assert!");
    }
    public static void test3() {
        String[] codewords = new String[] {
                "lamb",
                "moon"
        };
        Decryptor decryptor = new Decryptor(codewords);
        String[] candidates = decryptor.findCandidates("??mb");
        assert (candidates.length == 1);
        System.out.println("Test3 No Assert");
    }
}
