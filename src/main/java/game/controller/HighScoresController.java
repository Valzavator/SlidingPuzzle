package game.controller;

import game.model.highScores.HighScoreUnit;
import game.model.highScores.HighScoresModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class HighScoresController {
    private Stage highScoresStage;
    private HighScoresModel highScoresModel;

    @FXML
    private TableView<HighScoreUnit> highScoresTable;

    @FXML
    private TableColumn<HighScoreUnit, String> nameColumn;

    @FXML
    private TableColumn<HighScoreUnit, String> numberOfClickColumn;

    @FXML
    private TableColumn<HighScoreUnit, String> timeColumn;

    @FXML
    private TableColumn<HighScoreUnit, String> difficultyColumn;

    public HighScoresController() {
        highScoresModel = HighScoresModel.getInstance();
    }

    @FXML
    private void initialize() {
        highScoresTable.setItems(highScoresModel.getHighScoresData());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        numberOfClickColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfClickProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        difficultyColumn.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty());
    }

    @FXML
    private void closeTableButtonAction() {
        highScoresStage.close();
    }

    @FXML
    private void resetTableButtonAction() {
        highScoresModel.reset();
        highScoresModel.saveToFile();
    }

    public void setHighScoresStage(Stage highScoresStage) {
        this.highScoresStage = highScoresStage;
    }
}
