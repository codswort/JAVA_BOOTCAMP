package src.ex01;

public class Consumer {
    static private boolean isEgg = true;
    public synchronized void print() throws InterruptedException {
        if (isEgg) System.out.println("Egg");
        else System.out.println("Hen");
        isEgg = !isEgg;
        notify();
    }
}
