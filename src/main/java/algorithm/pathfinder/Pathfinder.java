package algorithm.pathfinder;

import algorithm.Algorithm;
import model.Vertex;


public interface Pathfinder extends Algorithm {
    double NON_DIAG_COST = 1.0;
    double DIAG_COST = Math.sqrt(2);
    void setVertices(Vertex start, Vertex end);
    void setVertexEventHandlers(Vertex vertex);
    Vertex getStartVertex();
    Vertex getEndVertex();
}
