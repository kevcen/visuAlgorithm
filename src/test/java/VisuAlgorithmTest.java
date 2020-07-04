import algorithm.mazegenerator.MazeGenerator;
import algorithm.mazegenerator.RandomisedKruskal;
import algorithm.mazegenerator.RandomisedPrim;
import algorithm.search.BinarySearch;
import algorithm.search.LinearSearch;
import algorithm.search.Search;
import algorithm.sort.*;
import com.jfoenix.controls.JFXComboBox;
import controller.VisualiserController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BarsModel;
import model.BoardModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class VisuAlgorithmTest {

    @Start
    public void start(Stage stage) {
        stage.show();
    }

    @Test
    public void testMazeGenerators() {
        testMazeGeneratorHelper(new RandomisedPrim());

        testMazeGeneratorHelper(new RandomisedKruskal());
    }

    // Uncomment below lines to print out the state of the board
    private void testMazeGeneratorHelper(MazeGenerator mazeGenerator) {

        var boardModel = new BoardModel();
        mazeGenerator.setModel(boardModel);
        mazeGenerator.generateMaze();
        //boardModel.printMaze();
    }

    @Test
    public void testSorts() {
        testSortHelper(new BubbleSort());
        testSortHelper(new InsertionSort());
        testSortHelper(new MergeSort());
        testSortHelper(new QuickSort());


        // Bogo Sort can take a long time due to it's random nature
        //testSortHelper(new BogoSort());
    }

    private void testSortHelper(Sort sort) {
        var barsModel = new BarsModel();
        barsModel.setBars(Sort.NUM_OF_BARS);
        sort.setModel(barsModel);
        barsModel.randomiseBars();

        sort.doAlgorithm();

        //barsModel.printBars();

        boolean sorted = true;
        for (int i = 1; i < Sort.NUM_OF_BARS; i++) {
            if (barsModel.getElements().get(i - 1).getValue() > barsModel.getElements().get(i).getValue()) {
                sorted = false;
                break;
            }
        }

        assertTrue(sorted);
    }

    @Test
    public void testSearches() {
        testSearchHelper(new LinearSearch(), true);
        testSearchHelper(new LinearSearch(), false);

        //Binary search needs to have a sorted list
        testSearchHelper(new BinarySearch(), false);
    }

    private void testSearchHelper(Search search, boolean shuffle) {
        var barsModel = new BarsModel();
        barsModel.setBars(Search.NUM_OF_BARS);

        search.setModel(barsModel);
        if (shuffle) barsModel.randomiseBars();

        var searchBar = barsModel.getElements().get(new Random().nextInt(Search.NUM_OF_BARS));
        search.setSearchBar(searchBar);

        search.doAlgorithm();

        //barsModel.printBars();
        assertTrue(searchBar.getValue() == search.getCurrentBar().getValue());
    }


}
