package algorithm;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public abstract class AbstractAlgorithm implements Algorithm {
    private DoubleProperty timePerFrame = new SimpleDoubleProperty(DEFAULT_TIME_PER_FRAME);
    private Timeline timeline;
    private EventHandler<ActionEvent> animationHandler = e -> {
        if (hasNext()) {
            doStep();
            visualise();
        } else {
            timeline.stop();
            showResult();
        }
    };

    @Override
    public Timeline getAnimation() {
        timeline = new Timeline();
//        var animationHandler =

        var kf = new KeyFrame(Duration.millis(timePerFrame.get()), animationHandler);

        timeline.getKeyFrames().add(kf);

        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;


    }

    public DoubleProperty timePerFrameProperty() {
        return timePerFrame;
    }

    @Override
    public void changeTime(double time) {
        if(timeline == null)
            return;
        timeline.stop();
        var kf = new KeyFrame(
                Duration.millis(time), //TODO: Use slider for time
                animationHandler);
        timeline.getKeyFrames().setAll(kf);
        timeline.play();
    }
}
