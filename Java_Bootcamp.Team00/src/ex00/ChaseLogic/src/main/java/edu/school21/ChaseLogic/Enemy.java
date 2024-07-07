package src.ex00.ChaseLogic.src.main.java.edu.school21.ChaseLogic;

import java.util.Arrays;
import java.util.Comparator;

public class Enemy {
    private int[] coordEnemy;

    public class Vector {
        private double dist;
        private int index;
        public Vector(double dist, int index) {
            this.dist = dist;
            this.index = index;
        }
    }

    public class VectorComparator implements Comparator<Vector> {
        @Override
        public int compare(Vector a, Vector b) {
            return Double.compare(a.dist, b.dist);
        }
    }

    public Enemy () {}

    public Enemy(int[] coordEnemy) {
        this.coordEnemy = coordEnemy;
    }

    public void setCoordEnemy(int[] coord) {
        coordEnemy = coord;
    }
    public int[] getCoordEnemy() {
        return coordEnemy;
    }

    public boolean coordEnemyUpdate(int[][] grid, int[] player) {
        int x = coordEnemy[0];
        int y = coordEnemy[1];
        int[][] nextMoves = new int[2][4];
        Vector[] vectors = new Vector[4];
        for (int i = 0; i < 4; i++) {
            nextMoves[0][i] = x;
            nextMoves[1][i] = y;
        }
        if (checkBordersMap(x + 1, y, grid.length)) {
            char cell = (char) grid[x + 1][y];
            if (cell != '#' && cell != 'X' && cell != 'O') {
                nextMoves[0][0] = x + 1;
                nextMoves[1][0] = y;
            }
        }

        if (checkBordersMap(x - 1, y, grid.length)) {
            char cell = (char) grid[x-1][y];
            if (cell != '#' && cell != 'X' && cell != 'O') {
                nextMoves[0][1] = x - 1;
                nextMoves[1][1] = y;
            }
        }

        if (checkBordersMap(x, y + 1, grid.length)) {
            char cell = (char) grid[x][y+1];
            if (cell != '#' && cell != 'X' && cell != 'O') {
                nextMoves[0][2] = x;
                nextMoves[1][2] = y + 1;
            }
        }

        if (checkBordersMap(x, y - 1, grid.length)) {
            char cell = (char) grid[x][y-1];
            if (cell != '#' && cell != 'X' && cell != 'O') {
                nextMoves[0][3] = x;
                nextMoves[1][3] = y - 1;
            }
        }

        for (int i = 0; i < 4; i++) {
            double dist = Math.sqrt(Math.pow(player[0] - nextMoves[0][i], 2) + Math.pow(player[1] - nextMoves[1][i], 2));
            vectors[i] = new Vector(dist, i);
        }
        Arrays.sort(vectors, new VectorComparator());
        if (coordEnemy[0] != nextMoves[0][vectors[0].index] || coordEnemy[1] != nextMoves[1][vectors[0].index]) {
            coordEnemy[0] = nextMoves[0][vectors[0].index];
            coordEnemy[1] = nextMoves[1][vectors[0].index];
        } else {
            coordEnemy[0] = nextMoves[0][vectors[1].index];
            coordEnemy[1] = nextMoves[1][vectors[1].index];
        }

        if (coordEnemy[0] == player[0] && coordEnemy[1] == player[1]) return false;
        return true;
    }
    private boolean checkBordersMap(int newX, int newY, int size) {
        if (newX < 0 || newY < 0 || newX >= size || newY >= size) return false;
        return true;
    }

}
