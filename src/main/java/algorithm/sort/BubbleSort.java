package algorithm.sort;

import model.Bar;
import model.BarsModel;

import java.util.Collections;
import java.util.List;

public class BubbleSort extends AbstractSort {
    boolean sorted = false;


    @Override
    public void initialiseStep() {
        var lastBar = model.getElements().get(BarsModel.NUM_OF_BARS - 1);
        lastBar.setVisited();
    }

    @Override
    public boolean hasNext() {
        return !sorted;
    }

    @Override
    public void doStep() {
        boolean swapped = false;
        var elements = model.getElements();
        for (int i = 1; i < BarsModel.NUM_OF_BARS; i++) {
            currentBar = elements.get(i);
            if (elements.get(i).isVisited()) {
                elements.get(i-1).setVisited();
                break;
            } else if (elements.get(i - 1).getValue() > elements.get(i).getValue()) {
                Collections.swap(model.getElements(), i - 1, i);
                swapped = true;
            }
        }
        if(!swapped) sorted = true;
    }
}
