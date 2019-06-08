package game.helpers;

import javafx.animation.AnimationTimer;
import javafx.beans.property.StringProperty;

public class Timer {
    private AnimationTimer timer;
    private long lastTime;
    private boolean timerIsStarted;
    private final long ONE_SECOND = 1000000000;

    private final StringProperty timeCounter;

    public Timer(StringProperty timeCounter) {
        this.timeCounter = timeCounter;
        this.timerIsStarted = false;
        this.lastTime = 0;
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTime > ONE_SECOND) {
                    timeCounter.set(String.valueOf(Integer.parseInt(timeCounter.getValue()) + 1));
                    lastTime = now;
                }
            }
        };
    }

    public String getTimeCounter() {
        return timeCounter.get();
    }

    public StringProperty timeCounterProperty() {
        return timeCounter;
    }

    public void setTimeCounter(String timeCounter) {
        this.timeCounter.set(timeCounter);
    }

    public void start() {
        if (!timerIsStarted) {
            timer.start();
            timerIsStarted = true;
        }
    }

    public void stop() {
        if (timerIsStarted) {
            timer.stop();
            timerIsStarted = false;
        }
    }

    public void reset() {
        stop();
        timeCounter.setValue("0");
    }
}
