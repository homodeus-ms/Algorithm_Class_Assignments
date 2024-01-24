package academy.pocu.comp3500.lab4;

import java.util.zip.CRC32;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;
import java.nio.charset.StandardCharsets;

import java.util.Locale;

public final class Cracker {

    private User[] userTable;
    private String email;
    private String password;
    private int myPasswordHashLength;
    //public String[] myPassWordHashs = new String[5];
    /*public String crcValue;
    public String md2Value;
    public String md5Value;
    public String sha1Value;
    public String sha256Value;*/

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;

        for (int i = 0; i < userTable.length; ++i) {
            User get = userTable[i];
            if (get.getEmail().equals(email)) {
                myPasswordHashLength = get.getPasswordHash().length();
                break;
            }
        }

        /*byte[] buffer = password.getBytes(StandardCharsets.UTF_8);
        byte[] res;

        CRC32 crc32 = new CRC32();
        crc32.update(buffer);
        myPassWordHashs[0] = String.valueOf(crc32.getValue());

        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD2");
            res = md.digest(buffer);
            myPassWordHashs[1] = getStringFromByteArr(res);
        } catch (NoSuchAlgorithmException e) {
            assert (false);
        }
        try {
            md = MessageDigest.getInstance("MD5");
            res = md.digest(buffer);
            myPassWordHashs[2] = getStringFromByteArr(res);
        } catch (NoSuchAlgorithmException e) {
            assert (false);
        }
        try {
            md = MessageDigest.getInstance("SHA-1");
            res = md.digest(buffer);
            myPassWordHashs[3] = getStringFromByteArr(res);
        } catch (NoSuchAlgorithmException e) {
            assert (false);
        }
        try {
            md = MessageDigest.getInstance("SHA-256");
            res = md.digest(buffer);
            myPassWordHashs[4] = getStringFromByteArr(res);
        } catch (NoSuchAlgorithmException e) {
            assert (false);
        }*/
    }

    // crc32 : 9자리 이하 문자열?
    // md2, md5 : 128비트 16진수 문자열
    // sha1 : 160비트 문자열
    // shar256 : 64 * 4 = 256비트 문자열
    public String[] run(final RainbowTable[] rainbowTables) {

        assert (rainbowTables.length == 5) : "Input Length is always 5";

        String[] result = new String[userTable.length];

        Exit:
        for (int i = 0; i < userTable.length; ++i) {
            String hashGet = userTable[i].getPasswordHash();
            String valueOrNull = null;

            if (myPasswordHashLength == 64) {
                valueOrNull = rainbowTables[4].get(hashGet);
            } else if (myPasswordHashLength == 40) {
                valueOrNull = rainbowTables[3].get(hashGet);
            } else if (myPasswordHashLength == 32) {
                valueOrNull = rainbowTables[2].get(hashGet);
                if (valueOrNull == null) {
                    valueOrNull = rainbowTables[1].get(hashGet);
                }
            } else if (myPasswordHashLength <= 16){
                valueOrNull = rainbowTables[0].get(hashGet);
            }

            result[i] = valueOrNull;
        }


        return result;
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
