package game.controller;

import game.App;
import game.board.GameBoard;
import game.board.ImageBoard;
import game.button.ButtonPrototype;
import game.enumeration.BoardType;
import game.helpers.ImageProcessing;
import game.model.SettingsModel;
import game.model.gameModel.GameModel;
import game.model.IObserver;
import game.model.highScores.HighScoreUnit;
import game.model.highScores.HighScoresModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Objects;
import java.util.Optional;


public class MainController implements IObserver {

    private App mainApp;
    private GameModel gameModel;
    private boolean isPause;

    @FXML
    GridPane gamePane;

    @FXML
    Label countLabel;

    @FXML
    Label timeLabel;

    @FXML
    Button undoButton;

    @FXML
    Button redoButton;

    @FXML
    Button pauseButton;

    @FXML
    MenuItem newRandomImageButton;

    public MainController() {
        gameModel = new GameModel();
        gameModel.subscribe(this);
        isPause = false;
    }

    @FXML
    private void initialize() {
        newRandomImageButton.setVisible(gameModel.getGameBoard() instanceof ImageBoard);
        countLabel.textProperty().bind(gameModel.clickCounterProperty());
        timeLabel.textProperty().bind(gameModel.timeCounterProperty());
        initGridPane(SettingsModel.getInstance().getDifficulty().getColumns(), SettingsModel.getInstance().getDifficulty().getRows());
    }

    @FXML
    private void closeButtonAction() {
        this.mainApp.getPrimaryStage().close();
    }

    @FXML
    private void restartButtonAction() {
        if (isPause) {
            pauseAction();
            isPause = false;
        }
        gameModel.restartGame();
        ((BorderPane) mainApp.getPrimaryStage().getScene().getRoot()).setCenter(gamePane);
        redrawGridPane(gameModel.getGameBoard());
        pauseButton.setDisable(true);
        newRandomImageButton.setDisable(false);
    }

    @FXML
    private void undoButtonAction() {
        gameModel.undoSwitchButton();
        redrawGridPane(gameModel.getGameBoard());
        redoButton.setDisable(gameModel.getRedoCommandCounter() == 0);
    }

    @FXML
    private void redoButtonAction() {
        gameModel.redoSwitchButton();
        redrawGridPane(gameModel.getGameBoard());
        undoButton.setDisable(gameModel.getUndoCommandCounter() == 0);
    }

    @FXML
    private void newRandomImageButtonAction() {
        gameModel.setNewRandomImage();
    }

    @FXML
    private void settingsButtonAction() {
        if (!isPause)
            pauseAction();
        mainApp.showSettingsDialog();
    }

    @FXML
    private void highScoresButtonAction() {
        if (!isPause)
            pauseAction();
        mainApp.showHighScores();
    }

