package ex01;
import java.util.*;
import java.io.*;
public class Comparator {
    private String path1 = new String();
    private String path2 = new String();
    private List<String> input1 = new ArrayList<>();
    private List<String> input2 = new ArrayList<>();
    private List<String> dictionary = new ArrayList<>();
    public Comparator(String path1, String path2) {
        this.path1 = "src/ex01/" + path1;
        this.path2 = "src/ex01/" + path2;
    }
    public double compareFiles() {
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(path1));
            BufferedReader br2 = new BufferedReader(new FileReader(path2));
            createFile();
            BufferedWriter br3 = new BufferedWriter(new FileWriter("src/ex01/dictionary.txt"));
            readFile(input1, br1);
            readFile(input2, br2);
            writeDictionary(dictionary, br3);
            int[] vector1 = new int[dictionary.size()];
            int[] vector2 = new int[dictionary.size()];
            addVectors(input1, vector1);
            addVectors(input2, vector2);
            return calculateSimilarity(vector1, vector2);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Double.NaN;
        }
    }
    private void writeDictionary(List<String> dictionary, BufferedWriter br3) throws IOException{
        for (String i : dictionary) {
            br3.write(i);
            br3.newLine();
        }
        br3.close();
    }
    private double calculateSimilarity(int[] vector1, int[] vector2) {
        int numerator = 0, denominator1 = 0, denominator2 = 0;
        for (int i = 0; i < vector1.length; i++) {
            numerator += vector1[i] * vector2[i];
            denominator1 += vector1[i] * vector1[i];
            denominator2 += vector2[i] * vector2[i];
        }
        return numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));

    }
    private void addVectors(List<String> list, int[] vector) {
        for (int i = 0; i < dictionary.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).equals(dictionary.get(i)))
                    vector[i]++;
            }
        }
    }

    private void readFile(List<String> list, BufferedReader br) throws IOException {
        String tmp;
        while ((tmp = br.readLine()) != null) {
            tmp = tmp.replaceAll("\\p{Punct}", "");
            String[] parts = tmp.split(" ");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].isEmpty()) continue;
                list.add(parts[i]);
                if (!dictionary.contains(parts[i]))
                    dictionary.add(parts[i]);
            }
        }
    }
    private void createFile() throws IOException {
        File file = new File("src/ex01/dictionary.txt");
        if (!file.exists()) file.createNewFile();
    }
}
