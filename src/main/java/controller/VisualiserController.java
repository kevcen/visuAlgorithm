package controller;

import algorithm.Algorithm;
import algorithm.pathfind.AStar;
import algorithm.pathfind.Dijkstra;
import algorithm.sort.BubbleSort;
import algorithm.sort.InsertionSort;
import algorithm.sort.MergeSort;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class VisualiserController {

    private VisualiserModel visModel;
    private Scene scene;
    @FXML
    AnchorPane visualiserPane;
    @FXML
    JFXButton playBtn;
    @FXML
    JFXComboBox<String> algorithmCombo;
    @FXML
    JFXSlider timeSlider;

    GridPane grid;
    AnchorPane barsPane;

    private Algorithm currentAlgorithm;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Timeline animation = new Timeline();

    private ObservableList<String> algorithmList = FXCollections.observableArrayList(
            "A* Pathfind", "Dijkstra's Pathfind", "Bubble Sort", "Insertion Sort", "Merge Sort");

    @FXML
    public void initialize() {
        // Set the values of the algorithm combo box
        algorithmCombo.setItems(algorithmList);
        algorithmCombo.setValue(algorithmList.get(0));
        changeAlgorithm(new ActionEvent()); // Initialise the UI to the first algorithm
    }

    public void addScene(Scene scene) {
        this.scene = scene;

        // Walls
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            Node node = e.getPickResult().getIntersectedNode();
            if (node instanceof Vertex) {
                Vertex vertex = (Vertex) node;
                if (pressedKeys.contains(KeyCode.W)) {
                    vertex.setWall(true);
                } else if (pressedKeys.contains(KeyCode.U)) {
                    vertex.setWall(false);
                }
            }
        });
    }

    private void initialiseBoard() {
        visModel = new BoardModel();

        try {
            grid = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear pre-existing children
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        for (Vertex vertex : visModel.asBoardModel().getBoard()) {
            // Set on button click
            currentAlgorithm.asPathfind().setVertexEventHandlers(vertex);
            // Set UI
            grid.add(vertex, vertex.getCol(), vertex.getRow());
        }

        visualiserPane.getChildren().clear();
        visualiserPane.getChildren().add(grid);
    }


    private void initialiseBars() {
        visModel = new BarsModel();

        try {
            barsPane = FXMLLoader.load(getClass().getResource("../view/bars.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Bar bar : visModel.asBarsModel().getElements()) {
            barsPane.getChildren().add(bar);
        }

        visualiserPane.getChildren().clear();
        visualiserPane.getChildren().add(barsPane);
    }



    @FXML
    public void performAlgorithm(ActionEvent event) {
        if (!currentAlgorithm.canPlay()) {
            return;
        }
        currentAlgorithm.initialiseStep();

        animation = currentAlgorithm.getAnimation();

        animation.play();
    }

    @FXML
    public void changeAlgorithm(ActionEvent event) {
        switch(algorithmCombo.getValue()) {
            case "A* Pathfind":
                currentAlgorithm = new AStar();
                initialiseBoard();
                currentAlgorithm.setModel(visModel);
                break;
            case "Dijkstra's Pathfind":
                currentAlgorithm = new Dijkstra();
                initialiseBoard();
                currentAlgorithm.setModel(visModel);
                break;
            case "Bubble Sort":
                currentAlgorithm = new BubbleSort();
                initialiseBars();
                currentAlgorithm.setModel(visModel);
                currentAlgorithm.asSort().randomiseBars();
                break;
            case "Insertion Sort":
                currentAlgorithm = new InsertionSort();
                initialiseBars();
                currentAlgorithm.setModel(visModel);
                currentAlgorithm.asSort().randomiseBars();
                break;
            case "Merge Sort":
                currentAlgorithm = new MergeSort();
                initialiseBars();
                currentAlgorithm.setModel(visModel);
                currentAlgorithm.asSort().randomiseBars();
                break;
        }

    }


}
