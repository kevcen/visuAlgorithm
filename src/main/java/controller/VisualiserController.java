package controller;

import algorithm.Algorithm;
import algorithm.mazegenerator.RandomisedPrim;
import algorithm.pathfinder.AStar;
import algorithm.pathfinder.DFS;
import algorithm.pathfinder.Dijkstra;
import algorithm.search.BinarySearch;
import algorithm.search.LinearSearch;
import algorithm.search.Search;
import algorithm.sort.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.*;
import org.kordamp.ikonli.javafx.FontIcon;

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
    @FXML
    Text statusText;
    @FXML
    FontIcon playIcon;

    GridPane grid;
    AnchorPane barsPane;

    private Algorithm currentAlgorithm;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Timeline animation;
    private BooleanProperty playing = new SimpleBooleanProperty(false);
    private BooleanProperty finished = new SimpleBooleanProperty(false);
    private boolean firstPlay = true;
    private ObservableList<String> algorithmList = FXCollections.observableArrayList(
            "A* Pathfinder", "Dijkstra's Pathfinder", "Depth First Search", "Bubble Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Bogo Sort", "Linear Search", "Binary Search", "Random Prim's Maze");

    @FXML
    public void initialize() {
        algorithmCombo.setItems(algorithmList);
        algorithmCombo.setValue(algorithmList.get(0));
        changeAlgorithm(); // Initialise the UI to the first algorithm
        playing.addListener((obs, ov, nv) -> {
            if(nv) {
                playIcon.setIconLiteral("mdi-pause");
                statusText.setText("Playing");
            } else {
                playIcon.setIconLiteral("mdi-play");
                if(!firstPlay)statusText.setText("Paused");
            }
        });
        finished.addListener((obs, ov, nv) -> {
            if(nv) {
                playIcon.setIconLiteral("mdi-repeat");
                if (currentAlgorithm.isSort())
                    statusText.setText("Done: sorted");
                else if (currentAlgorithm.isSearch())
                    statusText.setText("Done: " + currentAlgorithm.asSearch().getSearchBar().getValue() + " found");
                else if (currentAlgorithm.isPathfinder())
                    statusText.setText("Done: path found");
                else if (currentAlgorithm.isMazeGenerator())
                    statusText.setText("Done: maze generated");
            }
        });
    }


    public void addScene(Scene scene) {
        this.scene = scene;

        // Walls for pathfinders
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case X:
                    Platform.exit();
                case M:
                    if (currentAlgorithm.isPathfinder())
                        currentAlgorithm.asPathfinder().generateMaze();
            }
            pressedKeys.add(e.getCode());
        });
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));

        // Walls spawning/erasing
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            if (!currentAlgorithm.isPathfinder())
                return;
            Node node = e.getPickResult().getIntersectedNode();
            if (node instanceof Vertex) {
                Vertex vertex = (Vertex) node;
                if (playing.get() || currentAlgorithm.asPathfinder().isEndOrStart(vertex)) {
                    return;
                }
                if (pressedKeys.contains(KeyCode.W)) {
                    vertex.setWall(true);
                } else if (pressedKeys.contains(KeyCode.E)) {
                    vertex.setWall(false);
                }
            }
        });
    }

    private void initialiseBoard() {
        visModel = new BoardModel();

        // Initialise UI
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
            if(currentAlgorithm.isPathfinder())
                currentAlgorithm.asPathfinder().setVertexEventHandlers(vertex, firstPlay, statusText);
            // Set UI
            grid.add(vertex, vertex.getCol(), vertex.getRow());
        }

        visualiserPane.getChildren().setAll(grid);
    }


    private void initialiseBars(int numBars) {
        visModel = new BarsModel();
        visModel.asBarsModel().setBars(numBars);

        // Initialise UI
        try {
            barsPane = FXMLLoader.load(getClass().getResource("../view/bars.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Bar bar : visModel.asBarsModel().getElements()) {
            if (currentAlgorithm.isSearch()) {
                currentAlgorithm.asSearch().setBarEventHandlers(bar, playing, statusText);
            }
            barsPane.getChildren().add(bar);
        }

        visualiserPane.getChildren().setAll(barsPane);
    }

    private void initialisePathfinder() {
        initialiseBoard();
        currentAlgorithm.setModel(visModel);
        if (currentAlgorithm instanceof DFS) {
            statusText.setText("Press 'm' to create a maze");
        } else {
            statusText.setText("Select starting point");
        }
    }

    private void initialiseMazeGenerator() {
        initialiseBoard();
        currentAlgorithm.setModel(visModel);
        statusText.setText("Press play to start");
    }
    private void initialiseSort() {
        initialiseBars(Sort.NUM_OF_BARS);
        currentAlgorithm.setModel(visModel);
        visModel.asBarsModel().randomiseBars();
        currentAlgorithm.asSort().visualise();
        statusText.setText("Press play to start");
    }

    private void initialiseSearch(boolean shuffle) {
        initialiseBars(Search.NUM_OF_BARS);
        currentAlgorithm.setModel(visModel);
        if (shuffle) visModel.asBarsModel().randomiseBars();
        currentAlgorithm.asSearch().visualise();
        statusText.setText("Select bar to search for");
    }

    @FXML
    public void performAlgorithm(ActionEvent event) {
        if (!currentAlgorithm.canPlay())
            return;
        else if (finished.get())
            changeAlgorithm();
        else if (playing.get()) {
            animation.pause();
            playing.set(false);
        }else if (!firstPlay) {
            animation.play();
            playing.set(true);
        }else {
            firstPlay = false;
            playing.set(true);
            currentAlgorithm.initialiseStep();

            animation = currentAlgorithm.getAnimation(finished);
            animation.play();
        }
    }

    @FXML
    public void changeAlgorithm() {
        //TODO: Make the stuff after loading a different task?
//        System.out.println("hi");
        if(animation != null) animation.stop(); //Stop previous animation if exists
        statusText.setText("Loading");
        finished.set(false);
        playing.set(false);
        firstPlay = true;

        switch (algorithmCombo.getValue()) {
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
            case "Bogo Sort":
                currentAlgorithm = new BogoSort();
                initialiseSort();
                break;
            case "Binary Search":
                currentAlgorithm = new BinarySearch();
                initialiseSearch(false);
                break;
            case "Depth First Search":
                currentAlgorithm = new DFS();
                initialisePathfinder();
                break;
            case "Random Prim's Maze":
                currentAlgorithm = new RandomisedPrim();
                initialiseMazeGenerator();
                break;
            default:
                System.out.println("Unsupported algorithm");
                return;
        }

        // Control speed of animation with timeSlider
        currentAlgorithm.timePerFrameProperty().bind(timeSlider.valueProperty());
        timeSlider.valueProperty().addListener((obs, ov, nv) -> animation = currentAlgorithm.changeTime(playing.get()));

    }


}
