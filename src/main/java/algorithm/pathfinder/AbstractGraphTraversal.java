package algorithm.pathfinder;

public abstract class AbstractGraphTraversal extends AbstractPathfinder {

    @Override
    public boolean canPlay() {
        return startIsSet() && endIsSet() && mazeGeneratedProperty().get();
    }

    @Override
    public boolean isGraphTraversal() {
        return true;
    }

}
