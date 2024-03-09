package academy.pocu.comp3500.assignment3;

import java.util.Random;

public class ZobristHashing {
    public static Random random = new Random();
    public static final long[][] values = new long[64][];

    // char c - 'b' || char c - 'B'
    // [0] = b, B  |  [9] = k, K  |  [12] = n, N
    // [14] = p, P  |  [15] = q, Q  |  [16] = r, R
    private static final int[] WHITE_INDEX_TABLE = {
            7, -1, -1, -1, -1, -1, -1, -1, -1, 1,
            -1, -1, 9, -1, 11, 3, 5,
    };
    private static final int[] BLACK_INDEX_TABLE = {
            6, -1, -1, -1, -1, -1, -1, -1, -1, 0,
            -1, -1, 8, -1, 10, 2, 4,
    };

    private ZobristHashing() {

    }

    public static void init() {
        // 0  1  2  3  4  5  6  7  8  9  10 11
        // K, k, Q, q, R, r, B, b, N, n, P, p
        long[] pieceValue;

        for (int i = 0; i < 64; ++i) {
            pieceValue = new long[12];
            values[i] = pieceValue;
        }

        for (int y = 0; y < 64; ++y) {
            for (int x = 0; x < 12; ++x) {
                values[y][x] = random.nextLong();
            }
        }
    }
    public static long getHash(char[] board) {
        long hash = 0;
        for (int y = 0; y < 64; ++y) {
            if (board[y] != 0) {
                int idx = getIdx(board[y]);
                hash ^= values[y][idx];
            }
        }
        return hash;
    }
    private static int getIdx(char c) {
        if (c >= 'b') {
            return WHITE_INDEX_TABLE[c - 'b'];
        } else {
            return BLACK_INDEX_TABLE[c - 'B'];
        }
    }

    /*public static void print() {
        for (int y = 0 ; y < 3; ++y) {
            for (int x = 0; x < 12; ++x) {
                System.out.printf("%d ", values[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }*/

}
