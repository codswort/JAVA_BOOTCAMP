package src.ex00.Game.src.main.java.edu.school21.Game;
import com.beust.jcommander.*;
@Parameters(separators = "=")
public class Args {
    @Parameter(names = {"--enemiesCount"})
    private int enemiesCount;
    @Parameter(names = {"--wallsCount"})
    private int wallsCount;
    @Parameter(names = {"--size"})
    private int size;
    @Parameter(names = {"--profile"})
    private String profile;
    public int getEnemiesCount() {
        return enemiesCount;
    }
    public int getWallsCount() {
        return wallsCount;
    }
    public int getSize() {
        return size;
    }
    public String getProfile() {
        return profile;
    }
}