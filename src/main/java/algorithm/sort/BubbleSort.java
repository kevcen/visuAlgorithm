package algorithm.sort;

import java.util.Collections;

public class BubbleSort extends AbstractSort {
    boolean sorted = false;


    @Override
    public void initialiseStep() {
    }

    @Override
    public boolean hasNext() {
        return !sorted;
    }

    @Override
    public void doStep() {
        boolean swapped = false;
        var elements = model.getElements();
        for (int i = 1; i <= Sort.NUM_OF_BARS; i++) {

            if ( i == Sort.NUM_OF_BARS || elements.get(i).isVisited()) {
                elements.get(i-1).setVisited();
                break;
            } else if (elements.get(i - 1).getValue() > elements.get(i).getValue()) {
                Collections.swap(model.getElements(), i - 1, i);
                swapped = true;
            }
            currentBar = elements.get(i);
        }
        if(!swapped) sorted = true;
    }
}