    @FXML
    private void aboutButtonAction() {
        if (!isPause)
            pauseAction();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Game \"Sliding Puzzle\"\nCopyright Maksym Svynarchuk, 2018\n");
        alert.showAndWait();
    }

    @FXML
    private void pauseAction() {
        if (gameModel.pause()) {
            undoButton.setDisable(true);
            redoButton.setDisable(true);
            newRandomImageButton.setDisable(true);
            pauseButton.setText("Continue");
            ((BorderPane) mainApp.getPrimaryStage().getScene().getRoot()).setCenter(
                    new ImageView(ImageProcessing.loadImage("/images/pause.png")));
            isPause = true;
        } else if (isPause) {
            checkUndoRedo();
            pauseButton.setText("Pause");
            ((BorderPane) mainApp.getPrimaryStage().getScene().getRoot()).setCenter(gamePane);
            redrawGridPane(gameModel.getGameBoard());
            isPause = false;
            newRandomImageButton.setDisable(false);
        }
    }

    @FXML
    private void createNewHighScoreUnit() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("High-scores");
        dialog.setHeaderText("Congratulations!!!");

        ButtonType OKButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(OKButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Enter your name");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);

        Node OKButton = dialog.getDialogPane().lookupButton(OKButtonType);
        OKButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            OKButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(name::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == OKButtonType) {
                return name.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(username -> {
            HighScoresModel.getInstance().addUnit(new HighScoreUnit(username,
                    gameModel.clickCounterProperty().getValue(),
                    gameModel.timeCounterProperty().getValue(),
                    String.format("%s - %s",
                            SettingsModel.getInstance().getDifficulty(),
                            SettingsModel.getInstance().getBoardType() == BoardType.ImageBoard
                                    ? "Image" : "Numbers")));
        });
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    private void initGridPane(int numberOfColumns, int numberOfRows) {
        gamePane.getColumnConstraints().removeIf(Objects::nonNull);
        gamePane.getRowConstraints().removeIf(Objects::nonNull);
        for (int i = 0; i < numberOfColumns; i++) {
            gamePane.getColumnConstraints().add(new ColumnConstraints(gamePane.getPrefWidth() / numberOfColumns));
        }
        for (int i = 0; i < numberOfRows; i++) {
            gamePane.getRowConstraints().add(new RowConstraints(gamePane.getPrefHeight() / numberOfRows));
        }
        initButtons(gameModel.getGameBoard());
        redrawGridPane(gameModel.getGameBoard());
    }

    private void redrawGridPane(GameBoard gameBoard) {
        gamePane.getChildren().removeIf(Objects::nonNull);
        for (int i = 0; i < gameBoard.size(); i++) {
            ButtonPrototype currButton = gameBoard.getButton(i);
            gamePane.add(currButton,
                    i % SettingsModel.getInstance().getDifficulty().getColumns(),
                    i / SettingsModel.getInstance().getDifficulty().getRows());
        }
    }

    private void initButtons(GameBoard gameBoard) {
        for (int i = 0; i < gameBoard.size(); i++) {
            ButtonPrototype currButton = gameBoard.getButton(i);
            currButton.setPrefSize(
                    gamePane.getPrefHeight() / gameBoard.getColumns(),
                    gamePane.getPrefHeight() / gameBoard.getRows()
            );
            currButton.setOnMousePressed(event -> {
                ButtonPrototype pressedButton = (ButtonPrototype) event.getSource();
                if (gameModel.switchButton(pressedButton)) {
                    redrawGridPane(gameBoard);
                }
                checkUndoRedo();
            });
        }
    }

    private void checkUndoRedo() {
        boolean redoBtnIsDisable = gameModel.getRedoCommandCounter() == 0;
        boolean undoBtnIsDisable = gameModel.getUndoCommandCounter() == 0;
        redoButton.setDisable(redoBtnIsDisable);
        undoButton.setDisable(undoBtnIsDisable);
    }

    private void finishGame() {
        if (gameModel.getGameBoard() instanceof ImageBoard) {
            newRandomImageButton.setDisable(true);
            ((BorderPane) mainApp.getPrimaryStage().getScene().getRoot()).setCenter(
                    new ImageView(((ImageBoard) gameModel.getGameBoard()).getBoardImage())
            );
        } else {
            ((BorderPane) mainApp.getPrimaryStage().getScene().getRoot()).setCenter(
                    new ImageView(ImageProcessing.loadImage("/images/winner.jpg"))
            );
        }
        pauseButton.setDisable(true);
        createNewHighScoreUnit();
    }

    @Override
    public void update() {
        if (gameModel.gameIsStarted())
            pauseButton.setDisable(false);
        if (gameModel.isGameOver())
            finishGame();
        if (gameModel.reloadWithNewSettings()) {
            if (isPause)
                pauseAction();
            initGridPane(SettingsModel.getInstance().getDifficulty().getColumns(),
                    SettingsModel.getInstance().getDifficulty().getRows());
            ((BorderPane) mainApp.getPrimaryStage().getScene().getRoot()).setCenter(gamePane);
            gameModel.cancelReload();
            newRandomImageButton.setVisible(gameModel.getGameBoard() instanceof ImageBoard);
        }
        checkUndoRedo();
    }

}