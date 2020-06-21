package algorithm;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import model.Vertex;

public interface Algorithm {
    ObservableList<Vertex> getFringe();
    ObservableMap<Vertex, Double> fValues();
    boolean doStep();
    void setVertices(Vertex start, Vertex end);
    double NON_DIAG_COST = 1.0;
    double DIAG_COST = Math.sqrt(2);
}
