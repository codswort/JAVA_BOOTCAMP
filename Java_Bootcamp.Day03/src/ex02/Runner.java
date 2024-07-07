package src.ex02;

public class Runner extends Thread {
    private static int[] myArray;
    private static int summ;
    private static int start;
    private static int end;
    public Runner(int[] myArray, int start, int end) {
        Runner.myArray = myArray;
        Runner.start = start;
        Runner.end = end;
    }

    @Override
    public void run() {
        int tmp = 0;
        for (int i = start; i < end; i++) {
            tmp += myArray[i];
        }
        System.out.println(this.getName() + ": from " + start + " to " + (end-1) + " sum is " + tmp);
        summ = tmp;
    }
    public int getResult() {
        return summ;
    }
}
