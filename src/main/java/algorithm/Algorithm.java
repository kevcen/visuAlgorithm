package algorithm;

import algorithm.pathfind.Pathfind;
import algorithm.search.Search;
import algorithm.sort.Sort;
import javafx.animation.Timeline;
import model.VisualiserModel;


public interface Algorithm {
    double TIME_PER_FRAME = 200;
    void setModel(VisualiserModel model);
    Timeline getAnimation();
    boolean canPlay();
    boolean isSort();
    boolean isSearch();
    boolean isPathfind();
    Pathfind asPathfind();
    Sort asSort();
    Search asSearch();
    void initialiseStep();
    boolean hasNext();
    void doStep();
}
