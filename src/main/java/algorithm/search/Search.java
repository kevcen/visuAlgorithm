package algorithm.search;

import algorithm.Algorithm;
import javafx.beans.property.BooleanProperty;
import javafx.scene.text.Text;
import model.Bar;

public interface Search extends Algorithm {
    int NUM_OF_BARS = 250;
    void setBarEventHandlers(Bar bar, BooleanProperty playing, Text statusText);
}
