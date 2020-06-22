package algorithm;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class AbstractAlgorithm implements Algorithm {
    @Override
    public Timeline getAnimation() {
        var timeline = new Timeline();
        var kf = new KeyFrame(
                Duration.millis(Algorithm.TIME_PER_FRAME), //TODO: Use slider for time
                e -> {
                    if (hasNext()) {
                        doStep();
                        visualise();
                    } else {
                        timeline.stop();
                        showResult();
                    }
                });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }


}
