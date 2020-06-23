package algorithm.search;

import model.Bar;

public class BinarySearch extends AbstractSearch {
    int lo = 0;
    int hi = NUM_OF_BARS;
    @Override
    public void initialiseStep() {
        currentBar = model.getElements().get(getMid());
    }

    @Override
    public boolean hasNext() {
        return currentBar != searchBar;
    }

    @Override
    public void doStep() {
        int compare = currentBar.compareTo(searchBar);
        assert (compare != 0); // hasNext check is done first
        if (compare > 0) {
            hi = currentBar.getValue() - 1;
        } else {
            lo = currentBar.getValue() + 1;
        }
        currentBar = model.getElements().get(getMid());
    }

    private int getMid() {
        return (hi + lo) / 2;
    }
    @Override
    public void showResult() {

    }
}
