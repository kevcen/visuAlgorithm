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

import java.util.List;
import java.util.Random;

public abstract class AbstractMazeGenerator extends AbstractAlgorithm implements MazeGenerator {
    private BoardModel model;
    private ObservableList<Vertex> frontier = FXCollections.observableArrayList();
    private Random rand = new Random();

    @Override
    public void setModel(VisualiserModel model) {
        this.model = model.asBoardModel();

        model.asBoardModel().getBoard().forEach(v -> v.setWall(true));
    }

    protected void addFrontierCells(Vertex vertex) {
        model.getFrontierNeighbours(vertex)
                .stream().filter(v -> v.isWall() && !frontier.contains(v)).forEach(frontier::add);
    }

    @Override
    public void generateMaze() {
        initialiseStep();

        while (hasNext())
            doStep();
    }

    private void printFrontier() {
        System.out.print("{");
        frontier.forEach(this::printVertex);
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

    public List<Vertex> getFrontier() {
        return frontier;
    }

    public Random getRand() {
        return rand;
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

    public BoardModel getModel() {
        return model;
    }

}
