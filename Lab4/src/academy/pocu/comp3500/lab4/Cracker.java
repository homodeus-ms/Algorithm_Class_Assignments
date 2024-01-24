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
    private String myHashFromUserTable = "";
    private int targetIdx = -1;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;

        String userEmail;

        for (int i = 0; i < userTable.length; ++i) {
            userEmail = userTable[i].getEmail();
            if (userEmail.hashCode() == email.hashCode()) {
                myHashFromUserTable = userTable[i].getPasswordHash();
                break;
            }
        }
        //myHashFromUserTable = myHashFromUserTable.replaceFirst("^0+", "");

        CRC32 crc32 = new CRC32();
        crc32.update(password.getBytes(StandardCharsets.UTF_8));
        String getHash = String.valueOf(crc32.getValue());

        if (myHashFromUserTable.equals(getHash)) {
            targetIdx = 0;
            return;
        }

        try {
            getHash = getHash(password, "MD2");
            if (myHashFromUserTable.equals(getHash)) {
                targetIdx = 1;
                return;
            }
            getHash = getHash(password, "MD5");
            if (myHashFromUserTable.equals(getHash)) {
                targetIdx = 2;
                return;
            }
            getHash = getHash(password, "SHA-1");
            if (myHashFromUserTable.equals(getHash)) {
                targetIdx = 3;
                return;
            }
            getHash = getHash(password, "SHA-256");
            if (myHashFromUserTable.equals(getHash)) {
                targetIdx = 4;
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            assert (false);
        }


    }

    public String[] run(final RainbowTable[] rainbowTables) {

        assert (rainbowTables.length == 5) : "Input Length is always 5";

        String[] result = new String[userTable.length];

        if (targetIdx != -1) {
            for (int i = 0; i < result.length; ++i) {
                result[i] = rainbowTables[targetIdx].get(userTable[i].getPasswordHash());
            }
        }

        return result;
    }
    private String getHash(String str, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
        return getStringFromByteArr(hashBytes);
    }
    private String getStringFromByteArr(byte[] bytes) {
        StringBuilder builder = new StringBuilder(256);
        for (int i = 0; i < bytes.length; ++i) {
            builder.append(String.format("%02x", bytes[i]));
        }
        return builder.toString();
    }

}
