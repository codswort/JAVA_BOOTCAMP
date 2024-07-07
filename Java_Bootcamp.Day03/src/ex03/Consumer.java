package src.ex03;

public class Consumer {
    private static int i;
    public synchronized int getNextIndex() {
        return i++;
    }
}