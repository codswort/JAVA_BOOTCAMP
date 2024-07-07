package src.ex00;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || args[0].length() < 9) {
            System.err.print("Необходимо указывать в аргументах командной строки \"--count=n\",  где n - количество!");
            System.exit(-1);
        }
        int count = -1;
        try {
            count = Integer.parseInt(args[0].substring(8));
            if (count <= 0) {
                System.err.print("Количество не можеть быть отрицательным!");
                System.exit(-1);
            }
            Thread t1 = new Thread(new Egg(count));
            Thread t2 = new Thread(new Hen(count));
            t1.start();
            t2.start();

            for(int i = 0; i < count; i++) {
                System.out.println("Human");
            }
        } catch (NumberFormatException e) {
            System.err.print("Указано нечисловое значение количества!");
            System.exit(-1);
        }
    }
}
