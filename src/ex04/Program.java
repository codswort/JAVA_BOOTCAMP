package ex04;
import java.util.Scanner;
public class Program {
    static Scanner scanner = new Scanner(System.in);
    static int[] amount = new int[65535];
    static int amountLetters = 0;
    static char[] symbols = new char[65535];
    public static void main(String[] args) {
        calculateAmount();
        findMax();
        printGraph();
    }
    static void printGraph() {
        int maxVal = amount[0] * 100 / amount[0] / 10;
        for (int i = 0; amount[i] == amount[0]; i++)
            System.out.print(amount[i] + "\t");
        System.out.println();
        for (int i = maxVal; i > 0; i--) {
            for (int j = 0; j < maxVal; j++) {
                int coeff = amount[j] * 100 / amount[0] / 10;
                if ( coeff >= i) System.out.print("#\t");
                if (coeff == i - 1) {
                    if (amount[j] != 0) System.out.print(amount[j] + "\t");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < amountLetters && i < maxVal; i++) {
            System.out.print(symbols[i] + "\t");
        }
    }
    static void calculateAmount() {
        String input = scanner.nextLine();
        char[] tmp = input.toCharArray();
        for (int i = 0; i < tmp.length; i++) {
            if (++amount[tmp[i]] == 1) amountLetters++;
            symbols[tmp[i]] = tmp[i];
        }
    }
    static void findMax() {
        for (int out = amount.length - 1; out >= 1; out--) {
            for (int in = 0; in < out; in++) {
                if(amount[in] < amount[in + 1]) toSwap(in, in + 1);
            }
        }
    }
    static void toSwap(int first, int second) {
        int tmp = amount[first];
        char tmpCh = symbols[first];
        amount[first] = amount[second];
        symbols[first] = symbols[second];
        amount[second] = tmp;
        symbols[second] = tmpCh;
    }
}
