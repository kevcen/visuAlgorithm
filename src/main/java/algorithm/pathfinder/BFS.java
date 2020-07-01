package algorithm.pathfinder;

import model.Vertex;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BFS extends AbstractGraphTraversal {
    Queue<Vertex> queue = new LinkedList<>();
    @Override
    public void initialiseStep() {
        queue.add(startVertexProperty().get());
    }

    @Override
    public void doStep() {
        currentVertexProperty().set(queue.remove());
        currentVertexProperty().get().getNeighbours().stream().filter(v -> !(v.isWall() || v.isVisited())).forEach(v -> {
            v.setVisited(true);
            v.setParentVertex(currentVertexProperty().get());
            queue.add(v);
        });
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
