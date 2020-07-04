import algorithm.mazegenerator.MazeGenerator;
import algorithm.mazegenerator.RandomisedKruskal;
import algorithm.mazegenerator.RandomisedPrim;
import algorithm.pathfinder.AStar;
import algorithm.pathfinder.Pathfinder;
import algorithm.search.BinarySearch;
import algorithm.search.LinearSearch;
import algorithm.search.Search;
import algorithm.sort.*;
import javafx.stage.Stage;
import model.BarsModel;
import model.BoardModel;
import model.Vertex;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisuAlgorithmTest extends ApplicationTest {
    private static int DECIMAL_PLACES = 10;


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

        for (int i = 1; i < Sort.NUM_OF_BARS; i++) {
            assertTrue(barsModel.getElements().get(i - 1).getValue() < barsModel.getElements().get(i).getValue());
        }

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


    @Test
    public void testHeuristicIsConsistent() {
        var pathfinder = new AStar();
        var boardModel = new BoardModel();
        pathfinder.setModel(boardModel);

        var rand = new Random();
        pathfinder.endVertexProperty().set(boardModel.getBoard().get(rand.nextInt(boardModel.getBoard().size())));
        pathfinder.startVertexProperty().set(boardModel.getBoard().get(rand.nextInt(boardModel.getBoard().size())));

        pathfinder.processHeuristic();

        for (Vertex vertex : boardModel.getBoard()) {
            for (Vertex neighbour : vertex.getNeighbours()) {
                double weight = vertex.getNonDiagNeighbours().contains(neighbour)? Pathfinder.NON_DIAG_COST : Pathfinder.DIAG_COST;

                assertTrue(roundToDecimalPlaces(vertex.hValue()) <= roundToDecimalPlaces(weight + neighbour.hValue()));
            }
        }
    }

    // Rounding needs to be used due to imprecise floating arithmetic
    private double roundToDecimalPlaces(double value) {
        double multiplier = Math.pow(10, DECIMAL_PLACES);
        return Math.round(value * multiplier) / multiplier;
    }
}
