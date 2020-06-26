package algorithm.mazegenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BoardModel;
import model.Vertex;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomisedPrim implements MazeGenerator {
    private BoardModel model;
    private ObservableList<Vertex> frontier = FXCollections.observableArrayList();

    @Override
    public void setModel(BoardModel model) {
        this.model = model;

        for (Vertex vertex : model.getBoard()) {
            vertex.setWall(true);
        }
    }

    private void addFrontierCells(Vertex vertex) {
        for (Vertex frontierCell : model.getFrontierNeighbours(vertex)) {
            if (frontierCell.isWall()) frontier.add(frontierCell);
        }
    }

    @Override
    public void generateMaze() {
        int mid = BoardModel.ROWS / 2 * BoardModel.COLS + BoardModel.COLS / 2;

        var startVertex = model.getBoard().get(mid);

        startVertex.setWall(false);
        addFrontierCells(startVertex);

        var rand = new Random();
        while (!frontier.isEmpty()) {
            var randomFrontierCell = frontier.get(rand.nextInt(frontier.size()));

//            model.printMaze();


            var neighbours = model.getFrontierNeighbours(randomFrontierCell).stream()
                    .filter(v -> !v.isWall())
                    .collect(Collectors.toList());

            if (neighbours.size() > 0) {
                var randomNeighbourCell = neighbours.get(rand.nextInt(neighbours.size()));

                int middleRow = (randomNeighbourCell.getRow() + randomFrontierCell.getRow()) / 2;
                int middleCol = (randomNeighbourCell.getCol() + randomFrontierCell.getCol()) / 2;

                model.getVertex(middleRow, middleCol).setWall(false);
                addFrontierCells(randomFrontierCell);
            }


            randomFrontierCell.setWall(false);
            frontier.remove(randomFrontierCell);
        }
    }
}
