package algorithm.pathfinder;

import model.Vertex;

import java.util.Stack;

public class DFS extends AbstractPathfinder {

    Stack<Vertex> stack = new Stack<>();

    @Override
    public boolean hasNext() {
        return !stack.isEmpty() && !endVertex.isVisited();
    }

    @Override
    public void doStep() {
        currentVertex = stack.pop();
        currentVertex.setVisited(true);
        for (Vertex neighbour : currentVertex.getNeighbours()) {
            if (neighbour.isWall() || neighbour.isVisited())
                continue;
            stack.push(neighbour);
            neighbour.setParentVertex(currentVertex);
        }
    }

    @Override
    public void initialiseStep() {
        stack.push(startVertex);
    }


}
