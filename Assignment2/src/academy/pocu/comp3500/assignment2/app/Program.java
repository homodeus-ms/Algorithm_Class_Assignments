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

        basicTest1();
        basicTest2();
        basicTest3();
        basicTest4();
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
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
    public static void basicTest1() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("basicTest1.log"));

        log("first level 1");

        Indent indent = Logger.indent();
        {
            log("second level 1");
            indent.discard();
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
    public static void basicTest2() throws IOException {
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
    public static void basicTest3() throws IOException {
        final BufferedWriter writer1 = new BufferedWriter(new FileWriter("basicTest3.log"));

        int[] nums = new int[]{30, 10, 80, 90, 50, 70, 40};

        Sort.quickSort(nums);

        Logger.printTo(writer1);

        Logger.clear();
        writer1.close();
    }
    public static void basicTest4() throws IOException {
        final BufferedWriter writer2 = new BufferedWriter(new FileWriter("basicTest4.log"));

        int[] nums = new int[]{30, 10, 80, 90, 50, 70, 40};

        Sort.quickSort(nums);

        Logger.printTo(writer2, "90");

        Logger.clear();
        writer2.close();
    }
    public static void test1() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test1.log"));


        log("First");
        Logger.indent();
        {
            log("Second");
        }
        Logger.unindent();

        Logger.indent();
        {
            log("Second2");
        }
        Indent parent = Logger.indent();
        {
            log("blind");

            Logger.indent();
            {
                log("blind");
            }
            Logger.unindent();

            log("this?");


            parent.discard();
        }
        Logger.unindent();

        log("End");


        Logger.printTo(writer);

        Logger.clear();
        writer.close();
    }
    public static void test2() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test2.log"));

        log("First");
        Indent parent = Logger.indent();
        {
            log("Second");

            Logger.indent();
            {
                log("Cleard");
                log("Third");
                parent.discard();
            }
            Logger.indent();
            log("Fourth");
        }
        Logger.unindent();
        log("Third");
        Logger.unindent();
        log("Second");

        Indent in = Logger.indent();
        log("will be erased");
        Logger.unindent();
        in.discard();

        Logger.unindent();
        log("again First");


        Logger.printTo(writer);

        Logger.clear();
        writer.close();
    }
    public static void test3() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test3.log"));

        Logger.indent();
        log("plz i want clear the test");

        Indent indent = Logger.indent();
        {
            log("you can't see me");
            Logger.indent();
            {
                log("you can't see me");
            }
            Logger.unindent();
            indent.discard();
        }
        Logger.unindent();

        Logger.printTo(writer);


        Logger.clear();

    }
    public static void test4() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("test4.log"));

        Logger.indent();
        {
            Logger.log("[That] Rings a bell");
        }
        Logger.unindent();

        Logger.log("Count your chickens before they are hatched");
        Logger.indent();
        {
            Logger.log("Pulling one's leg");
            Logger.printTo(writer);
        }
        //  code by [조교] 보쿠린

        Logger.clear();
        writer.close();

    }
    public static void test5() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test5.txt"));
        Logger.clear();
        Logger.clear();
        Logger.log("810 755 266");
        Indent indent = Logger.indent();
        {
            Logger.log("L: 266");
            Logger.log("R: 755 810");
            Indent indent1 = Logger.indent();
            {
                Logger.log("L: 755 810");
                Logger.log("R: ");
                Logger.log("X: 755 810");
            }
            Logger.unindent();
            Logger.log("X: 266 755 810");
        }
        Logger.unindent();
        Logger.printTo(writer, "266"); // 이거 지워서도 확인해보세요~
        Logger.printTo(writer, "755");
        Logger.clear();
    }
    public static void test6() throws  IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("test6.log"));
        log("755");
        Logger.printTo(writer, "755");
        Logger.clear();
    }
}
