package controller;

import algorithm.AStar;
import algorithm.Dijkstra;
import algorithm.Pathfinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.BoardModel;
import model.Vertex;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class VisualiserController {

    private BoardModel boardModel;
    private Vertex startVertex, endVertex;
    private int step;
    private Scene scene;
    @FXML
    GridPane grid;
    @FXML
    JFXButton playBtn;
    @FXML
    JFXComboBox<String> algorithmCombo;

    private Pathfinder currentAlgorithm;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Timeline animation = new Timeline();

    private ObservableList<String> algorithmList = FXCollections.observableArrayList(
            "A* Pathfinder", "Dijkstra's Pathfinder");

    public void addScene(Scene scene) {
        this.scene = scene;
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));

        scene.addEventFilter(MouseEvent.MOUSE_MOVED, e->System.out.println("filter"));
        scene.setOnMouseMoved(e-> System.out.println("handler"));

//        scene.addEventFilter(MouseEvent.MOUSE_MOVED, e-> {
//            Node node = e.getPickResult().getIntersectedNode();
//            if (node instanceof Vertex) {
//                Vertex vertex = (Vertex) node;
//                if (pressedKeys.contains(KeyCode.W)) {
//                    vertex.setWall(true);
//                } else if (pressedKeys.contains(KeyCode.U)) {
//                    vertex.setWall(false);
//                }
//            }
//        });
    }

    public void initBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;

        for (Vertex vertex : boardModel.getBoard()) {
            // Set on button click
            setVertexEventHandlers(vertex);
            // Set UI
            grid.add(vertex, vertex.getCol(), vertex.getRow());
        }

        currentAlgorithm = new AStar(boardModel);
    }

    @FXML
    public void initialize() {
        // Clear pre-existing children
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        // Set the values of the algorithm combo box
        algorithmCombo.setItems(algorithmList);
        algorithmCombo.setValue(algorithmList.get(0));
    }

    private void setVertexEventHandlers(Vertex vertex) {
        // Clicked, change state of vertices
        vertex.setOnMouseClicked(e -> {
            if (startVertex == vertex) {
                vertex.resetStyle();
                startVertex = null;
            } else if (endVertex == vertex) {
                vertex.resetStyle();
                endVertex = null;
            } else if (!startIsSet()) {
                vertex.setStart();
                startVertex = vertex;
            } else if (!endIsSet()) {
                vertex.setEnd();
                endVertex = vertex;
            }
            currentAlgorithm.setVertices(startVertex, endVertex);
        });

    }

    @FXML
    public void performAlgorithm(ActionEvent event) {
        currentAlgorithm.initialiseStep();
        animation = currentAlgorithm.getAnimation();

        animation.play();
    }

    @FXML
    public void changeAlgorithm(ActionEvent event) {
        switch(algorithmCombo.getValue()) {
            case "A* Pathfinder":
                currentAlgorithm = new AStar(boardModel, startVertex, endVertex);
                break;
            case "Dijkstra's Pathfinder":
                currentAlgorithm = new Dijkstra(boardModel, startVertex, endVertex);
                break;
        }

    }

    private boolean startIsSet() {
        return startVertex != null;
    }

    private boolean endIsSet() {
        return endVertex != null;
    }

}
