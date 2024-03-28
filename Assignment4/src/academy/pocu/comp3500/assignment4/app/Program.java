package academy.pocu.comp3500.assignment4.app;

import academy.pocu.comp3500.assignment4.Project;
import academy.pocu.comp3500.assignment4.project.Task;

import java.util.List;

public class Program {
    public static void main(String[] args) {
	    basicTest();
        test1();
        test2();
        test3();
        test4();
        test5();
        //test3_3();
        test3_2();
        //test6();
        //testTaskOrder();
        System.out.println("NoAssert");
    }
    public static void test6() {
        Task a = new Task("A", 7);
        Task b = new Task("B", 5);
        Task c = new Task("C", 6);
        Task d = new Task("D", 2);
        Task e = new Task("E", 6);
        Task f = new Task("F", 6);

        b.addPredecessor(a);
        d.addPredecessor(b, c);
        c.addPredecessor(a);
        e.addPredecessor(c, d);
        f.addPredecessor(d, e);

        Task[] tasks = new Task[] {
                a, b, c, d, e, f
        };
        Project project = new Project(tasks);
        int result = 0;
        int idx = 0;
        result = project.findMaxBonusCount(tasks[idx++].getTitle());
        System.out.println(result);
        result = project.findMaxBonusCount(tasks[idx++].getTitle());
        System.out.println(result);
        result = project.findMaxBonusCount(tasks[idx++].getTitle());
        System.out.println(result);
        result = project.findMaxBonusCount(tasks[idx++].getTitle());
        System.out.println(result);
        result = project.findMaxBonusCount(tasks[idx++].getTitle());
        System.out.println(result);
        result = project.findMaxBonusCount(tasks[idx++].getTitle());
        System.out.println(result);

    }
    public static void test5() {
        Task a = new Task("A", 3);
        Task b = new Task("B", 5);
        Task c = new Task("C", 7);
        Task d = new Task("D", 2);

        Task[] tasks = new Task[] {
                a, b, c, d
        };

        Project project = new Project(tasks);

        b.addPredecessor(a);
        c.addPredecessor(a, b);
        d.addPredecessor(b, c);

        int result = 0;

    }
    public static void test4() {
        Task a = new Task("A", 3);
        Task b = new Task("B", 5);
        Task c = new Task("C", 1);
        Task d = new Task("D", 2);
        Task e = new Task("E", 3);
        Task f = new Task("F", 1);
        Task g = new Task("G", 2);
        Task h = new Task("H", 3);

        Task z = new Task("Z", 7);
        Task y = new Task("Y", 10);
        Task x = new Task("X", 10);

        a.addPredecessor(z, b, x);
        y.addPredecessor(a);
        x.addPredecessor(z);
        z.addPredecessor(b);
        c.addPredecessor(a, e);
        e.addPredecessor(d);
        d.addPredecessor(c);
        f.addPredecessor(b, h);
        h.addPredecessor(g);
        g.addPredecessor(f);

        Task[] tasks = new Task[] {
                a, b, c, d, e, f, g, h, z, y, x
        };

        Project project = new Project(tasks);

        int result = 0;



        System.out.println();
    }
    public static void test3_3() {
        Task a = new Task("A", 5);
        Task b = new Task("B", 4);
        Task c = new Task("C", 8);
        Task d = new Task("D", 7);

        c.addPredecessor(a);
        d.addPredecessor(b, a);

        Task[] tasks = new Task[] {
                d, c, b, a
        };
        Project project = new Project(tasks);

        int result = 0;
        /*result = project.findMaxBonusCount("A");
        System.out.println(result);
        result = project.findMaxBonusCount("B");
        System.out.println(result);
        result = project.findMaxBonusCount("C");
        System.out.println(result);*/
        result = project.findMaxBonusCount("D");
        System.out.println(result);
    }
    public static void test3_2() {
        Task a = new Task("A", 3);
        Task b = new Task("B", 5);
        Task c = new Task("C", 7);
        Task d = new Task("D", 5);
        Task e = new Task("E", 2);
        Task f = new Task("F", 5);
        Task g = new Task("G", 6);
        Task h = new Task("H", 9);
        Task i = new Task("I", 5);
        Task j = new Task("J", 4);
        Task k = new Task("K", 3);
        Task l = new Task("L", 6);
        Task m = new Task("M", 8);
        Task n = new Task("N", 4);
        Task o = new Task("O", 3);
        Task p = new Task("P", 2);
        Task q = new Task("Q", 5);
        Task r = new Task("R", 7);
        Task s = new Task("S", 8);
        Task t = new Task("T", 20);

        b.addPredecessor(a);
        c.addPredecessor(b, l);
        d.addPredecessor(c);
        e.addPredecessor(j);
        f.addPredecessor(d, e);
        g.addPredecessor(i);
        h.addPredecessor(g, e);
        i.addPredecessor(n);
        j.addPredecessor(m, n);
        l.addPredecessor(k, m);
        o.addPredecessor(i, q);
        p.addPredecessor(o);
        q.addPredecessor(p);
        r.addPredecessor(f, h);
        s.addPredecessor(f);
        t.addPredecessor(s);


        Task[] tasks = new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t,
        };

