import controller.VisualiserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //show login screen
        var loader = new FXMLLoader(getClass().getResource("view/visualiser.fxml"));
        Parent root = loader.load();
        VisualiserController controller = loader.getController();

        Scene mainScene = new Scene(root);
        controller.addScene(mainScene);

//        mainScene.setFill(null);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(mainScene);
        stage.setTitle("VisuAlgorithms");
        stage.show();
    }
}
