package algorithm.pathfinder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.Vertex;

public class Dijkstra extends AbstractPathfinder {
    private ObservableMap<Vertex, Double> distances = FXCollections.observableHashMap();


    @Override
    public void doStep() {
        // Get the fringe node with smallest distance
        currentVertexProperty().set(visitSmallestVertex(distances));

        updateNeighbours(currentVertexProperty().get(), distances);
    }

    @Override
    public void initialiseStep() {
        startVertexProperty().get().setVisited(true);
        for (Vertex neighbour : startVertexProperty().get().getNeighbours()) {
            getFringe().add(neighbour);
            neighbour.setParentVertex(startVertexProperty().get());

            if (startVertexProperty().get().getNonDiagNeighbours().contains(neighbour))
                neighbour.setGValue(NON_DIAG_COST);
            else
                neighbour.setGValue(DIAG_COST);

            distances.put(neighbour, neighbour.gValue());
        }
    }


}
