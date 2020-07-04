package algorithm.mazegenerator;

import model.BoardModel;
import model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomisedKruskal extends AbstractMazeGenerator {
    List<Set<Vertex>> vertexSets = new ArrayList<>();
    @Override
    public void initialiseStep() {
        getNodes().forEach(v -> {
            Set<Vertex> set = new HashSet<>();
            set.add(v);
            vertexSets.add(set);
        });
    }

    @Override
    public boolean hasNext() {
        return getWalls().size() > 0;
    }

    private List<Vertex> getNodes() {
        return getModel().getBoard()
                .stream()
                .filter(v -> v.getRow() % 2 == 1 && v.getCol() % 2 == 1)
                .collect(Collectors.toList());
    }
    private List<Vertex> getWalls() {
        return getModel().getBoard()
                .stream()
                .filter(v -> v.isWall() && (v.getRow() % 2 == 0 || v.getCol() % 2 ==0) && !v.isVisited())
                .collect(Collectors.toList());
    }
    private Vertex getRandomWall() {
        var walls = getWalls();
        return walls.get(getRand().nextInt(walls.size()));
    }

    @Override
    public void doStep() {
        var randomWall = getRandomWall();
        randomWall.setVisited(true);
        var neighbours = randomWall.getNonDiagNeighbours().stream().filter(v -> getNodes().contains(v)).collect(Collectors.toList());

        if (neighbours.size() > 1) {
            var randomNeighbour = neighbours.get(getRand().nextInt(neighbours.size()));

            var oppositeNeighbour = getModel().getVertex(
                        2 * randomWall.getRow() - randomNeighbour.getRow(),
                        2 * randomWall.getCol() - randomNeighbour.getCol());


            var set1 = getSet(randomNeighbour);
            var set2 = getSet(oppositeNeighbour);

            if (set1 != set2) {
                set1.addAll(set2);
                vertexSets.remove(set2);
                randomNeighbour.setWall(false);
                oppositeNeighbour.setWall(false);
                randomWall.setWall(false);
            }
        }
    }


    private Set<Vertex> getSet(Vertex vertex) {
        for (Set<Vertex> set : vertexSets) {
            if (set.contains(vertex)) return set;
        }
        System.err.println("Vertex set not found");
        return null;
    }


}
