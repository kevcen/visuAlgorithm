package algorithm.mazegenerator;

import algorithm.AbstractAlgorithm;
import algorithm.pathfinder.Pathfinder;
import algorithm.search.Search;
import algorithm.sort.Sort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BoardModel;
import model.Vertex;
import model.VisualiserModel;

import java.util.Random;
import java.util.stream.Collectors;


public class RandomisedPrim extends AbstractMazeGenerator {
    @Override
    public void initialiseStep() {
        int mid = BoardModel.ROWS / 2 * BoardModel.COLS + BoardModel.COLS / 2;
        var startVertex = getModel().getBoard().get(mid);

        startVertex.setWall(false);
        addFrontierCells(startVertex);
    }

    @Override
    public void doStep() {
        var randomFrontierCell = getFrontier().get(getRand().nextInt(getFrontier().size()));

        var neighbours = getModel().getFrontierNeighbours(randomFrontierCell).stream()
                .filter(v -> !v.isWall())
                .collect(Collectors.toList());

        if (!neighbours.isEmpty()) {
            var randomNeighbourCell = neighbours.get(getRand().nextInt(neighbours.size()));

            int middleRow = (randomNeighbourCell.getRow() + randomFrontierCell.getRow()) / 2;
            int middleCol = (randomNeighbourCell.getCol() + randomFrontierCell.getCol()) / 2;

            var middleVertex = getModel().getVertex(middleRow, middleCol);
            assert (middleVertex.getRow() == middleRow && middleVertex.getCol() == middleCol);
            middleVertex.setWall(false);
            randomFrontierCell.setWall(false);
            addFrontierCells(randomFrontierCell);
        }

        getFrontier().remove(randomFrontierCell);
    }



}
