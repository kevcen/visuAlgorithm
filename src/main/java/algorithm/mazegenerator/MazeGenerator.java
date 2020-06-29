package algorithm.mazegenerator;

import algorithm.Algorithm;
import model.VisualiserModel;

public interface MazeGenerator extends Algorithm {
    void generateMaze();
    void setModel(VisualiserModel model);
}
