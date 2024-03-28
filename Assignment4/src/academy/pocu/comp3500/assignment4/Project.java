package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public final class Project {
    private final Task[] tasks;
    private final HashMap<String, Task> tasksMap = new HashMap<>();
    private final HashMap<String, Boolean> visited = new HashMap<>();
    private final HashMap<String, Integer> estimateMinSum = new HashMap<>();


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
        getPathRecursive(target, path);

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

        Task end = tasksMap.get(task);
        if (end.getPredecessors().isEmpty()) {
            return end.getEstimate();
        }

        visited.clear();
        HashMap<Task, Integer> bonusMap = new HashMap<>();
        ArrayList<Task> path = new ArrayList<>();
        getPathRecursive(end, path, bonusMap);
        getBonus(path, bonusMap);

        List<Task> pres = end.getPredecessors();
        int sum = 0;
        for (Task pre : pres) {
            sum += bonusMap.get(pre);
        }

        return Math.min(sum, end.getEstimate());
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

    private void getPathRecursive(final Task task, ArrayList<Task> path) {
        if (task.getPredecessors().isEmpty()) {
            return;
        }
        List<Task> pres = task.getPredecessors();

        for (Task pre : pres) {
            if (!visited.containsKey(pre.getTitle())) {
                visited.put(pre.getTitle(), true);
                getPathRecursive(pre, path);
                path.add(pre);
            }
        }
    }
    private void getPathRecursive(final Task task, ArrayList<Task> path,
                                  HashMap<Task, Integer> bonusMap) {
        if (task.getPredecessors().isEmpty()) {
            return;
        }
        ArrayList<Task> pres = new ArrayList<>(task.getPredecessors());
        sortPres(pres);

        for (Task pre : pres) {
            if (!visited.containsKey(pre.getTitle())) {
                visited.put(pre.getTitle(), true);
                getPathRecursive(pre, path, bonusMap);
                path.add(pre);
                bonusMap.put(pre, pre.getEstimate());
            }
        }
    }
    private void getBonus(ArrayList<Task> path, HashMap<Task, Integer> bonusMap) {
        for (Task task : path) {
            if (task.getPredecessors().isEmpty()) {
                continue;
            } else if (task.getPredecessors().size() == 1) {
                Task preTask = task.getPredecessors().get(0);
                int preCount = bonusMap.get(preTask);
                int nextCount = task.getEstimate();
                int bonus = Math.min(preCount, nextCount);
                bonusMap.put(preTask, preCount - bonus);
                bonusMap.put(task, bonus);
            } else {
                List<Task> pres = task.getPredecessors();
                int sum = 0;
                int limit = task.getEstimate();
                int preEst = 0;
                int required = 0;

                for (Task pre : pres) {
                    preEst = bonusMap.get(pre);
                    required = limit - sum;
                    sum += preEst;

                    int value = Math.max(0, preEst - required);
                    bonusMap.put(pre, value);
                    if (sum >= limit) {
                        sum = limit;
                        break;
                    }
                }

                bonusMap.put(task, sum);
            }
        }
    }

    private void getTasksMap() {
        for (Task task : tasks) {
            tasksMap.put(task.getTitle(), task);
        }
    }
    private void sortPres(ArrayList<Task> pres) {
        sortRecursive(pres, 0, pres.size() - 1);
    }
    private void sortRecursive(ArrayList<Task> list, int left, int right) {
        if (left >= right) {
            return;
        }
        int keepLeft = left;

        for (int i = left; i < right; ++i) {
            if (list.get(i).getEstimate() > list.get(right).getEstimate()) {
                swap(list, i, left);
                ++left;
            }
        }
        swap(list, left, right);

        sortRecursive(list, keepLeft, left - 1);
        sortRecursive(list, left + 1, right);
    }
    private void swap(ArrayList<Task> list, int i, int j) {
        Task temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
