import algorithm.mazegenerator.MazeGenerator;
import algorithm.mazegenerator.RandomisedPrim;
import com.jfoenix.controls.JFXComboBox;
import controller.VisualiserController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BoardModel;
import org.testfx.api.FxRobot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class VisuAlgorithmTest {
    private VisualiserController controller;

    @Start
    public void start(Stage stage) {
        //show login screen
        var loader = new FXMLLoader(getClass().getResource("view/visualiser.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = loader.getController();

        Scene mainScene = new Scene(root);
        controller.initScene(mainScene);

        mainScene.setFill(null);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(mainScene);
        stage.setTitle("VisuAlgorithms");
        stage.show();

    }

    @Test
    public void testMaze(FxRobot robot) {
        Platform.runLater(() -> {

            JFXComboBox<String> algorithmCombo = robot.lookup("algorithmCombo").query();
            Assert.assertNotNull(algorithmCombo);
            for (Node child : algorithmCombo.getChildrenUnmodifiable()) {
                if (child.getStyleClass().contains("Random Prim's Maze")) {
                    Node region = ((Pane) child).getChildren().get(0);
                    robot.clickOn(region);
                }
            }
            controller.changeAlgorithm();

            MazeGenerator mazeGenerator = new RandomisedPrim();
            BoardModel model = new BoardModel();
            mazeGenerator.setModel(model);

            mazeGenerator.generateMaze();

            System.out.print("After Maze");
            model.printMaze();

            controller.performAlgorithm();

            for (int i = 0; i < BoardModel.COLS * BoardModel.ROWS; i++) {
                .assertTrue(model.getBoard().get(i).isWall()
                        == controller.getVisModel().asBoardModel().getBoard().get(i).isWall());
            }
        });
    }


}
