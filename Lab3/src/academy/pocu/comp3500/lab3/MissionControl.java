package academy.pocu.comp3500.lab3;

import java.util.ArrayList;

public final class MissionControl {
    private MissionControl() {
    }

    public static int findMaxAltitudeTime(final int[] altitudes) {
        int lastIdx = altitudes.length - 1;
        if (lastIdx == 0) {
            return 0;
        }
        int mid = altitudes.length / 2;

        if (altitudes[lastIdx] > altitudes[0]) {
            if (altitudes[lastIdx] >= altitudes[lastIdx - 1]) {
                return lastIdx;
            } else {
                return findMaxIndex(altitudes, altitudes[mid], mid);
            }
        } else if (altitudes[0] > altitudes[lastIdx]) {
            if (altitudes[0] >= altitudes[1]) {
                return 0;
            } else {
                return findMaxIndex(altitudes, altitudes[mid], mid);

            }
        } else {
            return findMaxIndex(altitudes, altitudes[mid], mid);
        }

    }

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {
        ArrayList<Integer> bounds = new ArrayList<>();
        int lastIdx = altitudes.length - 1;
        if (lastIdx == 0 && altitudes[0] == targetAltitude) {
            bounds.add(0);
            return bounds;
        } else if (lastIdx == 0) {
            return bounds;
        }

        for (int i = 0; i < altitudes.length; ++i) {
            int currAltitude = altitudes[i];
            if (currAltitude == targetAltitude) {
                bounds.add(i);
            }
        }
        return bounds;
    }
    private static int findMaxIndex(final int[] altitudes, int max, int midIdx) {
        boolean isMaxRightDir = false;
        int index = midIdx + 1;

        while (altitudes[index] > max) {
            isMaxRightDir = true;
            max = altitudes[index++];
        }

        if (isMaxRightDir) {
            return index - 1;
        } else {
            index -= 2;
            while (altitudes[index] > max) {
                max = altitudes[index--];
            }

            return max > altitudes[midIdx] ? index + 1 : midIdx;
        }
    }
}