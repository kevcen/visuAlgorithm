package model;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class Vertex extends JFXButton {
    public static final String styles = "-fx-background-radius: 0; -fx-border-color: white; -fx-border-width: 0.1";
    private final int row, col;
    private double hValue = 0, gValue = Integer.MAX_VALUE;


    private Vertex parentVertex;
    private boolean visited = false, isWall = false;

    private ObservableList<Vertex> neighbours;


    public Vertex(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        neighbours = FXCollections.observableArrayList();
        setMinSize(0, 0);
        resetStyle();
    }

    public List<Vertex> getNeighbours() {
        return neighbours;
    }

    public List<Vertex> getNonDiagNeighbours() {
        return neighbours.stream().filter(v -> v.getRow() == row || v.getCol() == col).collect(Collectors.toList());
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
        if (visited)
            setStyle("-fx-background-color: #7bd1ce;" + styles);
    }

    public double hValue() {
        return hValue;
    }
    public void setHValue(double hValue) {
        this.hValue = hValue;
    }

    public double gValue() {
        return gValue;
    }
    public void setGValue(double gValue) {
        this.gValue = gValue;
    }

    public Vertex getParentVertex() {
        return parentVertex;
    }
    public void setParentVertex(Vertex parent) {
        parentVertex = parent;
    }


    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        if(wall) {
            setStyle(styles + ";-fx-background-color: white");
        } else {
            resetStyle();
        }
        isWall = wall;
    }

    public void resetStyle() {
        setStyle(styles + ";-fx-background-color: #3d3d3d");
    }

    public void setStart() {
        setStyle("-fx-background-color: #ff9012;" + styles);
    }

    public void setEnd() {
        setStyle("-fx-background-color: #ff9012;" + styles);
    }

    public void setCurrent() {
        setStyle("-fx-background-color: red;" + styles);
    }

    public void setFringe() {
        setStyle("-fx-background-color: powderblue;" + styles);
    }

    public void setResult() {
        setStyle("-fx-background-color: navajowhite;" + Vertex.styles);
    }
}
