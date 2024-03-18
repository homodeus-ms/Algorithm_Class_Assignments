package academy.pocu.comp3500.lab10.app;

import academy.pocu.comp3500.lab10.Project;
import academy.pocu.comp3500.lab10.project.Task;

import java.util.List;

public class Program {

    public static void main(String[] args) {

        //basicTest();
        //test1();
        test2();

        System.out.println("No Assert");
    }
    private static void test2() {
        Task a = new Task("A", 1);
        Task[] tasks = new Task[]{a};
        List<String> list = Project.findSchedule(tasks, false);
        assert (list.size() == 1);
        assert (list.get(0).equals("A"));
        list = Project.findSchedule(tasks, true);
        assert (list.size() == 1);
        assert (list.get(0).equals("A"));
    }
    private static void test1() {
        {
            Task t1 = new Task("1", 1);
            Task t2 = new Task("2", 1);
            Task t3 = new Task("3", 1);
            Task t4 = new Task("4", 1);
            Task t5 = new Task("5", 1);
            Task t6 = new Task("6", 1);
            Task t7 = new Task("7", 1);
            Task t8 = new Task("8", 1);
            Task t9 = new Task("9", 1);
            Task t10 = new Task("10", 1);
            Task t11 = new Task("11", 1);
            Task t12 = new Task("12", 1);
            Task t13 = new Task("13", 1);
            Task t14 = new Task("14", 1);

            t2.addPredecessor(t1);
            t5.addPredecessor(t1);
            t3.addPredecessor(t2);
            t6.addPredecessor(t2);
            t6.addPredecessor(t5);
            t4.addPredecessor(t3);
            t4.addPredecessor(t6);
            t7.addPredecessor(t4);
            t9.addPredecessor(t4);
            t8.addPredecessor(t7);
            t8.addPredecessor(t9);
            t10.addPredecessor(t4);
            t11.addPredecessor(t10);
            t13.addPredecessor(t10);
            t12.addPredecessor(t11);
            t10.addPredecessor(t12);
            t14.addPredecessor(t13);
            t10.addPredecessor(t14);

            Task[] tasks = new Task[]{
                    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14
            };

            List<String> list = Project.findSchedule(tasks, false); // 요 경우에서 사이즈가 다름
            System.out.println(list.size());
            printList(list);
            list = Project.findSchedule(tasks, true);
            System.out.println(list.size());
            printList(list);
            assert (list != null);
        }

    }
    private static void printList(List<String> list) {
        for (String s : list) {
            System.out.printf("%s ", s);
        }
        System.out.println();
    }
    private static void basicTest() {
        {
            Task a = new Task("A", 15);
            Task b = new Task("B", 12);
            Task c = new Task("C", 11);

            c.addPredecessor(b);
            b.addPredecessor(a);

            Task[] tasks = new Task[]{b, c, a};

            List<String> schedule = Project.findSchedule(tasks, false);

            assert (schedule.size() == 3);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
        }

        {
            Task[] tasks = createTasks();

            List<String> schedule = Project.findSchedule(tasks, false);

            assert (schedule.size() == 6);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
            assert (schedule.get(3).equals("E"));
            assert (schedule.get(4).equals("F"));
            assert (schedule.get(5).equals("I"));
        }

        {
            Task[] tasks = createTasks();

            List<String> schedule = Project.findSchedule(tasks, true);

            assert (schedule.size() == 9);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
            assert (schedule.indexOf("C") < schedule.indexOf("E"));
            assert (schedule.indexOf("E") < schedule.indexOf("F"));
            assert (schedule.indexOf("F") < schedule.indexOf("I"));

            assert (schedule.indexOf("C") < schedule.indexOf("D"));
            assert (schedule.indexOf("D") < schedule.indexOf("G"));

            assert (schedule.indexOf("G") < schedule.indexOf("H"));
        }
    }
    private static Task[] createTasks() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);

        i.addPredecessor(f);
        f.addPredecessor(e);
        e.addPredecessor(b, c);
        d.addPredecessor(c, h);
        c.addPredecessor(b);
        b.addPredecessor(a);
        h.addPredecessor(g);
        g.addPredecessor(d);

        return new Task[]{
                a, b, c, d, e, f, g, h, i
        };
    }
}
