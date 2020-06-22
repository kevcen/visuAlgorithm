package algorithm.pathfinder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.Vertex;

public class Dijkstra extends AbstractPathfinder {
    private ObservableMap<Vertex, Double> distances = FXCollections.observableHashMap();


    @Override
    public void doStep() {
        // Get the fringe node with smallest distance
        Vertex currentVertex = visitSmallestVertex(distances);

        updateNeighbours(currentVertex, distances);
    }

    @Override
    public void initialiseStep() {
        startVertex.setVisited(true);
        for (Vertex neighbour : startVertex.getNeighbours()) {
            getFringe().add(neighbour);
            neighbour.setParentVertex(startVertex);

            if (neighbour.getRow() == startVertex.getRow() || neighbour.getCol() == startVertex.getCol())
                neighbour.setGValue(NON_DIAG_COST);
            else
                neighbour.setGValue(DIAG_COST);

            distances.put(neighbour, neighbour.gValue());
        }
    }


}
