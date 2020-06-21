package controller;

import algorithm.AStar;
import algorithm.Algorithm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.BoardModel;
import model.Vertex;

import java.util.Collection;

public class VisualiserController {
//    private static final double COST_OF_MOVEMENT = 1.0;
    private static final double TIME_PER_FRAME = 10;
    private BoardModel boardModel;
    private Vertex startVertex, endVertex;
    private int step;
    private Scene scene;
    @FXML
    GridPane grid;
    @FXML
    Button stateBtn, stepbtn;

    private Algorithm currentAlgorithm = new AStar();

    public void addScene(Scene scene) {
        this.scene = scene;

    }

    private Timeline animation = new Timeline();

    public BooleanProperty isDragging() {
        return isDragging;
    }

    private BooleanProperty isDragging = new SimpleBooleanProperty(false);

    public void initModel(BoardModel boardModel) {
        this.boardModel = boardModel;


        for (Vertex vertex : boardModel.getBoard()) {
            setEventHandlers(vertex);


            grid.add(vertex, vertex.getCol(), vertex.getRow());
        }

        //add neighbours per vertex
        for (Vertex vertex : boardModel.getBoard()) {
            for (Vertex neighbour : boardModel.getBoard()) {
                if (neighbour.getCol() >= vertex.getCol() - 1 && neighbour.getCol() <= vertex.getCol() + 1
                        && neighbour.getRow() >= vertex.getRow() - 1 && neighbour.getRow() <= vertex.getRow() + 1) {
                    if (vertex != neighbour) vertex.getNeighbours().add(neighbour);
                }
            }
//            vertex.sortNeighbours(); //favours the straight paths than diagonals
        }
    }


    @FXML
    public void initialize() {
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();


        stateBtn.setOnMouseClicked(e -> performAlgorithm());

        step = 0;
        stepbtn.setOnMousePressed(e-> {
            if (currentAlgorithm.doStep()) {
                visualise(currentAlgorithm.getFringe());
            } else {
//                System.out.println("finished");
                animation.stop();
                showPath();
            }
        });
    }

    private void setEventHandlers(Vertex vertex) {
        vertex.setOnMouseClicked(e -> {
            if (startVertex == vertex) {
//                    vertex.setState(Vertex.State.UNVISITED);
                vertex.setStyle("-fx-background-color: white");
                startVertex = null;
            } else if (endVertex == vertex) {
//                    vertex.setState(Vertex.State.UNVISITED);
                vertex.setStyle("-fx-background-color: white");
                endVertex = null;
            } else if (!startIsSet()) {
//                    vertex.setState(Vertex.State.START);
                vertex.setStyle("-fx-background-color: mediumslateblue");
                startVertex = vertex;
            } else if (!endIsSet()) {
//                    vertex.setState(Vertex.State.END);
                vertex.setStyle("-fx-background-color: midnightblue");
                endVertex = vertex;
            }
            currentAlgorithm.setVertices(startVertex, endVertex);
        });

        vertex.setOnMouseDragged(e-> {
            Node node = e.getPickResult().getIntersectedNode();
            if (node instanceof Vertex && endIsSet() && startIsSet()) {
                Vertex btn = (Vertex) node;
                btn.setStyle("-fx-background-color: black");
                btn.setWall(true);
            }
        });
    }

    private void performAlgorithm() {
        processHeuristic();

        animation = new Timeline(new KeyFrame(Duration.millis(TIME_PER_FRAME), e -> {
            if (currentAlgorithm.doStep()) {
                visualise(currentAlgorithm.getFringe());
            } else {
                animation.stop();
                showPath();
            }
        }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

    }

    /*Calculates euclidean distance from current vertex to end vertex for heuristic*/
    private void processHeuristic() {
        for (Vertex vertex : boardModel.getBoard()) {
            double xDistance = Math.abs(vertex.getCol() - endVertex.getCol());
            double yDistance = Math.abs(vertex.getRow() - endVertex.getRow());

            vertex.setHValue(Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
        }
    }

    private void showPath() {
        Vertex currentVertex = endVertex.getParentVertex();
        while (currentVertex != startVertex) {
            System.out.println("Current vertex: (" + currentVertex.getRow() + ", " + currentVertex.getCol() + ")");
            currentVertex.setStyle("-fx-background-color: navajowhite;" + Vertex.styles);
            currentVertex = currentVertex.getParentVertex();
        }
    }

    private void visualise(Collection<Vertex> fringe) {
        for (Vertex vertex : boardModel.getBoard()) {
            StringBuilder style = new StringBuilder();
            style.append("-fx-background-color: ");
            if (vertex.isWall())
                style.append("black;");
            else if (vertex == endVertex || vertex == startVertex)
                continue;
            else if (vertex.isVisited())
                style.append("lightseagreen;");
            else if (fringe.contains(vertex))
                style.append("powderblue;");
            else
                style.append("white;");
            style.append(Vertex.styles);
            vertex.setStyle(style.toString());
        }
    }
    private boolean startIsSet() {
        return startVertex != null;
    }

    private boolean endIsSet() {
        return endVertex != null;
    }
}
