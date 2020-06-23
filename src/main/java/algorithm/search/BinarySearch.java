package algorithm.search;

public class BinarySearch extends AbstractSearch {
    int lo = 0;
    int hi = NUM_OF_BARS;
    @Override
    public void initialiseStep() {
        currentBar = model.getElements().get(getMid());
        currentBar.setVisited();
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
        currentBar.setVisited();
    }

    private int getMid() {
        return (hi + lo) / 2;
    }

}
