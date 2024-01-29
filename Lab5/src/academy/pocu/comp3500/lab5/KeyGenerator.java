package academy.pocu.comp3500.lab5;

import java.math.BigInteger;

public class KeyGenerator {
    public static boolean isPrime(final BigInteger number) {
        if (number.compareTo(BigInteger.valueOf(1)) <= 0) {
            return false;
        }
        if (!number.testBit(0) && number.compareTo(BigInteger.valueOf(2)) != 0) {
            return false;
        }

        BigInteger end = number.sqrt();

        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(end) < 0; i = i.add(BigInteger.ONE)) {
            if (number.mod(i).equals(BigInteger.ZERO)) {
                return false;
            }
        }
        return true;
    }
}
