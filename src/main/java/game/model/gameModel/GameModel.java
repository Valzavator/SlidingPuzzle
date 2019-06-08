package game.model.gameModel;

import game.board.GameBoard;
import game.board.GameBoardCreator;
import game.board.ImageBoard;
import game.button.ButtonPrototype;
import game.gameContoller.GameController;
import game.helpers.RandomImage;
import game.helpers.Timer;
import game.model.IObserver;
import game.model.SettingsModel;
import game.model.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class GameModel extends Subject implements IObserver {
    private GameBoardCreator gameBoardCreator;
    private GameBoard gameBoard;
    private GameController gameController;
    private Timer timer;

    private final StringProperty clickCounter;
    private final StringProperty timeCounter;
    private int undoCommandCounter;
    private int redoCommandCounter;

    private boolean reloadWithNewSettings;

    private GameState gameState;
    private GameState gameIsWaiting = new GameIsWaiting(this);
    private GameState gameIsStarted = new GameIsStarted(this);
    private GameState gameIsOver = new GameIsOver(this);
    private GameState gameIsSuspended = new GameIsSuspended(this);

    public GameModel() {
        SettingsModel.getInstance().subscribe(this);
        this.gameBoardCreator = new GameBoardCreator();

        this.gameState = gameIsWaiting;
        this.clickCounter = new SimpleStringProperty("0");
        this.timeCounter = new SimpleStringProperty("0");
        this.timer = new Timer(this.timeCounter);
        this.reloadWithNewSettings = false;
        this.gameBoard = gameBoardCreator.createGameBoard(SettingsModel.getInstance().getBoardType(),
                SettingsModel.getInstance().getDifficulty());
        this.gameBoard.init();
        this.gameController = new GameController(
                gameBoard,
                SettingsModel.getInstance().getHighlightButtons(),
                SettingsModel.getInstance().getMultiSwitch()
        );
    }

    GameState getGameIsWaiting() {
        return gameIsWaiting;
    }

    GameState getGameIsStarted() {
        return gameIsStarted;
    }

    GameState getGameIsOver() {
        return gameIsOver;
    }

    GameState getGameIsSuspended() {
        return gameIsSuspended;
    }

    void setUndoCommandCounter(int undoCommandCounter) {
        this.undoCommandCounter = undoCommandCounter;
    }

    void setRedoCommandCounter(int redoCommandCounter) {
        this.redoCommandCounter = redoCommandCounter;
    }

    void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean switchButton(ButtonPrototype pressedButton) {
        gameState.start();
        return gameState.switchButton(pressedButton);
    }

    public void undoSwitchButton() {
        gameState.undoSwitchButton();
    }

    public void redoSwitchButton() {
        gameState.redoSwitchButton();
    }

    public boolean pause() {
        return gameState.pause();
    }

    void finishGame() {
        gameState.finish();
    }

    public void setNewRandomImage() {
        if (gameBoard instanceof ImageBoard) {
            ((ImageBoard) this.gameBoard).changeImage(RandomImage.getRandomImage());
            gameController.highlightAllButtons();
        }
    }

    @Override
    public void update() {
        reloadWithNewSettings = true;
        restartGame();
    }

    public void restartGame() {
        if (reloadWithNewSettings) {
            gameBoard = gameBoardCreator.createGameBoard(SettingsModel.getInstance().getBoardType(),
                    SettingsModel.getInstance().getDifficulty());
            this.gameController = new GameController(
                    gameBoard,
                    SettingsModel.getInstance().getHighlightButtons(),
                    SettingsModel.getInstance().getMultiSwitch()
            );
            gameBoard.init();
        } else {
            gameBoard.shuffleGameBoard();
            if (gameBoard instanceof ImageBoard) {
                ((ImageBoard) gameBoard).changeImage(RandomImage.getRandomImage());
            }
            gameController.reset();
        }
        gameState = gameIsWaiting;
        timer.reset();
        clickCounter.setValue("0");
        undoCommandCounter = 0;
        redoCommandCounter = 0;
        gameController.highlightAllButtons();
        notifyObservers();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public boolean isGameOver() {
        return gameState == gameIsOver;
    }

    public boolean gameIsStarted() {
        return gameState == gameIsStarted;
    }

    public boolean reloadWithNewSettings() {
        return reloadWithNewSettings;
    }

    public void cancelReload() {
        this.reloadWithNewSettings = false;
    }

    public StringProperty clickCounterProperty() {
        return clickCounter;
    }

    public StringProperty timeCounterProperty() {
        return timeCounter;
    }

    public int getUndoCommandCounter() {
        return undoCommandCounter;
    }

    public int getRedoCommandCounter() {
        return redoCommandCounter;
    }

    GameController getGameController() {
        return gameController;
    }

    Timer getTimer() {
        return timer;
    }

    void incClickCounter(int switchCounter) {
        clickCounter.set(String.valueOf(Integer.parseInt(clickCounter.getValue()) + switchCounter));
    }
}
