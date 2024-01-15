package academy.pocu.comp3500.lab3.app;

import academy.pocu.comp3500.lab3.MissionControl;

import java.util.ArrayList;
import java.util.Collections;

public class Program {

    public static void main(String[] args) {
        /*{
            final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 6, 7, 4, 3, 2 };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);

            assert (maxAltitudeTime == 6);
        }

        {
            final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 6, 7, 4, 3, 2 };

            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 2);

            assert (bounds.size() == 2);

            assert (bounds.get(0) == 1);
            assert (bounds.get(1) == 9);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);

            assert (bounds.size() == 1);
            assert (bounds.get(0) == 4);
        }*/
        Test0();
        //Test1();
        //Test2();
        System.out.println("No Assert!");
    }

    public static void Test0() {
        {
            final int[] altitudes = new int[] {
                    1, 2, 3, 4, 5, 6, 7, 4, 3, 2
            };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert(maxAltitudeTime == 6);
        }

        {
            final int[] altitudes = new int[] {
                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            };
            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert(maxAltitudeTime == 9);
        }

        {
            final int[] altitudes = new int[] {
                    10, 9, 8, 7, 6, 5, 4, 3, 2, 1
            };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);

            assert(maxAltitudeTime == 0);
        }

        {
            final int[] altitudes = new int[] {
                    4, 5, 4, 3, 2
            };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert(maxAltitudeTime == 1);
        }

        {
            final int[] altitudes = new int[] {
                    1, 2, 3, 4, 3, 0
            };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert(maxAltitudeTime == 3);
        }

        {
            final int[] altitudes = new int[] {
                    1
            };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert(maxAltitudeTime == 0);
        }

        {
            final int[] altitudes = new int[] {
                    1, 2, 3, 4, 5, 6, 7, 4, 3, 2
            };

            ArrayList < Integer > bounds = MissionControl.findAltitudeTimes(altitudes, 2);

            assert(bounds.size() == 2);

            assert(bounds.get(0) == 1);
            assert(bounds.get(1) == 9);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);

            assert(bounds.size() == 1);
            assert(bounds.get(0) == 4);
        }

        {
            final int[] altitudes = new int[] {
                    1
            };
            ArrayList < Integer > bounds = MissionControl.findAltitudeTimes(altitudes, 0);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 0);
        }

        // 상승
        {
            final int[] altitudes = new int[] {
                    2, 4, 6, 8, 10
            };
            ArrayList < Integer > bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 9);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 11);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 1);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 2);

            bounds = MissionControl.findAltitudeTimes(altitudes, 8);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 10);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 4);
        }

        // 하강
        {
            final int[] altitudes = new int[] {
                    10, 8, 6, 4, 2
            };
            ArrayList < Integer > bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 9);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 11);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 10);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 8);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 1);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 2);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 4);
        }

        // 상승-하강
        {
            final int[] altitudes = new int[] {
                    2, 4, 6, 4, 2
            };
            ArrayList < Integer > bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert(bounds.size() == 2);
            assert(bounds.get(0) == 0);
            assert(bounds.get(1) == 4);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert(bounds.size() == 2);
            assert(bounds.get(0) == 1);
            assert(bounds.get(1) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 2);
        }

        {
            final int[] altitudes = new int[] {
                    6, 8, 6, 4, 2
            };
            ArrayList < Integer > bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 9);
            assert(bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 4);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert(bounds.size() == 2);
            assert(bounds.get(0) == 0);
            assert(bounds.get(1) == 2);

            bounds = MissionControl.findAltitudeTimes(altitudes, 8);
            assert(bounds.size() == 1);
            assert(bounds.get(0) == 1);
        }
    }

    public static void Test1() {
        {
            for (int k = 0; k < 100; ++k) {
                int[] altitude = new int[100];
                for (int i = 0; i < k; ++i) {
                    altitude[i] = i + 1;
                }
                for (int i = 0; i < 100 - k; ++i) {
                    altitude[k + i] = k - i - 1;
                }
                for (int i = 0; i < altitude.length; ++i) {
                    ArrayList < Integer > range = MissionControl.findAltitudeTimes(altitude, altitude[i]);
                    ArrayList < Integer > expected = new ArrayList < > ();
                    Collections.sort(range);
                    for (int j = 0; j < altitude.length; ++j) {
                        if (altitude[j] == altitude[i]) {
                            expected.add(j);
                        }
                    }
                    Collections.sort(expected);
                    assert(expected.size() <= 2);
                    assert(expected.size() == range.size());
                    for (int j = 0; j < range.size(); ++j) {
                        assert(range.get(j) == expected.get(j));
                    }
                }
            }
        }

        {
            for (int k = 0; k < 100; ++k) {
                int[] altitude = new int[100];
                for (int i = 0; i < k; ++i) {
                    altitude[i] = i + 1;
                }
                for (int i = 0; i < 100 - k; ++i) {
                    altitude[k + i] = k - i - 1;
                }
                int index = MissionControl.findMaxAltitudeTime(altitude);
                int maxIndex = 0;
                for (int i = 0; i < altitude.length; ++i) {
                    if (altitude[i] > altitude[maxIndex]) {
                        maxIndex = i;
                    }
                }
                assert(index == maxIndex);
            }
        }

    }

    public static void Test2() {
        {
            final int[] altitudes = new int[] { 1, 4, 5, 6, 7 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert (bounds.size() == 0);
        }

        {
            final int[] altitudes = new int[] { 7, 6, 5, 4, 3, 2, 1 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 8);
            assert (bounds.size() == 0);
        }

        {
            final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 3, 2, 1 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert (bounds.size() == 0);
        }

    }
}