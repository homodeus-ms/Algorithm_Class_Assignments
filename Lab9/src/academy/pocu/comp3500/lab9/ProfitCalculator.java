package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.Task;


public class ProfitCalculator {
    private static Node root = null;

    public static int findMaxProfit(final Task[] tasks, final int[] skillLevels) {
        if (tasks.length == 0 || skillLevels.length == 0) {
            return 0;
        }
        root = new Node(tasks[0].getDifficulty());
        root.updateMaxProfit(tasks[0].getProfit());

        for (int i = 1; i < tasks.length; ++i) {
            insertNodeRecursive(root, tasks[i]);
        }

        int[] max = new int[1];
        int sum = 0;

        for (int i = 0; i < skillLevels.length; ++i) {
            getMaxProfitRecursive(root, skillLevels[i], max);
            sum += max[0];
            max[0] = 0;
        }

        return sum;
    }
    private static Node insertNodeRecursive(Node start, Task task) {
        if (start == null) {
            Node newNode = new Node(task.getDifficulty());
            newNode.updateMaxProfit(task.getProfit());
            return newNode;
        }
        if (start.getDifficulty() == task.getDifficulty()) {
            start.updateMaxProfit(task.getProfit());
        } else if (start.getDifficulty() > task.getDifficulty()) {
            start.setLeft(insertNodeRecursive(start.getLeft(), task));
        } else {
            start.setRight(insertNodeRecursive(start.getRight(), task));
        }

        return start;
    }
    private static void getMaxProfitRecursive(Node node, int skillLevel, int[] max) {
        if (node == null) {
            return;
        }

        if (skillLevel >= node.getDifficulty() && max[0] < node.getMaxProfits()) {
            max[0] = node.getMaxProfits();
        }

        getMaxProfitRecursive(node.getLeft(), skillLevel, max);
        getMaxProfitRecursive(node.getRight(), skillLevel, max);
    }


    private static void printTree(Node start) {
        if (start == null) {
            return;
        }
        printTree(start.getLeft());

        System.out.printf("Diffi : %d, Prof: %d\n", start.getDifficulty(), start.getMaxProfits());

        printTree(start.getRight());
    }
}
