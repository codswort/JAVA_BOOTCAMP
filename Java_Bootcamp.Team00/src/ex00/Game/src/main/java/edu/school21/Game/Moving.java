package src.ex00.Game.src.main.java.edu.school21.Game;


import src.ex00.ChaseLogic.src.main.java.edu.school21.ChaseLogic.Enemy;

public class Moving {
    Field field;
    int size;
    public Moving(Field field) {
        this.field = field;
        this.size = field.getGrid().length;
    }
    public boolean menu(String direction) {
        switch (direction) {
            case "W":
            case "w":
                return moveUp();
            case "A":
            case "a":
                return moveLeft();
            case "D":
            case "d":
                return moveRight();
            case "S":
            case "s":
                return moveDown();
        }
        return true;
    }

    private boolean moveUp() {
        int cordX = field.getPlayerCoord().getcordX();
        int cordY = field.getPlayerCoord().getcordY();
        int[][] grid = field.getGrid();
        if (cordX > 0 && field.getGrid()[cordX-1][cordY] != 35) {
            grid[cordX-1][cordY] = 111;
            grid[cordX][cordY] = 0;
            field.setPlayerCoord(new Coordinate(cordX-1, cordY));
            return true;
        }
        return false;
    }
    private boolean moveLeft() {
        int cordX = field.getPlayerCoord().getcordX();
        int cordY = field.getPlayerCoord().getcordY();
        int[][] grid = field.getGrid();
        if (cordY > 0 && field.getGrid()[cordX][cordY-1] != 35) {
            grid[cordX][cordY-1] = 111;
            grid[cordX][cordY] = 0;
            field.setPlayerCoord(new Coordinate(cordX, cordY-1));
            return true;
        }
        return false;
    }
    private boolean moveRight() {
        int cordX = field.getPlayerCoord().getcordX();
        int cordY = field.getPlayerCoord().getcordY();
        int[][] grid = field.getGrid();
        if (cordY < size-1 && field.getGrid()[cordX][cordY+1] != 35) {
            grid[cordX][cordY+1] = 111;
            grid[cordX][cordY] = 0;
            field.setPlayerCoord(new Coordinate(cordX, cordY+1));
            return true;
        }
        return false;
    }
    private boolean moveDown() {
        int cordX = field.getPlayerCoord().getcordX();
        int cordY = field.getPlayerCoord().getcordY();
        int[][] grid = field.getGrid();
        if (cordX < size-1 && field.getGrid()[cordX+1][cordY] != 35) {
            grid[cordX+1][cordY] = 111;
            grid[cordX][cordY] = 0;
            field.setPlayerCoord(new Coordinate(cordX+1, cordY));
            return true;
        }
        return false;
    }
    public boolean moveEnemies(Enemy[] allEnemies) {
        for (int i = 0; i < allEnemies.length; i++) {
            int preX = allEnemies[i].getCoordEnemy()[0];
            int preY = allEnemies[i].getCoordEnemy()[1];
            int[] playerCord = {field.getPlayerCoord().getcordX(), field.getPlayerCoord().getcordY()};
            int[][] grid = field.getGrid();
            if (playerCord[0] == preX && playerCord[1] == preY) {
                grid[preX][preY] = 0;
                return false;
            }
            boolean gameResult = allEnemies[i].coordEnemyUpdate(field.getGrid(), playerCord);
            if (!gameResult) {
                grid[preX][preY] = 0;
                return false;
            }
            int x = allEnemies[i].getCoordEnemy()[0];
            int y = allEnemies[i].getCoordEnemy()[1];
            grid[preX][preY] = 0;
            grid[x][y] = 88;
            field.setEnemyCoord(allEnemies[i].getCoordEnemy(), i);
        }
        return true;
    }
    public void setField(Field field) {
        this.field = field;
    }

}


