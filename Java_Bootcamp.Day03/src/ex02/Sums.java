package src.ex02;
import java.util.Random;

public class Sums {
    private static int[] myArray;
    private static int threadsCount;
    private static int arraySize;
    private static int countsInEachThread;
    public Sums(int[] myArray, int threadsCount) {
        Sums.myArray = myArray;
        Sums.threadsCount = threadsCount;
        arraySize = myArray.length;
        countsInEachThread = (int)Math.ceil((double)arraySize/threadsCount);
        fillArray(myArray);
    }
    public int multithreadingSum() {
        int result = 0;
        Runner[] thread = new Runner[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            int start = i * countsInEachThread;
            int end = (i == threadsCount - 1) ? arraySize : (i + 1) * countsInEachThread;
            thread[i] = new Runner(myArray, start, end);
            thread[i].start();
            try {
                thread[i].join();
                result += thread[i].getResult();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    private static void fillArray(int[] myArray) {
        for (int i = 0; i < myArray.length; i++) {
            Random random = new Random();
            myArray[i] = random.nextInt(2001) - 1000;
        }
    }
    public int justSum() {
        int result = 0;
        for (int j : myArray) {
            result += j;
        }
        return result;
    }
}
