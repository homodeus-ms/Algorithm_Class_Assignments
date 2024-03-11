package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.Random;

public class CodingMan {
    private static Random random = new Random();

    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        if (clips.length == 0) {
            return -1;
        }

        sortArrAscending(clips);

        if (clips[0].getStartTime() != 0) {
            return -1;
        }

        int idx = 0;
        int startTime = 0;
        int endTime = clips[idx].getEndTime();

        int longestEndTimeIdx = idx;
        int longestEndTime = endTime;
        int requireClipsCount = 1;
        boolean isJumped;
        ++idx;

        while (endTime < time && idx < clips.length) {
            isJumped = true;
            while (idx < clips.length && endTime >= clips[idx].getStartTime()) {
                if (longestEndTime < clips[idx].getEndTime()) {
                    longestEndTimeIdx = idx;
                    longestEndTime = clips[idx].getEndTime();
                    isJumped = false;
                }
                ++idx;
            }
            if (isJumped) {
                return -1;
            }
            if (startTime != clips[longestEndTimeIdx].getStartTime()) {
                ++requireClipsCount;
            }

            endTime = clips[longestEndTimeIdx].getEndTime();
        }

        if (idx >= clips.length - 1 && endTime < time) {
            return -1;
        }

        return requireClipsCount;
    }
    public static void sortArrAscending(VideoClip[] clips) {
        sortRecursive(clips, 0, clips.length - 1);
    }
    public static void sortRecursive(VideoClip[] clips, int left, int right) {
        if (left >= right) {
            return;
        }
        int keepLeft = left;
        int randomIdx = random.nextInt(right - left) + left;
        swap(clips, randomIdx, right);

        int rightStartTime = clips[right].getStartTime();
        int rightEndTime = clips[right].getEndTime();

        for (int i = left; i < right; ++i) {
            int startTime = clips[i].getStartTime();
            int endTime = clips[i].getEndTime();

            if (startTime == rightStartTime) {
                if (endTime < rightEndTime) {
                    swap(clips, i, left);
                    ++left;
                }
            } else if (startTime < rightStartTime) {
                swap(clips, i, left);
                ++left;
            }
        }
        swap(clips, left, right);

        sortRecursive(clips, keepLeft, left - 1);
        sortRecursive(clips, left + 1, right);

    }
    public static void swap(VideoClip[] width, int i, int j) {
        VideoClip temp = width[i];
        width[i] = width[j];
        width[j] = temp;
    }
}
