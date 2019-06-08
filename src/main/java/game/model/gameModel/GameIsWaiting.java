package game.model.gameModel;

import game.button.ButtonPrototype;

public class GameIsWaiting extends GameState {
    public GameIsWaiting(GameModel context) {
        super(context);
    }

    @Override
    public boolean start() {
        gameModel.getTimer().start();
        gameModel.setGameState(gameModel.getGameIsStarted());
        gameModel.notifyObservers();
        return true;
    }

    @Override
    public boolean switchButton(ButtonPrototype pressedButton) {
        return false;
    }

    @Override
    public boolean undoSwitchButton() {
        return false;
    }

    @Override
    public boolean redoSwitchButton() {
        return false;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public void finish() {}
}
