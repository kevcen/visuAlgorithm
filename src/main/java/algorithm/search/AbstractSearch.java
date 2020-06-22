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
    protected int searchValue = -1;

    @Override
    public void setModel(VisualiserModel model) {
        this.model = model.asBarsModel();
    }


    @Override
    public boolean canPlay() {
        return searchValue > 0 && searchValue <= BarsModel.NUM_OF_BARS;
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
//            if(bar == searchBar) bar.setEnd();
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

    public Pathfinder asPathfind() {
        return null;
    }

    public Sort asSort() {
        return null;
    }

    public Search asSearch() {
        return this;
    }
}
