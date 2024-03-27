package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;


public class CodingMan {

    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        if (clips.length == 0) {
            return -1;
        }

        VideoClip[] copyArr = new VideoClip[clips.length];
        int idx = 0;
        boolean noPossibleEndTime = true;
        for (VideoClip v : clips) {
            copyArr[idx++] = v;
            if (v.getEndTime() >= time) {
                noPossibleEndTime = false;
            }
        }
        if (noPossibleEndTime) {
            return -1;
        }

        VideoClip[] buffer = new VideoClip[clips.length];
        int bufferSize = 0;
        int endTime = time;
        int minStartTime = Integer.MAX_VALUE;
        int length = copyArr.length;
        int count = 0;
        boolean countNext;

        do {
            bufferSize = 0;
            countNext = false;

            for (int i = 0; i < length; ++i) {
                VideoClip clip = copyArr[i];
                if (clip.getEndTime() >= endTime) {
                    if (minStartTime > clip.getStartTime()) {
                        minStartTime = clip.getStartTime();
                    }
                    countNext = true;
                } else {
                    buffer[bufferSize++] = clip;
                }
            }

            if (countNext) {
                ++count;
            } else {
                return -1;
            }

            if (minStartTime == 0) {
                break;
            }


            copyArr = buffer;
            length = bufferSize;
            endTime = minStartTime;
            minStartTime = Integer.MAX_VALUE;

        } while (true);


        return count;

    }

}