        Project project = new Project(tasks);

        int result = 0;
        //result = project.findMaxBonusCount("H");

        /*int[] bonusResult = new int[tasks.length];
        int idx = 0;
        for (Task task : tasks) {
            result = project.findMaxBonusCount(task.getTitle());
            bonusResult[idx++] = result;
        }*/

        assert (project.findMaxBonusCount("A") == 3); // a
        assert (project.findMaxBonusCount("B") == 3); // b
        assert (project.findMaxBonusCount("C") == 7); // c
        assert (project.findMaxBonusCount("D") == 5); // d
        assert (project.findMaxBonusCount("E") == 2); // e
        assert (project.findMaxBonusCount("F") == 5); // f
        assert (project.findMaxBonusCount("G") == 4); // g
        assert (project.findMaxBonusCount("H") == 6); // h
        assert (project.findMaxBonusCount("I") == 4); // i
        assert (project.findMaxBonusCount("J") == 4); // j
        assert (project.findMaxBonusCount("K") == 3); // k
        assert (project.findMaxBonusCount("L") == 6); // l
        assert (project.findMaxBonusCount("M") == 8); // m
        assert (project.findMaxBonusCount("N") == 4); // n
        assert (project.findMaxBonusCount("O") == 3); // o
        assert (project.findMaxBonusCount("P") == 2); // p
        assert (project.findMaxBonusCount("Q") == 2); // q

