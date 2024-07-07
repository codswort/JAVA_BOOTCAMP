package src.ex00.Game.src.main.java.edu.school21.Game;
import java.io.IOException;
import com.beust.jcommander.JCommander;


public class Main {
    public static void main(String[] args) throws IOException {
        Args arguments = new Args();
        JCommander jcommander = JCommander.newBuilder().addObject(arguments).build();
        jcommander.parse(args);
        GameManager gameManager = new GameManager(arguments, args);
        boolean result = gameManager.start();
        if (result) printVictoryMessage();
        else printLossMessage();

    }
    private static void printVictoryMessage() {
        System.out.println("[**]          [**]    [******]    [**]        [**]");
        System.out.println("[**]   [**]   [**]      [**]      [**][**]    [**]");
        System.out.println("[**]   [**]   [**]      [**]      [**]  [**]  [**]");
        System.out.println("[**] [**][**] [**]      [**]      [**]    [**][**]");
        System.out.println("[**]          [**]    [******]    [**]        [**]\n");

        String message = "Congratulations on your amazing victory!\n";
        System.out.println(message);
        printStars(3);
    }
    private static void printLossMessage() {
        System.out.println("[**]          [**][**][**]    [**][**][**]    [**][**][**]");
        System.out.println("[**]          [**]    [**]    [**]            [**]");
        System.out.println("[**]          [**]    [**]        [**]            [**]");
        System.out.println("[**]          [**]    [**]            [**]            [**]");
        System.out.println("[**][**][**]  [**][**][**]    [**][**][**]    [**][**][**]\n");

        String message = "Unfortunately, you lost this time!\n";
        System.out.println(message);
        printStars(3);
    }

    private static void printStars(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println("**************************************************");
        }
    }



}




