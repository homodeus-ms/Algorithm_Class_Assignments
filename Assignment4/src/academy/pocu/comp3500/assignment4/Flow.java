package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

public class Flow {
    protected final Task from;
    protected final Task to;
    protected int flow = 0;
    protected int max;

    public Flow(Task from, Task to, int max) {
        this.from = from;
        this.to = to;
        this.max = max;
    }

    public Task getFrom() {
        return from;
    }

    public Task getTo() {
        return to;
    }

    public int getFlow() {
        return flow;
    }
    public void addFlow(int amount) {

    }
    public int getRemains() {
        return max - flow;
    }
    public boolean isAlive() {
        return max - flow != 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof Flow)) {
            return false;
        }
        Flow o = (Flow) other;

        return from == o.from && to == o.to;
    }
    @Override
    public int hashCode() {
        return from.getTitle().hashCode() + to.getTitle().hashCode();
    }
}
