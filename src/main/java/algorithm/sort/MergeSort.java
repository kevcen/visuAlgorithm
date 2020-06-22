package algorithm.sort;

import model.BarsModel;


public class MergeSort extends AbstractSort {
    private int size = 1;


    @Override
    public void initialiseStep() {
    }

    @Override
    public boolean hasNext() {
        return size <= BarsModel.NUM_OF_BARS - 1;
    }

    private void printArray(int[] arr, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
    }

    private void printElements() {
        for (int i = 0; i < BarsModel.NUM_OF_BARS; i++) {
            System.out.print(model.getElements().get(i).getValue() + ", ");
        }
        System.out.println();
    }

    @Override
    public void doStep() {
        for (int lower = 0; lower < BarsModel.NUM_OF_BARS - 1; lower += 2 * size) {
            System.out.println(lower);
            int mid = Math.min(lower + size, BarsModel.NUM_OF_BARS);

            int upper = Math.min(lower + 2 * size, BarsModel.NUM_OF_BARS);

            merge(lower, mid, upper);
            printElements();
        }

        size *= 2;
    }

    /**
     * Merges the two sublists elements[l..m) and elements[m..u) together
     *
     * @param l
     * @param m
     * @param u
     */
    private void merge(int l, int m, int u) {
        // Initialise right,left arrays with values of each bar
        int[] right = new int[u - m];
        int[] left = new int[m - l];
        for (int i = 0; i < u - m; i++)
            right[i] = model.getElements().get(m + i).getValue();
        for (int i = 0; i < m - l; i++)
            left[i] = model.getElements().get(l + i).getValue();

        // Merge the two arrays into the list
        int rIndex = 0;
        int lIndex = 0;
        int elemIndex;
        for (elemIndex = l; rIndex < u - m && lIndex < m - l; elemIndex++) {
            int leftVal = left[lIndex];
            int rightVal = right[rIndex];

            if (leftVal <= rightVal) {
                model.getElements().get(elemIndex).setValue(leftVal);
                lIndex++;
            } else {
                model.getElements().get(elemIndex).setValue(rightVal);
                rIndex++;
            }
        }

        // Copy remaining values if any
        for (int i = rIndex; i < u - m; i++) {
            model.getElements().get(elemIndex++).setValue(right[i]);
        }
        for (int i = lIndex; i < m - l; i++) {
            model.getElements().get(elemIndex++).setValue(left[i]);
        }

        currentBar = model.getElements().get(elemIndex - 1);
    }
}
