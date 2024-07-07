package src.ex01;

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
            Consumer consumer = new Consumer();
            Thread t1 = new Thread(new Egg(count, consumer));
            Thread t2 = new Thread(new Hen(count, consumer));
            t1.start();
            t2.start();

        } catch (NumberFormatException e) {
            System.err.print("Указано нечисловое значение количества!");
            System.exit(-1);
        }
    }
}
