package controller;

import algorithm.Algorithm;
import algorithm.pathfinder.AStar;
import algorithm.pathfinder.Dijkstra;
import algorithm.search.BinarySearch;
import algorithm.search.LinearSearch;
import algorithm.search.Search;
import algorithm.sort.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
            "A* Pathfinder", "Dijkstra's Pathfinder", "Bubble Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Linear Search", "Binary Search");

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
            currentAlgorithm.asPathfinder().setVertexEventHandlers(vertex);
            // Set UI
            grid.add(vertex, vertex.getCol(), vertex.getRow());
        }

        visualiserPane.getChildren().clear();
        visualiserPane.getChildren().add(grid);
    }


    private void initialiseBars(int numBars) {
        visModel = new BarsModel();
        visModel.asBarsModel().setBars(numBars);
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

    private void initialisePathfinder() {
        initialiseBoard();
        currentAlgorithm.setModel(visModel);
    }

    private void initialiseSort() {
        initialiseBars(Sort.NUM_OF_BARS);
        currentAlgorithm.setModel(visModel);
        visModel.asBarsModel().randomiseBars();
        currentAlgorithm.asSort().visualise();
    }

    private void initialiseSearch(boolean shuffle) {
        initialiseBars(Search.NUM_OF_BARS);
        currentAlgorithm.setModel(visModel);
        if (shuffle) visModel.asBarsModel().randomiseBars();
        currentAlgorithm.asSearch().visualise();
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
            case "A* Pathfinder":
                currentAlgorithm = new AStar();
                initialisePathfinder();
                break;
            case "Dijkstra's Pathfinder":
                currentAlgorithm = new Dijkstra();
                initialisePathfinder();
                break;
            case "Bubble Sort":
                currentAlgorithm = new BubbleSort();
                initialiseSort();
                break;
            case "Insertion Sort":
                currentAlgorithm = new InsertionSort();
                initialiseSort();
                break;
            case "Merge Sort":
                currentAlgorithm = new MergeSort();
                initialiseSort();
                break;
            case "Quick Sort":
                currentAlgorithm = new QuickSort();
                initialiseSort();
                break;
            case "Linear Search":
                currentAlgorithm = new LinearSearch();
                initialiseSearch(true);
                break;
            case "Binary Search":
                currentAlgorithm = new BinarySearch();
                initialiseSearch(false);
                break;
        }
        currentAlgorithm.timePerFrameProperty().bind(timeSlider.valueProperty());
        timeSlider.valueProperty().addListener((obs, ov, nv) -> currentAlgorithm.changeTime((double) nv));
    }


}
