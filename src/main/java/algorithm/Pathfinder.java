package algorithm;

import javafx.animation.Timeline;
import model.Vertex;


public interface Pathfinder {
    double TIME_PER_FRAME = 10;
    double NON_DIAG_COST = 1.0;
    double DIAG_COST = Math.sqrt(2);
    void setVertices(Vertex start, Vertex end);
    void initialiseStep();
    Timeline getAnimation();
}
