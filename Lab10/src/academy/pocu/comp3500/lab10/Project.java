package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
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
        //ends = new ArrayList<>();
        nextMap = new HashMap<>();
        visited = new HashSet<>();
        //orderedTask = new LinkedList<>();

        LinkedList<Task> firstDfs = new LinkedList<>();
        initNextMap(tasks);

        for (Task task : starts) {
            visited.add(task.getTitle());
            searchDepthFirst1(task, firstDfs);
        }

        LinkedList<Task> normal = new LinkedList<>();
        LinkedList<Task> cycles = new LinkedList<>();
        visited.clear();


        for (Task task : firstDfs) {
            if (!visited.contains(task.getTitle())) {
                seperate(task, cycles);
            }
        }

        if (includeMaintenance) {
            for (Task task : cycles) {
                result.add(task.getTitle());
            }
        }

        return result;
    }
    private static void searchDepthFirst1(Task task, LinkedList<Task> dfsList) {
        List<Task> nexts = nextMap.get(task);

        for (Task next : nexts) {
            if (!visited.contains(next.getTitle())) {
                visited.add(next.getTitle());
                searchDepthFirst1(next, dfsList);
            }
        }
        dfsList.addFirst(task);
    }
    private static void seperate(Task task, LinkedList<Task> cycles) {
        List<Task> pres = task.getPredecessors();

        if (!pres.isEmpty() && !hasVisitedAllPres(task)) {
            visited.add(task.getTitle());
            putCycles(task, cycles);
        } else {
            result.add(task.getTitle());
            visited.add(task.getTitle());
        }

    }
    private static void putCycles(Task task, LinkedList<Task> cycles) {

        List<Task> nexts = nextMap.get(task);
        for (Task next : nexts) {
            if (!visited.contains(next.getTitle())) {
                visited.add(next.getTitle());
                putCycles(next, cycles);
            }
        }
        cycles.addFirst(task);
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


    private static boolean hasVisitedAllPres(Task task) {
        List<Task> pres = task.getPredecessors();
        for (Task pre : pres) {
            if (!visited.contains(pre.getTitle())) {
                return false;
            }
        }
        return true;
    }
}