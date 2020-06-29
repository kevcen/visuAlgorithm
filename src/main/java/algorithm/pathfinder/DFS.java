package algorithm.pathfinder;

import model.Vertex;

import java.util.ArrayDeque;
import java.util.Deque;

public class DFS extends AbstractPathfinder {

    Deque<Vertex> stack = new ArrayDeque<>();

    @Override
    public boolean hasNext() {
        return !stack.isEmpty() && !endVertexProperty().get().isVisited();
    }

    @Override
    public void doStep() {
        currentVertexProperty().set(stack.pop());
        currentVertexProperty().get().setVisited(true);
        for (Vertex neighbour : currentVertexProperty().get().getNeighbours()) {
            if (neighbour.isWall() || neighbour.isVisited())
                continue;
            stack.push(neighbour);
            neighbour.setParentVertex(currentVertexProperty().get());
        }
    }

    @Override
    public void initialiseStep() {
        stack.push(startVertexProperty().get());
    }

    @Override
    public boolean canPlay() {
        System.out.println("Maze generated: " + mazeGeneratedProperty().get());
        return startIsSet() && endIsSet() && mazeGeneratedProperty().get();
    }

    @Override
    public boolean isGraphTraversal() {
        return true;
    }

}
