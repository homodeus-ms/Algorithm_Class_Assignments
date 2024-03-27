package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

public class Edge extends Flow {

    public Edge(Task from, Task to) {
        super(from, to, getEst(from, to));

    }
    public static int getEst(Task from, Task to) {
        return Math.min(from.getEstimate(), to.getEstimate());
    }
    @Override
    public void addFlow(int amount) {
        flow += amount;
    }
}
