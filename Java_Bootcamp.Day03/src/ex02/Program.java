package src.ex02;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2 || args[0].length() < 13 || args[1].length() < 16) {
            System.err.print("Необходимо указывать в аргументах командной строки \"--arraySize=n --threadsCount=m\",  где n - размер массива, m - количество потоков!");
            System.exit(-1);
        }
        try {
            int arraySize = Integer.parseInt(args[0].substring(12));
            int threadsCount = Integer.parseInt(args[1].substring(15));
            if (arraySize < 1 || threadsCount < 1 || arraySize > 2000000 || arraySize < threadsCount) {
                System.err.print("Неправильные входные данные!");
                System.exit(-1);
            }
            int[] myArray = new int[arraySize];
            System.out.println("arraySize = " + arraySize + ", threadsCount = " + threadsCount);
            Sums sums = new Sums(myArray, threadsCount);
            int jSum = sums.justSum();
            System.out.println("Sum: " + jSum);
            int threadingSum = sums.multithreadingSum();
            System.out.println("Sum by threads: " + threadingSum);
        } catch (NumberFormatException e) {
            System.err.print(e.getMessage());
            System.exit(-1);
        }
    }

    public static void print(int[] myArray) {
        for (int j : myArray) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
