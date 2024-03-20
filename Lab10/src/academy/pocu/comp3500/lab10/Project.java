package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.LinkedList;


public class Project {

    private static ArrayList<Task> starts = new ArrayList<>();
    private static HashMap<String, ArrayList<Task>> next = new HashMap<>();
    //private static HashSet<String> visited;
    //private static HashSet<String> discovered;

    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {
        starts.clear();
        next.clear();

        setNext(tasks);

        HashSet<Task> visited = new HashSet<>();
        HashSet<Task> processed = new HashSet<>();
        Stack<Task> stack = new Stack<>();
        LinkedList<Task> list = new LinkedList<>();


        for (Task start : starts) {
            visited.add(start);
            stack.add(start);

            while (!stack.isEmpty()) {
                Task pop = stack.pop();
                ArrayList<Task> nexts = next.get(pop.getTitle());
                list.add(pop);
                processed.add(pop);

                for (Task nextT : nexts) {
                    if (!visited.contains(nextT)) {
                        if (processed.containsAll(nextT.getPredecessors())) {
                            visited.add(nextT);
                            stack.add(nextT);
                        }
                    }
                }
            }
        }

        if (includeMaintenance) {
            int listSize = list.size();
            for (int i = listSize - 1; i >= 0; --i) {

                stack.add(list.get(i));

                while (!stack.isEmpty()) {
                    Task pop = stack.pop();
                    ArrayList<Task> nexts = next.get(pop.getTitle());

                    for (Task nextT : nexts) {
                        if (!visited.contains(nextT)) {
                            list.add(nextT);
                            visited.add(nextT);
                            stack.add(nextT);
                        }
                    }
                }
            }
        }

        List<String> result = new ArrayList<>(list.size());
        for (Task t : list) {
            result.add(t.getTitle());
        }

        //printList(list);

        return result;
    }

    private static void setNext(final Task[] tasks) {
        for (Task task : tasks) {

            if(!next.containsKey(task.getTitle())) {
                next.put(task.getTitle(), new ArrayList<>());
            }

            List<Task> pres = task.getPredecessors();

            if (pres.isEmpty()) {
                starts.add(task);
            }

            for (Task pre : pres) {
                if (!next.containsKey(pre.getTitle())) {
                    ArrayList<Task> nexts = new ArrayList<>();
                    nexts.add(task);
                    next.put(pre.getTitle(), nexts);
                } else {
                    next.get(pre.getTitle()).add(task);
                }
            }
        }

        /*for (Task task : tasks) {
            if (!next.containsKey(task.getTitle())) {
                next.put(task.getTitle(), new ArrayList<>());
            }

            List<Task> pres = task.getPredecessors();
            if (pres.isEmpty()) {
                starts.add(task);
            }

            for (Task t : pres) {
                if (t.getPredecessors().isEmpty()) {
                    if (!next.containsKey(t.getTitle())) {
                        next.put(t.getTitle(), new ArrayList<>());

                    }

                    next.get(t.getTitle()).add(task);

                } else {
                    if (next.containsKey(t.getTitle())) {
                        next.get(t.getTitle()).add(task);
                    } else {
                        next.put(t.getTitle(), new ArrayList<>());
                        next.get(t.getTitle()).add(task);
                    }
                }
            }
        }*/
    }


    /*public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {
        visited = new HashSet<>();
        discovered = new HashSet<>();

        List<String> result = new LinkedList<>();
        HashSet<Task> cycles = new HashSet<>();

        LinkedList<Task> starts = new LinkedList<>();
        for (Task t : tasks) {
            if (t.getPredecessors().isEmpty()) {
                starts.addFirst(t);
            } else {
                starts.add(t);
            }
        }

        for (Task task : starts) {
            getProcessExceptCycles(task, result, cycles);
        }

        if (includeMaintenance) {
            discovered.clear();
            Task cycleEntrance = null;
            for (Task task : cycles) {
                if (task.getPredecessors().size() > 1) {
                    cycleEntrance = task;
                    break;
                }
            }

            if (cycleEntrance != null) {
                getCycleToResult(cycleEntrance, result);
            }
        }

        return result;
    }
    private static boolean getProcessExceptCycles(Task task,
                                                  List<String> result,
                                                  HashSet<Task> cycles) {
        if (task.getPredecessors().isEmpty()) {
            if (!visited.contains(task.getTitle())) {
                String title = task.getTitle();
                discovered.add(title);
                visited.add(title);
                result.add(title);
            }

            return true;
        }
        List<Task> pres = task.getPredecessors();
        boolean isCycle = false;

        if (visitedAllPreNodes(pres)) {
            if (!visited.contains(task.getTitle())) {
                String title = task.getTitle();
                discovered.add(title);
                visited.add(title);
                result.add(title);
            }
            return true;
        } else {
            for (Task t : pres) {
                if (discovered.contains(t.getTitle())) {
                    if (!visited.contains(t.getTitle())) {
                        isCycle = true;
                    }
                } else {
                    discovered.add(t.getTitle());
                    if (!getProcessExceptCycles(t, result, cycles)) {
                        cycles.add(t);
                        return false;
                    }
                }
            }

        }
        if (!visited.contains(task.getTitle()) && !isCycle) {
            String title = task.getTitle();
            discovered.add(title);
            visited.add(title);
            result.add(title);
        }

        return false;
    }

    private static void getCycleToResult(Task task, List<String> result) {
        if (discovered.contains(task.getTitle())) {
            return;
        }

        discovered.add(task.getTitle());
        for (Task t : task.getPredecessors()) {
            if (visited.contains(t.getTitle())) {
                continue;
            }
            getCycleToResult(t, result);
            if (!visited.contains(t.getTitle())) {
                result.add(t.getTitle());
                visited.add(t.getTitle());
            }
        }
    }

    private static boolean visitedAllPreNodes(List<Task> pres) {
        for (Task t : pres) {
            if (!visited.contains(t.getTitle())) {
                return false;
            }
        }
        return true;
    }*/


    /*public static void printStarts() {
        for (Task t : starts) {
            System.out.printf("%s ", t.getTitle());
        }
        System.out.println();
    }
    public static void printNexts() {
        for (String s : next.keySet()) {
            ArrayList<Task> values = next.get(s);
            System.out.printf("%s -> ", s);
            for (Task t : values) {
                System.out.printf("%s, ", t.getTitle());
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printList(LinkedList<Task> list) {
        for (Task t : list) {
            System.out.printf("%s ", t.getTitle());
        }
        System.out.println();
    }*/
}