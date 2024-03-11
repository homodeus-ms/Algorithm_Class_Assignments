package academy.pocu.comp3500.lab9;

import java.util.Random;

public class PyramidBuilder {

    public static int findMaxHeight(final int[] widths, int statue) {
        if (widths.length == 0 || widths.length == 1) {
            return 0;
        }

        Helper.sortArrAscending(widths);

        int sum = widths[0];
        int idx = 1;
        int levelCount = 0;
        int rockCount = 1;
        int preRockCount = 1;
        int preSumValue = statue;

        while (true) {
            do {
                sum += widths[idx++];
                ++rockCount;
            } while ((idx != widths.length && sum <= preSumValue) ||
                    ((idx != widths.length) && rockCount <= preRockCount));

            if (idx == widths.length) {
                if (sum > preSumValue && rockCount > preRockCount) {
                    ++levelCount;
                }
                break;
            }
            ++levelCount;
            preSumValue = sum;
            preRockCount = rockCount;
            sum = 0;
            rockCount = 0;
        }

        return levelCount;
    }

}
