package academy.pocu.comp3500.lab9;

import java.util.Random;

public class Helper {
    private static Random random = new Random();
    public static void sortArrAscending(int[] width) {
        sortRecursive(width, 0, width.length - 1);
    }
    public static void sortRecursive(int[] width, int left, int right) {
        if (left >= right) {
            return;
        }
        int keepLeft = left;
        int randomIdx = random.nextInt(right - left) + left;
        swap(width, randomIdx, right);

        for (int i = left; i < right; ++i) {
            if (width[i] < width[right]) {
                swap(width, i, left);
                ++left;
            }
        }
        swap(width, left, right);

        sortRecursive(width, keepLeft, left - 1);
        sortRecursive(width, left + 1, right);

    }
    public static void swap(int[] width, int i, int j) {
        int temp = width[i];
        width[i] = width[j];
        width[j] = temp;
    }
}