        result = project.findMaxBonusCount("R");
        assert (result == 7); // r
        result = project.findMaxBonusCount("S");
        assert (result == 5); // s
        result = project.findMaxBonusCount("T");
        assert (result == 5); // t
    }
    public static void test3() {
        Task a = new Task("A", 8);
        Task b = new Task("B", 7);
        Task c = new Task("C", 6);
        Task d = new Task("D", 5);
        Task e = new Task("E", 4);
        Task f = new Task("F", 7);
        Task g = new Task("G", 8);
        Task h = new Task("H", 9);
        Task i = new Task("I", 4);
        Task j = new Task("J", 5);
        Task k = new Task("K", 7);
        Task l = new Task("L", 3);
        Task m = new Task("M", 5);
        Task n = new Task("N", 8);
        Task o = new Task("O", 9);
        Task p = new Task("P", 4);
        Task q = new Task("Q", 5);
        Task r = new Task("R", 6);
        Task s = new Task("S", 7);
        Task t = new Task("T", 8);

        b.addPredecessor(a);
        c.addPredecessor(b);
        d.addPredecessor(b);
        e.addPredecessor(d, g);
        f.addPredecessor(h, e);
        g.addPredecessor(c, d);
        h.addPredecessor(g, e);
        i.addPredecessor(h, e);
        j.addPredecessor(i);
        k.addPredecessor(f, m);
        l.addPredecessor(o);
        m.addPredecessor(l);
        n.addPredecessor(k);
        o.addPredecessor(d, q);
        q.addPredecessor(p);
        r.addPredecessor(s);
        s.addPredecessor(q);
        t.addPredecessor(s, m);


        Task[] tasks = new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t,
        };

        Project project = new Project(tasks);

        int result = 0;
        result = project.findMaxBonusCount("F");

        int[] bonusResult = new int[tasks.length];
        int idx = 0;
        for (Task task : tasks) {
            result = project.findMaxBonusCount(task.getTitle());
            bonusResult[idx++] = result;
        }

        assert (bonusResult[0] == 8); // a
        assert (bonusResult[1] == 7); // b
        assert (bonusResult[2] == 6); // c
        assert (bonusResult[3] == 5); // d
        assert (bonusResult[4] == 4); // e
        assert (bonusResult[5] == 7); // f
        assert (bonusResult[6] == 7); // g
        assert (bonusResult[7] == 7); // h
        assert (bonusResult[8] == 4); // i
        assert (bonusResult[9] == 4); // j
        assert (bonusResult[10] == 7); // k
        assert (bonusResult[11] == 3); // l
        assert (bonusResult[12] == 3); // m
        assert (bonusResult[13] == 7); // n
        assert (bonusResult[14] == 9); // o
        assert (bonusResult[15] == 4); // p
        assert (bonusResult[16] == 4); // q
        assert (bonusResult[17] == 4); // r
        assert (bonusResult[18] == 4); // s
        assert (bonusResult[19] == 7); // t


    }
    public static void test2() {
        {
            Task a = new Task("A", 2);
            Task b = new Task("B", 1);
            Task c = new Task("C", 3);
            Task d = new Task("D", 5);
            Task e = new Task("E", 7);
            Task f = new Task("F", 2);
            Task g = new Task("G", 11);

            b.addPredecessor(a);
            c.addPredecessor(b);
            d.addPredecessor(c);

            f.addPredecessor(b, e);
            g.addPredecessor(d, f);

            Task[] tasks = new Task[]{  a, b, c, d, e, f, g };
            Project project = new Project(tasks);

            int bonusCount1 = project.findMaxBonusCount("G");
            assert (bonusCount1 == 3);
        }

    }
    public static void test1() {
        {
            Task a = new Task("A", 3);
            Task b = new Task("B", 5);
            Task c = new Task("C", 3);
            Task d = new Task("D", 2);
            Task e = new Task("E", 1);
            Task f = new Task("F", 2);
            Task g = new Task("G", 6);
            Task h = new Task("H", 8);
            Task i = new Task("I", 2);
            Task j = new Task("J", 4);
            Task k = new Task("K", 2);
            Task l = new Task("L", 8);
            Task m = new Task("M", 7);
            Task n = new Task("N", 1);
            Task o = new Task("O", 1);
            Task p = new Task("P", 6);
            Task ms1 = new Task("ms1", 6);
            Task ms2 = new Task("ms2", 4);

            c.addPredecessor(b);
            d.addPredecessor(a);

            ms1.addPredecessor(a, c);

            e.addPredecessor(c);
            f.addPredecessor(g);
            g.addPredecessor(e);

            i.addPredecessor(h);
            j.addPredecessor(ms1);

            k.addPredecessor(j);
            n.addPredecessor(k);
            m.addPredecessor(n);
            l.addPredecessor(m);

            p.addPredecessor(i, j);
            o.addPredecessor(j);

            ms2.addPredecessor(o, p);

            Task[] tasks = new Task[]{
                    a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, ms1, ms2
            };

            Project project = new Project(tasks);

            int manMonths1 = project.findTotalManMonths("ms1");
            assert (manMonths1 == 17);

            int manMonths2 = project.findTotalManMonths("ms2");
            assert (manMonths2 == 42);

            int minDuration1 = project.findMinDuration("ms1");
            assert (minDuration1 == 14);

            int minDuration2 = project.findMinDuration("ms2");
            assert (minDuration2 == 28);

            int bonusCount1 = project.findMaxBonusCount("ms1");
            assert (bonusCount1 == 6);

            int bonusCount2 = project.findMaxBonusCount("ms2");
            assert (bonusCount2 == 4);
        }


    }
    public static void basicTest() {
        {
            Task[] tasks = createTasks();

            Project project = new Project(tasks);

            int manMonths1 = project.findTotalManMonths("ms1");
            assert (manMonths1 == 17);

            int manMonths2 = project.findTotalManMonths("ms2");
            assert (manMonths2 == 46);

            int minDuration1 = project.findMinDuration("ms1");
            assert (minDuration1 == 14);

            int minDuration2 = project.findMinDuration("ms2");
            assert (minDuration2 == 32);

            int bonusCount1 = project.findMaxBonusCount("ms1");
            assert (bonusCount1 == 6);

            int bonusCount2 = project.findMaxBonusCount("ms2");
            assert (bonusCount2 == 6);

            System.out.println("basicTest is OK");
        }
    }
    private static Task[] createTasks() {
        Task a = new Task("A", 3);
        Task b = new Task("B", 5);
        Task c = new Task("C", 3);
        Task d = new Task("D", 2);
        Task e = new Task("E", 1);
        Task f = new Task("F", 2);
        Task g = new Task("G", 6);
        Task h = new Task("H", 8);
        Task i = new Task("I", 2);
        Task j = new Task("J", 4);
        Task k = new Task("K", 2);
        Task l = new Task("L", 8);
        Task m = new Task("M", 7);
        Task n = new Task("N", 1);
        Task o = new Task("O", 1);
        Task p = new Task("P", 6);
        Task ms1 = new Task("ms1", 6);
        Task ms2 = new Task("ms2", 8);

        c.addPredecessor(b);
        d.addPredecessor(a);

        ms1.addPredecessor(a, c);

        e.addPredecessor(c);
        f.addPredecessor(g);
        g.addPredecessor(e);

        i.addPredecessor(h);
        j.addPredecessor(ms1);

        k.addPredecessor(j);
        n.addPredecessor(k);
        m.addPredecessor(n);
        l.addPredecessor(m);

        p.addPredecessor(i, j);
        o.addPredecessor(j);

        ms2.addPredecessor(o, p);

        Task[] tasks = new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, ms1, ms2
        };

        return tasks;
    }
    /*public static void testTaskOrder() {

        {

            Task[] tasks = createTasks2();
            Project project = new Project(tasks);

            List<Task> schedule = project.findSchedule(false);

            assert (schedule.size() == 6);

            printList(schedule);

        }

        {
            Task[] tasks = createTasks2();
            Project project = new Project(tasks);
            List<Task> schedule = project.findSchedule(true);

            assert (schedule.size() == 9);
            printList(schedule);
        }
    }
    private static Task[] createTasks2() {
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
    }*/
    private static void printList(List<Task> schedule) {
        for (Task task : schedule) {
            System.out.printf("(%s %d) ", task.getTitle(), task.getEstimate());
        }
        System.out.println();
    }
}
