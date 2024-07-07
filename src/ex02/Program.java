package ex02;
import java.util.Scanner;

public class Program {
    static int amount = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите число: ");
                int raw = scanner.nextInt();
                int tmp = getSumOfNumber(raw);
                if (raw == 42) {
                    System.out.println("Count of coffee-request – " + amount);
                    break;
                }
                if (tmp > 2 && checkIsJustNumber(tmp)) amount++;
        }
        scanner.close();
    }

    static boolean checkIsJustNumber(int tmp) {
        for (int delim = 2; delim < Math.sqrt(tmp); delim++) {
            if (tmp % delim == 0) return false;
        }
        return true;
    }

    static int getSumOfNumber(int input) {
        int output = 0;
        while (input > 0) {
            output += input % 10;
            input /= 10;
        }
        return output;
    }
}
