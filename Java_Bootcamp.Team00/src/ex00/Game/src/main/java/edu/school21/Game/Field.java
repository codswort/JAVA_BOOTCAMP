package src.ex00.Game.src.main.java.edu.school21.Game;
import java.util.*;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import src.ex00.ChaseLogic.src.main.java.edu.school21.ChaseLogic.Enemy;


public class Field {
    private int enemiesCount, wallsCount, size;
    private Ansi.BColor playerColor, goalColor, wallColor, enemyColor, emptyColor;
    private char enemyChar, playerChar, wallChar, goalChar, emptyChar;
    private Map<Coordinate, String> map = new HashMap<>();
    private int[][] grid;
    private Enemy[] allEnemies;
    private Player player;
    private Coordinate goalCord;
    public Field(int enemiesCount, int wallsCount, int size, Player player, Enemy[] allEnemies, Map<String, String> properties) {
        this.enemiesCount = enemiesCount;
        this.wallsCount = wallsCount;
        this.size = size;
        this.allEnemies = allEnemies;
        this.player = player;
        grid = new int[size][size];
        enemyChar = properties.containsKey("enemy.char") ? properties.get("enemy.char").charAt(0) : 'X';
        playerChar = properties.containsKey("player.char") ? properties.get("player.char").charAt(0) : 'o';
        wallChar = properties.containsKey("wall.char") ? properties.get("wall.char").charAt(0) : '#';
        goalChar = properties.containsKey("goal.char") ? properties.get("goal.char").charAt(0) : 'O';
        emptyChar = properties.containsKey("empty.char") ? properties.get("empty.char").charAt(0) : ' ';
        enemyColor = properties.containsKey("enemy.color") ? Ansi.BColor.valueOf(properties.get("enemy.color")) : Ansi.BColor.RED;
        playerColor = properties.containsKey("player.color") ? Ansi.BColor.valueOf(properties.get("player.color")) : Ansi.BColor.GREEN;
        wallColor = properties.containsKey("wall.color") ? Ansi.BColor.valueOf(properties.get("wall.color")) : Ansi.BColor.MAGENTA;
        goalColor = properties.containsKey("goal.color") ? Ansi.BColor.valueOf(properties.get("goal.color")) : Ansi.BColor.BLUE;
        emptyColor = properties.containsKey("empty.color") ? Ansi.BColor.valueOf(properties.get("empty.color")) : Ansi.BColor.YELLOW;
    }

    public void generateStartMap() {
        int[] startPosition = createRandom(111, null, null);//создание игрока
        player.setCord(new Coordinate(startPosition[0], startPosition[1]));
        int[] goalPosition = createRandom(79, null, null);//создание цели
        goalCord = new Coordinate(goalPosition[0], goalPosition[1]);
        for (int i = 0; i < wallsCount; i++) {//создание стен
            createRandom(35, startPosition, goalPosition);
        }
        for (int i = 0; i < enemiesCount; i++) {//создание врагов
            int[] enemyPosition = createRandom(88, null, null);
            Enemy enemy = new Enemy(enemyPosition);
            allEnemies[i] = enemy;
        }
    }



    private int[] createRandom(int role, int[] start, int[] end) {
        Random random = new Random();
        int[] position = null;
        int attempts = 0;
        while (attempts < size * size) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (grid[x][y] == 0) {
                int[] newPosition = {x, y};
                if (role != 35 || isReachableTarget(start, end, newPosition)) {
                    grid[x][y] = role;
                    position = newPosition;
                    break;
                }
            }
            attempts++;
        }
        if (position == null) throw new IllegalStateException("Не удалось найти подходящее место для размещения элемента.");
        return position;
    }
    private boolean isReachableTarget(int[] start, int[] end, int[] newPosition) {
        Queue<int[]> queue = new LinkedList<>();
        int[] durationRow = {-1, 1, 0, 0};
        int[] durationCol = {0, 0, -1, 1};
        boolean[][] visited = new boolean[size][size];
        visited[start[0]][start[1]] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            if (current[0] == end[0] && current[1] == end[1]) return true;
            for (int i = 0; i < 4; i++) {
                int newX = current[0] + durationRow[i];
                int newY = current[1] + durationCol[i];
                if (isValidMove(newX, newY, visited, newPosition)) {
                    queue.add(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        return false;
    }
    private boolean isValidMove(int newX, int newY, boolean[][] visited, int[] newPosition) {
        if (newX < 0 || newY < 0 || newX >= size || newY >= size) return false;
        if (visited[newX][newY]) return false;
        if (grid[newX][newY] == 35 || grid[newX][newY] == 88) return false;
        if (newPosition[0] == newX && newPosition[1] == newY) return false;
        return true;
    }
    public void paintMap() {
        ColoredPrinter coloredPrinter = new ColoredPrinter();
        for (int  i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j]==0) coloredPrinter.print(" " +emptyChar+" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, emptyColor);
                if (grid[i][j]==111) coloredPrinter.print(" " +playerChar+" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, playerColor);
                if (grid[i][j]==79) coloredPrinter.print(" " +goalChar+" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, goalColor);
                if (grid[i][j]==35) coloredPrinter.print(" " +wallChar+" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, wallColor);
                if (grid[i][j]==88) coloredPrinter.print(" " +enemyChar+" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, enemyColor);
                if (grid[i][j]==666)  coloredPrinter.print(" * ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.BLACK);
                if (grid[i][j]==777) coloredPrinter.print(" V ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.GREEN);
            }
            System.out.println();
        }
    }

    public int[][] getGrid() {
        return grid;
    }
    public Coordinate getPlayerCoord() {
        return player.getCord();
    }
    public Coordinate getGoalCoord() {
        return goalCord;
    }
    public void setPlayerCoord(Coordinate coord) {
        player.setCord(coord);
    }
    public void setEnemyCoord(int[] coord, int index) {
        allEnemies[index].setCoordEnemy(coord);
    }
    public void clearScene(boolean scene) {
        if (scene) {
//            try {
            System.out.print("\033[H\033[2J");
                //new ProcessBuilder("clear").inheritIO().start().waitFor();
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
    public int getCellGrid(int x, int y) {
        return grid[x][y];
    }
}
