package algorithm.mazegenerator;

import algorithm.AbstractAlgorithm;
import algorithm.pathfinder.Pathfinder;
import algorithm.search.Search;
import algorithm.sort.Sort;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BoardModel;
import model.Vertex;
import model.VisualiserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class RandomisedPrim extends AbstractAlgorithm implements MazeGenerator {
    private BoardModel model;
    private ObservableList<Vertex> frontier = FXCollections.observableArrayList();
    private Random rand = new Random();

    @Override
    public void setModel(VisualiserModel model) {
        this.model = model.asBoardModel();

        for (Vertex vertex : model.asBoardModel().getBoard()) {
            vertex.setWall(true);
        }
    }

    private void addFrontierCells(Vertex vertex) {
        for (Vertex frontierCell : model.getFrontierNeighbours(vertex)) {
            if (frontierCell.isWall() && !frontier.contains(frontierCell))
                frontier.add(frontierCell);
        }
    }

    @Override
    public void generateMaze() {
        initialiseStep();

        while (hasNext())
            doStep();
    }

    private void printFrontier() {
        System.out.print("{");
        for (Vertex vertex : frontier) printVertex(vertex);
        System.out.println("}");
    }

    private void printVertex(Vertex vertex) {
        System.out.printf("(%d, %d) ", vertex.getRow(), vertex.getCol());
    }


    @Override
    public boolean canPlay() {
        return true;
    }

    @Override
    public boolean isSort() {
        return false;
    }

    @Override
    public boolean isSearch() {
        return false;
    }

    @Override
    public boolean isMazeGenerator() {
        return false;
    }

    @Override
    public MazeGenerator asMazeGenerator() {
        return this;
    }

    @Override
    public boolean isPathfinder() {
        return false;
    }

    @Override
    public Pathfinder asPathfinder() {
        return null;
    }


    @Override
    public Sort asSort() {
        return null;
    }

    @Override
    public Search asSearch() {
        return null;
    }

    @Override
    public void initialiseStep() {
        int mid = BoardModel.ROWS / 2 * BoardModel.COLS + BoardModel.COLS / 2;
        var startVertex = model.getBoard().get(mid);

        startVertex.setWall(false);
        addFrontierCells(startVertex);
    }

    @Override
    public boolean hasNext() {
        return !frontier.isEmpty();
    }

    @Override
    public void doStep() {
        var randomFrontierCell = frontier.get(rand.nextInt(frontier.size()));

        var neighbours = model.getFrontierNeighbours(randomFrontierCell).stream()
                .filter(v -> !v.isWall())
                .collect(Collectors.toList());

        if (!neighbours.isEmpty()) {
            var randomNeighbourCell = neighbours.get(rand.nextInt(neighbours.size()));

            int middleRow = (randomNeighbourCell.getRow() + randomFrontierCell.getRow()) / 2;
            int middleCol = (randomNeighbourCell.getCol() + randomFrontierCell.getCol()) / 2;

            var middleVertex = model.getVertex(middleRow, middleCol);
            assert (middleVertex.getRow() == middleRow && middleVertex.getCol() == middleCol);
            middleVertex.setWall(false);
            randomFrontierCell.setWall(false);
        }

        addFrontierCells(randomFrontierCell);
        frontier.remove(randomFrontierCell);
    }

    @Override
    public void visualise() {
        for (Vertex vertex : model.getBoard()) {
            if (vertex.isWall()) vertex.setWall(true);
            else vertex.resetStyle();
        }
    }

    @Override
    public void showResult() {
        return;
    }

}
