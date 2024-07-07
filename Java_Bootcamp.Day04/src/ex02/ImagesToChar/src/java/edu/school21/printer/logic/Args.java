package ImagesToChar.src.java.edu.school21.printer.logic;
import com.beust.jcommander.*;
@Parameters(separators = "=")
public class Args {
    @Parameter(names = {"--white"})
    private String white;

    @Parameter(names = {"--black"})
    private String black;

    public String getCol1() {
        return white;
    }

    public String getCol2() {
        return black;
    }

}
