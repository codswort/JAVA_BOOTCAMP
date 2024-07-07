package ex05;
import java.util.Scanner;
public class Program {
    static Scanner scanner = new Scanner(System.in);
    static String[] studentNames = new String[11];
    static int[][] schedule = new int[8][7];
    static int[][][] graph = new int[11][31][7];
    public static void main(String[] args) {
        int lenName = inputNames();
        int[] amountDays = inputSchedule();
        if (amountDays != null) {
            inputAttendance();
            printResult(amountDays, lenName);
        }
    }
    static void printResult(int[] count, int lenName) {
        for (int p = 0; p < lenName; p++) System.out.print(" ");
        for (int k = 1; k < 31; k++) {
            for (int j = 1; j < 7; j++) {
                int dd = getDayInWeek(k);
                if (count[dd] == 1 && count[j + 12] == 1 && schedule[dd][j] == 1) {
                    System.out.print(j + ":00 " + getNameOfDay(dd) + " ");
                    if (k < 10) System.out.print(" ");
                    System.out.print(k + "|");
                }
            }
        }
        System.out.println();
        for (int i = 1; i < 11; i++) {
            if (studentNames[i] == null) continue;
            int l = lenName-studentNames[i].length();
            boolean first = true;
            if (studentNames[i] == null) continue;
            System.out.print(studentNames[i]);
            for (int k = 1; k < 31; k++) {
                for (int j = 1; j < 7; j++) {
                    int dd = getDayInWeek(k);
                    if (count[dd] != 1 || count[j + 12] != 1 || schedule[dd][j] != 1) continue;
                    if (!first) l = 0;
                        for (int pr = 0; pr < (8+l); pr++) {
                            System.out.print(" ");//8
                            first = false;
                        }
                    if (graph[i][k][j] != 0) {
                        if (graph[i][k][j] == 1) System.out.print(" ");
                        System.out.print(graph[i][k][j]);
                    } else {
                        System.out.print("  ");
                    }
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }
    static int getNumberWeek(int input) {
        if (input < 1 || input > 30) return -1;
        return (input / 7) + 1;
    }
    static int getDayInWeek(int input) {
        if (input < 1 || input > 30) return -1;
        switch (input - (input / 7) * 7) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            default: return -1;
        }
    }

    static int inputNames() {
        int amountStudents = 0, lenghtName = 0;
        while (true) {
            amountStudents++;
            String name = scanner.next();
            if (name.length() > 10) return 0;
            if (name.equals(".")) {
                break;
            }
            if (lenghtName < name.length()) lenghtName = name.length();
            studentNames[amountStudents] = name;
            if (amountStudents == 10) break;
        }
        return lenghtName;
    }

    static int[] inputSchedule() {
        String input = "";
        int[] amountDays = new int[19];
        while (true) {
            input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (input.equals(".")) break;
            int day = getNumberOfDay(parts[1]);
            int hour = Integer.parseInt(parts[0]);
            if (day == 0 || hour < 1 || hour > 6) return null;
            schedule[day][hour] = 1;
            amountDays[day] = 1;
            amountDays[hour + 12] = 1;
        }


        return amountDays;
    }

    static int getIndexName(String input) {
        for (int i = 0; i < studentNames.length; i++) {
            if (studentNames[i] != null && studentNames[i].equals(input)) return i;
        }
        return -1;
    }
    static boolean checkHour(int input) {
        for (int i = 0; i < 8; i++) {
            if (schedule[i][input] == 1) return true;
        }
        return false;
    }

    static boolean isEnoughLessons(int[] arr) {
        for (int i = 1; i < 8; i++)
            if (arr[i] > 10) return true;
        return false;
    }

    static void inputAttendance() {
        int[] lessonsInWeek = new int[8];
        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (input.equals(".")) break;
            int name = getIndexName(parts[0]);
            int hour = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            int numbersDay = -1;
            int numbersWeek = getNumberWeek(day);
            if (parts.length != 4) return;
            if (name != -1 && checkHour(hour) && (numbersDay = getDayInWeek(day)) != -1 && numbersWeek != -1) {
                if (schedule[numbersDay][hour] != 0 && graph[name][day][hour] == 0) {
                    lessonsInWeek[numbersWeek]++;
                    if (isEnoughLessons(lessonsInWeek)) break;
                    if (parts[3].equals("HERE"))
                        graph[name][day][hour] = 1;
                    else if (parts[3].equals("NOT_HERE"))
                        graph[name][day][hour] = -1;
                }
            }
        }
    }

    static int getNumberOfDay(String str) {
        switch (str) {
            case "MO":
                return 1;
            case "TU":
                return 2;
            case "WE":
                return 3;
            case "TH":
                return 4;
            case "FR":
                return 5;
            case "SA":
                return 6;
            case "SU":
                return 7;
            default: return 0;
        }
    }
    static String getNameOfDay(int number) {
        switch (number) {
            case 1:
                return "MO";
            case 2:
                return "TU";
            case 3:
                return "WE";
            case 4:
                return "TH";
            case 5:
                return "FR";
            case 6:
                return "SA";
            case 7:
                return "SU";
            default: return "#";
        }
    }
}
