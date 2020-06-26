package algorithm.mazegenerator;

import model.BoardModel;

public interface MazeGenerator {
    void generateMaze();
    void setModel(BoardModel model);
}
