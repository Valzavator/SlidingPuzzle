package game.model.gameModel;

import game.button.ButtonPrototype;
import game.model.SettingsModel;

public class GameIsStarted extends GameState {
    GameIsStarted(GameModel context) {
        super(context);
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean switchButton(ButtonPrototype pressedButton) {
        int switchCounter = gameModel.getGameController().switchButton(pressedButton);
        if (switchCounter > 0) {
            if (SettingsModel.getInstance().getHardmode())
                gameModel.setNewRandomImage();
            gameModel.setUndoCommandCounter(gameModel.getUndoCommandCounter() + 1);
            gameModel.incClickCounter(switchCounter);
            gameModel.getGameController().highlightButton(pressedButton);
            if (gameModel.getGameController().checkSolution()) {
                gameModel.setGameState(gameModel.getGameIsOver());
                gameModel.finishGame();
                gameModel.notifyObservers();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean undoSwitchButton() {
        int switchCounter = gameModel.getGameController().undoSwitchButton();
        if (switchCounter > 0) {
            gameModel.setRedoCommandCounter(gameModel.getRedoCommandCounter() + 1);
            gameModel.setUndoCommandCounter(gameModel.getUndoCommandCounter() - 1);
            gameModel.incClickCounter(switchCounter);
            gameModel.notifyObservers();
        }
        return true;
    }

    @Override
    public boolean redoSwitchButton() {
        int switchCounter = gameModel.getGameController().redoSwitchButton();
        if (switchCounter > 0) {
            gameModel.setUndoCommandCounter(gameModel.getUndoCommandCounter() + 1);
            gameModel.setRedoCommandCounter(gameModel.getRedoCommandCounter() - 1);
            gameModel.incClickCounter(switchCounter);
            gameModel.notifyObservers();
        }
        return true;
    }

    @Override
    public boolean pause() {
        gameModel.getTimer().stop();
        gameModel.setGameState(gameModel.getGameIsSuspended());
        return true;
    }

    @Override
    public void finish() {
    }
}
