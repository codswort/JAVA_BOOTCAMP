package src.ex00.Game.src.main.java.edu.school21.Game;

import src.ex00.ChaseLogic.src.main.java.edu.school21.ChaseLogic.Enemy;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class GameManager {
    private int enemiesCount, wallsCount, size;
    private String profile;
    private String[] args;
    private Player player;
    private Enemy[] allEnemies;
    Scanner scanner;

    public GameManager(Args arguments, String[] args) {
        this.enemiesCount = arguments.getEnemiesCount();
        this.wallsCount = arguments.getWallsCount();
        this.size = arguments.getSize();
        this.profile = arguments.getProfile();
        this.player = new Player();
        this.allEnemies = new Enemy[enemiesCount];
        this.scanner = new Scanner(System.in);
        this.args = args;
    }
    public boolean start() throws IOException {
        checkInputs();
        Architecture arch = new Architecture();
        String propertiesFilePath = profile.equals("production") ? "application-production.properties" : "application-dev.properties";
        Map<String, String> properties = arch.readPropertiesFile(propertiesFilePath);

        Field field = new Field(enemiesCount, wallsCount, size, player, allEnemies, properties);
        field.generateStartMap();
        field.paintMap();

        String direction = scanner.next();
        Moving move = new Moving(field);
        if (profile.equals("production")) return isProduction(field, move, direction);
        else return isDevelopment(field, move, direction);
    }
    private boolean containsString(String mainString, String subString) {
        return mainString.contains(subString);
    }
    private void checkInputs() {
        if (args.length != 4) {
            throw new IllegalArgumentException("Количество аргументов должно быть 4! Например, --enemiesCount=10 --wallsCount=10 --size=30 --profile=production");
        }
        if (enemiesCount+wallsCount+2 > size*size) {
            throw new IllegalArgumentException("Невозможно разместить указанное количество врагов и препятствий на карте заданного размера!");
        }
        if (!profile.equals("production") && !profile.equals("development")) {
            throw new IllegalArgumentException("В бесплатной версии этой игры нет такого режима, выберите \"production\" или \"development\"!");
        }
    }
    private boolean isProduction(Field field, Moving move, String direction) {
        while (true) {
            if (direction.equals("9")) return false;// если пользователь ввел 9, то проигрыш
            field.clearScene(true);
            move.setField(field);
            if (containsString("WwAaSsDd", direction) && move.menu(direction)) {//если пользователь смог сходить
                if (player.getCord().equals(field.getGoalCoord())) return winSets(field);//если игрок дошел до цели, про выигрыш
                if (!move.moveEnemies(allEnemies)) return lossSets(field);//если враг догнал игрока, то проигрыш
            }
            field.paintMap();
            direction = scanner.next();
        }
    }
    private boolean isDevelopment(Field field, Moving move, String direction) {
        while (true) {
            if (direction.equals("9")) return false;// если пользователь ввел 9, то проигрыш
            move.setField(field);

            if (containsString("WwAaSsDd8", direction)) {//если пользователь ввел валидное значение
                if (direction.equals("8")) {
                    boolean loss = !move.moveEnemies(allEnemies);
                    if (loss) return lossSets(field);//если враг догнал игрока, то проигрыш
                } else {
                    move.menu(direction);
                    if (player.getCord().equals(field.getGoalCoord())) return winSets(field);//если игрок дошел до цели, про выигрыш
                    if (!checkEnemiesCord(field)) return lossSets(field);//если игрок дошел до врага
                }
            }
            field.paintMap();
            direction = scanner.next();
        }
    }
    private boolean winSets(Field field) {
        field.getGrid()[player.getCord().getcordX()][player.getCord().getcordY()] = 777;
        field.paintMap();
        return true;
    }
    private boolean lossSets(Field field) {
        field.getGrid()[player.getCord().getcordX()][player.getCord().getcordY()] = 666;
        field.paintMap();
        return false;
    }
    private boolean checkEnemiesCord(Field field) {
        for (int i = 0; i < enemiesCount; i++) {
            int[] enemyCord = allEnemies[i].getCoordEnemy();
            if (enemyCord.equals(player.getCord())) {
                int[][] grid = field.getGrid();
                grid[enemyCord[0]][enemyCord[1]] = 0;
                return false;
            }
        }
        return true;
    }
}
