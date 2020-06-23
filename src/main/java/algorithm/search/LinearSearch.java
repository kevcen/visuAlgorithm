package algorithm.search;

public class LinearSearch extends AbstractSearch {
    int index = 0;
    @Override
    public void initialiseStep() {
    }

    @Override
    public boolean hasNext() {
        return currentBar != searchBar;
    }

    @Override
    public void doStep() {
        currentBar = model.getElements().get(index++);
    }

    @Override
    public void showResult() {

    }
}
