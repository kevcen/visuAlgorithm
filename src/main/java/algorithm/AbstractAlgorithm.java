package algorithm;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public abstract class AbstractAlgorithm implements Algorithm {
    private DoubleProperty timePerFrame = new SimpleDoubleProperty(DEFAULT_TIME_PER_FRAME);
    private Timeline timeline;
    private BooleanProperty finished;
    private EventHandler<ActionEvent> animationHandler = e -> {
        if (hasNext()) {
            doStep();
            visualise();
        } else {
            timeline.stop();
            showResult();
            finished.set(true);
        }
    };

    @Override
    public void doAlgorithm() {
        initialiseStep();

        while (hasNext())
            doStep();
    }

    @Override
    public Timeline getAnimation(BooleanProperty finished) {
        this.finished = finished;
        timeline = new Timeline();

        var kf = new KeyFrame(Duration.millis(timePerFrame.get()), animationHandler);

        timeline.getKeyFrames().add(kf);

        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;

    }

    public DoubleProperty timePerFrameProperty() {
        return timePerFrame;
    }

    @Override
    public Timeline changeTime(boolean playing) {
        if(timeline == null)
            return null;
        timeline.stop();
        var kf = new KeyFrame(
                Duration.millis(timePerFrame.get()), animationHandler);
        timeline.getKeyFrames().setAll(kf);
        if(playing) timeline.play();
        return timeline;
    }
}
