package academy.pocu.comp3500.lab9.app;

import academy.pocu.comp3500.lab9.CodingMan;
import academy.pocu.comp3500.lab9.ProfitCalculator;
import academy.pocu.comp3500.lab9.PyramidBuilder;
import academy.pocu.comp3500.lab9.data.Task;
import academy.pocu.comp3500.lab9.data.VideoClip;

public class Program {

    public static void main(String[] args) {

        basicTest();
        //pyramidTest1();
        //profitTest1();
        videoTest1();
        videoTest2();
        videoTest3();
        videoTest4();

        /*VideoClip[] clips = new VideoClip[] {
                new VideoClip(0, 3),
                new VideoClip(1, 3),
                new VideoClip(5, 27),
                new VideoClip(2, 26),
                new VideoClip(3, 25),
        };
        CodingMan.sortArrDescending(clips);
        printArr2(clips);*/

        System.out.println("NoAssert!");
    }
    private static void pyramidTest1() {
        int[] widths = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int statue = 55;
        assert (PyramidBuilder.findMaxHeight(widths, statue) == 0);
        statue = 54;
        assert (PyramidBuilder.findMaxHeight(widths, statue) == 1);

        int[] widths2 = {3, 3, 3, 5};
        statue = 10;
        assert (PyramidBuilder.findMaxHeight(widths2, statue) == 1);

        int[] widths3 = {3, 3, 3, 5};
        statue = 6;
        assert (PyramidBuilder.findMaxHeight(widths3, statue) == 1);

        int[] widths4 = {3, 3, 3, 3, 3, 3};
        statue = 6;
        assert (PyramidBuilder.findMaxHeight(widths4, statue) == 1);

        int[] widths5 = {3, 3, 3, 3, 3, 3, 3};
        statue = 6;
        assert (PyramidBuilder.findMaxHeight(widths5, statue) == 2);

        assert ((PyramidBuilder.findMaxHeight(new int[] {
                2, 2, 2, 2, 3, 3
        }, 1) == 2));

    }
    private static void profitTest1() {
        Task[] tasks = {
                new Task(10, 5),
        };
        int[] skillLevel = new int[] { 10, 10, 10};

        assert (ProfitCalculator.findMaxProfit(tasks, skillLevel) == 15);

        Task[] tasks2 = {
                new Task(30, 1),
                new Task(30, 2),
                new Task(30, 3),
                new Task(21, 5),
                new Task(40, 5),
        };
        skillLevel[1] = 20;
        assert (ProfitCalculator.findMaxProfit(tasks2, skillLevel) == 0);

    }
    private static void basicTest() {
        // PyramidBuilder
        int pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{3}, 2);

        assert (pyramidHeight == 0);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 5}, 10);

        assert (pyramidHeight == 0);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 5}, 9);

        assert (pyramidHeight == 1);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 4, 6}, 8);

        assert (pyramidHeight == 1);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 6, 8, 10, 12, 16, 16}, 17);

        assert (pyramidHeight == 2);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{60, 40, 20, 16, 16, 12, 10, 8, 6, 5}, 10);

        assert (pyramidHeight == 3);

        // ProfitCalculator
        Task[] tasks = new Task[]{
                new Task(20, 30),
        };
        int[] skillLevels = new int[]{20};

        int profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 30);

        tasks = new Task[]{
                new Task(20, 30),
        };
        skillLevels = new int[]{10};

        profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 0);

        tasks = new Task[]{
                new Task(20, 50),
                new Task(20, 40)
        };
        skillLevels = new int[]{25};

        profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 50);

        tasks = new Task[]{
                new Task(20, 40),
                new Task(30, 40),
                new Task(50, 25),
                new Task(60, 45)
        };
        skillLevels = new int[]{10, 20, 35, 70, 45};

        profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 165);

        // CodingMan
        VideoClip[] clips = new VideoClip[]{
                new VideoClip(0, 10),
        };
        int airTime = 10;

        int count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 1);

        clips = new VideoClip[]{
                new VideoClip(30, 60),
                new VideoClip(0, 20)
        };
        airTime = 60;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == -1);

        clips = new VideoClip[]{
                new VideoClip(0, 5),
                new VideoClip(0, 20),
                new VideoClip(5, 30),
                new VideoClip(25, 35),
                new VideoClip(35, 70),
                new VideoClip(50, 75)
        };
        airTime = 60;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 4);

        System.out.println("No Assert in basicTest");
    }
    private static void videoTest1() {
        VideoClip[] clips = new VideoClip[] {
                new VideoClip(0, 7),
                new VideoClip(0, 15),
                new VideoClip(10, 20),
                new VideoClip(15, 25),
                new VideoClip(20, 26),
                new VideoClip(23, 30),
                new VideoClip(24, 31),
                new VideoClip(25, 32),
                new VideoClip(27, 32),
                new VideoClip(28, 32),
                new VideoClip(25, 33),
                new VideoClip(29, 33),
                new VideoClip(30, 35),
                new VideoClip(33, 35),
                new VideoClip(31, 40),
                new VideoClip(40, 50),
        };
        int airTime = 35;

        int count = CodingMan.findMinClipsCount(clips, airTime);

        assert(count == 4);

        System.out.println("videoTest1 is O.K");
    }
    public static void videoTest2() {
        VideoClip[] clips1 = new VideoClip[] {
                new VideoClip(0, 7),
                new VideoClip(7, 15)
        };
        int count1 = CodingMan.findMinClipsCount(clips1, 35);
        assert(count1 == -1);
    }
    public static void videoTest3() {
        VideoClip[] clips1 = new VideoClip[] {
                new VideoClip(0, 7),
                new VideoClip(8, 15),
                new VideoClip(15, 20),
                new VideoClip(20, 25),
                new VideoClip(25, 35)
        };
        int count1 = CodingMan.findMinClipsCount(clips1, 35);
        assert(count1 == -1);
    }

    public static void videoTest4() {
        VideoClip[] clips1 = new VideoClip[] {};
        int count1 = CodingMan.findMinClipsCount(clips1, 35);
        assert(count1 == -1);
    }


    private static void printArr(int[] arr) {
        for (int n : arr) {
            System.out.printf("%d ", n);
        }
        System.out.println();
    }
    private static void printArr2(VideoClip[] arr) {
        for (VideoClip n : arr) {
            System.out.printf("(%d~%d)\n", n.getStartTime(), n.getEndTime());
        }
        System.out.println();
    }
}
