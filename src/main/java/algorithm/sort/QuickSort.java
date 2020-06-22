package algorithm.sort;

import model.Bar;
import model.BarsModel;

import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class QuickSort extends AbstractSort {
    Stack<Integer> stack = new Stack<>();

    @Override
    public void initialiseStep() {
        stack.push(0);
        stack.push(BarsModel.NUM_OF_BARS - 1);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public void doStep() {
        var hi = stack.pop();
        var lo = stack.pop();

        var pivot = partition(lo, hi);

        if (pivot - 1 > lo) {
            stack.push(lo);
            stack.push(pivot - 1);
        }

        if (pivot + 1 < hi) {
            stack.push(pivot + 1);
            stack.push(hi);
        }

        currentBar = model.getElements().get(pivot);
    }

    private int partition(int lo, int hi) {
        var elements = model.getElements();
        var pivot = elements.get(hi);
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (elements.get(j).getValue() < pivot.getValue()) {
                i++;
                Collections.swap(elements, i, j);
            }
        }

        Collections.swap(elements, hi, i + 1);
        return i + 1;
    }
}
