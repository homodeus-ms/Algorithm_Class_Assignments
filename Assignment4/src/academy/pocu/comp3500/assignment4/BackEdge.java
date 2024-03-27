package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

public class BackEdge extends Flow {

    public BackEdge(Task from, Task to) {
        super(from, to, 0);
    }
    @Override
    public void addFlow(int amount) {
        if (flow == 0) {
            flow -= amount;
        } else {
            flow += amount;
        }
    }
}
