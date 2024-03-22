package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;


public class Project {

    private static LinkedList<String> result;
    private static ArrayList<Task> starts;
    private static ArrayList<Task> ends;
    private static LinkedList<Task> orderedTask;
    private static HashMap<Task, ArrayList<Task>> nextMap;
    private static HashSet<String> visited;

    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {

        result = new LinkedList<>();
        starts = new ArrayList<>();
        ends = new ArrayList<>();
        nextMap = new HashMap<>();
        visited = new HashSet<>();
        orderedTask = new LinkedList<>();

        initNextMap(tasks);
        getEndTasks();

        for (Task start : ends) {
            getTaskOrderExceptCycle(start);
        }

        for (Task start : starts) {
            if (!visited.contains(start.getTitle())) {
                getTaskOrderExceptCycle(start);
            }
        }

        if (includeMaintenance) {
            getCycles();
        }

        return result;
    }
    private static void initNextMap(final Task[] tasks) {
        for (Task task : tasks) {
            if (!nextMap.containsKey(task)) {
                nextMap.put(task, new ArrayList<>());
            }

            List<Task> pres = task.getPredecessors();

            if (pres.isEmpty()) {
                starts.add(task);
            }

            for (Task pre : pres) {
                if (!nextMap.containsKey(pre)) {
                    nextMap.put(pre, new ArrayList<>());
                }
                nextMap.get(pre).add(task);
            }
        }
    }
    private static void getEndTasks() {
        for (Task key : nextMap.keySet()) {
            if (nextMap.get(key).isEmpty()) {
                ends.add(key);
            }
        }
    }

    private static void getTaskOrderExceptCycle(Task start) {
        //visited.add(start.getTitle());
        getTaskOrderRecursive(start);

    }
    private static void getTaskOrderRecursive(Task task) {
        /*if (task.getPredecessors().isEmpty() || hasVisitedAllPres(task)) {

            result.add(task.getTitle());
            orderedTask.add(task);
            visited.add(task.getTitle());
            return;
        }*/

        List<Task> pres = task.getPredecessors();

        for (Task pre : pres) {
            if (!visited.contains(pre.getTitle())) {
                visited.add(pre.getTitle());
                getTaskOrderRecursive(pre);
            }
        }
        result.add(task.getTitle());
        orderedTask.add(task);
        visited.add(task.getTitle());
    }
    private static boolean hasVisitedAllPres(Task task) {
        List<Task> pres = task.getPredecessors();
        for (Task pre : pres) {
            if (!visited.contains(pre.getTitle())) {
                return false;
            }
        }
        return true;
    }
    private static void getCycles() {

        int currResultSize = result.size();
        for (int i = 0; i < currResultSize; ++i) {
            Task start = orderedTask.get(i);
            List<Task> nexts = nextMap.get(start);

            for (Task next : nexts) {
                if (!visited.contains(next.getTitle())) {
                    getCycleRecursive(next);
                }
            }
        }
    }
    private static void getCycleRecursive(Task task) {
        if (visited.contains(task.getTitle())) {
            return;
        }
        result.add(task.getTitle());
        visited.add(task.getTitle());

        for (Task next : nextMap.get(task)) {
            getCycleRecursive(next);
        }
    }


    private static void printList(List<Task> tasks) {
        for (Task t : tasks) {
            System.out.printf("%s ", t.getTitle());
        }
        System.out.println();
    }
    private static void printMap() {
        for (Task task : nextMap.keySet()) {
            System.out.println(task.getTitle());
            List<Task> nexts = nextMap.get(task);
            for (Task next : nexts) {
                System.out.printf("    - %s\n", next.getTitle());
            }
            System.out.println();
        }
    }

}