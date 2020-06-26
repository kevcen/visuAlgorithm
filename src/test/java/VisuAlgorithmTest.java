import algorithm.mazegenerator.MazeGenerator;
import algorithm.mazegenerator.RandomisedPrim;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.BoardModel;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

public class VisuAlgorithmTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        stage.show();
    }

    @Test
    public void test() {
        MazeGenerator mazeGenerator = new RandomisedPrim();

        BoardModel model = new BoardModel();
        System.out.print("Before ");
        model.printMaze();
        mazeGenerator.setModel(model);

        mazeGenerator.generateMaze();

        System.out.print("After ");
        model.printMaze();
    }


}
