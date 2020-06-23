package algorithm.sort;

import model.Bar;
import model.BarsModel;

import java.util.Collections;
import java.util.List;

public class InsertionSort extends AbstractSort {
    private int index = 1;


    @Override
    public void initialiseStep() {
        model.getElements().get(0).setVisited();
    }

    @Override
    public boolean hasNext() {
        return index < Sort.NUM_OF_BARS;
    }

    @Override
    public void doStep() {
        var j = index;
        var elements = model.getElements();
        while (j > 0 && elements.get(j - 1).getValue() > elements.get(j).getValue()){
            Collections.swap(elements, j, j - 1);
            j--;
        }
        elements.get(j).setVisited();
        currentBar = elements.get(j);
        index++;
    }
}
