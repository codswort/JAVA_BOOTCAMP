package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) System.err.println("Необходимо указывать 2 входных файла!");
        Comparator comparator = new Comparator(args[0], args[1]);
        double similarity = comparator.compareFiles();
        if (!Double.isNaN(similarity))
            System.out.print("Similarity = " + (Math.floor(similarity * 100) / 100));
        else {
            System.err.println("Один из входных файлов не содержит текста!");
        }
    }
}
