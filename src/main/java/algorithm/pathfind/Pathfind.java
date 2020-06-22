package algorithm.pathfind;

import algorithm.Algorithm;
import javafx.animation.Timeline;
import model.BoardModel;
import model.Vertex;
import model.VisualiserModel;


public interface Pathfind extends Algorithm {
    double NON_DIAG_COST = 1.0;
    double DIAG_COST = Math.sqrt(2);
    void setVertices(Vertex start, Vertex end);
    void setVertexEventHandlers(Vertex vertex);
    Vertex getStartVertex();
    Vertex getEndVertex();
}
