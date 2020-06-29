package algorithm.sort;

import algorithm.AbstractAlgorithm;
import algorithm.mazegenerator.MazeGenerator;
import algorithm.pathfinder.Pathfinder;
import algorithm.search.Search;
import javafx.scene.paint.Color;
import model.Bar;
import model.BarsModel;
import model.VisualiserModel;

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


    /**
     * Use the current state of the algorithm to visualise the current state
     */
    public void visualise() {
        for (int i = 0 ; i < Sort.NUM_OF_BARS; i++) {
            var bar = model.getElements().get(i);
            bar.setWidth(BarsModel.getWidthOfBar(Sort.NUM_OF_BARS));
            bar.setX(i * BarsModel.getWidthOfBar(Sort.NUM_OF_BARS) + Bar.PADDING);
            bar.resetStyle();
            if(bar == currentBar) bar.setCurrent();
        }
    }
    public void showResult() {
        for (int i = 0 ; i < Sort.NUM_OF_BARS; i++) {
            Bar bar = model.getElements().get(i);
            bar.setX(i * BarsModel.getWidthOfBar(Sort.NUM_OF_BARS) + Bar.PADDING);
            bar.setResult();
        }
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

    public boolean isMazeGenerator() {return false;}

    public Pathfinder asPathfinder() {
        return null;
    }

    public Sort asSort() {
        return this;
    }

    public Search asSearch() {
        return null;
    }

    public MazeGenerator asMazeGenerator() {return null;}
}
