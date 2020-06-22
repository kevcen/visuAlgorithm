package algorithm;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.util.Duration;
import model.BoardModel;
import model.Vertex;

public class Dijkstra extends AbstractPathfinder {
    private ObservableMap<Vertex, Double> distances = FXCollections.observableHashMap();


    public Dijkstra(BoardModel model, Vertex start, Vertex end) {
        super(model, start, end);
    }

    @Override
    public void doStep() {
        // Get the fringe node with smallest distance
        Vertex currentVertex = visitSmallestVertex(distances);

        updateNeighbours(currentVertex, distances);
    }

    @Override
    public void initialiseStep() {
        startVertex.setVisited(true);
        for (Vertex neighbour : startVertex.getNeighbours()) {
            getFringe().add(neighbour);
            neighbour.setParentVertex(startVertex);

            if (neighbour.getRow() == startVertex.getRow() || neighbour.getCol() == startVertex.getCol())
                neighbour.setGValue(NON_DIAG_COST);
            else
                neighbour.setGValue(DIAG_COST);

            distances.put(neighbour, neighbour.gValue());
        }
    }

    @Override
    public Timeline getAnimation() {
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(
                Duration.millis(TIME_PER_FRAME),
                e -> {
                    if (hasNext()) {
                        doStep();
                        visualise(getFringe());
                    } else {
                        timeline.stop();
                        showFinalPath();
                    }
                });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }
}
