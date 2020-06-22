package controller;

import algorithm.AStar;
import algorithm.Dijkstra;
import algorithm.Pathfinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import model.BoardModel;
import model.Vertex;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class VisualiserController {

    private BoardModel boardModel;
    private Scene scene;
    @FXML
    AnchorPane visualiserPane;
    @FXML
    JFXButton playBtn;
    @FXML
    JFXComboBox<String> algorithmCombo;

    GridPane grid;

    private enum AlgorithmType {PATHFIND, SEARCH, SORT}

    ;
    private AlgorithmType currentType;
    private Pathfinder currentAlgorithm;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Timeline animation = new Timeline();

    private ObservableList<String> algorithmList = FXCollections.observableArrayList(
            "A* Pathfinder", "Dijkstra's Pathfinder");

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
        boardModel = new BoardModel();

        try {
            grid = FXMLLoader.load(getClass().getResource("../view/board.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear pre-existing children
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        initBoardModel();

        visualiserPane.getChildren().clear();
        visualiserPane.getChildren().add(grid);
    }

    public void initBoardModel() {
        this.boardModel = new BoardModel();

        for (Vertex vertex : boardModel.getBoard()) {
            // Set on button click
            currentAlgorithm.setVertexEventHandlers(vertex);
            // Set UI
            grid.add(vertex, vertex.getCol(), vertex.getRow());
        }
    }





    @FXML
    public void performAlgorithm(ActionEvent event) {
        currentAlgorithm.initialiseStep();
        animation = currentAlgorithm.getAnimation();

        if (currentAlgorithm.canPlay()) animation.play();
    }

    @FXML
    public void changeAlgorithm(ActionEvent event) {
        switch(algorithmCombo.getValue()) {
            case "A* Pathfinder":
                currentAlgorithm = new AStar();
                initialiseBoard();
                currentAlgorithm.setModel(boardModel);
                currentType = AlgorithmType.PATHFIND;
                break;
            case "Dijkstra's Pathfinder":
                currentAlgorithm = new Dijkstra();
                initialiseBoard();
                currentAlgorithm.setModel(boardModel);
                currentType = AlgorithmType.PATHFIND;
                break;
            case "Bubble Sort":

        }

    }


}
