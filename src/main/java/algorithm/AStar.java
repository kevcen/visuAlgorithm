package algorithm;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import model.Vertex;

import java.util.HashMap;
import java.util.Map;

public class AStar implements Algorithm {
    private ObservableMap<Vertex, Double> fValues = FXCollections.observableHashMap();
    private ObservableList<Vertex> fringe = FXCollections.observableArrayList();
    private int step = 0;


    private Vertex startVertex = null, endVertex = null;


    public ObservableMap<Vertex, Double> fValues() {
        return fValues;
    }

    public ObservableList<Vertex> getFringe() {
        return fringe;
    }

    public boolean doStep() {
//        System.out.println("step " + step);
        if (step == 0) {
            startVertex.setGValue(0);
            fValues.put(startVertex, startVertex.gValue() + startVertex.hValue());
            for (Vertex neighbour : startVertex.getNeighbours()) {
                if(neighbour.isWall())
                    continue;
                fringe.add(neighbour);
                //fringe[neighbour] = true
                neighbour.setParentVertex(startVertex);
                if(neighbour.getRow() == startVertex.getRow() || neighbour.getCol() == startVertex.getCol())
                    neighbour.setGValue(NON_DIAG_COST);
                else
                    neighbour.setGValue(DIAG_COST);

                fValues.put(neighbour, neighbour.gValue() + neighbour.hValue());
            }
        } else {
            if (!endVertex.isVisited() && !fringe.isEmpty()) {
                //get the fringe node with smallest f value
                Vertex currentVertex = fringe.get(0);
                for (Vertex fringeNode : fringe) {
                    if (fValues.get(fringeNode) < fValues.get(currentVertex)) {
                        currentVertex = fringeNode;
                    }
                }
                fringe.remove(currentVertex);
                currentVertex.setVisited(true);

                for (Vertex neighbour : currentVertex.getNeighbours()) {
                    if(neighbour.isWall())
                        continue;
                    boolean nonDiagonal = neighbour.getCol() == currentVertex.getCol()
                            || neighbour.getRow() == currentVertex.getRow();
                    double distance = currentVertex.gValue() + (nonDiagonal ? NON_DIAG_COST : DIAG_COST);
                    if (!neighbour.isVisited()) {
                        if (fringe.contains(neighbour)) {
                            if (distance < neighbour.gValue()) {
                                neighbour.setGValue(distance);
                                fValues.put(neighbour, neighbour.gValue() + neighbour.hValue());
                                neighbour.setParentVertex(currentVertex);
                            }
                        } else {
                            fringe.add(neighbour);
                            neighbour.setGValue(distance);
                            fValues.put(neighbour, neighbour.gValue() + neighbour.hValue());
                            neighbour.setParentVertex(currentVertex);
                        }
                    }
                }
            } else { //end of while loop
                return false;
            }
        }
        step++;
        return true;
    }

    @Override
    public void setVertices(Vertex start, Vertex end) {
        startVertex = start;
        endVertex = end;
    }
}
