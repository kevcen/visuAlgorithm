package algorithm;

import algorithm.pathfinder.Pathfinder;
import algorithm.search.Search;
import algorithm.sort.Sort;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import model.VisualiserModel;


public interface Algorithm {
    double DEFAULT_TIME_PER_FRAME = 5;
    void setModel(VisualiserModel model);
    Timeline getAnimation();
    boolean canPlay();
    boolean isSort();
    boolean isSearch();
    boolean isPathfinder();
    Pathfinder asPathfinder();
    Sort asSort();
    Search asSearch();
    void initialiseStep();
    boolean hasNext();
    void doStep();
    void visualise();
    void showResult();
    DoubleProperty timePerFrameProperty();
    void changeTime(double time);
}
