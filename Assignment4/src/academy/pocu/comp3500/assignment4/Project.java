package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public final class Project {
    private final Task[] tasks;
    private final HashMap<String, Task> tasksMap = new HashMap<>();
    private final HashMap<Task, List<Task>> nextMap = new HashMap<>();
    private final HashMap<String, Boolean> visited = new HashMap<>();
    private final LinkedList<Task> cycle = new LinkedList<>();
    private final HashMap<String, Boolean> cycleMap = new HashMap<>();

    private final HashMap<String, Integer> estimateSum = new HashMap<>();
    private final HashMap<String, Integer> estimateMinSum = new HashMap<>();
    private final HashMap<Task, Integer> bonusCountMap = new HashMap<>();
    private final HashMap<Task, ArrayList<Flow>> flowMap = new HashMap<>();


    public Project(final Task[] tasks) {
        this.tasks = tasks;
        getTasksMap();

    }
    public int findTotalManMonths(final String task) {
        visited.clear();
        int result = 0;
        Task target = tasksMap.get(task);

        if (target == null) {
            return 0;
        }

        ArrayList<Task> path = new ArrayList<>();
        findTotalManMonthRecursive2(target, path);
        /*for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                result = findTotalManMonthRecursive(t);
                break;
            }
        }*/
        for (Task t : path) {
            result += t.getEstimate();
        }
        result += target.getEstimate();

        return result;
    }
    public int findMinDuration(final String task) {
        visited.clear();

        int result = 0;
        for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                result = findMinDurationRecursive(t);
                break;
            }
        }
        return result;
    }

    public int findMaxBonusCount(final String task) {

        Task target = tasksMap.get(task);
        if (target.getPredecessors().isEmpty()) {
            return target.getEstimate();
        }

        getNextMap();
        visited.clear();
        flowMap.clear();


        ArrayList<Task> starts = new ArrayList<>();
        HashMap<Flow, Boolean> added = new HashMap<>();
        getFlows(target, starts, added);


        boolean checkAll;

        while (true) {
            checkAll = false;
            ArrayList<Flow> flows = new ArrayList<>();
            for (Task start : starts) {
                if (getProcess(start, target, flows)) {
                    break;
                } else {
                    if (start == starts.get(starts.size() - 1)) {
                        checkAll = true;
                    }
                }
            }

            if (checkAll) {
                break;
            }
            int min = getMin(flows);
            subtractFlowAmount(flows, min);
        }

        List<Task> pres = target.getPredecessors();
        ArrayList<Flow> flowsToTarget = new ArrayList<>();
        for (Task pre : pres) {
            ArrayList<Flow> flows = flowMap.get(pre);
            flowsToTarget.addAll(flows);
        }

        int sum = 0;
        for (Flow flow : flowsToTarget) {
            Task from = flow.from;
            int value = flow.getFlow();
            if (from.getPredecessors().isEmpty()) {
                value = from.getEstimate();
            }

            if (value > 0) {
                sum += value;
            }
        }


        return Math.min(sum, target.getEstimate());
    }
    private boolean getProcess(Task start, Task end, ArrayList<Flow> flows) {
        if (start == null) {
            return false;
        }
        if (start == end) {
            return true;
        }

        Flow flow = getFlowOrNull(start);
        Task nextTask = null;
        if (flow != null) {
            nextTask = flow.to;
            flows.add(flow);
        }
        return getProcess(nextTask, end, flows);
    }
    private void subtractFlowAmount(ArrayList<Flow> flows, int value) {
        for (Flow flow : flows) {
            flow.addFlow(value);
        }
    }
    private int getMin(ArrayList<Flow> flows) {
        int min = Integer.MAX_VALUE;
        int remains = 0;
        for (Flow flow : flows) {
            remains = flow.getRemains();
            min = Math.min(min, remains);
        }
        return min;
    }
    private Flow getFlowOrNull(Task task) {
        ArrayList<Flow> flows = flowMap.get(task);
        for (Flow flow : flows) {
            if (flow.isAlive()) {
                return flow;
            }
        }
        return null;
    }

    private void getFlows(Task task, ArrayList<Task> starts, HashMap<Flow, Boolean> added) {

        if (task.getPredecessors().isEmpty()) {
            if (!visited.containsKey(task.getTitle())) {
                starts.add(task);
                visited.put(task.getTitle(), true);
            }


            return;
        }

        List<Task> pres = task.getPredecessors();

        for (Task pre : pres) {
            addBackEdgeToFlowMap(task, pre, added);
            getFlows(pre, starts, added);
            addEdgeToFlowMap(pre, task, added);
        }

    }
    private void addEdgeToFlowMap(Task from, Task to, HashMap<Flow, Boolean> added) {
        Flow edge = new Edge(from, to);
        if (!flowMap.containsKey(from)) {
            flowMap.put(from, new ArrayList<>());
        }
        if (added.containsKey(edge)) {
            return;
        }
        flowMap.get(from).add(edge);
        added.put(edge, true);
    }
    private void addBackEdgeToFlowMap(Task from, Task to, HashMap<Flow, Boolean> added) {
        Flow backEdge = new BackEdge(from, to);
        if (!flowMap.containsKey(from)) {
            flowMap.put(from, new ArrayList<>());
        }
        if (added.containsKey(backEdge)) {
            return;
        }
        flowMap.get(from).add(backEdge);
        added.put(backEdge, true);
    }


    private int findMinDurationRecursive(final Task task) {
        if (task.getPredecessors().isEmpty()) {
            visited.put(task.getTitle(), true);
            estimateMinSum.put(task.getTitle(), task.getEstimate());
            return task.getEstimate();
        }
        if (estimateMinSum.containsKey(task.getTitle())) {
            return estimateMinSum.get(task.getTitle());
        }

        List<Task> pres = task.getPredecessors();
        int max = 0;
        int sum = 0;

        for (Task pre : pres) {
            if (!visited.containsKey(pre.getTitle())) {
                sum = findMinDurationRecursive(pre);
            } else {
                sum = estimateMinSum.get(pre.getTitle());
            }
            max = Math.max(max, sum);
        }

        visited.put(task.getTitle(), true);
        int result = max + task.getEstimate();
        estimateMinSum.put(task.getTitle(), result);
        return result;
    }

    private void findTotalManMonthRecursive2(final Task task, ArrayList<Task> path) {
        if (task.getPredecessors().isEmpty()) {
            //path.add(task);
            return;
        }
        List<Task> pres = task.getPredecessors();

        for (Task pre : pres) {
            if (!visited.containsKey(pre.getTitle())) {
                visited.put(pre.getTitle(), true);
                findTotalManMonthRecursive2(pre, path);
                path.add(pre);
            }
        }
    }

    private int findTotalManMonthRecursive(final Task task) {
        if (task.getPredecessors().isEmpty()) {
            visited.put(task.getTitle(), true);
            estimateSum.put(task.getTitle(), task.getEstimate());
            return task.getEstimate();
        }
        if (estimateSum.containsKey(task.getTitle())) {
            return estimateSum.get(task.getTitle());
        }

        List<Task> pres = task.getPredecessors();

        int sum = 0;

        for (Task pre : pres) {
            if (estimateSum.containsKey(pre.getTitle())) {
                sum += estimateSum.get(pre.getTitle());
            } else if (!visited.containsKey(pre.getTitle())) {
                sum += findTotalManMonthRecursive(pre);
            }
        }

        visited.put(task.getTitle(), true);
        int result = sum + task.getEstimate();
        estimateSum.put(task.getTitle(), result);
        return result;
    }

    private void getNextMap() {
        for (Task task : tasks) {
            if (!nextMap.containsKey(task)) {
                nextMap.put(task, new ArrayList<>());
            }
            List<Task> pres = task.getPredecessors();

            if (pres.isEmpty()) {
                bonusCountMap.put(task, task.getEstimate());
            }

            for (Task t : pres) {
                if (!nextMap.containsKey(t)) {
                    nextMap.put(t, new ArrayList<>());
                }
                nextMap.get(t).add(task);
            }
        }
    }

    private List<Task> findSchedule(boolean includeMaintenance) {
        getNextMap();
        List<Task> orderedTasks = new LinkedList<>();
        LinkedList<Task> dfsList = new LinkedList<>();
        visited.clear();

        for (Task task : bonusCountMap.keySet()) {
            visited.put(task.getTitle(), true);
            searchDfs1(task, dfsList);
        }
        visited.clear();

        for (Task task : dfsList) {
            if (!visited.containsKey(task.getTitle())) {
                getTaskOrder(task, orderedTasks);
            }
        }

        if (includeMaintenance) {
            orderedTasks.addAll(cycle);
        }

        return orderedTasks;
    }
    private void getTaskOrder(Task task, List<Task> orderedTasks) {
        List<Task> pres = task.getPredecessors();

        if (!pres.isEmpty() && !hasVisitedAllpres(task)) {
            visited.put(task.getTitle(), true);
            putCycle(task);
        } else {
            orderedTasks.add(task);
            visited.put(task.getTitle(), true);
        }
    }
    private void searchDfs1(Task task, LinkedList<Task> dfsList) {
        List<Task> nexts = nextMap.get(task);
        for (Task next : nexts) {
            if (!visited.containsKey(next.getTitle())) {
                visited.put(next.getTitle(), true);
                searchDfs1(next, dfsList);
            }
        }
        dfsList.addFirst(task);
    }
    private void putCycle(Task task) {
        List<Task> nexts = nextMap.get(task);
        for (Task next : nexts) {
            if (!visited.containsKey(next.getTitle())) {
                visited.put(next.getTitle(), true);
                putCycle(next);
            }
        }
        cycle.addFirst(task);
        cycleMap.put(task.getTitle(), true);
    }
    private boolean hasVisitedAllpres(Task task) {
        List<Task> pres = task.getPredecessors();
        for (Task pre : pres) {
            if (!visited.containsKey(pre.getTitle())) {
                return false;
            }
        }
        return true;
    }

    private void getTasksMap() {
        for (Task task : tasks) {
            tasksMap.put(task.getTitle(), task);
        }
    }
    private void printMap() {
        for (Task task : nextMap.keySet()) {
            System.out.printf("=== %s ===\n", task.getTitle());
            List<Task> nexts = nextMap.get(task);
            for (Task next : nexts) {
                System.out.printf("%s ", next.getTitle());
            }
            System.out.println();
        }
        System.out.println();
    }
    private void printMap(HashMap<String, Boolean> map) {
        for (String str : map.keySet()) {
            System.out.printf("%s ", str);
        }
        System.out.println();
    }
}
