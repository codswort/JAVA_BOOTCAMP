package src.ex01;

public class Hen implements Runnable {
    private int count = 0;
    private final Consumer consumer;
    public Hen(int count, Consumer consumer) {
        this.count = count;
        this.consumer = consumer;
    }
    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                consumer.print();
            } catch (InterruptedException ie) {
                System.err.println(ie.getMessage());
            }
        }
    }
}
