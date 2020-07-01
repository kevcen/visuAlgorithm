package algorithm.pathfinder;

import model.Vertex;

import java.util.ArrayDeque;
import java.util.Deque;

public class DFS extends AbstractGraphTraversal {

    Deque<Vertex> stack = new ArrayDeque<>();


    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public void doStep() {
        currentVertexProperty().set(stack.pop());
        currentVertexProperty().get().setVisited(true);
        currentVertexProperty().get().getNeighbours().stream().filter(v -> !(v.isWall() || v.isVisited())).forEach(v -> {
            stack.push(v);
            v.setParentVertex(currentVertexProperty().get());
        });
    }

    @Override
    public void initialiseStep() {
        stack.push(startVertexProperty().get());
    }

}
