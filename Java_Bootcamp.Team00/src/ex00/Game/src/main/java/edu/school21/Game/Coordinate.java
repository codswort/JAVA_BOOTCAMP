package src.ex00.Game.src.main.java.edu.school21.Game;

import java.util.Objects;

public class Coordinate {
    private int cordX;
    private int cordY;
    Coordinate() {
    }
    public Coordinate(int cordX, int cordY) {
        this.cordX = cordX;
        this.cordY = cordY;
    }
    public void setCoord(int cordX, int cordY) {
        this.cordX = cordX;
        this.cordY = cordY;
    }
    public int getcordX() {
        return this.cordX;
    }
    public int getcordY() {
        return this.cordY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return cordX == that.cordX && cordY == that.cordY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cordX, cordY);
    }
}


