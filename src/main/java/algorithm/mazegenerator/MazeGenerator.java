package algorithm.mazegenerator;

import algorithm.Algorithm;
import algorithm.pathfinder.Pathfinder;
import model.BoardModel;
import model.VisualiserModel;

public interface MazeGenerator extends Algorithm {
    void generateMaze();
    void setModel(VisualiserModel model);
}
