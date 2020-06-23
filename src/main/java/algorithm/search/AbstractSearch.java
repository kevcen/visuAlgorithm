package algorithm.search;

import algorithm.AbstractAlgorithm;
import algorithm.pathfinder.Pathfinder;
import algorithm.sort.Sort;
import javafx.scene.paint.Color;
import model.Bar;
import model.BarsModel;
import model.VisualiserModel;

public abstract class AbstractSearch extends AbstractAlgorithm implements Search {
    protected BarsModel model;
    protected Bar currentBar;
    protected Bar searchBar = null;

    @Override
    public void setModel(VisualiserModel model) {
        this.model = model.asBarsModel();

        // Select search bar
        for (Bar bar : model.asBarsModel().getElements()) {
            bar.setHeightScale(2);
            bar.setOnMouseClicked(e -> {
                searchBar = bar;
                visualise();
            });
        }
    }

    @Override
    public boolean hasNext() {
        return currentBar != searchBar;
    }

    @Override
    public boolean canPlay() {
        return searchBar != null;
    }

    @Override
    public void showResult() {
        searchBar.setFill(Color.NAVAJOWHITE);
    }

    public void visualise() {
        for (int i = 0 ; i < Search.NUM_OF_BARS; i++) {
            var bar = model.getElements().get(i);
            // Adjust the width (used in first time visualise)
            bar.setWidth(BarsModel.getWidthOfBar(Search.NUM_OF_BARS));
            // Set the X coordinate of the bars (used when shuffled)
            bar.setX(i * BarsModel.getWidthOfBar(Search.NUM_OF_BARS) + Bar.PADDING);

            // Set the colours
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
