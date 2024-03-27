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
        //testTaskOrder();
        System.out.println("NoAssert");
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

        //int result = project.findMaxBonusCount("H");

        int[] bonusResult = new int[tasks.length];
        int idx = 0;
        for (Task task : tasks) {
            int result = project.findMaxBonusCount(task.getTitle());
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
