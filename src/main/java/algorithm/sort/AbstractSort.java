package algorithm.sort;

import algorithm.AbstractAlgorithm;
import algorithm.pathfinder.Pathfinder;
import algorithm.search.Search;
import javafx.scene.paint.Color;
import model.*;

import java.util.Collections;

public abstract class AbstractSort extends AbstractAlgorithm implements Sort {
    protected BarsModel model;
    protected Bar currentBar;

    @Override
    public void setModel(VisualiserModel model) {
        this.model = model.asBarsModel();
    }

    @Override
    public boolean canPlay() {
        return true;
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
            bar.setFill(Color.BLACK);
//            if(bar.isVisited()) bar.setFill(Color.LIGHTSEAGREEN);
            if(bar == currentBar) bar.setFill(Color.RED);
        }
    }
    public void showResult() {
//        for (int i = 0 ; i < BarsModel.NUM_OF_BARS; i++) {
//            Bar bar = model.getElements().get(i);
//            bar.setX(i * BarsModel.getWidthOfBar() + Bar.PADDING);
//            bar.setFill(Color.NAVAJOWHITE);
//        }
    }
    public boolean isSort() {
        return true;
    }

    public boolean isSearch() {
        return false;
    }

    public boolean isPathfinder() {
        return false;
    }

    public Pathfinder asPathfinder() {
        return null;
    }

    public Sort asSort() {
        return this;
    }

    public Search asSearch() {
        return null;
    }
}
