package ex01;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    static int amount = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Введите число: ");
            try {
                int tmp = scanner.nextInt();
                if (tmp < 2) {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }
                if (checkIsJustNumber(tmp)) System.out.println("true " + amount);
                else System.out.println("false " + amount);
            } catch (InputMismatchException e) {
                System.err.println("Введено не число!");
            }
        scanner.close();
    }

    static boolean checkIsJustNumber(int tmp) {
        amount = 1;
        for (int delim = 2; delim < Math.sqrt(tmp); delim++) {
            if (tmp % delim == 0) return false;
            amount++;
        }
        return true;
    }
}
