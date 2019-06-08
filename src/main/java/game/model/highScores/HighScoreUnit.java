package game.model.highScores;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HighScoreUnit {
    private final StringProperty name;
    private final StringProperty numberOfClick;
    private final StringProperty time;
    private final StringProperty difficulty;

    public HighScoreUnit() {
        this.name = new SimpleStringProperty("");
        this.numberOfClick = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");
        this.difficulty = new SimpleStringProperty("");
    }

    public HighScoreUnit(String name, String numberOfClick, String time, String difficulty) {
        this.name = new SimpleStringProperty(name);
        this.numberOfClick = new SimpleStringProperty(numberOfClick);
        this.time = new SimpleStringProperty(time);
        this.difficulty = new SimpleStringProperty(difficulty);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setNumberOfClick(String numberOfClick) {
        this.numberOfClick.set(numberOfClick);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.set(difficulty);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getNumberOfClick() {
        return numberOfClick.get();
    }

    public StringProperty numberOfClickProperty() {
        return numberOfClick;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }

}
