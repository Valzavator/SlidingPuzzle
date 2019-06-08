package game.model.gameModel;

import game.button.ButtonPrototype;

public class GameIsSuspended extends GameState {
    GameIsSuspended(GameModel context) {
        super(context);
    }

    @Override
    public boolean start() {
        return false;
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
        gameModel.getTimer().start();
        gameModel.setGameState(gameModel.getGameIsStarted());
        return false;
    }

    @Override
    public void finish() {
    }
}
