package algorithm.search;

public class LinearSearch extends AbstractSearch {
    int index = 0;
    @Override
    public void initialiseStep() {
        return;
    }

    @Override
    public void doStep() {
        currentBar = model.getElements().get(index++);
        currentBar.setVisited();
    }

}
