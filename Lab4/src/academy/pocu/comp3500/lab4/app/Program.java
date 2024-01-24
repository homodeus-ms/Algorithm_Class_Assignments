package academy.pocu.comp3500.lab4.app;

import academy.pocu.comp3500.lab4.Cracker;
import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Program {

    public static void main(String[] args) {


        /*byte[] bytes = {1, 0, 1, 2};
        String get = getStringFromByteArr(bytes);
        System.out.println(get);*/

        basicTest();

        System.out.println("No Assert");
    }
    public static void basicTest() {
        HashMap<String, String> crc32Map = new HashMap<>(Map.of(
                "211534962", "0000",
                "477404077", "letmein",
                "55151997", "qwerty",
                "901924565", "password"));
        HashMap<String, String> md2Map = new HashMap<>(Map.of(
                "ca244d081350810113cfafa278ffd581", "0000",
                "11f225d2c77a99c2e84b8e70002a9352", "letmein",
                "c2cb085c24f850986e55f1c44abe6876", "qwerty",
                "f03881a88c6e39135f0ecc60efd609b9", "password"));
        HashMap<String, String> md5Map = new HashMap<>(Map.of(
                "4a7d1ed414474e4033ac29ccb8653d9b", "0000",
                "0d107d09f5bbe40cade3de5c71e9e9b7", "letmein",
                "d8578edf8458ce06fbc5bb76a58c5ca4", "qwerty",
                "5f4dcc3b5aa765d61d8327deb882cf99", "password"));
        HashMap<String, String> sha1Map = new HashMap<>(Map.of(
                "39dfa55283318d31afe5a3ff4a0e3253e2045e43", "0000",
                "b7a875fc1ea228b9061041b7cec4bd3c52ab3ce3", "letmein",
                "b1b3773a05c0ed0176787a4f1574ff0075f7521e", "qwerty",
                "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "password"));
        HashMap<String, String> sha256Map = new HashMap<>(Map.of(
                "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0", "0000",
                "1c8bfe8f801d79745c4631d09fff36c82aa37fc4cce4fc946683d7b336b63032", "letmein",
                "65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5", "qwerty",
                "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "password"));

        RainbowTable[] rainbowTables = new RainbowTable[] {
                new RainbowTable(crc32Map),
                new RainbowTable(md2Map),
                new RainbowTable(md5Map),
                new RainbowTable(sha1Map),
                new RainbowTable(sha256Map),
        };

        final String email = "notahacker@not.a.hacker";
        final String password = "notahackerpassword";

        final String normalUser0 = "john.smith@te.st";
        final String normalUser1 = "hong.gil.dong@nor.mal";

        // CRC32
        {
            User[] userTable = new User[]{
                    new User("001", normalUser0, "2418662205"),
                    new User("004", email, "632000577"),
                    new User("011", normalUser1, "477404077")
            };

            Cracker cracker = new Cracker(userTable, email, password);
            String[] plainTexts = cracker.run(rainbowTables);

            assert (plainTexts[0] == null);
            assert (plainTexts[1] == null);
            assert (plainTexts[2] != null && plainTexts[2].equals("letmein"));
        }

        // MD2
        {
            User[] userTable = new User[] {
                    new User("001", normalUser0, "f03881a88c6e39135f0ecc60efd609b9"),
                    new User("005", normalUser1, "ca244d081350810113cfafa278ffd581"),
                    new User("006", email, "507903338904402d4952c5c43cddd070")
            };

            Cracker cracker = new Cracker(userTable, email, password);
            String[] plainTexts = cracker.run(rainbowTables);

            assert(plainTexts[0] != null && plainTexts[0].equals("password"));
            assert(plainTexts[1] != null && plainTexts[1].equals("0000"));
            assert(plainTexts[2] == null);
        }

        // MD5
        {
            User[] userTable = new User[] {
                    new User("010", email, "9501a4e4eb71f74287f797ca036e40ba"),
                    new User("011", normalUser0, "d8578edf8458ce06fbc5bb76a58c5ca4"),
                    new User("012", normalUser1, "a3ae95c21f6dbb5573b9645c98bfa10b")
            };

            Cracker cracker = new Cracker(userTable, email, password);
            String[] plainTexts = cracker.run(rainbowTables);

            assert(plainTexts[0] == null);
            assert(plainTexts[1] != null && plainTexts[1].equals("qwerty"));
            assert(plainTexts[2] == null);
        }

        // SHA1
        {
            User[] userTable = new User[] {
                    new User("001", normalUser1, "39dfa55283318d31afe5a3ff4a0e3253e2045e43"),
                    new User("002", email, "2e172f9ea021d7f4e67b4ad8a86d91dfbf89f1a9"),
                    new User("003", normalUser0, "b5432f98cae2c95fe4d60f30c6f363e4b0f8e362")
            };

            Cracker cracker = new Cracker(userTable, email, password);
            String[] plainTexts = cracker.run(rainbowTables);

            assert(plainTexts[0] != null && plainTexts[0].equals("0000"));
            assert(plainTexts[1] == null);
            assert(plainTexts[2] == null);
        }

        // SHA256
        {
            User[] userTable = new User[] {
                    new User("001", email, "d3c588495ef2196b10a5409796712b365ea579dbabc31ee94613c6892df38480"),
                    new User("002", normalUser1, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"),
                    new User("003", normalUser0, "cf9d164eb9111d3d7d53bd04075a4c4b24b8cfae97add501ae9307b299892c83")
            };

            Cracker cracker = new Cracker(userTable, email, password);
            String[] plainTexts = cracker.run(rainbowTables);

            assert(plainTexts[0] == null);
            assert(plainTexts[1] != null && plainTexts[1].equals("password"));
            assert(plainTexts[2] == null);
        }
    }

    private static String getHash(String str, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));

        for (int i = 0; i < hashBytes.length; ++i) {
            System.out.printf("%02x", hashBytes[i]);
        }
        String res = getStringFromByteArr(hashBytes);
        System.out.println();
        System.out.println(res);

        return getStringFromByteArr(hashBytes);
    }
    private static String getStringFromByteArr(byte[] bytes) {
        StringBuilder builder = new StringBuilder(256);
        boolean isZeroFirst = bytes[0] == 0;

        for (int i = 0; i < bytes.length; ++i) {
            if (!isZeroFirst) {
                builder.append(String.format("%02x", bytes[i]));
            } else {
                isZeroFirst = bytes[i + 1] == 0;
            }
        }
        return builder.toString();
    }
}
