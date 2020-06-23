package algorithm.sort;

import model.BarsModel;


public class MergeSort extends AbstractSort {
    private int size = 1;
    private int lower = 0;


    @Override
    public void initialiseStep() {
    }

    @Override
    public boolean hasNext() {
        return size <= Sort.NUM_OF_BARS - 1;
    }

    @Override
    public void doStep() {

        var mid = Math.min(lower + size, Sort.NUM_OF_BARS);
        var upper = Math.min(lower + 2 * size, Sort.NUM_OF_BARS);

        merge(lower, mid, upper);

        lower += 2 * size;

        if (lower >= Sort.NUM_OF_BARS - 1) {
            size *= 2;
            lower = 0;
        }
    }


    private void merge(int l, int m, int u) {
        var elements = model.getElements();
        // Initialise right,left arrays with values of each bar
        var right = new int[u - m];
        var left = new int[m - l];
        for (int i = 0; i < u - m; i++)
            right[i] = elements.get(m + i).getValue();
        for (int i = 0; i < m - l; i++)
            left[i] = elements.get(l + i).getValue();

        // Merge the two arrays into the list
        int rIndex = 0;
        int lIndex = 0;
        int elemIndex;
        for (elemIndex = l; rIndex < u - m && lIndex < m - l; elemIndex++) {
            var leftVal = left[lIndex];
            var rightVal = right[rIndex];

            if (leftVal <= rightVal) {
                elements.get(elemIndex).setValue(leftVal);
                lIndex++;
            } else {
                elements.get(elemIndex).setValue(rightVal);
                rIndex++;
            }
        }

        // Copy remaining values if any
        for (int i = rIndex; i < u - m; i++) {
            elements.get(elemIndex++).setValue(right[i]);
        }
        for (int i = lIndex; i < m - l; i++) {
            elements.get(elemIndex++).setValue(left[i]);
        }

        currentBar = elements.get(elemIndex - 1);
    }
}
