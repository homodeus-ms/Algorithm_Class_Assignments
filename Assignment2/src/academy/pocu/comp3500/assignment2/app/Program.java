package academy.pocu.comp3500.assignment2.app;

import academy.pocu.comp3500.assignment2.Indent;
import academy.pocu.comp3500.assignment2.Logger;
import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.LinkedList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;
import academy.pocu.comp3500.assignment2.datastructure.Sort;
//import academy.pocu.comp3500.assignment2.datastructure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

import static academy.pocu.comp3500.assignment2.Logger.log;
import static academy.pocu.comp3500.assignment2.Logger.printTo;

public class Program {

    public static void main(String[] args) throws IOException {

        /*BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

        log("first level 1");

        Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            doMagic();

            log("second level 3");
        }
        Logger.unindent();

        log("first level 2");
        Logger.printTo(writer);*/





        /*BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

        int x = 10;

        log("first level 1");

        Indent indent = Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            if (x % 2 == 0) {
                indent.discard();
            }
        }
        Logger.unindent();

        log("first level 2");
        Logger.printTo(writer);*/

        /*final BufferedWriter writer1 = new BufferedWriter(new FileWriter("quicksort1.log"));

        int[] nums = new int[]{30, 10, 80, 90, 50, 70, 40};

        Sort.quickSort(nums);

        Logger.printTo(writer1);*/

        final BufferedWriter writer2 = new BufferedWriter(new FileWriter("quicksort2.log"));

        int[] nums = new int[]{30, 10, 80, 90, 50, 70, 40};

        Sort.quickSort(nums);

        Logger.printTo(writer2, "90");


        System.out.println("NoAssert!");
    }

    private static void doMagic() {
        Indent indent = Logger.indent();
        {
            log("you can also nest an indent");
            log("like this!");
        }
        Logger.unindent();
    }
    public static void depth_hell_test() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("depth_hell.txt"));
        Logger.clear();
        Logger.log("depth 0");
        Indent ParentIndent = Logger.indent();
        {
            Logger.log("depth 1 - discarded");
            Indent indent1 = Logger.indent();
            {
                Logger.log("depth2 - discarded");
                Indent indent2 = Logger.indent();
                {
                    Logger.log("depth3 - discarded");

                    Indent indent3 = Logger.indent();
                    {
                        Logger.log("depth4 - discarded");

                        ParentIndent.discard();

                        Logger.log("depth4 - stay");

                    }
                    Logger.unindent();

                    Logger.log("depth3 - stay");

                }
                Logger.unindent();
            }
            Logger.unindent();
        }
        Logger.unindent();
        Logger.printTo(writer);
        writer.close();
    }


}
