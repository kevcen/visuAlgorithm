package algorithm.pathfind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.Vertex;


public class AStar extends AbstractPathfind {
    private ObservableMap<Vertex, Double> fValues = FXCollections.observableHashMap();



    @Override
    public void initialiseStep() {
        processHeuristic();
        // Initialise starting node and it's neighbours
        startVertex.setGValue(0);
        fValues.put(startVertex, startVertex.gValue() + startVertex.hValue());
        for (Vertex neighbour : startVertex.getNeighbours()) {
            if (neighbour.isWall())
                continue;
            getFringe().add(neighbour);
            neighbour.setParentVertex(startVertex);

            if (neighbour.getRow() == startVertex.getRow() || neighbour.getCol() == startVertex.getCol())
                neighbour.setGValue(NON_DIAG_COST);
            else
                neighbour.setGValue(DIAG_COST);

            fValues.put(neighbour, neighbour.gValue() + neighbour.hValue());
        }
    }

    @Override
    public void doStep() {
        assert (!endVertex.isVisited() && !getFringe().isEmpty());

        // Get the fringe node with smallest f value
        Vertex currentVertex = visitSmallestVertex(fValues);

        updateNeighbours(currentVertex, fValues);
    }

    /**
     * Calculates euclidean distance from current vertex to end vertex for heuristic
     */
    public void processHeuristic() {
        for (Vertex vertex : model.getBoard()) {
            double xDistance = Math.abs(vertex.getCol() - endVertex.getCol());
            double yDistance = Math.abs(vertex.getRow() - endVertex.getRow());

            vertex.setHValue(Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
        }
    }

}
