package algorithm.pathfinder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.Vertex;


public class AStar extends AbstractPathfinder {
    private ObservableMap<Vertex, Double> fValues = FXCollections.observableHashMap();

    @Override
    public void initialiseStep() {
        // Process the heuristic
        processHeuristic();

        // Initialise starting node and it's neighbours' values
        startVertexProperty().get().setGValue(0);
        fValues.put(startVertexProperty().get(), startVertexProperty().get().gValue() + startVertexProperty().get().hValue());
        for (Vertex neighbour : startVertexProperty().get().getNeighbours()) {
            if (neighbour.isWall())
                continue;
            getFringe().add(neighbour);
            neighbour.setParentVertex(startVertexProperty().get());

            if (neighbour.getRow() == startVertexProperty().get().getRow() || neighbour.getCol() == startVertexProperty().get().getCol())
                neighbour.setGValue(NON_DIAG_COST);
            else
                neighbour.setGValue(DIAG_COST);

            fValues.put(neighbour, neighbour.gValue() + neighbour.hValue());
        }
    }

    @Override
    public void doStep() {
        assert (!endVertexProperty().get().isVisited() && !getFringe().isEmpty());

        // Get the fringe node with smallest f value
        currentVertexProperty().set(visitSmallestVertex(fValues));

        // Update the values of the neighbours
        updateNeighbours(currentVertexProperty().get(), fValues);
    }

    public void processHeuristic() {
        for (Vertex vertex : getModel().getBoard()) {
            double xDistance = Math.abs(vertex.getCol() - endVertexProperty().get().getCol());
            double yDistance = Math.abs(vertex.getRow() - endVertexProperty().get().getRow());

            vertex.setHValue(Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
        }
    }

}
