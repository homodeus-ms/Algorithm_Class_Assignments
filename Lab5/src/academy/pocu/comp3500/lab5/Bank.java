package academy.pocu.comp3500.lab5;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class Bank {

    private final HashMap<String, Long> map = new HashMap<>();

    public Bank(byte[][] pubKeys, final long[] amounts) {

        if (pubKeys == null || amounts == null || pubKeys.length != amounts.length) {
            return;
        }

        for (int i = 0; i < pubKeys.length; ++i) {
            String key = encodeToHexString(pubKeys[i]);
            map.put(key, amounts[i] < 0 ? 0 : amounts[i]);
        }
    }
    public long getBalance(final byte[] pubKey) {
        if (pubKey == null) {
            return 0;
        }
        String key = encodeToHexString(pubKey);
        return map.get(key) == null ? 0 : map.get(key);
    }
    public boolean transfer(final byte[] from, byte[] to, final long amount, final byte[] signature) {
        if (from == null || to == null || signature == null) {
            return false;
        }
        String fromStr = encodeToHexString(from);
        String toStr = encodeToHexString(to);
        if (map.get(fromStr) == null || map.get(toStr) == null) {
            return false;
        }
        boolean bResult = false;
        byte[] hash = getSha256Hash(from, to, amount);

        try {
            bResult = tryDecryptWithPubKey(signature, from, hash);

            if (bResult) {

                long fromAmount = getBalance(from);
                long toAmount = getBalance(to);

                if (fromAmount - amount < 0) {
                    return false;
                }

                map.put(fromStr, fromAmount - amount);
                map.put(toStr, toAmount + amount);
            }

        } catch (Exception e) {
            return false;
        }

        return bResult;
    }
    private boolean tryDecryptWithPubKey(final byte[] signature, final byte[] pubKeyBytes, final byte[] hash) throws Exception {
        KeyFactory factory;
        PublicKey pubKey;

        try {
            factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKeyBytes);
            pubKey = factory.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        boolean bResult = false;
        byte[] outBytes;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            outBytes = cipher.doFinal(signature);

            if (outBytes != null) {
                if (isSameArr(outBytes, hash)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static byte[] getSha256Hash(final byte[] from, final byte[] to, long amount) {
        byte[] amountBytes = longToByteArr(amount);

        byte[] hash;

        /*byte[] concatBytes = new byte[from.length + to.length + amountsBytes.length];

        int idx = 0;
        for (int i = 0; i < from.length; ++i) {
            concatBytes[idx++] = from[i];
        }
        for (int i = 0; i < to.length; ++i) {
            concatBytes[idx++] = to[i];
        }
        for (int i = 0; i < amountsBytes.length; ++i) {
            concatBytes[idx++] = amountsBytes[i];
        }*/

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(from);
            md.update(to);
            hash = md.digest(amountBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return hash;
    }
    private static byte[] decodeFromHexString(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            int firstDigit = Character.digit(hexString.charAt(i), 16);
            int secondDigit = Character.digit(hexString.charAt(i + 1), 16);
            bytes[i / 2] = (byte) ((firstDigit << 4) + secondDigit);
        }
        return bytes;
    }

    private static String encodeToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte oneByte : bytes) {
            result.append(String.format("%02x", oneByte));
        }
        return result.toString();
    }
    private static byte[] longToByteArr(long number) {
        final int LONG_BYTE_SIZE = 8;
        byte[] res = new byte[LONG_BYTE_SIZE];

        for (int i = 0; i < LONG_BYTE_SIZE; ++i) {
            byte b = (byte) ((number >> (i * 8)) & 0xFF);
            res[LONG_BYTE_SIZE - i - 1] = b;
        }

        return res;
    }
    private static boolean isSameArr(final byte[] arrA, final byte[] arrB) {
        if (arrA.length != arrB.length) {
            return false;
        }
        for (int i = 0; i < arrA.length; ++i) {
            if (arrA[i] != arrB[i]) {
                return false;
            }
        }
        return true;
    }


}
