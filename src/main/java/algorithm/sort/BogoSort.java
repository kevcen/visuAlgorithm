package algorithm.sort;

import java.util.Collections;

public class BogoSort extends AbstractSort {
  @Override
  public void initialiseStep() {
    return;
  }

  @Override
  public boolean hasNext() {
    return !isSorted();
  }

  @Override
  public void doStep() {
    Collections.shuffle(model.asBarsModel().getElements());
  }

  private boolean isSorted() {
    for (int i = 1; i < NUM_OF_BARS; i++) {
      if (model.asBarsModel().getElements().get(i).getValue()
          < model.asBarsModel().getElements().get(i - 1).getValue()) {
        return false;
      }
    }
    return true;
  }
}
