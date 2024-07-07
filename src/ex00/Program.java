package ex00;
public class Program {
    public static void main(String[] args) {
        int input = 479598;
        int output = 0;
        for(int i = 0; i < 6; i++) {
            output += input % 10;
            input /= 10;
        }
        System.out.println(output);
    }
}
