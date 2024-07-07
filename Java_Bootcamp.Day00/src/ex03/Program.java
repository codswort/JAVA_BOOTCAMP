package ex03;
import java.util.Scanner;
public class Program {
    static boolean error = false;
    static int numberOfWeek = 0;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int result = toConcatInt();
        if (result != -1) printResult(result);

    }

    static int toConcatInt() {
        int preResult = 0;
        while (numberOfWeek <= 18 && !error) {
            numberOfWeek++;
            String raw = scanner.nextLine();
            if (raw.equals("42")) break;
            if (!raw.equals("Week " + numberOfWeek)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int rawNumber1 = scanner.nextInt();
            if (!isValid(rawNumber1)) return -1;
            for (int i = 0; i < 4 && !error; i++) {
                int rawNumber2 = scanner.nextInt();
                if (!isValid(rawNumber2)) return -1;
                if (rawNumber1 > rawNumber2) rawNumber1 = rawNumber2;
            }
            preResult += rawNumber1;
            preResult *= 10;
            scanner.nextLine();
        }
        preResult /= 10;
        return preResult;
    }
    static boolean isValid(int n) {
        return (n > 0 && n < 10) ? true : false;
    }
    static void printResult(int input) {
        int output = 0;
        while (input > 0) {
            output += input % 10;
            output *= 10;
            input /= 10;
        }
        output /= 10;
        for (int i = 0; output > 0; i++) {
            System.out.print("Week " + (i + 1) + " ");
            for (int j = output % 10; j > 0; j--) {
                System.out.print("=");
                if (j == 1) System.out.println(">");
            }
            output /= 10;
        }
    }
}
