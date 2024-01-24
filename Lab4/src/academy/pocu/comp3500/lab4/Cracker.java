package academy.pocu.comp3500.lab4;

import java.util.zip.CRC32;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;
import java.nio.charset.StandardCharsets;

import java.util.Locale;

public final class Cracker {
    private final int RAINBOW_TABLE_COUNT = 5;
    private User[] userTable;
    private String email;
    private String password;
    private HashFuncs usedHashFunc = HashFuncs.NONE;
    private String myHashFromUserTable = "";

    /*//public String[] myPassWordHashs = new String[5];
    public String crcValue;
    public String md2Value;
    public String md5Value;
    public String sha1Value;
    public String sha256Value;*/

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;

        String userEmail;
        String[] result = new String[userTable.length];

        for (int i = 0; i < userTable.length; ++i) {
            userEmail = userTable[i].getEmail();
            if (userEmail.hashCode() == email.hashCode()) {
                myHashFromUserTable = userTable[i].getPasswordHash();
                break;
            }
        }
        myHashFromUserTable = myHashFromUserTable.replaceFirst("^0+", "");
    }
    // crc32 : 9자리 이하 문자열?
    // md2, md5 : 128비트 16진수 문자열
    // sha1 : 160비트 문자열
    // shar256 : 64 * 4 = 256비트 문자열
    public String[] run(final RainbowTable[] rainbowTables) {

        assert (rainbowTables.length == 5) : "Input Length is always 5";

        String[] result = new String[userTable.length];
        int targetIdx = -1;
        for (int i = 0; i < RAINBOW_TABLE_COUNT; ++i) {
            if (rainbowTables[i].contains(myHashFromUserTable)) {
                targetIdx = i;
                break;
            }
        }
        if (targetIdx != -1) {
            for (int i = 0; i < result.length; ++i) {
                result[i] = rainbowTables[targetIdx].get(userTable[i].getPasswordHash());
            }
        }

        return result;
    }
    private boolean hasStringSameBit(String a, String b) {
        int aLength = a.length();
        int bLength = b.length();

        if (aLength == bLength) {
            return a.equals(b);
        }

        if (bLength > aLength) {
            String temp = a;
            a = b;
            b = temp;

            aLength ^= bLength;
            bLength ^= aLength;
            aLength ^= bLength;
        }
        int lengthDiff = aLength - bLength;

        for (int i = 0; i < bLength; ++i) {
            if (a.charAt(i + lengthDiff) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }


    private boolean hashHashKey(final RainbowTable[] rainbowTable, int index, String key) {
        if (rainbowTable[index].contains(key)) {
            return true;
        }
        return false;
    }
    private String getStringFromByteArr(byte[] bytes) {
        StringBuilder builder = new StringBuilder(256);
        for (int i = 0; i < bytes.length; ++i) {
            builder.append(String.format("%02x", bytes[i]));
        }
        return builder.toString();
    }

}
