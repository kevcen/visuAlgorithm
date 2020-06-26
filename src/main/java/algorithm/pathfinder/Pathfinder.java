package algorithm.pathfinder;

import algorithm.Algorithm;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import model.Vertex;


public interface Pathfinder extends Algorithm {
    double NON_DIAG_COST = 1.0;
    double DIAG_COST = Math.sqrt(2);
    void setVertices(Vertex start, Vertex end);
    void setVertexEventHandlers(Vertex vertex, boolean played, Text statusText);
    boolean isEndOrStart(Vertex vertex);
    void generateMaze();
    boolean isGraphTraversal();
}
