package academy.pocu.comp3500.lab5;

import java.math.BigInteger;

public class KeyGenerator {
    public static boolean isPrime(final BigInteger number) {
        if (number.compareTo(BigInteger.TWO) < 0) {
            return false;
        }
        if (number.compareTo(BigInteger.TWO) == 0) {
            return true;
        }
        if (!number.testBit(0)) {
            return false;
        }

        //밀러-라빈 소수 판별
        final int[] PRIME_NUMS = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        final int PRIME_NUMS_LENGTH = 15;
        for (int i = 0; i < PRIME_NUMS_LENGTH; ++i) {
            if (number.compareTo(BigInteger.valueOf(PRIME_NUMS[i])) == 0) {
                return true;
            }
        }
        if (number.compareTo(BigInteger.valueOf(PRIME_NUMS[PRIME_NUMS_LENGTH - 1])) < 0) {
            return false;
        }

        BigInteger minusOne = number.subtract(BigInteger.ONE);
        BigInteger d = number.subtract(BigInteger.ONE);
        BigInteger pNum;

        for (int i = 0; i < PRIME_NUMS_LENGTH; ++i) {
            pNum = BigInteger.valueOf(PRIME_NUMS[i]);
            if (number.mod(pNum).compareTo(BigInteger.ZERO) == 0) {
                return false;
            }

            while (!d.testBit(0)) {
                d = d.divide(BigInteger.TWO);
            }
            // 첫번째 조건
            if (pNum.modPow(d, number).compareTo(BigInteger.ONE) == 0) {
                continue;
            }

            //int idx = 0;
            while (true) {
                BigInteger modRet = pNum.modPow(d, number);
                if (modRet.compareTo(minusOne) == 0) {
                    break;
                }
                d = d.multiply(BigInteger.TWO);

                if (d.compareTo(minusOne) >= 0) {
                    return false;
                }
            }
        }

        return true;
    }
}
