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

        /*basicTest1();
        basicTest2();
        basicTest3();
        basicTest4();*/
        test1();

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
    public static void basicTest1() throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("basicTest1.log"));

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
        Logger.printTo(writer);

        Logger.clear();
        writer.close();
    }
    public static void basicTest2() throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("basicTest2.log"));

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
        Logger.printTo(writer);

        Logger.clear();
        writer.close();
    }
    public static void basicTest3() throws IOException
    {
        final BufferedWriter writer1 = new BufferedWriter(new FileWriter("basicTest3.log"));

        int[] nums = new int[]{30, 10, 80, 90, 50, 70, 40};

        Sort.quickSort(nums);

        Logger.printTo(writer1);

        Logger.clear();
        writer1.close();
    }
    public static void basicTest4() throws IOException
    {
        final BufferedWriter writer2 = new BufferedWriter(new FileWriter("basicTest4.log"));

        int[] nums = new int[]{30, 10, 80, 90, 50, 70, 40};

        Sort.quickSort(nums);

        Logger.printTo(writer2, "90");

        Logger.clear();
        writer2.close();
    }
    public static void test1() throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test1.log"));

        log("First");
        Indent parent = Logger.indent();
        {
            log("Second");

            Logger.indent();
            {
                log("Cleard");
                parent.discard();

                log("Third");
            }
            Logger.unindent();
            log("Second");
        }
        Logger.unindent();
        log("againFirst?");

        Logger.printTo(writer);

        Logger.clear();
        writer.close();
    }


}
