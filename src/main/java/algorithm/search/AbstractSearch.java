package algorithm.search;

import algorithm.Algorithm;
import algorithm.pathfind.Pathfind;
import algorithm.sort.Sort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.*;

import java.util.Collections;

public abstract class AbstractSearch implements Search {
    protected BarsModel model;
    protected Bar currentBar;
    protected Bar searchBar;

    @Override
    public void setModel(VisualiserModel model) {
        this.model = model.asBarsModel();
    }

    @Override
    public Timeline getAnimation() {
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(
                Duration.millis(Algorithm.TIME_PER_FRAME), //TODO: Use slider for time
                e -> {
                    if (hasNext()) {
                        doStep();
                        visualise();
                    } else {
                        timeline.stop();
//                        showFinalPath();
                    }
                });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    @Override
    public boolean canPlay() {
        return searchBar != null;
    }

    public void randomiseBars() {
        Collections.shuffle(model.getElements());
        visualise();
    }

    /**
     * Use the current state of the algorithm to visualise the current state
     */
    public void visualise() {
        for (int i = 0 ; i < BarsModel.NUM_OF_BARS; i++) {
            Bar bar = model.getElements().get(i);
            bar.setX(i * BarsModel.getWidthOfBar() + Bar.PADDING);
            if(bar.isVisited()) bar.setFill(Color.LIGHTSEAGREEN);
            if(bar == searchBar) bar.setEnd();
            if(bar == currentBar) bar.setFill(Color.RED);
        }
    }

    public boolean isSort() {
        return false;
    }

    public boolean isSearch() {
        return true;
    }

    public boolean isPathfind() {
        return false;
    }

    public Pathfind asPathfind() {
        return null;
    }

    public Sort asSort() {
        return null;
    }

    public Search asSearch() {
        return this;
    }
}
