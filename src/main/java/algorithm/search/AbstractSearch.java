package algorithm.search;

import algorithm.AbstractAlgorithm;
import algorithm.pathfinder.Pathfinder;
import algorithm.sort.Sort;
import javafx.scene.paint.Color;
import model.*;

import java.util.Collections;

public abstract class AbstractSearch extends AbstractAlgorithm implements Search {
    protected BarsModel model;
    protected Bar currentBar;
    protected Bar searchBar = null;

    @Override
    public void setModel(VisualiserModel model) {
        this.model = model.asBarsModel();

        for (Bar bar : model.asBarsModel().getElements()) {
            bar.setHeightScale(2);
            bar.setOnMouseClicked(e -> {
                searchBar = bar;
                visualise();
            });
        }


        visualise();
    }


    @Override
    public boolean canPlay() {
        return searchBar != null;
    }



    /**
     * Use the current state of the algorithm to visualise the current state
     */
    public void visualise() {
        for (int i = 0 ; i < Search.NUM_OF_BARS; i++) {
            var bar = model.getElements().get(i);
            bar.setWidth(BarsModel.getWidthOfBar(Search.NUM_OF_BARS));
            bar.setX(i * BarsModel.getWidthOfBar(Search.NUM_OF_BARS) + Bar.PADDING);
            bar.setFill(Color.BLACK);
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

    public boolean isPathfinder() {
        return false;
    }

    public Pathfinder asPathfinder() {
        return null;
    }

    public Sort asSort() {
        return null;
    }

    public Search asSearch() {
        return this;
    }
}
